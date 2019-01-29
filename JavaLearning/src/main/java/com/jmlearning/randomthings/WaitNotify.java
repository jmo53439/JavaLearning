package com.jmlearning.randomthings;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WaitNotify {

    private static final Logger log = Logger.getLogger(WaitNotify.class.getName());

    public static void main(String[] args) {

        runLight();
        runHeavy();
    }

    private static void runLight() {

        log.info("[Consumer With Light CPU Load]");

        Queue<String> queue = new LinkedList <>();
        Thread producer = new Thread(new ProducerThread(10, queue), "Producer-LightTest");
        Thread consumerA = new Thread(new ConsumerThreadLight("Consumer A", queue), "Consumer A");
        Thread consumerB = new Thread(new ConsumerThreadLight("Consumer B", queue), "Consumber B");

        consumerA.setDaemon(true);
        consumerB.setDaemon(true);

        producer.start();
        consumerA.start();
        consumerB.start();

        try {

            producer.join();
        }
        catch(InterruptedException e) {

            log.log(Level.WARNING, "Interrupted Waiting Producer to Join", e);
        }
    }

    private static void runHeavy() {

        log.info("Consumer with Heavy CPU Load");

        Queue<String> queue = new LinkedList <>();
        Thread producer = new Thread(new ProducerThread(10, queue), "Producer-HeavyTest");
        Thread consumerA = new Thread(new ConsumerThreadHeavy("Consumer A", queue), "Consumer A");
        Thread consumerB = new Thread(new ConsumerThreadHeavy("Consumer B", queue), "Consumer B");

        consumerA.setDaemon(true);
        consumerB.setDaemon(true);

        producer.start();
        consumerA.start();
        consumerB.start();

        try {

            producer.join();
        }
        catch(InterruptedException e) {

            log.log(Level.WARNING, "Interrupted Waiting Producer to Join", e);
        }
    }

    private static class ProducerThread implements Runnable {

        private final Queue<String> queue;
        private final int duration;

        private ProducerThread(int duration, Queue<String> queue) {

            this.queue = queue;
            this.duration = duration;
        }

        @Override
        public void run() {

            log.info("Starting...");

            for(int i = 0; i < duration; i++) {

                String line = readLineFromFile(i);

                synchronized(queue) {

                    queue.add(line);
                    queue.notifyAll();
                }
            }

            log.info("Done");
        }

        private String readLineFromFile(int i) {

            try {

                log.info("Reading Line from File");
                Thread.sleep(TimeUnit.SECONDS.toMillis(2));
            }
            catch(InterruptedException e) {

                log.log(Level.WARNING, "Interrupted Waiting for Next Line from File");
            }

            return "Line #" + i;
        }
    }

    private static class ConsumerThreadLight implements Runnable {

        private final String name;
        private final Queue<String> queue;

        private ConsumerThreadLight(String name, Queue<String> queue) {

            this.name = name;
            this.queue = queue;
        }

        @Override
        public void run() {

            while(true) {

                String line;

                synchronized(queue) {

                    while((line = queue.poll()) == null) {

                        try {

                            queue.wait();
                        }
                        catch(InterruptedException e) {

                            log.log(Level.WARNING, "Interrupted Waiting for a Line from the Queue");
                        }
                    }
                }

                log.info(name + " got message: " + line);
            }
        }
    }

    private static class ConsumerThreadHeavy implements Runnable {

        private final String name;
        private final Queue<String> queue;

        private ConsumerThreadHeavy(String name, Queue<String> queue) {

            this.name = name;
            this.queue = queue;
        }

        @Override
        public void run() {

            while(true) {

                synchronized(queue) {

                    String line = queue.poll();

                    if(line != null) {

                        log.info(name + " got message: " + line);
                    }
                }
            }
        }
    }
}

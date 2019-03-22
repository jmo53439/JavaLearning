package com.jmlearning.randomthings.gamingprogramming.threads;

import java.util.Random;
import java.util.concurrent.*;

public class BlockingQueueExample {

    class Producer implements Callable<Void> {

        private Random random = new Random();
        private int numberOfMessages;
        private int sleep;

        private Producer(int numberOfMessages, int sleep) {

            this.numberOfMessages = numberOfMessages;
            this.sleep = sleep;
        }

        @Override
        public Void call() throws Exception {

            Message[] messages = Message.values();

            for(int i = 0; i < numberOfMessages; ++i) {

                try {

                    int index = random.nextInt(messages.length - 2);
                    queue.put(messages[index]);
                    System.out.println("PUT(" + (i + 1) + ") " + messages[index]);
                    sleep(sleep);
                }
                catch(InterruptedException ex) {

                }
            }

            queue.put(messages[messages.length - 1]);
            return null;
        }
    }

    class Consumer implements Callable<Integer> {


        private int messageCount = 0;

        @Override
        public Integer call() throws Exception {

            while(true) {

                Message msg = queue.take();
                messageCount++;
                System.out.println("Received: " + msg);

                if(msg == Message.QUIT) {

                    break;
                }
            }

            return messageCount;
        }
    }

    enum Message {

        MESSAGE_ONE,
        MESSAGE_TWO,
        MESSAGE_THREE,
        QUIT
    }

    private ExecutorService exec;
    private BlockingQueue<Message> queue;

    public BlockingQueueExample() {

        exec = Executors.newCachedThreadPool();
        queue = new LinkedBlockingQueue <>();
    }

    public void runTest() {

        int numberOfMessages = 100;
        int sleep = 100;
        System.out.println("Messages Sent: " + numberOfMessages);
        exec.submit(new Producer(numberOfMessages, sleep));
        sleep(2000);

        try {

            Future<Integer> consumer = exec.submit(new Consumer());

            try {

                System.out.println("Messages Processed: " + consumer.get());
            }
            catch(ExecutionException | InterruptedException ex ) {

            }
        }
        finally {

            try {

                exec.shutdown();
                exec.awaitTermination(10, TimeUnit.SECONDS);
                System.out.println("Thread Pool Shutdown");
            }
            catch(InterruptedException e) {

                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    protected void sleep(int ms) {

        try {

            Thread.sleep(ms);
        }
        catch(InterruptedException ex) {

        }
    }

    public static void main(String[] args) {

        new BlockingQueueExample().runTest();
    }
}

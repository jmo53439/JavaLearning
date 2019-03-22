package com.jmlearning.randomthings.gamingprogramming.sound;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SoundEvent implements Runnable {

    public static final String SHUT_DOWN = "shutdown";
    protected AudioStream audio;
    protected BlockingQueue<String> queue;
    private Thread consumer;

    public SoundEvent(AudioStream audio) {

        this.audio = audio;
    }

    public void initialize() {

        audio.addListener(getListener());
        queue = new LinkedBlockingQueue <>();
        consumer = new Thread(this);
        consumer.start();
    }

    public void put(String event) {

        try {

            queue.put(event);
        }
        catch(InterruptedException e) {

        }
    }

    public void shutDown() {

        Thread temp = consumer;
        consumer = null;

        try {

            queue.put(SHUT_DOWN);
            temp.join(10000L);
            System.out.println("Event Shut Down");
        }
        catch(InterruptedException ex) {

        }
    }

    @Override
    public void run() {

        while(Thread.currentThread() == consumer) {

            try {

                processEvent(queue.take());
            }
            catch(InterruptedException e) {

            }
        }
    }

    protected void processEvent(String event) throws InterruptedException {

    }

    protected void onAudioFinished() {

    }

    private BlockingAudioListener getListener() {

        return this :: onAudioFinished;
    }
}

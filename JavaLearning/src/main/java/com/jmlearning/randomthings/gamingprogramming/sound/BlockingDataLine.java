package com.jmlearning.randomthings.gamingprogramming.sound;

public class BlockingDataLine extends AudioStream {

    private AudioDataLine stream;

    public BlockingDataLine(byte[] soundData) {

        super(soundData);
    }

    @Override
    public void open() {

        lock.lock();

        try {

            stream = new AudioDataLine(soundData);
            stream.initialize();
            stream.addLineListener(this);
            stream.open();

            while(!open) {

                condition.await();
            }

            createControls(stream.getLine());
            System.out.println("Open");
        }
        catch(InterruptedException ex) {

            ex.printStackTrace();
        }
        finally {

            lock.unlock();
        }
    }

    @Override
    public void close() {

        lock.lock();

        try {

            stream.close();

            while(open) {

                condition.await();
            }

            clearControls();
            System.out.println("Closed");
        }
        catch(InterruptedException ex) {

            ex.printStackTrace();
        }
        finally {

            lock.unlock();
        }
    }

    @Override
    public void start() {

        lock.lock();

        try {

            stream.start();

            while(!started) {

                condition.await();
            }

            System.out.println("Started");
        }
        catch(InterruptedException ex) {

            ex.printStackTrace();
        }
        finally {

            lock.unlock();
        }
    }

    @Override
    public void loop(int count) {

        lock.lock();

        try {

            stream.loop(count);

            while(!started) {

                condition.await();
            }

            System.out.println("Started");
        }
        catch(InterruptedException ex) {

            ex.printStackTrace();
        }
        finally {

            lock.unlock();
        }
    }

    @Override
    public void restart() {

        stream.reset();
    }

    @Override
    public void stop() {

        lock.lock();

        try {

            stream.stop();

            while(started) {

                condition.await();
            }

            System.out.println("Stopped");
        }

        catch(InterruptedException ex) {

            ex.printStackTrace();
        }

        finally {

            lock.unlock();
        }
    }
}

package com.jmlearning.randomthings.gamingprogramming.sound;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BlockingClip extends AudioStream {

    private Clip clip;
    private boolean restart;

    public BlockingClip(byte[] soundData) {

        super(soundData);
    }

    @Override
    public void open() {

        lock.lock();

        try {

            ByteArrayInputStream in = new ByteArrayInputStream(soundData);
            AudioInputStream ais = AudioSystem.getAudioInputStream(in);
            clip = AudioSystem.getClip();
            clip.addLineListener(this);
            clip.open(ais);

            while(!open) {

                condition.await();
            }

            createControls(clip);
            System.out.println("Open");
        }
        catch(UnsupportedAudioFileException |
                LineUnavailableException | IOException ex) {

            throw new SoundException(ex.getMessage(), ex);
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

            clip.close();

            while(open) {

                condition.await();
            }

            clip = null;
            clearControls();
            System.out.println("Turned Off");
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

            clip.flush();
            clip.setFramePosition(0);
            clip.start();

            while(!started) {

                condition.await();
            }

            System.out.println("Started");
        }
        catch(InterruptedException e) {

            e.printStackTrace();
        }
        finally {

            lock.unlock();
        }
    }

    @Override
    public void loop(int count) {

        lock.lock();

        try {

            clip.flush();
            clip.setFramePosition(0);
            clip.loop(count);

            while(!started) {

                condition.await();
            }

            System.out.println("Started");
        }
        catch(InterruptedException e) {

            e.printStackTrace();
        }
        finally {

            lock.unlock();
        }
    }

    @Override
    public void restart() {

        restart = true;
        stop();
        restart = false;
        start();
    }

    @Override
    public void stop() {

        lock.lock();

        try {

            clip.stop();

            while(started) {

                condition.await();
            }
        }
        catch(InterruptedException ex) {

            ex.printStackTrace();
        }
        finally {

            lock.unlock();
        }
    }

    @Override
    protected void fireTaskFinished() {

        if(!restart) {

            super.fireTaskFinished();
        }
    }
}

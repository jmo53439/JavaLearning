package com.jmlearning.randomthings.gamingprogramming.sound;

import com.jmlearning.randomthings.gamingprogramming.utils.ResourceLoader;

import javax.sound.sampled.*;
import java.io.*;

public class PlayingClipsExample implements LineListener {

    private volatile boolean open = false;
    private volatile boolean started = false;

    public byte[] readBytes(InputStream in) {

        try {

            BufferedInputStream bis = new BufferedInputStream(in);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int read;
            while((read = bis.read()) != -1) {

                out.write(read);
            }
            in.close();

            return out.toByteArray();
        }
        catch(IOException ex) {

            ex.printStackTrace();
            return null;
        }
    }

    public void runTestWithoutWaiting() throws Exception {

       System.out.println("runTestWithoutWaiting()");
        Clip clip = AudioSystem.getClip();
        clip.addLineListener(this);
        InputStream resource = ResourceLoader.load(PlayingClipsExample.class,
                "res/assets/sound/<INSERT WAV FILE HERE.wav>", "notneeded");
        byte[] rawBytes = readBytes(resource);
        ByteArrayInputStream in = new ByteArrayInputStream(rawBytes);
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(in);
        clip.open(audioInputStream);

        for(int i = 0; i < 10; ++i) {

            clip.stop();
            clip.flush();
            clip.setFramePosition(0);
            clip.start();
            clip.drain();
        }

        clip.close();
    }

    public void runTestWithWaiting() throws Exception {

        System.out.println("runTestWithWaiting()");
        Clip clip = AudioSystem.getClip();
        clip.addLineListener(this);
        InputStream resource = ResourceLoader.load(PlayingClipsExample.class,
                "res/assets/sound/<INSERT WAV FILE HERE.wav>", "notneeded");
        byte[] rawBytes = readBytes(resource);
        ByteArrayInputStream in = new ByteArrayInputStream(rawBytes);
        in = new ByteArrayInputStream(rawBytes);
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(in);

        synchronized(this) {

            clip.open(audioInputStream);

            while(!open) {

                wait();
            }
        }

        for(int i = 0; i < 10; ++i) {

            clip.setFramePosition(0);

            synchronized(this) {

                clip.start();

                while(!started) {

                    wait();
                }
            }

            clip.drain();

            synchronized(this) {

                clip.stop();

                while(started) {

                    wait();
                }
            }
        }

        synchronized(this) {

            clip.close();

            while(open) {

                wait();
            }
        }
    }

    @Override
    public void update(LineEvent lineEvent) {

        System.out.println("Got Event: " + lineEvent.getType());
        LineEvent.Type type = lineEvent.getType();

        if(type == LineEvent.Type.OPEN) {

            open = true;
        }
        else if(type == LineEvent.Type.START) {

            started = true;
        }
        else if(type == LineEvent.Type.STOP) {

            started = false;
        }
        else if(type == LineEvent.Type.CLOSE) {

            open = false;
        }

        notifyAll();
    }

    public static void main(String[] args) throws Exception {

        PlayingClipsExample lineListenerExample = new PlayingClipsExample();
        lineListenerExample.runTestWithWaiting();
        lineListenerExample.runTestWithoutWaiting();
    }
}

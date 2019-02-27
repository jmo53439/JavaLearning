package com.jmlearning.randomthings.gamingprogramming.sound;

import com.jmlearning.randomthings.gamingprogramming.utils.ResourceLoader;
import com.jmlearning.randomthings.gamingprogramming.utils.SimpleFramework;
import com.jmlearning.randomthings.gamingprogramming.utils.Utility;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SoundPlayerExample extends SimpleFramework {

    private OneShotEvent oneShotClip;
    private LoopEvent loopClip;
    private RestartEvent restartClip;
    private OneShotEvent oneShotStream;
    private LoopEvent loopStream;
    private RestartEvent restartStream;
    private byte[] someSoundBytes;
    private byte[] someOtherSoundBytes;
    private String loaded;

    public SoundPlayerExample() {

        appWidth = 340;
        appHeight = 340;
        appSleep = 10L;
        appTitle = "Sound Player Example";
        appBackground = Color.WHITE;
        appFPSColor = Color.BLACK;
    }

    @Override
    protected void initialize() {

        super.initialize();

        InputStream in = ResourceLoader.load(SoundPlayerExample.class,
                "./res/assets/sound/<Insert WAV File Here.wav>", "asdf");
        someSoundBytes = readBytes(in);
        in = ResourceLoader.load(SoundPlayerExample.class,
                "./res/assets/sound/<Insert WAV File Here.wav>", "asdf");
        someOtherSoundBytes = readBytes(in);
        loadWaveFile(someSoundBytes);
        loaded = "someSound";
    }

    private byte[] readBytes(InputStream in) {

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

    private void loadWaveFile(byte[] rawData) {

        shutDownClips();

        oneShotClip = new OneShotEvent(new BlockingClip(rawData));
        oneShotClip.initialize();
        loopClip = new LoopEvent(new BlockingClip(rawData));
        loopClip.initialize();
        restartClip = new RestartEvent(new BlockingClip(rawData));
        restartClip.initialize();
        oneShotStream = new OneShotEvent(new BlockingDataLine(rawData));
        oneShotStream.initialize();
        loopStream = new LoopEvent(new BlockingDataLine(rawData));
        loopStream.initialize();
        restartStream = new RestartEvent(new BlockingDataLine(rawData));
        restartStream.initialize();
    }

    private void shutDownClips() {

        if(oneShotClip != null)
            oneShotClip.shutDown();

        if(loopClip != null)
            loopClip.shutDown();

        if(restartClip != null)
            restartClip.shutDown();

        if(oneShotStream != null)
            oneShotStream.shutDown();

        if(loopStream != null)
            loopStream.shutDown();

        if(restartStream != null)
            restartStream.shutDown();
    }

    @Override
    protected void processInput(float delta) {

        super.processInput(delta);

        if(keyboard.keyDownOnce(KeyEvent.VK_F1)) {

            loadWaveFile(someSoundBytes);
            loaded = "soundSound";
        }

        if(keyboard.keyDownOnce(KeyEvent.VK_F2)) {

            loadWaveFile(someOtherSoundBytes);
            loaded = "someOtherSound";
        }

        if(keyboard.keyDownOnce(KeyEvent.VK_1))
            oneShotClip.fire();

        if(keyboard.keyDownOnce(KeyEvent.VK_2))
            oneShotClip.done();

        if(keyboard.keyDownOnce(KeyEvent.VK_3))
            loopClip.fire();

        if(keyboard.keyDownOnce(KeyEvent.VK_4))
            loopClip.done();

        if(keyboard.keyDownOnce(KeyEvent.VK_5))
            restartClip.fire();

        if(keyboard.keyDownOnce(KeyEvent.VK_6))
            oneShotStream.fire();

        if(keyboard.keyDownOnce(KeyEvent.VK_7))
            oneShotStream.done();

        if(keyboard.keyDownOnce(KeyEvent.VK_8))
            loopStream.fire();

        if(keyboard.keyDownOnce(KeyEvent.VK_9))
            loopStream.done();

        if(keyboard.keyDownOnce(KeyEvent.VK_0))
            restartStream.fire();
    }

    @Override
    protected void updateObjects(float delta) {

        super.updateObjects(delta);
    }

    @Override
    protected void render(Graphics g) {

        super.render(g);

        textPos = Utility.drawString(g, 20, textPos, "",
                "(F1) Load Weapon",
                "(F2) Load Rain", loaded + " loaded!", "",
                "(1) Fire One Shot (clip)",
                "(2) Cancel One Shot (clip)",
                "(3) Start Loop (clip)",
                "(4) Stop Loop (clip)",
                "(5) Reusable (clip)", "",
                "(6) Fire One Shot (stream)",
                "(7) Cancel One Shot (stream)",
                "(8) Start Loop (stream)",
                "(9) Stop Loop (stream)",
                "(0) Reusable (stream)");
    }

    @Override
    protected void terminate() {

        super.terminate();
        shutDownClips();
    }

    public static void main(String[] args) {

        launchApp(new SoundPlayerExample());
    }
}

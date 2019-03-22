package com.jmlearning.randomthings.gamingprogramming.utils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SimpleKeyboardInput implements KeyListener {

    private boolean[] keys;

    public SimpleKeyboardInput() {

        keys = new boolean[256];
    }

    public synchronized boolean keyDown(int keyCode) {

        return keys[keyCode];
    }

    @Override
    public void keyTyped(KeyEvent e) {

        // not used
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();

        if(keyCode >= 0 && keyCode < keys.length)
            keys[keyCode] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int keyCode = e.getKeyCode();

        if(keyCode >= 0 && keyCode < keys.length)
            keys[keyCode] = false;
    }
}

package com.jmlearning.randomthings.gamingprogramming.utils;

import java.awt.*;
import java.awt.event.*;

public class SimpleMouseInput implements MouseListener, MouseMotionListener, MouseWheelListener {

    private static final int BUTTON_COUNT = 3;

    private Point mousePosition;
    private Point currentPosition;
    private boolean[] mouse;
    private int[] polled;
    private int notches;
    private int polledNotches;

    public SimpleMouseInput() {

        mousePosition = new Point(0, 0);
        currentPosition = new Point(0, 0);
        mouse = new boolean[BUTTON_COUNT];
        polled = new int[BUTTON_COUNT];
    }

    public synchronized void poll() {

        mousePosition = new Point(currentPosition);
        polledNotches = notches;
        notches = 0;

        for(int i = 0; i < mouse.length; ++i) {

            if(mouse[i]) {

                polled[i]++;
            }
            else {

                polled[i] = 0;
            }
        }
    }

    public Point getMousePosition() {

        return mousePosition;
    }

    public int getPolledNotches() {

        return polledNotches;
    }

    public boolean buttonDown(int button) {

        return polled[button - 1] > 0;
    }

    public boolean buttonDownOnce(int button) {

        return polled[button - 1] == 1;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        // not used
    }

    @Override
    public synchronized void mousePressed(MouseEvent e) {

        int button = e.getButton() - 1;

        if(button >= 0 && button < mouse.length)
            mouse[button] = true;
    }

    @Override
    public synchronized void mouseReleased(MouseEvent e) {

        int button = e.getButton() - 1;

        if(button >= 0 && button < mouse.length)
            mouse[button] = false;
    }

    @Override
    public synchronized void mouseEntered(MouseEvent e) {

        mouseMoved(e);
    }

    @Override
    public synchronized void mouseExited(MouseEvent e) {

        mouseMoved(e);
    }

    @Override
    public synchronized void mouseDragged(MouseEvent e) {

        mouseMoved(e);
    }

    @Override
    public synchronized void mouseMoved(MouseEvent e) {

        currentPosition = e.getPoint();
    }

    @Override
    public synchronized void mouseWheelMoved(MouseWheelEvent e) {

        notches += e.getWheelRotation();
    }
}

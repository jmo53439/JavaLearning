package com.jmlearning.randomthings.gamingprogramming.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RelativeMouseInput implements MouseListener, MouseMotionListener, MouseWheelListener {

    private static final int BUTTON_COUNT = 3;

    private Point mousePosition;
    private Point currentPosition;
    private boolean[] mouse;
    private int[] polled;
    private int notches;
    private int polledNotches;
    private int dx, dy;
    private Robot robot;
    private Component component;
    private boolean relative;

    public RelativeMouseInput(Component component) {

        this.component = component;

        try {

            robot = new Robot();
        }
        catch(Exception e) {

            e.printStackTrace();
        }

        mousePosition = new Point(0, 0);
        currentPosition = new Point(0, 0);
        mouse = new boolean[BUTTON_COUNT];
        polled = new int[BUTTON_COUNT];
    }

    public synchronized void poll() {

        if(isRelative()) {

            mousePosition = new Point(dx, dy);
        }
        else {

            mousePosition = new Point(currentPosition);
        }

        dx = dy = 0;
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

    public boolean isRelative() {

        return relative;
    }

    public void setRelative(boolean relative) {

        this.relative = relative;

        if(relative)
            centerMouse();
    }

    public Point getMousePosition() {

        return mousePosition;
    }

    public int getNotches() {

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

        if(isRelative()) {

            Point p = e.getPoint();
            Point center = getCenter();
            dx += p.x - center.x;
            dy += p.y - center.y;
            centerMouse();
        }
        else {

            currentPosition = e.getPoint();
        }
    }

    @Override
    public synchronized void mouseWheelMoved(MouseWheelEvent e) {

       notches += e.getWheelRotation();
    }

    private Point getCenter() {

        int w = component.getWidth();
        int h = component.getHeight();

        return new Point(w / 2, h / 2);
    }

    private void centerMouse() {

        if(robot != null && component.isShowing()) {

            Point center = getCenter();
            SwingUtilities.convertPointToScreen(center, component);
            robot.mouseMove(center.x, center.y);
        }
    }
}

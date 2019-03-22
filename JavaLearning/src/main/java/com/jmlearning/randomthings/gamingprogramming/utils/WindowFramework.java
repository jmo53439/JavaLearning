package com.jmlearning.randomthings.gamingprogramming.utils;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class WindowFramework extends GameFramework {

    private Canvas canvas;

    @Override
    protected void createFramework() {
        
        canvas = new Canvas();
        canvas.setBackground(appBackground);
        canvas.setIgnoreRepaint(true);
        getContentPane().add(canvas);
        setLocationByPlatform(true);
        
        if(appMaintainRatio) {
            
            getContentPane().setBackground(appBorder);
            setSize(appWidth, appHeight);
            setLayout(null);
            getContentPane().addComponentListener(new ComponentAdapter() {

                @Override
                public void componentResized(ComponentEvent e) {

                    onComponentResized(e);
                }
            });
        }
        else {

            canvas.setSize(appWidth, appHeight);
            pack();
        }
        
        setTitle(appTitle);
        setupInput(canvas);
        setVisible(true);
        createBufferStrategy(canvas);
        canvas.requestFocus();
    }

    protected void onComponentResized(ComponentEvent e) {

        Dimension size = getContentPane().getSize();
        screenCoordinates(size.width, size.height);
        canvas.setLocation(vx, vy);
        canvas.setSize(vw, vh);
    }

    @Override
    protected void renderFrame(Graphics g) {

        g.clearRect(0, 0, getScreenWidth(), getScreenHeight());
        render(g);
    }

    @Override
    public int getScreenWidth() {

        return canvas.getWidth();
    }

    @Override
    public int getScreenHeight() {

        return canvas.getHeight();
    }
}

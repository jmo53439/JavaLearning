package com.jmlearning.randomthings.gamingprogramming.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class SwingFramework extends GameFramework {

    protected Canvas canvas;
    private JPanel mainPanel;
    private JPanel centerPanel;

    protected JPanel getMainPanel() {

        if(mainPanel == null) {

            mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());
            mainPanel.add(getCenterPanel(), BorderLayout.CENTER);
        }

        return mainPanel;
    }

    private JPanel getCenterPanel() {

        if(centerPanel == null) {

            centerPanel = new JPanel();
            centerPanel.setBackground(appBorder);
            centerPanel.setLayout(null);
            centerPanel.add(getCanvas());
        }

        return centerPanel;
    }

    private Canvas getCanvas() {

        if(canvas == null) {
            canvas = new Canvas();
            canvas.setBackground(appBackground);
        }

        return canvas;
    }

    private void setupLookAndFeel() {

        try {

            UIManager.setLookAndFeel(
                    "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    protected void onGameSetup() {

    }

    @Override
    protected void createFramework() {

        setupLookAndFeel();
        getContentPane().add(getMainPanel());
        setLocationByPlatform(true);
        setSize(appWidth, appHeight);
        setTitle(appTitle);
        getContentPane().setBackground(appBorder);
        setSize(appWidth, appHeight);
        canvas.setSize(appWidth, appHeight);
        getContentPane().addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {

                onComponentResized(e);
            }
        });

        setupInput(getCanvas());
        onGameSetup();
        setVisible(true);
        createBufferStrategy(getCanvas());
        getCanvas().requestFocus();
    }

    protected void onComponentResized(ComponentEvent e) {

        Dimension size = getCenterPanel().getSize();
        screenCoordinates(size.width, size.height);
        getCanvas().setLocation(vx, vy);
        getCanvas().setSize(vw, vh);
        getCanvas().repaint();
    }

    @Override
    protected void renderFrame(Graphics g) {

        g.clearRect(0, 0, getScreenWidth(), getScreenHeight());
    }

    @Override
    public int getScreenWidth() {

        return getCanvas().getWidth();
    }

    @Override
    public int getScreenHeight() {

        return getCanvas().getHeight();
    }
}

package com.jmlearning.randomthings.gamingprogramming.render;

import com.jmlearning.randomthings.gamingprogramming.utils.FrameRate;

import javax.swing.*;
import java.awt.*;

public class HelloWorld extends JFrame {

    private FrameRate frameRate;

    public HelloWorld() {

        frameRate = new FrameRate();
    }

    protected void gui() {

        GamePanel gamePanel = new GamePanel();
        gamePanel.setBackground(Color.WHITE);
        gamePanel.setPreferredSize(new Dimension(320, 240));
        getContentPane().add(gamePanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Hello World!");
        pack();
        frameRate.initialize();
        setVisible(true);
    }

    private class GamePanel extends JPanel {

        public void paint(Graphics g) {

            super.paint(g);
            onPaint(g);
        }
    }

    protected void onPaint(Graphics g) {

        frameRate.calculate();
        g.setColor(Color.BLACK);
        g.drawString(frameRate.getFrameRate(), 30, 30);
        repaint();
    }

    public static void main(String[] args) {

        final HelloWorld app = new HelloWorld();

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                app.gui();
            }
        });
    }
}

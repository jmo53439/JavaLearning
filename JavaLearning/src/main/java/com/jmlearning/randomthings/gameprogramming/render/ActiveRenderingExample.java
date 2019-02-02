package com.jmlearning.randomthings.gamingprogramming.render;

import com.jmlearning.randomthings.gamingprogramming.utils.FrameRate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

public class ActiveRenderingExample extends JFrame implements Runnable {

    private FrameRate frameRate;
    private BufferStrategy bs;
    private volatile boolean running;
    private Thread gameThread;

    public ActiveRenderingExample() {

        frameRate = new FrameRate();
    }

    protected void gui() {

        Canvas canvas = new Canvas();
        canvas.setSize(320, 240);
        canvas.setBackground(Color.BLACK);
        canvas.setIgnoreRepaint(true);
        getContentPane().add(canvas);
        setTitle("Active Rendering");
        setIgnoreRepaint(true);
        pack();
        setVisible(true);
        canvas.createBufferStrategy(2);
        bs = canvas.getBufferStrategy();
        gameThread = new Thread(this);
        gameThread.start();
    }


    @Override
    public void run() {

        running = true;
        frameRate.initialize();

        while(running) {

            gameLoop();
        }
    }

    public void gameLoop() {

        do {

            do {

                Graphics g = null;

                try {

                    g = bs.getDrawGraphics();
                    g.clearRect(0, 0, getWidth(), getHeight());
                    render(g);
                }
                finally {

                    if(g != null) {

                        g.dispose();
                    }
                }
            }
            while(bs.contentsRestored());

            bs.show();
        }
        while(bs.contentsLost());
    }

    private void render(Graphics g) {

        frameRate.calculate();
        g.setColor(Color.GREEN);
        g.drawString(frameRate.getFrameRate(), 30, 30);
    }

    protected void onWindowClose() {

        try {

            running = false;
            gameThread.join();
        }
        catch(InterruptedException e) {

            e.printStackTrace();
        }

        System.exit(0);
    }

    public static void main(String[] args) {

        final ActiveRenderingExample app = new ActiveRenderingExample();
        app.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {

                app.onWindowClose();
            }
        });

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                app.gui();
            }
        });
    }
}

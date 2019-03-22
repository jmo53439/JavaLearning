package com.jmlearning.randomthings.gamingprogramming.render;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RenderThreadExample extends JFrame implements Runnable {

    private volatile boolean running;
    private Thread gameThread;

    private RenderThreadExample() {

    }

    private void gui() {

        setSize(320, 240);
        setTitle("Render Thread");
        setVisible(true);

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        running = true;

        while(running) {

            System.out.println("Game Loop");
            sleep(10);
        }
    }

    private void sleep(long sleep) {

        try {

            Thread.sleep(sleep);
        }
        catch(InterruptedException ignored) {

        }
    }

    private void onWindowClosing() {

        try {

            System.out.println("Stopping Thread...");
            running = false;
            gameThread.join();
            System.out.println("*Stopped*");
        }
        catch(InterruptedException e) {

            e.printStackTrace();
        }

        System.exit(0);
    }

    public static void main(String[] args) {

        final RenderThreadExample app = new RenderThreadExample();
        app.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                app.onWindowClosing();
            }
        });

//        SwingUtilities.invokeLater(new Runnable() {
//
//            @Override
//            public void run() {
//
//                app.gui();
//            }
//        });

//        SwingUtilities.invokeLater(() -> app.gui());
        SwingUtilities.invokeLater(app :: gui);
    }
}

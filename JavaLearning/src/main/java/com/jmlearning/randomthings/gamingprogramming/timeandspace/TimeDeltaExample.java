package com.jmlearning.randomthings.gamingprogramming.timeandspace;

import com.jmlearning.randomthings.gamingprogramming.utils.FrameRate;
import com.jmlearning.randomthings.gamingprogramming.utils.KeyboardInput;
import com.jmlearning.randomthings.gamingprogramming.utils.RelativeMouseInput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

public class TimeDeltaExample extends JFrame implements Runnable {

    private FrameRate frameRate;
    private BufferStrategy bs;
    private volatile boolean running;
    private Thread gameThread;
    private RelativeMouseInput mouse;
    private KeyboardInput keyboard;
    private Canvas canvas;
    private float angle;
    private float step;
    private long sleep;

    public TimeDeltaExample() {

    }

    protected void gui() {

        canvas = new Canvas();
        canvas.setSize(480, 480);
        canvas.setBackground(Color.WHITE);
        canvas.setIgnoreRepaint(true);
        getContentPane().add(canvas);
        setTitle("Time Delta Example");
        setIgnoreRepaint(true);
        pack();

        keyboard = new KeyboardInput();
        canvas.addKeyListener(keyboard);

        mouse = new RelativeMouseInput(canvas);
        canvas.addMouseListener(mouse);
        canvas.addMouseMotionListener(mouse);
        canvas.addMouseWheelListener(mouse);

        setVisible(true);

        canvas.createBufferStrategy(2);
        bs = canvas.getBufferStrategy();
        canvas.requestFocus();

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        running = true;
        initialize();
        long currentTime = System.nanoTime();
        long lastTime = currentTime;
        double nsPerFrame;

        while(running) {

            currentTime = System.nanoTime();
            nsPerFrame = currentTime - lastTime;
            gameLoop(nsPerFrame / 1.0E9);
            lastTime = currentTime;
        }
    }

    private void gameLoop(double delta) {

        processInput(delta);
        updateObjects(delta);
        renderFrame();
        sleep(sleep);

    }

    private void renderFrame() {

        do {

            do {

                Graphics g = null;

                try {

                    g = bs.getDrawGraphics();
                    g.clearRect(0, 0, getWidth(), getHeight());
                    render(g);
                }
                finally {

                    if(g != null)
                        g.dispose();
                }
            }
            while(bs.contentsRestored());

            bs.show();
        }
        while(bs.contentsLost());
    }

    private void sleep(long sleep) {

        try {

            Thread.sleep(sleep);
        }
        catch(InterruptedException ex) {

        }
    }

    private void initialize() {

        frameRate = new FrameRate();
        frameRate.initialize();
        angle = 0.0f;
        step = (float) Math.PI / 2.0f;
    }

    private void processInput(double delta) {

        keyboard.poll();
        mouse.poll();

        if(keyboard.keyDownOnce(KeyEvent.VK_UP))
            sleep += 10;

        if(keyboard.keyDownOnce(KeyEvent.VK_DOWN))
            sleep -= 10;

        if(sleep > 1000)
            sleep = 1000;

        if(sleep < 0)
            sleep = 0;
    }

    private void updateObjects(double delta) {

        angle += step * delta;

        if(angle > 2 * Math.PI)
            angle -= 2 * Math.PI;
    }

    private void render(Graphics g) {

        g.setColor(Color.BLACK);
        frameRate.calculate();
        g.drawString(frameRate.getFrameRate(), 20, 20);
        g.drawString("Up Arrow increases sleep time", 20, 35);
        g.drawString("Down Arrow decreases sleep time", 20, 50);
        g.drawString("Sleep Time(ms): " + sleep, 20, 65);

        int x = canvas.getWidth() / 4;
        int y = canvas.getHeight() / 4;
        int w = canvas.getWidth() / 2;
        int h = canvas.getHeight() / 2;

        g.drawOval(x, y, w, h);

        // polar coordinates
        float rw = w / 2;
        float rh = h / 2;
        int rx = (int)(rw * Math.cos(angle));
        int ry = (int)(rh * Math.sin(angle));
        int cx = rx + w;
        int cy = ry + h;

        // clock hand
        g.drawLine(w, h, cx, cy);

        // pointer at the end of the clock hand
        g.drawRect(cx - 2, cy - 2, 4, 4);
    }

    protected void onWindowClosing() {

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

        final TimeDeltaExample app = new TimeDeltaExample();
        app.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                app.onWindowClosing();
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

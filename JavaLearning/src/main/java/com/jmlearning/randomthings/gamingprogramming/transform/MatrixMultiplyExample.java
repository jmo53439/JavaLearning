package com.jmlearning.randomthings.gamingprogramming.transform;

import com.jmlearning.randomthings.gamingprogramming.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class MatrixMultiplyExample extends JFrame implements Runnable {

    private static final int SCREEN_W = 640;
    private static final int SCREEN_H = 480;
    private FrameRate frameRate;
    private BufferStrategy bs;
    private volatile boolean running;
    private Thread gameThread;
    private RelativeMouseInput mouse;
    private KeyboardInput keyboard;
    private float earthRotation, earthDelta;
    private float moonRotation, moonDelta;
    private boolean showStars;
    private int[] stars;
    private Random random = new Random();

    public MatrixMultiplyExample() {

    }

    protected void gui() {

        Canvas canvas = new Canvas();
        canvas.setSize(SCREEN_W, SCREEN_H);
        canvas.setBackground(Color.BLACK);
        canvas.setIgnoreRepaint(true);
        getContentPane().add(canvas);
        setTitle("Matrix Multiply Example");
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

        while(running) {

            gameLoop();
        }
    }

    private void initialize() {

        frameRate = new FrameRate();
        frameRate.initialize();

        earthDelta = (float) Math.toRadians(0.5);
        moonDelta = (float) Math.toRadians(2.5);
        showStars = true;
        stars = new int[1000];

        for(int i = 0; i < stars.length - 1; i += 2) {

            stars[i] = random.nextInt(SCREEN_W);
            stars[i + 1] = random.nextInt(SCREEN_H);
        }
    }

    private void gameLoop() {

        processInput();
        renderFrame();
        sleep(10L);
    }

    private void sleep(long sleep) {

        try {
            
            Thread.sleep(sleep);
        }
        catch(InterruptedException ex) {
            
        }
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

        g.setColor(Color.GREEN);
        frameRate.calculate();
        g.drawString(frameRate.getFrameRate(), 20, 20);
        g.drawString("Press [Space] to toggle stars", 20, 35);

        if(showStars) {

            g.setColor(Color.WHITE);

            for(int i = 0; i < stars.length - 1; i += 2) {

                g.fillRect(stars[i], stars[i + 1], 1, 1);
            }
        }

        // draw sun
        Matrix3x3f sunMatrix = Matrix3x3f.identity();
        sunMatrix = sunMatrix.multiply(Matrix3x3f.translate(SCREEN_W / 2, SCREEN_H / 2));
        Vector2f sun = sunMatrix.multiply(new Vector2f());
        g.setColor(Color.YELLOW);
        g.fillOval((int) sun.x - 50, (int) sun.y - 50, 100, 100);

        // draw earth orbit
        g.setColor(Color.WHITE);
        g.drawOval((int) sun.x - SCREEN_W / 4, (int) sun.y - SCREEN_W / 4,
                SCREEN_W / 2, SCREEN_W / 2);


        // draw earth
        Matrix3x3f earthMatrix = Matrix3x3f.translate(SCREEN_W / 4, 0);
        earthMatrix = earthMatrix.multiply(Matrix3x3f.rotate(earthRotation));
        earthMatrix = earthMatrix.multiply(sunMatrix);
        earthRotation += earthDelta;
        Vector2f earth = earthMatrix.multiply(new Vector2f());
        g.setColor(Color.BLUE);
        g.fillOval((int) earth.x - 10, (int) earth.y - 10, 20, 20);

        // draw moon
        Matrix3x3f moonMatrix = Matrix3x3f.translate(30, 0);
        moonMatrix = moonMatrix.multiply(Matrix3x3f.rotate(moonRotation));
        moonMatrix = moonMatrix.multiply(earthMatrix);
        moonRotation += moonDelta;
        Vector2f moon = moonMatrix.multiply(new Vector2f());
        g.setColor(Color.LIGHT_GRAY);
        g.fillOval((int) moon.x - 5, (int) moon.y - 5, 10, 10);
    }

    private void processInput() {

        keyboard.poll();
        mouse.poll();

        if(keyboard.keyDownOnce(KeyEvent.VK_SPACE))
            showStars = !showStars;
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

        final MatrixMultiplyExample app = new MatrixMultiplyExample();
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

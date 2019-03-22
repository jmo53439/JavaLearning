package com.jmlearning.randomthings.gamingprogramming.timeandspace;

import com.jmlearning.randomthings.gamingprogramming.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

public class ScreenMappingExample extends JFrame implements Runnable {

    private Canvas canvas;
    private FrameRate frameRate;
    private BufferStrategy bs;
    private volatile boolean running;
    private Thread gameThread;
    private RelativeMouseInput mouse;
    private KeyboardInput keyboard;
    private Vector2f[] tri;
    private Vector2f[] triWorld;
    private Vector2f[] rect;
    private Vector2f[] rectWorld;

    public ScreenMappingExample() {

    }

    protected void gui() {

        canvas = new Canvas();
        canvas.setSize(640, 480);
        canvas.setBackground(Color.WHITE);
        canvas.setIgnoreRepaint(true);
        getContentPane().add(canvas);
        setTitle("Screen Mapping Example");
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
        sleep(10L);
    }

    private void processInput(double delta) {

        keyboard.poll();
        mouse.poll();
    }

    private void updateObjects(double delta) {

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

    private void render(Graphics g) {

        g.setColor(Color.BLACK);
        frameRate.calculate();
        g.drawString(frameRate.getFrameRate(), 20, 20);
        float worldWidth = 2.0f;
        float worldHeight = 2.0f;
        float screenWidth = canvas.getWidth() - 1;
        float screenHeight = canvas.getWidth() - 1;
        float sx = screenWidth / worldWidth;
        float sy = screenHeight / worldHeight;
        float tx = screenWidth / 2.0f;
        float ty = screenHeight / 2.0f;

        Matrix3x3f viewport = Matrix3x3f.scale(sx, sy);
        viewport = viewport.multiply(Matrix3x3f.translate(tx, ty));

        for(int i = 0; i < tri.length; ++i) {

            triWorld[i] = viewport.multiply(tri[i]);
        }

        drawPolygon(g, triWorld);

        for(int i = 0; i < rect.length; ++i) {

            rectWorld[i] = viewport.multiply(rect[i]);
        }

        drawPolygon(g, rectWorld);
    }

    private void drawPolygon(Graphics g, Vector2f[] polygon) {

        Vector2f P;
        Vector2f S = polygon[polygon.length - 1];

        for(Vector2f aPolygon : polygon) {

            P = aPolygon;
            g.drawLine((int) S.x, (int) S.y, (int) P.x, (int) P.y);
            S = P;
        }
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
        tri = new Vector2f[] {new Vector2f(0.0f, 0.5f),
                new Vector2f(-0.5f, -0.5f),
                new Vector2f(0.5f, -0.5f)};
        triWorld = new Vector2f[tri.length];

        rect = new Vector2f[] {new Vector2f(-1.0f, 1.0f),
                new Vector2f(1.0f, 1.0f),
                new Vector2f(1.0f, -1.0f),
                new Vector2f(-1.0f, -1.0f)};
        rectWorld = new Vector2f[rect.length];
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

        final ScreenMappingExample app = new ScreenMappingExample();
        app.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                app.onWindowClosing();
            }
        });

        SwingUtilities.invokeLater(() -> app.gui());
    }
}

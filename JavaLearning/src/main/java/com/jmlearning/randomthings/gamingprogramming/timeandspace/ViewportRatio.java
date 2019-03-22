package com.jmlearning.randomthings.gamingprogramming.timeandspace;

import com.jmlearning.randomthings.gamingprogramming.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

public class ViewportRatio extends JFrame implements Runnable {

    private FrameRate frameRate;
    private BufferStrategy bs;
    private volatile boolean running;
    private Thread gameThread;
    private RelativeMouseInput mouse;
    private KeyboardInput keyboard;
    private Canvas canvas;
    private Vector2f[] tri;
    private Vector2f[] triWorld;
    private float worldWidth;
    private float worldHeight;

    public ViewportRatio() {

    }

    protected void gui() {

        canvas = new Canvas();
        canvas.setBackground(Color.WHITE);
        canvas.setIgnoreRepaint(true);
        getContentPane().setBackground(Color.LIGHT_GRAY);
        setLayout(null);
        setTitle("Viewport Ratio");
        setSize(640, 640);
        getContentPane().add(canvas);

        keyboard = new KeyboardInput();
        canvas.addKeyListener(keyboard);

        mouse = new RelativeMouseInput(canvas);
        canvas.addMouseListener(mouse);
        canvas.addMouseMotionListener(mouse);
        canvas.addMouseWheelListener(mouse);

        getContentPane().addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {

                onComponentResized(e);
            }
        });

        setVisible(true);

        canvas.createBufferStrategy(2);
        bs = canvas.getBufferStrategy();
        canvas.requestFocus();

        gameThread = new Thread(this);
        gameThread.start();
    }

    protected void onComponentResized(ComponentEvent e) {

        Dimension size = getContentPane().getSize();
        int vw = size.width * 3 / 4;
        int vh = size.height * 3 / 4;
        int vx = (size.width - vw) / 2;
        int vy = (size.height - vh) / 2;

        int newWidth = vw;
        int newHeight = (int)(vw * worldHeight / worldWidth);

        if(newHeight > vh) {

            newWidth = (int)(vh * worldWidth / worldHeight);
            newHeight = vh;
        }

        // center
        vx += (vw - newWidth) / 2;
        vy += (vh - newHeight) / 2;

        canvas.setLocation(vx, vy);
        canvas.setSize(newWidth, newHeight);
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
        float sx = (canvas.getWidth() - 1) / worldWidth;
        float sy = (canvas.getHeight() - 1) / worldHeight;
        float tx = (canvas.getWidth() - 1) / 2.0f;
        float ty = (canvas.getHeight() - 1) / 2.0f;
        Matrix3x3f viewport = Matrix3x3f.identity();
        viewport = viewport.multiply(Matrix3x3f.scale(sx, -sy));
        viewport = viewport.multiply(Matrix3x3f.translate(tx, ty));

        for(int i = 0; i < tri.length; ++i) {

            triWorld[i] = viewport.multiply(tri[i]);
        }

        drawPolygon(g, triWorld);
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

    private void updateObjects(double delta) {

    }

    private void processInput(double delta) {

        keyboard.poll();
        mouse.poll();
    }

    private void initialize() {

        frameRate = new FrameRate();
        frameRate.initialize();
        tri = new Vector2f[] {new Vector2f(0.0f, 2.25f),
                new Vector2f(-4.0f, -2.25f),
                new Vector2f(4.0f, -2.25f)};
        triWorld = new Vector2f[tri.length];
        worldWidth = 16.0f;
        worldHeight = 9.0f;
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

        final ViewportRatio app = new ViewportRatio();
        app.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                app.onWindowClosing();
            }
        });

        SwingUtilities.invokeLater(app :: gui);
    }
}

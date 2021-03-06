package com.jmlearning.randomthings.gamingprogramming.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

public class SimpleFramework extends JFrame implements Runnable {

    private BufferStrategy bs;
    private volatile boolean running;
    private Thread gameThread;
    protected FrameRate frameRate;
    protected Canvas canvas;
    protected RelativeMouseInput mouse;
    protected KeyboardInput keyboard;
    protected Color appBackground = Color.BLACK;
    protected Color appBorder = Color.LIGHT_GRAY;
    protected Color appFPSColor = Color.GREEN;
    protected Font appFont = new Font("Courier New", Font.PLAIN, 14);
    protected String appTitle = "Some-Title";
    protected float appBorderScale = 0.8f;
    protected int appWidth = 640;
    protected int appHeight = 480;
    protected float appWorldWidth = 2.0f;
    protected float appWorldHeight = 2.0f;
    protected long appSleep = 10L;
    protected boolean appMaintainRatio = false;
    protected int textPos = 0;

    public SimpleFramework() {

    }

    protected void gui() {

        canvas = new Canvas();
        canvas.setBackground(appBackground);
        canvas.setIgnoreRepaint(true);
        getContentPane().add(canvas);
        setLocationByPlatform(true);

        if(appMaintainRatio) {

            getContentPane().setBackground(appBorder);
            setSize(appWidth, appHeight);
            canvas.setSize(appWidth, appHeight);
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

    protected void onComponentResized(ComponentEvent e) {

        Dimension size = getContentPane().getSize();
        int vw = (int)(size.width * appBorderScale);
        int vh = (int)(size.height * appBorderScale);
        int vx = (size.width - vw) / 2;
        int vy = (size.height - vh) / 2;
        int newWidth = vw;
        int newHeight = (int)(vw * appWorldHeight / appWorldWidth);

        if(newHeight > vh) {

            newWidth = (int)(vh * appWorldWidth / appWorldHeight);
            newHeight = vh;
        }

        // center
        vx += (vw - newWidth) / 2;
        vy += (vh - newHeight) / 2;
        canvas.setLocation(vx, vy);
        canvas.setSize(newWidth, newHeight);
    }

    protected Matrix3x3f getViewportTransform() {

        return Utility.createViewport(appWorldWidth, appWorldHeight, canvas.getWidth(), canvas.getHeight());
    }

    protected Matrix3x3f getReverseViewportTransform() {

        return Utility.createReverseViewport(appWorldWidth, appWorldHeight, canvas.getWidth(), canvas.getHeight());
    }

    protected Vector2f getWorldMousePosition() {

        Matrix3x3f screenToWorld = getReverseViewportTransform();
        Point mousePosition = mouse.getMousePosition();
        Vector2f screenPosition = new Vector2f(mousePosition.x, mousePosition.y);

        return screenToWorld.multiply(screenPosition);
    }

    protected Vector2f getRelativeWorldMousePosition() {

        float sx = appWorldWidth / (canvas.getWidth() - 1);
        float sy = appWorldHeight / (canvas.getHeight() - 1);
        Matrix3x3f viewport = Matrix3x3f.scale(sx, -sy);
        Point p = mouse.getMousePosition();

        return viewport.multiply(new Vector2f(p.x, p.y));
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
            gameLoop((float)(nsPerFrame / 1.0E9));
            lastTime = currentTime;
        }

        terminate();
    }

    protected void initialize() {

        frameRate = new FrameRate();
        frameRate.initialize();
    }

    protected void terminate() {

    }

    private void gameLoop(float delta) {

        processInput(delta);
        updateObjects(delta);
        renderFrame();
        sleep(appSleep);
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

    protected void render(Graphics g) {

        g.setFont(appFont);
        g.setColor(appFPSColor);
        frameRate.calculate();
        textPos = Utility.drawString(g, 20, 0, frameRate.getFrameRate());
    }

    protected void updateObjects(float delta) {

    }

    protected void processInput(float delta) {

        keyboard.poll();
        mouse.poll();
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

    protected static void launchApp(final SimpleFramework app) {

        app.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                app.onWindowClosing();
            }
        });

        SwingUtilities.invokeLater(app :: gui);
    }
}

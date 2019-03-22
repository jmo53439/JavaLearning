package com.jmlearning.randomthings.gamingprogramming.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import static java.lang.Thread.sleep;

public abstract class GameFramework extends JFrame implements Runnable {

    private BufferStrategy bs;
    private volatile boolean running;
    private Thread gameThread;
    protected int vx;
    protected int vy;
    protected int vw;
    protected int vh;
    protected FrameRate frameRate;
    protected RelativeMouseInput mouse;
    protected KeyboardInput keyboard;
    protected Color appBackground = Color.BLACK;
    protected Color appBorder = Color.LIGHT_GRAY;
    protected Color appFPSColor = Color.GREEN;
    protected Font appFont = new Font("Courier New", Font.PLAIN, 14);
    protected String appTitle = "Moment of Truth";
    protected float appBorderScale = 0.8f;
    protected int appWidth = 640;
    protected int appHeight = 640;
    protected float appWorldWidth = 2.0f;
    protected float appWorldHeight = 2.0f;
    protected long appSleep = 10L;
    protected boolean appMaintainRatio = false;
    protected boolean appDisableCursor = false;
    protected int textPOS = 0;

    public GameFramework() {

    }

    protected abstract void createFramework();
    protected abstract void renderFrame(Graphics g);
    public abstract int getScreenWidth();
    public abstract int getScreenHeight();

    protected void setupGame() {

        createFramework();

        if(appDisableCursor)
            disableCursor();

        gameThread = new Thread(this);
        gameThread.start();
    }

    private void disableCursor() {

        Toolkit tk = Toolkit.getDefaultToolkit();
        Image img = tk.createImage("");
        Point pt = new Point(0, 0);
        String name = "SomeName";
        Cursor cursor = tk.createCustomCursor(img, pt, name);
        setCursor(cursor);
    }

    protected void setupInput(Component component) {

        keyboard = new KeyboardInput();
        component.addKeyListener(keyboard);

        mouse = new RelativeMouseInput(component);
        component.addMouseListener(mouse);
        component.addMouseMotionListener(mouse);
        component.addMouseWheelListener(mouse);
    }

    protected void createBufferStrategy(Canvas component) {

        component.createBufferStrategy(2);
        bs = component.getBufferStrategy();
    }

    protected void createBufferStrategy(Window window) {

        window.createBufferStrategy(2);
        bs = window.getBufferStrategy();
    }

    protected void screenCoordinates(int sw, int sh) {

        int w = (int)(sw * appBorderScale);
        int h = (int)(sh * appBorderScale);
        int x = (sw - w) / 2;
        int y = (sh - h) / 2;
        vw = w;
        vh = (int)(w * appWorldHeight / appWorldWidth);

        if(vh > h ) {

            vw = (int)(h * appWorldWidth / appWorldHeight);
            vh = h;
        }

        vx = x + (w - vw) / 2;
        vy = y + (h - vh) / 2;
    }

    protected Matrix3x3f getViewportTransform() {

        return Utility.createViewport(appWorldWidth, appWorldHeight,
                getScreenWidth(), getScreenHeight());
    }

    protected Matrix3x3f getReverseViewportTransform() {

        return Utility.createReverseViewport(appWorldWidth, appWorldHeight,
                getScreenWidth(), getScreenHeight());
    }

    protected Vector2f getWorldMousePosition() {

        Matrix3x3f screenToWorld = getReverseViewportTransform();
        Point mousePOS = mouse.getMousePosition();
        Vector2f screePOS = new Vector2f(mousePOS.x, mousePOS.y);

        return screenToWorld.multiply(screePOS);
    }

    protected Vector2f getRelativeWorldMousePosition() {

        float sx = appWorldWidth / (getScreenWidth() - 1);
        float sy = appWorldHeight / (getScreenHeight() - 1);
        Matrix3x3f viewport = Matrix3x3f.scale(sx, -sy);
        Point p = mouse.getMousePosition();

        return viewport.multiply(new Vector2f(p.x, p.y));
    }

    @Override
    public void run() {

        running = true;
        initialize();
        long currentTime = System.nanoTime();
        long previousTime = currentTime;
        double nsPerFrame;

        while(running) {

            currentTime = System.nanoTime();
            nsPerFrame = currentTime - previousTime;
            gameLoop((float)(nsPerFrame / 1.0E9));
            previousTime = currentTime;
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

    protected void processInput(float delta) {

        keyboard.poll();
        mouse.poll();
    }

    protected void updateObjects(float delta) {

    }
    
    protected void render(Graphics g) {
        
        g.setFont(appFont);
        g.setColor(appFPSColor);
        frameRate.calculate();
        textPOS = Utility.drawString(g, 20, 0, frameRate.getFrameRate());
    }

    private void renderFrame() {

        do {

            do {

                Graphics g = null;

                try {

                    g = bs.getDrawGraphics();
                    renderFrame(g);
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

    protected void shutDown() {

        if(Thread.currentThread() != gameThread) {

            try {

                running = false;
                gameThread.join();
                onShutDown();
            }
            catch(InterruptedException e) {

                e.printStackTrace();
            }

            System.exit(0);
        }
        else {

            SwingUtilities.invokeLater(() -> shutDown());
        }
    }

    protected void onShutDown() {

    }

    protected static void launchApp(final GameFramework game) {

        game.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                game.shutDown();
            }
        });

        SwingUtilities.invokeLater(() -> game.setupGame());
    }
}

package com.jmlearning.randomthings.gamingprogramming.timeandspace;

import com.jmlearning.randomthings.gamingprogramming.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import static java.lang.Thread.sleep;

public class CannonExample extends JFrame implements Runnable {

    private FrameRate frameRate;
    private BufferStrategy bs;
    private volatile boolean running;
    private Thread gameThread;
    private KeyboardInput keyboard;
    private RelativeMouseInput mouse;
    private Canvas canvas;
    private Vector2f[] cannon;
    private Vector2f[] cannonCpy;
    private float cannonRot, cannonDelta;
    private Vector2f bullet;
    private Vector2f bulletCpy;
    private Vector2f velocity;

    public CannonExample() {

    }

    protected void gui() {

        canvas = new Canvas();
        canvas.setSize(640, 480);
        canvas.setBackground(Color.WHITE);
        canvas.setIgnoreRepaint(true);
        getContentPane().add(canvas);
        setTitle("Cannon Example");
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
        g.drawString("Press (A) to raise, (D) to lower", 20, 35);
        g.drawString("Press [Space] to fire cannon", 20, 50);
        String vel = String.format("Velocity (%.2f,%.2f)", velocity.x, velocity.y);
        g.drawString(vel, 20, 65);

        float worldWidth = 5.0f;
        float worldHeight = 5.0f;
        float screenWidth = canvas.getWidth() - 1;
        float screenHeight = canvas.getHeight() - 1;
        float sx = screenWidth / worldWidth;
        float sy = -screenHeight / worldHeight;
        Matrix3x3f viewport = Matrix3x3f.scale(sx, sy);

        float tx = screenWidth / 2.0f;
        float ty = screenHeight / 2.0f;
        viewport = viewport.multiply(Matrix3x3f.translate(tx, ty));

        for(int i = 0; i < cannon.length; ++i) {

            cannonCpy[i] = viewport.multiply(cannonCpy[i]);
        }

        drawPolygon(g, cannonCpy);

        if(bullet != null) {

            bulletCpy = viewport.multiply(bulletCpy);
            g.drawRect((int) bulletCpy.x - 2, (int) bulletCpy.y - 2, 4, 4);
        }
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

        Matrix3x3f mat = Matrix3x3f.identity();
        mat = mat.multiply(Matrix3x3f.rotate(cannonRot));
        mat = mat.multiply(Matrix3x3f.translate(-2.0f, -2.0f));

        for(int i = 0; i < cannon.length; ++i) {

            cannonCpy[i] = mat.multiply(cannon[i]);
        }

        if(bullet != null) {

            velocity.y += -9.8f * delta;
            bullet.x += velocity.x * delta;
            bullet.y += velocity.y * delta;
            bulletCpy = new Vector2f(bullet);

            if(bullet.y < -2.5f)
                bullet = null;
        }
    }

    private void processInput(double delta) {

        keyboard.poll();
        mouse.poll();

        if(keyboard.keyDown(KeyEvent.VK_A))
            cannonRot += cannonDelta * delta;

        if(keyboard.keyDown(KeyEvent.VK_D))
            cannonRot -= cannonDelta * delta;

        if(keyboard.keyDownOnce(KeyEvent.VK_SPACE)) {

            Matrix3x3f mat = Matrix3x3f.translate(7.0f, 0.0f);
            mat = mat.multiply(Matrix3x3f.rotate(cannonRot));
            velocity = mat.multiply(new Vector2f());

            mat = Matrix3x3f.translate(.375f, 0.0f);
            mat = mat.multiply(Matrix3x3f.rotate(cannonRot));
            mat = mat.multiply(Matrix3x3f.translate(-2.0f, -2.0f));
            bullet = mat.multiply(new Vector2f());
        }
    }

    private void initialize() {

        frameRate = new FrameRate();
        frameRate.initialize();
        velocity = new Vector2f();
        cannonRot = 0.0f;
        cannonDelta = (float) Math.toRadians(90.0);
        cannon = new Vector2f[] {new Vector2f(-0.5f, 0.125f),
                new Vector2f(0.5f, 0.125f),
                new Vector2f(0.5f, -0.125f),
                new Vector2f(-0.5f, -0.125f)
        };
        
        cannonCpy = new Vector2f[cannon.length];
        Matrix3x3f scale = Matrix3x3f.scale(.75f, .75f);
        
        for(int i = 0; i < cannon.length; ++i) {
            
            cannon[i] = scale.multiply(cannon[i]);
        }
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

        final CannonExample app = new CannonExample();
        app.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                app.onWindowClosing();
            }
        });

        SwingUtilities.invokeLater(app :: gui);
    }
}

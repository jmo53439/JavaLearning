package com.jmlearning.randomthings.gamingprogramming.input;

import com.jmlearning.randomthings.gamingprogramming.utils.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.*;

public class SimpleMouseExample extends JFrame implements Runnable {

    private FrameRate frameRate;
    private BufferStrategy bs;
    private volatile boolean running;
    private Thread gameThread;

    private SimpleMouseInput mouse;
    private KeyboardInput keyboard;
    private ArrayList<Point> lines = new ArrayList <>();
    private boolean drawLine;

    private Color[] COLORS = {Color.BLACK, Color.GREEN, Color.YELLOW, Color.BLUE};
    private int colorPosition;

    public SimpleMouseExample() {

        frameRate = new FrameRate();
    }

    protected void gui() {

        Canvas canvas = new Canvas();
        canvas.setSize(640, 480);
        canvas.setBackground(Color.WHITE);
        canvas.setIgnoreRepaint(true);
        getContentPane().add(canvas);
        setTitle("Simple Mouse Example");
        setIgnoreRepaint(true);
        pack();

        keyboard = new KeyboardInput();
        canvas.addKeyListener(keyboard);

        mouse = new SimpleMouseInput();
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
        frameRate.initialize();

        while(running)
            gameLoop();
    }

    private void gameLoop() {

        processInput();
        renderFrame();
        sleep(10L);
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

    private void sleep(long sleep) {

        try {

            Thread.sleep(sleep);
        }
        catch(InterruptedException ex) {

        }
    }

    private void processInput() {

        keyboard.poll();
        mouse.poll();

        if(keyboard.keyDownOnce(KeyEvent.VK_SPACE))
            System.out.println("VK_SPACE");

        if(mouse.buttonDownOnce(MouseEvent.BUTTON1))
            drawLine = true;

        if(mouse.buttonDown(MouseEvent.BUTTON1)) {

            lines.add(mouse.getMousePosition());
        }
        else if(drawLine) {

            lines.add(null);
            drawLine = false;
        }

        if(keyboard.keyDownOnce(KeyEvent.VK_C))
            lines.clear();
    }

    private void render(Graphics g) {

        colorPosition += mouse.getPolledNotches();
        Color color = COLORS[Math.abs(colorPosition % COLORS.length)];
        g.setColor(color);

        frameRate.calculate();
        g.drawString(frameRate.getFrameRate(), 30, 30);
        g.drawString("Use 'Mouse' to draw lines", 30, 45);
        g.drawString("Press 'C' to clear lines", 30, 60);
        g.drawString("Use 'Mouse Wheel' to cycle through colors", 30, 75);
        g.drawString(mouse.getMousePosition().toString(), 30, 90);

        for(int i = 0; i < lines.size() - 1; ++i) {

            Point p1 = lines.get(i);
            Point p2 = lines.get(i + 1);

            if(!(p1 == null || p2 == null))
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
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

        final SimpleMouseExample app = new SimpleMouseExample();
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

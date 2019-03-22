package com.jmlearning.randomthings.gamingprogramming.images;

import com.jmlearning.randomthings.gamingprogramming.utils.SimpleFramework;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.util.Random;

public class ImageSpeedTest extends SimpleFramework {

    private Random random = new Random();
    private GraphicsConfiguration gc;
    private BufferedImage bi;
    private VolatileImage vi;
    private boolean realTime = true;
    private boolean isBufferedImage = true;

    public ImageSpeedTest() {

        appWidth = 640;
        appHeight = 640;
        appSleep = 0L;
        appTitle = "Image Speed Test";
    }

    @Override
    protected void initialize() {

        super.initialize();

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gc = gd.getDefaultConfiguration();
        bi = gc.createCompatibleImage(appWidth, appHeight);
        createVolatileImage();
        renderToBufferedImage();
    }

    private void createVolatileImage() {

        if(vi != null) {

            vi.flush();
            vi = null;
        }

        vi = gc.createCompatibleVolatileImage(getWidth(), getHeight());
    }

    private void renderToBufferedImage(Graphics g) {

        if(realTime)
            renderToBufferedImage();
        
        g.drawImage(bi, 0, 0, null);
    }

    private void renderToBufferedImage() {

        Graphics g2d = bi.createGraphics();
        g2d.setColor(new Color(random.nextInt()));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
    }

    @Override
    protected void processInput(float delta) {

        super.processInput(delta);

        if(keyboard.keyDownOnce(KeyEvent.VK_B))
            isBufferedImage = !isBufferedImage;

        if(keyboard.keyDownOnce(KeyEvent.VK_R))
            realTime = !realTime;
    }

    @Override
    protected void render(Graphics g) {

        if(isBufferedImage) {

            renderToBufferedImage(g);
        }
        else if(realTime) {

            renderToVolatileImageEveryFrame(g);
        }
        else {

            renderToVolatileImage(g);
        }
    }

    private void renderToVolatileImageEveryFrame(Graphics g) {

        do {

            int returnCode = vi.validate(gc);

            if(returnCode == VolatileImage.IMAGE_INCOMPATIBLE) {

                createVolatileImage();
            }

            renderVolatileImage();
            g.drawImage(vi, 0, 0, null);
        }
        while(vi.contentsLost());
    }

    private void renderToVolatileImage(Graphics g) {

        do {

            int returnCode = vi.validate(gc);

            if(returnCode == VolatileImage.IMAGE_RESTORED) {

                renderVolatileImage();
            }
            else if(returnCode == VolatileImage.IMAGE_INCOMPATIBLE) {

                createVolatileImage();
                renderVolatileImage();
            }

            g.drawImage(vi, 0, 0, null);
        }
        while(vi.contentsLost());
    }

    private void renderVolatileImage() {

        Graphics g2d = vi.createGraphics();
        g2d.setColor(new Color(random.nextInt()));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
    }

    public static void main(String[] args) {

        launchApp(new ImageSpeedTest());
    }
}

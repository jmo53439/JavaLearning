package com.jmlearning.randomthings.gamingprogramming.utils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Sprite {

    private BufferedImage img;
    private BufferedImage scaled;
    private Vector2f topLeft;
    private Vector2f bottomRight;

    public Sprite(BufferedImage img, Vector2f topLeft, Vector2f bottomRight) {

        this.img = img;
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public void render(Graphics2D g, Matrix3x3f view, Vector2f position, float angle) {

        if(img != null) {

            Vector2f tl = view.multiply(topLeft);
            Vector2f br = view.multiply(bottomRight);
            int width = (int) Math.abs(br.x - tl.x);
            int height = (int) Math.abs(br.y - tl.y);

            if(scaled == null || width != scaled.getWidth() ||
                    height != scaled.getHeight()) {

                scaled = Utility.scaleImage(img, width, height);
            }

            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            Vector2f screen = view.multiply(position);
            AffineTransform transform =
                    AffineTransform.getTranslateInstance(screen.x, screen.y);
            transform.rotate(-angle);
            transform.translate(-scaled.getWidth() / 2, -scaled.getHeight() / 2);
            g.drawImage(scaled, transform, null);
        }
    }
    
    public void scaleImage(Matrix3x3f view) {
        
        Vector2f screenTopLeft = view.multiply(topLeft);
        Vector2f screenBottomRight = view.multiply(bottomRight);
        int scaledWidth = (int) Math.abs(screenBottomRight.x - screenTopLeft.x);
        int scaledHeight = (int) Math.abs(screenBottomRight.y - screenTopLeft.y);
        scaled = Utility.scaleImage(img, scaledWidth, scaledHeight);
    }
}

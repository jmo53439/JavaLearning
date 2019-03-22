package com.jmlearning.randomthings.gamingprogramming.tools;

import com.jmlearning.randomthings.gamingprogramming.utils.ResourceLoader;
import com.jmlearning.randomthings.gamingprogramming.utils.Sprite;
import com.jmlearning.randomthings.gamingprogramming.utils.Vector2f;
import com.jmlearning.randomthings.gamingprogramming.utils.WindowFramework;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class SpriteExample extends WindowFramework {

    private Sprite sprite;
    private Vector2f pos;
    private Vector2f vel;
    private float rot;
    private float rotDelta;

    public SpriteExample() {

        appTitle = "Sprite Example";
        pos = new Vector2f();
        vel = new Vector2f(0.25f, -0.3f);
        rotDelta = (float) Math.toRadians(90.0);
    }

    @Override
    protected void initialize() {

        super.initialize();

        InputStream in = ResourceLoader.load(SpriteExample.class,
                "res/assets/images/<Insert PNG here>.png",
                "/images/<Insert PNG here>.png");

        try {

            BufferedImage img = ImageIO.read(in);
            Vector2f topLeft = new Vector2f(-0.25f, 0.25f);
            Vector2f bottomRight = new Vector2f(0.25f, -0.25f);
            sprite = new Sprite(img, topLeft, bottomRight);
        }
        catch(Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    protected void updateObjects(float delta) {

        super.updateObjects(delta);

        pos = pos.add(vel.multiply(delta));

        if(pos.x < -appWorldWidth / 2.0f) {

            pos.x = -appWorldWidth / 2.0f;
            vel.x = -vel.x;
        }
        else if(pos.x > appWorldWidth / 2.0f) {

            pos.x = appWorldWidth / 2.0f;
            vel.x = -vel.x;
        }

        if(pos.y < -appWorldHeight / 2.0f) {

            pos.y = -appWorldHeight / 2.0f;
            vel.y = -vel.y;
        }
        else if(pos.y > appWorldHeight / 2.0f) {

            pos.y = appWorldHeight / 2.0f;
            vel.y = -vel.y;
        }

        rot += rotDelta * delta;
    }

    @Override
    protected void render(Graphics g) {

        super.render(g);

        g.setColor(Color.GREEN);
        g.drawRect(0, 0, getScreenWidth() - 1, getScreenHeight() - 1);
        sprite.render((Graphics2D) g, getViewportTransform(), pos, rot);
    }

    public static void main(String[] args) {

        launchApp(new SpriteExample());
    }
}

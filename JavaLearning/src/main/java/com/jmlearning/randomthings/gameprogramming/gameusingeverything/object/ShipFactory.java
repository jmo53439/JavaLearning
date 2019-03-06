package com.jmlearning.randomthings.gamingprogramming.gameusingeverything.object;

import com.jmlearning.randomthings.gamingprogramming.utils.ResourceLoader;
import com.jmlearning.randomthings.gamingprogramming.utils.Sprite;
import com.jmlearning.randomthings.gamingprogramming.utils.Vector2f;
import com.jmlearning.randomthings.gamingprogramming.utils.XMLUtility;
import org.w3c.dom.Element;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Vector;

public class ShipFactory {

    private PolygonWrapper wrapper;
    private Vector2f[] polygon;
    private Sprite shipRegular;
    private Sprite shipGlow;

    public ShipFactory(PolygonWrapper wrapper) {

        this.wrapper = wrapper;
    }

    public Ship createShip() {

        Ship ship = new Ship(wrapper);
        ship.setAlive(true);
        ship.setPolygon(polygon);
        ship.setGlowSprite(shipGlow);
        ship.setShipSprite(shipRegular);

        return ship;
    }

    public void loadFactory(Element xml) {

        Vector<Vector2f> points = new Vector <>();
        String spritePath = xml.getAttribute("sprite");
        String glowPath = xml.getAttribute("glow");
        String bounds = xml.getAttribute("bounds");

        for(Element coords : XMLUtility.getAllElements(xml, "coord")) {

            float x = Float.parseFloat(coords.getAttribute("x"));
            float y = Float.parseFloat(coords.getAttribute("y"));
            points.add(new Vector2f(x, y));
        }

        polygon = points.toArray(new Vector2f[0]);
        float bound = Float.parseFloat(bounds);
        Vector2f topLeft = new Vector2f(-bound / 2.0f, bound / 2.0f);
        Vector2f bottomRight = new Vector2f(bound / 2.0f, -bound / 2.0f);
        BufferedImage img = loadSprite(spritePath);
        shipRegular = new Sprite(img, topLeft, bottomRight);
        img = loadSprite(glowPath);
        shipGlow = new Sprite(img, topLeft, bottomRight);
    }

    private BufferedImage loadSprite(String path) {

        InputStream stream = ResourceLoader.load(ShipFactory.class,
                "res/assets/images/" + path,
                "/images/" + path);

        try {

            return ImageIO.read(stream);
        }
        catch(Exception e) {

            e.printStackTrace();
        }

        return null;
    }
}

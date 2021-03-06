package com.jmlearning.randomthings.gamingprogramming.gameusingeverything.object;

import com.jmlearning.randomthings.gamingprogramming.utils.ResourceLoader;
import com.jmlearning.randomthings.gamingprogramming.utils.Sprite;
import com.jmlearning.randomthings.gamingprogramming.utils.Vector2f;
import com.jmlearning.randomthings.gamingprogramming.utils.XMLUtility;
import org.w3c.dom.Element;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class AsteroidFactory {

    private Vector<Asteroid> small;
    private Vector<Asteroid> medium;
    private Vector<Asteroid> large;
    private float worldWidth;
    private PolygonWrapper wrapper;
    private Random random;

    public AsteroidFactory(PolygonWrapper wrapper, float worldWidth) {

        this.worldWidth = worldWidth;
        this.wrapper = wrapper;
        small = new Vector <>();
        medium = new Vector <>();
        large = new Vector <>();
        random = new Random();
    }

    public void loadModels(Element root) {

        for(Element model : XMLUtility.getAllElements(
                root, "model")) {

            parseModel(model);
        }
    }

    private void parseModel(Element model) {

        Asteroid asteroid = new Asteroid(wrapper);
        Vector<Vector2f> polygon = new Vector <>();
        String modelSize = model.getAttribute("size");
        String image = model.getAttribute("sprite");
        String bounds = model.getAttribute("bounds");

        for(Element coords : XMLUtility.getAllElements(model, "coord")) {

            float x = Float.parseFloat(coords.getAttribute("x"));
            float y = Float.parseFloat(coords.getAttribute("y"));
            polygon.add(new Vector2f(x, y));
        }

        asteroid.setPolygon(polygon.toArray(new Vector2f[0]));
        BufferedImage bi = null;
        InputStream stream = ResourceLoader.load(AsteroidFactory.class,
                "res/assets/images/" + image,
                "/images/" + image);

        try {

            bi = ImageIO.read(stream);
        }
        catch(Exception e) {

            e.printStackTrace();
        }

        float bound = Float.parseFloat(bounds);
        Vector2f topLeft = new Vector2f(-bound / 2.0f, bound / 2.0f);
        Vector2f bottomRight = new Vector2f(bound / 2.0f, -bound / 2.0f);
        Sprite sprite = new Sprite(bi, topLeft, bottomRight);
        asteroid.setSprite(sprite);
        Asteroid.Size size = Asteroid.Size.valueOf(modelSize);
        asteroid.setSize(size);

        if(size == Asteroid.Size.Large) {

            large.add(asteroid);
        }
        else if(size == Asteroid.Size.Medium) {

            medium.add(asteroid);
        }
        else if(size == Asteroid.Size.Small) {

            small.add(asteroid);
        }
    }

    public Asteroid getLargeAsteroid() {

        return getLargeAsteroid(getAsteroidStartPosition());
    }

    public Asteroid getLargeAsteroid(Vector2f position) {

        return copy(getRandomAsteroid(large), position);
    }

    public Asteroid getMediumAsteroid() {

        return getMediumAsteroid(getAsteroidStartPosition());
    }

    public Asteroid getMediumAsteroid(Vector2f position) {

        return copy(getRandomAsteroid(medium), position);
    }

    public Asteroid getSmallAsteroid() {

        return getSmallAsteroid(getAsteroidStartPosition());
    }

    public Asteroid getSmallAsteroid(Vector2f position) {

        return copy(getRandomAsteroid(small), position);
    }

    private Vector2f getAsteroidStartPosition() {

        float angle = (float) Math.toRadians(random.nextInt(360));
        float minimum = worldWidth / 4.0f;
        float extra = random.nextFloat() * minimum;
        float radius = minimum + extra;

        return Vector2f.polar(angle, radius);
    }

    public Asteroid copy(Asteroid template, Vector2f position) {

        Asteroid asteroid = new Asteroid(wrapper);
        asteroid.setPosition(position);
        asteroid.setSprite(template.getSprite());
        asteroid.setSize(template.getSize());
        asteroid.setPolygon(template.getPolygon());

        return asteroid;
    }

    private Asteroid getRandomAsteroid(List<Asteroid> asteroids) {

        return asteroids.get(random.nextInt(asteroids.size()));
    }
}

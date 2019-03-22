package com.jmlearning.randomthings.gamingprogramming.prototype;

import com.jmlearning.randomthings.gamingprogramming.utils.Matrix3x3f;
import com.jmlearning.randomthings.gamingprogramming.utils.SimpleFramework;
import com.jmlearning.randomthings.gamingprogramming.utils.Vector2f;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class RandomAsteroidExample extends SimpleFramework {

    private PrototypeAsteroidFactory factory;
    private ArrayList<PrototypeAsteroid> asteroids;
    private Random random;

    public RandomAsteroidExample() {

        appBorderScale = 0.9f;
        appWidth = 640;
        appHeight = 640;
        appMaintainRatio = true;
        appSleep = 1L;
        appTitle = "Random Asteroids";
        appBackground = Color.WHITE;
        appFPSColor = Color.BLACK;
    }

    @Override
    protected void initialize() {

        super.initialize();
        random = new Random();
//        asteroids = new ArrayList <PrototypeAsteroid>();
        asteroids = new ArrayList <>();
        PolygonWrapper wrapper = new PolygonWrapper(appWorldWidth, appWorldHeight);
        factory = new PrototypeAsteroidFactory(wrapper);
        createAsteroids();
    }

    private void createAsteroids() {

        asteroids.clear();

        for(int i = 0; i < 42; ++i) {

            asteroids.add(getRandomAsteroid());
        }
    }

    private PrototypeAsteroid getRandomAsteroid() {

        float x = random.nextFloat() * 2.0f - 1.0f;
        float y = random.nextFloat() * 2.0f - 1.0f;
        Vector2f position = new Vector2f(x, y);
        PrototypeAsteroid.Size[] sizes = PrototypeAsteroid.Size.values();
        PrototypeAsteroid.Size randomSize = sizes[random.nextInt(sizes.length)];

        switch(randomSize) {

            case Small:
                return factory.createSmallAsteroid(position);

            case Medium:
                return factory.createMediumAsteroid(position);

            case Large:
                return factory.createLargeAsteroid(position);

            default:
                return factory.createLargeAsteroid(position);
        }
    }

    @Override
    protected void processInput(float delta) {

        super.processInput(delta);

        if(keyboard.keyDownOnce(KeyEvent.VK_ESCAPE))
            createAsteroids();
    }

    @Override
    protected void updateObjects(float delta) {

        super.updateObjects(delta);

        for(PrototypeAsteroid asteroid : asteroids) {
            asteroid.update(delta);
        }
    }

    @Override
    protected void render(Graphics g) {

        super.render(g);
        g.drawString("Press [ESC] to respawn", 20, 35);
        Matrix3x3f view = getViewportTransform();

        for(PrototypeAsteroid asteroid : asteroids) {

            asteroid.draw((Graphics2D) g, view);
        }
    }

    public static void main(String[] args) {

        launchApp(new RandomAsteroidExample());
    }
}

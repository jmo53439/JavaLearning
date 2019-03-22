package com.jmlearning.randomthings.gamingprogramming.prototype;

import com.jmlearning.randomthings.gamingprogramming.utils.Matrix3x3f;
import com.jmlearning.randomthings.gamingprogramming.utils.SimpleFramework;
import com.jmlearning.randomthings.gamingprogramming.utils.Vector2f;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class PrototypeGame extends SimpleFramework {

    private static final int STAR_COUNT = 1500;
    private PrototypeShip ship;
    private PolygonWrapper wrapper;
    private PrototypeAsteroidFactory factory;
    private ArrayList<PrototypeBullet> bullets;
    private ArrayList<PrototypeAsteroid> asteroids;
    private Random random;
    private Vector2f[] stars;
    private Color[] colors;

    public PrototypeGame() {

        appBorderScale = 0.9f;
        appWidth = 640;
        appHeight = 640;
        appMaintainRatio = true;
        appSleep = 1L;
        appTitle = "Prototype Game";
    }

    @Override
    protected void initialize() {

        super.initialize();

        random = new Random();
        bullets = new ArrayList <>();
        asteroids = new ArrayList <>();
        wrapper = new PolygonWrapper(appWorldWidth, appWorldHeight);
        ship = new PrototypeShip(wrapper);
        factory = new PrototypeAsteroidFactory(wrapper);
        createStars();
        createAsteroids();
    }

    private void createStars() {

        stars = new Vector2f[STAR_COUNT];
        colors = new Color[STAR_COUNT];

        for(int i = 0; i < stars.length; ++i) {

            float x = random.nextFloat() * 2.0f - 1.0f;
            float y = random.nextFloat() * 2.0f - 1.0f;
            stars[i] = new Vector2f(x, y);
            float color = random.nextFloat();
            colors[i] = new Color(color, color, color);
        }
    }

    private void createAsteroids() {

        asteroids.clear();

        for(int i = 0; i < 4; ++i) {

            Vector2f position = getAsteroidStartPosition();
            asteroids.add(factory.createLargeAsteroid(position));
        }
    }

    private Vector2f getAsteroidStartPosition() {

        float angle = (float) Math.toRadians(random.nextInt(360));
        float min = appWorldWidth / 4.0f;
        float extra = random.nextFloat() * min;
        float radius = min + extra;

        return Vector2f.polar(angle, radius);
    }

    @Override
    protected void processInput(float delta) {

        super.processInput(delta);

        if(keyboard.keyDown(KeyEvent.VK_LEFT))
            ship.rotateLeft(delta);

        if(keyboard.keyDown(KeyEvent.VK_RIGHT))
            ship.rotateRight(delta);

        if(keyboard.keyDownOnce(KeyEvent.VK_SPACE))
            bullets.add(ship.launchBullet());

        if(keyboard.keyDownOnce(KeyEvent.VK_ESCAPE))
            createAsteroids();

        ship.setThrusting(keyboard.keyDown(KeyEvent.VK_UP));
    }

    @Override
    protected void updateObjects(float delta) {

        super.updateObjects(delta);

        updateAsteroids(delta);
        updateBullets(delta);
        updateShip(delta);
    }

    private void updateAsteroids(float delta) {

        for(PrototypeAsteroid asteroid : asteroids) {

            asteroid.update(delta);
        }
    }

    private void updateBullets(float delta) {

//        ArrayList<PrototypeBullet> copy = new ArrayList <PrototypeBullet>(bullets);
        ArrayList<PrototypeBullet> copy = new ArrayList <>(bullets);
        
        for(PrototypeBullet bullet : copy) {
            
            updateBullet(delta, bullet);
        }
    }

    private void updateBullet(float delta, PrototypeBullet bullet) {

        bullet.update(delta);

        if(wrapper.hasLeftWorld(bullet.getPosition())) {

            bullets.remove(bullet);
        }
        else {

            ArrayList<PrototypeAsteroid> ast = new ArrayList <>(asteroids);

            for(PrototypeAsteroid asteroid : ast) {

                if(asteroid.contains(bullet.getPosition())) {

                    bullets.remove(bullet);
                    asteroids.remove(asteroid);
                    spawnAsteroidChunks(asteroid);
                }
            }
        }
    }

    private void spawnAsteroidChunks(PrototypeAsteroid asteroid) {

        if(asteroid.getSize() == PrototypeAsteroid.Size.Large) {

            asteroids.add(factory.createMediumAsteroid(asteroid.getPosition()));
            asteroids.add(factory.createMediumAsteroid(asteroid.getPosition()));
        }

        if(asteroid.getSize() == PrototypeAsteroid.Size.Medium) {

            asteroids.add(factory.createSmallAsteroid(asteroid.getPosition()));
            asteroids.add(factory.createSmallAsteroid(asteroid.getPosition()));
        }
    }

    private void updateShip(float delta) {

        ship.update(delta);
        boolean isHit = false;

        for(PrototypeAsteroid asteroid : asteroids) {

            if(ship.isTouching(asteroid)) {

                isHit = true;
            }
        }

        ship.setDamaged(isHit);
    }

    @Override
    protected void render(Graphics g) {

        super.render(g);

        g.drawString("Rotate: Left/Right Arrows", 20, 35);
        g.drawString("Thrusters: Up Arrow", 20, 50);
        g.drawString("Fire: [Space]", 20, 65);
        g.drawString("Press [ESC] to respawn", 20, 80);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Matrix3x3f view = getViewportTransform();
        drawStars(g2d, view);
        drawAsteroids(g2d, view);
        drawBullets(g2d, view);
        drawShip(g2d, view);
    }

    private void drawStars(Graphics2D g, Matrix3x3f view) {

        for(int i = 0; i < stars.length; ++i) {

            g.setColor(colors[i]);
            Vector2f screen = view.multiply(stars[i]);
            g.fillRect((int) screen.x, (int) screen.y, 1, 1);
        }
    }

    private void drawAsteroids(Graphics2D g, Matrix3x3f view) {

        for(PrototypeAsteroid asteroid : asteroids) {

            asteroid.draw(g, view);
        }
    }

    private void drawBullets(Graphics2D g, Matrix3x3f view) {

        for(PrototypeBullet b : bullets) {

            b.draw(g, view);
        }
    }

    private void drawShip(Graphics2D g, Matrix3x3f view) {

        ship.draw(g, view);
    }

    public static void main(String[] args) {

        launchApp(new PrototypeGame());
    }
}

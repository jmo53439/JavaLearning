package com.jmlearning.randomthings.gamingprogramming.gameusingeverything.object;

import com.jmlearning.randomthings.gamingprogramming.utils.Matrix3x3f;
import com.jmlearning.randomthings.gamingprogramming.utils.Sprite;
import com.jmlearning.randomthings.gamingprogramming.utils.Vector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Ship {

    private static final int MAX_PARTICLES = 300;
    private float angle;
    private float acceleration;
    private float friction;
    private float maxVelocity;
    private float rotationDelta;
    private float currentAccel;
    private Vector2f position;
    private Vector2f velocity;
    private ArrayList<Particle> particles;
    private Random random;
    private PolygonWrapper wrapper;
    private boolean alive;
    private boolean invincible;
    private float invincibleDelta;
    private Sprite ship;
    private Sprite glow;
    private Vector2f[] polygon;
    private ArrayList<Vector2f[]> collisionList;
    private ArrayList<Vector2f> positionList;

    public Ship(PolygonWrapper wrapper) {

        this.wrapper = wrapper;
        friction = 0.25f;
        rotationDelta = (float) Math.toRadians(180.0);
        acceleration = 1.0f;
        maxVelocity = 0.5f;
        velocity = new Vector2f();
        position = new Vector2f();
        particles = new ArrayList <>();
        random = new Random();
        collisionList = new ArrayList <>();
        positionList = new ArrayList <>();
    }

    public void setPolygon(Vector2f[] polygon) {

        this.polygon = polygon;
    }

    public void setShipSprite(Sprite ship) {

        this.ship = ship;
    }

    public void setGlowSprite(Sprite glow) {

        this.glow = glow;
    }

    public float getWidth() {

        float min = Float.MAX_VALUE;
        float max = -Float.MAX_VALUE;

        for(Vector2f v : polygon) {

            min = Math.min(min, v.x);
            max = Math.max(max, v.x);
        }

        return Math.abs(min) + Math.abs(max);
    }

    public float getHeight() {

        float min = Float.MAX_VALUE;
        float max = -Float.MAX_VALUE;

        for(Vector2f v : polygon) {

            min = Math.min(min, v.y);
            max = Math.max(max, v.y);
        }

        return Math.abs(min) + Math.abs(max);
    }

    public void reset() {

        setAlive(true);
        setPosition(new Vector2f());
        setAngle(0.0f);
        positionList.clear();
        collisionList.clear();
        velocity = new Vector2f();
        particles.clear();
    }

    public boolean isAlive() {

        return alive;
    }

    public void setAlive(boolean alive) {

        this.alive = alive;
    }

    public void setPosition(Vector2f position) {

        this.position = position;
    }

    public void setAngle(float angle) {

        this.angle = angle;
    }

    public void rotateLeft(float delta) {

        angle += rotationDelta * delta;
    }

    public void rotateRight(float delta) {

        angle -= rotationDelta * delta;
    }

    public void setThrusting(boolean thrusting) {

        if(isAlive()) {

            currentAccel = thrusting ? acceleration : 0.0f;

            if(thrusting) {

                while(particles.size() < MAX_PARTICLES) {

                    particles.add(createRandomParticle());
                }
            }
        }
    }

    private Particle createRandomParticle() {

        Particle p = new Particle();
        p.setRadius(0.002f + random.nextFloat() * 0.004f);
        p.setLifeSpan(random.nextFloat() * 0.5f);

        switch(random.nextInt(5)) {

            case 0:
                p.setColor(Color.WHITE);
                break;

            case 1:
                p.setColor(Color.RED);
                break;

            case 2:
                p.setColor(Color.YELLOW);
                break;

            case 3:
                p.setColor(Color.ORANGE);
                break;

            case 4:
                p.setColor(Color.PINK);
                break;
        }

        int thrustAngle = 100;
        float a = (float) Math.toRadians(random.nextInt(
                thrustAngle) - (thrustAngle / 2));
        float velocity = random.nextFloat() * 0.375f;
        Vector2f bulletPos = position.add(Vector2f.polar(angle, -0.0325f));
        p.setPosition(bulletPos);
        p.setVector(angle + (float) Math.PI + a, velocity);

        return p;
    }

    public void update(float delta) {

        if(isAlive()) {

            updatePosition(delta);
            updateInvincible(delta);
            updateParticles(delta);
            collisionList.clear();
            Vector2f[] world = transformPolygon();
            collisionList.add(world);
            wrapper.wrapPolygon(world, collisionList);
            positionList.clear();
            positionList.add(position);
            wrapper.wrapPositions(world, position, positionList);
        }
    }

    private void updatePosition(float time) {

        Vector2f accel = Vector2f.polar(angle, currentAccel);
        velocity = velocity.add(accel.multiply(time));
        float maxSpeed = Math.min(maxVelocity / velocity.len(), 1.0f);
        velocity = velocity.multiply(maxSpeed);
        float slowDown = 1.0f - friction * time;
        velocity = velocity.multiply(slowDown);
        position = position.add(velocity.multiply(time));
        position = wrapper.wrapPosition(position);
    }


    public boolean isInvincible() {

        return invincible;
    }

    public void setInvincible() {

        invincible = true;
    }

    private void updateInvincible(float time) {

        if(isInvincible()) {

            invincibleDelta += time;

            if(invincibleDelta > 3.0f) {

                invincibleDelta = 0.0f;
                invincible = false;
            }
        }
    }

    private void updateParticles(float delta) {

        Iterator<Particle> part = particles.iterator();

        while(part.hasNext()) {

            Particle p = part.next();
            Vector2f bulletPos = position.add(Vector2f.polar(angle, -0.0325f));
            p.setPosition(bulletPos);
            p.update(delta);

            if(p.hasDied()) {

                part.remove();
            }
        }
    }

    private Vector2f[] transformPolygon() {

        Matrix3x3f mat = Matrix3x3f.rotate(angle);
        mat = mat.multiply(Matrix3x3f.translate(position));

        return transform(polygon, mat);
    }

    private Vector2f[] transform(Vector2f[] poly, Matrix3x3f mat) {

        Vector2f[] copy = new Vector2f[poly.length];

        for(int i = 0; i < poly.length; ++i) {

            copy[i] = mat.multiply(poly[i]);
        }

        return copy;
    }

    public Bullet launchBullet() {

        Vector2f bulletPos = position.add(Vector2f.polar(angle, 0.0325f));

        return new Bullet(bulletPos, angle);
    }

    public Vector2f isTouching(Asteroid asteroid) {

        if(isAlive()) {

            for(Vector2f[] poly : collisionList) {

                for(Vector2f v : poly) {

                    if(asteroid.contains(v)) {

                        return v;
                    }
                }
            }
        }

        return null;
    }

    public void draw(Graphics2D g, Matrix3x3f view) {

        if(isAlive()) {

            for(Vector2f pos : positionList) {

                if(isInvincible()) {

                    glow.render(g, view, pos, angle);
                }
                else {

                    ship.render(g, view, pos, angle);
                }
            }

            for(Particle p : particles) {

                p.draw(g, view);
            }
        }
    }
}

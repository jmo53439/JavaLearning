package com.jmlearning.randomthings.gamingprogramming.gameusingeverything.object;

import com.jmlearning.randomthings.gamingprogramming.utils.Matrix3x3f;
import com.jmlearning.randomthings.gamingprogramming.utils.Sprite;
import com.jmlearning.randomthings.gamingprogramming.utils.Vector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Asteroid {

    public enum Size {

        Large, Medium, Small;
    }

    private PolygonWrapper wrapper;
    private Size size;
    private Sprite sprite;
    private float rotation;
    private float rotationDelta;
    private Vector2f[] polygon;
    private Vector2f position;
    private Vector2f velocity;
    private ArrayList<Vector2f[]> collisionList;
    private ArrayList<Vector2f> positionList;

    public Asteroid(PolygonWrapper wrapper) {

        this.wrapper = wrapper;
        collisionList = new ArrayList <>();
        positionList = new ArrayList <>();
        velocity = getRandomVelocity();
        rotationDelta = getRandomRotationDelta();
    }

    private Vector2f getRandomVelocity() {

        float angle = getRandomRadians(0, 360);
        float radius = getRandomFloat(0.06f, 0.3f);

        return Vector2f.polar(angle, radius);
    }

    private float getRandomRadians(int minDegree, int maxDegree) {

        int random = new Random().nextInt(maxDegree - minDegree + 1);

        return (float) Math.toRadians(random + minDegree);
    }

    private float getRandomRotationDelta() {

        float radians = getRandomRadians(5, 45);

        return new Random().nextBoolean() ? radians : -radians;
    }

    private float getRandomFloat(float min, float max) {

        float random = new Random().nextFloat();

        return random * (max - min) + min;
    }

    public Sprite getSprite() {

        return sprite;
    }

    public void setSprite(Sprite sprite) {

        this.sprite = sprite;
    }

    public Vector2f[] getPolygon() {

        return polygon;
    }

    public void setPolygon(Vector2f[] polygon) {

        this.polygon = polygon;
    }

    public Vector2f getPosition() {

        return position;
    }

    public void setPosition(Vector2f position) {

        this.position = position;
    }

    public Size getSize() {

        return size;
    }

    public void setSize(Size size) {

        this.size = size;
    }

    public void update(float time) {

        position = position.add(velocity.multiply(time));
        position = wrapper.wrapPosition(position);
        rotation += rotationDelta * time;
        collisionList.clear();
        Vector2f[] world = transformPolygon();
        collisionList.add(world);
        wrapper.wrapPolygon(world, collisionList);
        positionList.clear();
        positionList.add(position);
        wrapper.wrapPositions(world, position, positionList);
    }

    private Vector2f[] transformPolygon() {

        Matrix3x3f mat = Matrix3x3f.rotate(rotation);
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

    public void draw(Graphics2D g, Matrix3x3f view) {

        for(Vector2f pos : positionList) {

            sprite.render(g, view, pos, rotation);
        }
    }

    public boolean contains(Vector2f point) {

        for(Vector2f[] polygon : collisionList) {

            if(pointInPolygon(point, polygon))
                return true;
        }

        return false;
    }

    private boolean pointInPolygon(Vector2f point, Vector2f[] polygon) {

        boolean inside = false;
        Vector2f start = polygon[polygon.length - 1];
        boolean startAbove = start.y >= point.y;

        for(int i = 0; i < polygon.length; ++i) {

            Vector2f end = polygon[i];
            boolean endAbove = end.y >= point.y;

            if(startAbove != endAbove) {

                float m = (end.y - start.y) / (end.x - start.x);
                float x = start.x + (point.y - start.y) / m;

                if(x >= point.x)
                    inside = !inside;
            }

            startAbove = endAbove;
            start = end;
        }

        return inside;
    }
}

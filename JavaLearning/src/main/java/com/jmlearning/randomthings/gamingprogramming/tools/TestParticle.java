package com.jmlearning.randomthings.gamingprogramming.tools;

import com.jmlearning.randomthings.gamingprogramming.utils.Matrix3x3f;
import com.jmlearning.randomthings.gamingprogramming.utils.Vector2f;

import java.awt.*;

public class TestParticle {

    private Vector2f pos;
    private Vector2f curPOS;
    private Vector2f vel;
    private Vector2f curVel;
    private Color color;
    private float lifeSpan;
    private float time;
    private float radius;

    public TestParticle() {

    }

    public void setPosition(Vector2f pos) {

        this.pos = pos;
    }

    public void setRadius(float radius) {

        this.radius = radius;
    }

    public void setVector(float angle, float r) {

        vel = Vector2f.polar(angle, r);
    }

    public void setColor(Color color) {

        this.color = color;
    }

    public void setLifeSpan(float lifeSpan) {

        this.lifeSpan = lifeSpan;
    }
    
    public void update(float delta) {
        
        time += delta;
        curVel = vel.multiply(time);
        curPOS = pos.add(curVel);
    }
    
    public void draw(Graphics2D g, Matrix3x3f view) {
        
        g.setColor(color);
        Vector2f topLeft = new Vector2f(curPOS.x - radius, curPOS.y + radius);
        topLeft = view.multiply(topLeft);
        Vector2f bottomRight = new Vector2f(curPOS.x + radius, curPOS.y - radius);
        bottomRight = view.multiply(bottomRight);
        
        int circleX = (int) topLeft.x;
        int circleY = (int) topLeft.y;
        int circleWidth = (int)(bottomRight.x - topLeft.x);
        int circleHeight = (int)(bottomRight.y - topLeft.y);
        
        g.fillOval(circleX, circleY, circleWidth, circleHeight);
    }
    
    public boolean hasDied() {
        
        return time > lifeSpan;
    }
}

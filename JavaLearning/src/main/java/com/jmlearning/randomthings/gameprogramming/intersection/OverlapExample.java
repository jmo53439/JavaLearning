package com.jmlearning.randomthings.gamingprogramming.intersection;

import com.jmlearning.randomthings.gamingprogramming.utils.Matrix3x3f;
import com.jmlearning.randomthings.gamingprogramming.utils.SimpleFramework;
import com.jmlearning.randomthings.gamingprogramming.utils.Vector2f;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class OverlapExample extends SimpleFramework {

    private Vector2f mousePosition;
    private Vector2f mouseDelta;
    private boolean clicked;
    private boolean dragging;

    private Vector2f min0, max0;
    private Vector2f min0Cpy, max0Cpy;
    private Vector2f rect0Position;
    private boolean rect0Collision;
    private boolean rect0Moving;

    private Vector2f min1, max1;
    private Vector2f min1Cpy, max1Cpy;
    private Vector2f rect1Position;
    private boolean rect1Collision;
    private boolean rect1Moving;

    private Vector2f c0, c0Position;
    private float r0;
    private boolean circle0Collision;
    private boolean circle0Moving;

    private Vector2f c1, c1Position;
    private float r1;
    private boolean circle1Collision;
    private boolean circle1Moving;
    
    public OverlapExample() {
        
        appHeight = 640;
        appWidth = 640;
        appTitle = "Overlap Example";
        appBackground = Color.WHITE;
        appFPSColor = Color.BLACK;
    }
    
    protected void initialize() {
        
        super.initialize();
        
        mousePosition = new Vector2f();
        min0 = new Vector2f(-0.25f, -0.25f);
        max0 = new Vector2f(0.25f, 0.25f);
        min1 = new Vector2f(-0.3f, -0.3f);
        max1 = new Vector2f(0.3f, 0.3f);
        r0 = 0.25f;
        r1 = 0.125f;
        reset();
    }

    private void reset() {

        rect0Position = new Vector2f();
        rect1Position = new Vector2f(0.25f, 0.5f);
        c0Position = new Vector2f(-0.60f, -0.60f);
        c1Position = new Vector2f(0.6f, 0.6f);
    }

    @Override
    protected void processInput(float delta) {

        super.processInput(delta);

        if(keyboard.keyDownOnce(KeyEvent.VK_SPACE))
            reset();

        Vector2f position = getWorldMousePosition();
        mouseDelta = position.subtract(mousePosition);
        mousePosition = position;
        clicked = mouse.buttonDownOnce(MouseEvent.BUTTON1);
        dragging = mouse.buttonDown(MouseEvent.BUTTON1);
    }

    @Override
    protected void updateObjects(float delta) {

        super.updateObjects(delta);

        // calculate min/max values
        Matrix3x3f matrix = Matrix3x3f.translate(rect0Position.x, rect0Position.y);
        min0Cpy = matrix.multiply(min0);
        max0Cpy = matrix.multiply(max0);
        matrix = Matrix3x3f.translate(rect1Position.x, rect1Position.y);
        min1Cpy = matrix.multiply(min1);
        max1Cpy = matrix.multiply(max1);

        // circle positions
        matrix = Matrix3x3f.translate(c0Position.x, c0Position.y);
        c0 = matrix.multiply(new Vector2f());
        matrix = Matrix3x3f.translate(c1Position.x, c1Position.y);
        c1 = matrix.multiply(new Vector2f());

        if(clicked && pointInRect(mousePosition, min0Cpy, max0Cpy))
            rect0Moving = true;

        if(clicked && pointInRect(mousePosition, min1Cpy, max1Cpy))
            rect1Moving = true;

        if(clicked && pointInCircle(mousePosition, c0Position, r0))
            circle0Moving = true;

        if(clicked && pointInCircle(mousePosition, c1Position, r1))
            circle1Moving = true;

        rect0Moving = rect0Moving && dragging;

        if(rect0Moving)
            rect0Position = rect0Position.add(mouseDelta);

        rect1Moving = rect1Moving && dragging;

        if(rect1Moving)
            rect1Position = rect1Position.add(mouseDelta);

        circle0Moving = circle0Moving && dragging;

        if(circle0Moving)
            c0Position = c0Position.add(mouseDelta);

        circle1Moving = circle1Moving && dragging;

        if(circle1Moving)
            c1Position = c1Position.add(mouseDelta);

        rect0Collision = false;
        rect1Collision = false;
        circle0Collision = false;
        circle1Collision = false;

        // intersect
        if(intersectRect(min0Cpy, max0Cpy, min1Cpy, max1Cpy)) {

            rect0Collision = true;
            rect1Collision = true;
        }

        if(intersectCircle(c0, r0, c1, r1)) {

            circle0Collision = true;
            circle1Collision = true;
        }

        if(intersectCircleRect(c0, r0, min0Cpy, max0Cpy)) {

            circle0Collision = true;
            rect0Collision = true;
        }

        if(intersectCircleRect(c0, r0, min1Cpy, max1Cpy)) {

            circle0Collision = true;
            rect1Collision = true;
        }

        if(intersectCircleRect(c1, r1, min0Cpy, max0Cpy)) {

            circle1Collision = true;
            rect0Collision = true;
        }

        if(intersectCircleRect(c1, r1, min1Cpy, max1Cpy)) {

            circle1Collision = true;
            rect1Collision = true;
        }
    }

    private boolean pointInRect(Vector2f p, Vector2f min, Vector2f max) {

        return p.x > min.x && p.x < max.x && p.y > min.y && p.y < max.y;
    }

    private boolean pointInCircle(Vector2f p, Vector2f c, float r) {

        Vector2f dist = p.subtract(c);
        return dist.lenSqr() < r * r;
    }

    private boolean intersectRect(Vector2f minA, Vector2f maxA, Vector2f minB, Vector2f maxB) {

        if(minA.x > maxB.x || minB.x > maxA.x)
            return false;

        if(minA.y > maxB.y || minB.y > maxA.y)
            return false;

        return true;
    }

    private boolean intersectCircle(Vector2f c0, float r0, Vector2f c1, float r1) {

        Vector2f c = c0.subtract(c1);
        float r = r0 + r1;

        return c.lenSqr() < r * r;
    }

    private boolean intersectCircleRect(Vector2f c, float r, Vector2f min, Vector2f max) {

        float d = 0.0f;

        if(c.x < min.x)
            d += (c.x - min.x) * (c.x - min.x);

        if(c.x > max.x)
            d += (c.x - max.x) * (c.x - max.x);

        if(c.y < min.y)
            d += (c.y - min.y) * (c.y - min.y);

        if(c.y > max.y)
            d += (c.y - max.y) * (c.y - max.y);

        return d < r * r;
    }

    @Override
    protected void render(Graphics g) {

        super.render(g);

        g.drawString("Dragging: " + dragging, 20, 35);
        g.drawString("Click and Hold to drag shapes", 20, 50);
        g.drawString("Press [Space] to reset", 20, 65);

        // render obj's
        g.setColor(rect0Collision ? Color.BLACK : Color.BLUE);
        drawRect(g, min0Cpy, max0Cpy);

        g.setColor(rect1Collision ? Color.BLACK : Color.BLUE);
        drawRect(g, min1Cpy, max1Cpy);

        g.setColor(circle0Collision ? Color.BLACK : Color.BLUE);
        drawOval(g, c0, r0);

        g.setColor(circle1Collision ? Color.BLACK : Color.BLUE);
        drawOval(g, c1, r1);
    }

    private void drawRect(Graphics g, Vector2f min, Vector2f max) {

        Matrix3x3f view = getViewportTransform();

        Vector2f topLeft = new Vector2f(min.x, max.y);
        topLeft = view.multiply(topLeft);

        Vector2f bottomRight = new Vector2f(max.x, min.y);
        bottomRight = view.multiply(bottomRight);

        int rectX = (int) topLeft.x;
        int rectY = (int) topLeft.y;
        int rectWidth = (int)(bottomRight.x - topLeft.x);
        int rectHeight = (int)(bottomRight.y - topLeft.y);

        g.drawRect(rectX, rectY, rectWidth, rectHeight);
    }

    private void drawOval(Graphics g, Vector2f center, float radius) {

        Matrix3x3f view = getViewportTransform();

        Vector2f topLeft = new Vector2f(center.x - radius, center.y + radius);
        topLeft = view.multiply(topLeft);

        Vector2f bottomRight = new Vector2f(center.x + radius, center.y - radius);
        bottomRight = view.multiply(bottomRight);

        int circleX = (int) topLeft.x;
        int circleY = (int) topLeft.y;
        int circleWidth = (int)(bottomRight.x - topLeft.x);
        int circleHeight = (int)(bottomRight.y - topLeft.y);

        g.drawOval(circleX, circleY, circleWidth, circleHeight);
    }

    public static void main(String[] args) {

        launchApp(new OverlapExample());
    }
}

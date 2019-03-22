package com.jmlearning.randomthings.gamingprogramming.intersection;

import com.jmlearning.randomthings.gamingprogramming.utils.Matrix3x3f;
import com.jmlearning.randomthings.gamingprogramming.utils.SimpleFramework;
import com.jmlearning.randomthings.gamingprogramming.utils.Utility;
import com.jmlearning.randomthings.gamingprogramming.utils.Vector2f;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class PointInPolygonExample extends SimpleFramework {

    private static final int MAX_POINTS = 10_000;
    private ArrayList<Vector2f> poly;
    private ArrayList<Vector2f> polyCpy;
    private ArrayList<Vector2f> inside;
    private ArrayList<Vector2f> outside;
    private Vector2f mousePosition;
    private boolean selected;
    private boolean winding;

    public PointInPolygonExample() {

        appWidth = 640;
        appHeight = 640;
        appTitle = "Point in Polygon Example";
        appBackground = Color.BLACK;
        appFPSColor = Color.GREEN;
    }

    @Override
    protected void initialize() {

        super.initialize();

        poly = new ArrayList <>();
        polyCpy = new ArrayList <>();
        inside = new ArrayList <>();
        outside = new ArrayList <>();
        mousePosition = new Vector2f();
    }

    @Override
    protected void processInput(float delta) {

        super.processInput(delta);
        mousePosition = getWorldMousePosition();

        if(keyboard.keyDownOnce(KeyEvent.VK_SPACE))
            winding = !winding;

        if(mouse.buttonDownOnce(MouseEvent.BUTTON1))
            poly.add(mousePosition);

        if(mouse.buttonDownOnce(MouseEvent.BUTTON3))
            poly.clear();
    }

    @Override
    protected void updateObjects(float delta) {

        super.updateObjects(delta);

        // mouse inside polygon
        selected = pointInPolygon(mousePosition, poly, winding);

        // test
        Random random = new Random();

        inside.clear();
        outside.clear();

        for(int i = 0; i < MAX_POINTS; ++i) {

            float x = random.nextFloat() * 2.0f - 1.0f;
            float y = random.nextFloat() * 2.0f - 1.0f;

            Vector2f point = new Vector2f(x, y);

            if(pointInPolygon(point, poly, winding)) {

                inside.add(point);
            }
            else {

                outside.add(point);
            }
        }
    }

    private boolean pointInPolygon(Vector2f point, List<Vector2f> poly, boolean winding) {

        int inside = 0;

        if(poly.size() > 2) {

            Vector2f start = poly.get(poly.size() - 1);
            boolean startAbove = start.y >= point.y;

            for(int i = 0; i < poly.size(); ++i) {

                Vector2f end = poly.get(i);
                boolean endAbove = end.y >= point.y;

                if(startAbove != endAbove) {

                    float m = (end.y - start.y) / (end.x - start.x);
                    float x = start.x + (point.y - start.y) / m;

                    if(x >= point.x) {

                        if(winding) {

                            inside += startAbove ? 1 : -1;
                        }
                        else {

                            inside = inside == 1 ? 0 : 1;
                        }
                    }
                }

                startAbove = endAbove;
                start = end;
            }
        }

        return inside != 0;
    }

    @Override
    protected void render(Graphics g) {

        super.render(g);

        g.drawString("Winding: " + (winding ? "ON" : "OFF"), 20, 35);
        String mouse = String.format("Mouse: (%.2f,%.2f)", mousePosition.x, mousePosition.y);
        g.drawString(mouse, 20, 50);
        g.drawString("Left Click to add points", 20, 65);
        g.drawString("Right Click to clear points", 20, 80);
        g.drawString("Press [Space] to toggle winding", 20, 95);

        Matrix3x3f view = getViewportTransform();

        // test
        if(poly.size() > 1) {

            polyCpy.clear();

            for(Vector2f vector : poly) {

                polyCpy.add(view.multiply(vector));
            }

            g.setColor(selected ? Color.GREEN : Color.BLUE);
            Utility.drawPolygon(g, polyCpy);
        }

        // draw inside point blue, outside red
        g.setColor(Color.BLUE);

        for(Vector2f vector : inside) {

            Vector2f point = view.multiply(vector);
            g.fillRect((int) point.x, (int) point.y, 1, 1);
        }

        g.setColor(Color.RED);

        for(Vector2f vector : outside) {

            Vector2f point = view.multiply(vector);
            g.fillRect((int) point.x, (int) point.y, 1, 1);
        }
    }

    public static void main(String[] args) {

        launchApp(new PointInPolygonExample());
    }
}

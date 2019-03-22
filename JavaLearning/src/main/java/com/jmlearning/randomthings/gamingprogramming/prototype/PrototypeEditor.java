package com.jmlearning.randomthings.gamingprogramming.prototype;

import com.jmlearning.randomthings.gamingprogramming.utils.Matrix3x3f;
import com.jmlearning.randomthings.gamingprogramming.utils.SimpleFramework;
import com.jmlearning.randomthings.gamingprogramming.utils.Vector2f;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PrototypeEditor extends SimpleFramework {

    private ArrayList<Vector2f> polygon;
    private Vector2f mousePosition;
    private boolean closed;

    public PrototypeEditor() {

        appTitle = "Prototype Editor 1.0";
        appWidth = 640;
        appHeight = 640;
        appBackground = Color.WHITE;
        appFPSColor = Color.BLACK;
    }

    protected void initialize() {

        super.initialize();
        polygon = new ArrayList <Vector2f>();
    }

    protected void processInput(float delta) {

        super.processInput(delta);
        mousePosition = getWorldMousePosition();

        if(mouse.buttonDownOnce(MouseEvent.BUTTON1))
            polygon.add(mousePosition);

        if(mouse.buttonDownOnce(MouseEvent.BUTTON2))
            closed = !closed;

        if(mouse.buttonDownOnce(MouseEvent.BUTTON3)) {

            if(!polygon.isEmpty()) {

                polygon.remove(polygon.size() - 1);
            }
        }

        if(keyboard.keyDownOnce(KeyEvent.VK_C))
            polygon.clear();

        if(keyboard.keyDownOnce(KeyEvent.VK_SPACE))
            printPolygon();
    }

    private void printPolygon() {

        System.out.println("Vector2f[] v = new Vector2f[] { ");

        for(Vector2f v : polygon) {

            System.out.print(" new Vector2f(");
            System.out.print(v.x + "f, ");
            System.out.print(v.y + "f)");
            System.out.println(",");
        }

        System.out.println("};");
    }

    protected void render(Graphics g) {

        super.render(g);
        g.drawString("Close Polygon: " + closed, 20, 35);
        g.drawString("Left Click: Add Point", 20, 50);
        g.drawString("Right Click: Remove Point", 20, 65);
        g.drawString("Mouse Wheel: Toggle Close", 20, 80);
        g.drawString("C Key: Clear Polygon", 20, 95);
        g.drawString("[Space]: Print Polygon", 20, 110);
        drawAxisLines(g);
        drawPolygon(g);
    }

    private void drawAxisLines(Graphics g) {

        g.setColor(Color.BLUE);
        Vector2f left = new Vector2f(appWorldWidth / 2.0f, 0.0f);
        Vector2f right = new Vector2f(-left.x, 0.0f);
        drawLine(g, left, right);
        Vector2f top = new Vector2f(0.0f, appWorldHeight / 2.0f);
        Vector2f bottom = new Vector2f(0.0f, -top.y);
        drawLine(g, top, bottom);
    }

    private void drawPolygon(Graphics g) {

        g.setColor(Color.BLACK);

        if(polygon.size() == 1)
            drawPoint(g, polygon.get(0));

        for(int i = 0; i < polygon.size() - 1; ++i) {

            drawLine(g, polygon.get(i), polygon.get(i + 1));
        }

        if(closed && polygon.size() > 1) {

            Vector2f P = polygon.get(polygon.size() - 1);
            Vector2f S = polygon.get(0);
            drawLine(g, S, P);
        }

        if(!(polygon.isEmpty() || closed)) {

            Vector2f P = polygon.get(polygon.size() - 1);
            Vector2f S = mousePosition;
            drawLine(g, S, P);
        }
    }

    private void drawLine(Graphics g, Vector2f v0, Vector2f v1) {

        Matrix3x3f view = getViewportTransform();
        Vector2f S = view.multiply(v0);
        Vector2f P = view.multiply(v1);
        g.drawLine((int) S.x, (int) S.y, (int) P.x, (int) P.y);
    }

    private void drawPoint(Graphics g, Vector2f v) {

        Matrix3x3f view = getViewportTransform();
        Vector2f s = view.multiply(v);
        g.drawRect((int) s.x, (int) s.y, 1, 1);
    }

    public static void main(String[] args) {

        launchApp(new PrototypeEditor());
    }
}

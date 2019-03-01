package com.jmlearning.randomthings.gamingprogramming.collision;

import com.jmlearning.randomthings.gamingprogramming.utils.Matrix3x3f;
import com.jmlearning.randomthings.gamingprogramming.utils.SimpleFramework;
import com.jmlearning.randomthings.gamingprogramming.utils.Vector2f;

import java.awt.*;
import java.awt.event.MouseEvent;

public class LineLineIntersectionExample extends SimpleFramework {

    private static final float EPSILON = 0.00001f;
    private Vector2f A, B, C, D;
    private Vector2f intersect;

    public LineLineIntersectionExample() {

        appTitle = "Line Line Intersection";
        appBackground = Color.WHITE;
        appFPSColor = Color.BLACK;
    }

    @Override
    protected void processInput(float delta) {

        super.processInput(delta);

        D = getWorldMousePosition();

        if(mouse.buttonDownOnce(MouseEvent.BUTTON1)) {

            if(A == null) {

                A = D;
            }
            else if(B == null) {

                B = D;
            }
            else {

                C = D;
            }
        }

        if(mouse.buttonDownOnce(MouseEvent.BUTTON3)) {

            A = B = C = D = null;
            intersect = null;
        }
    }

    @Override
    protected void updateObjects(float delta) {

        super.updateObjects(delta);

        if(!(A == null || B == null || C == null || D == null)) {

            float[] t = twoLineIntersection(A, B, C, D);

            if(t == null) {

                intersect = null;
            }
            else {

                intersect = A.add(B.subtract(A).multiply(t[0]));
            }
        }
    }

    private float[] twoLineIntersection(
            Vector2f A, Vector2f B, Vector2f C, Vector2f D) {

        Vector2f DsubC = D.subtract(C);
        Vector2f DsubCperp = DsubC.perp();
        Vector2f AsubB = A.subtract(B);
        float f = DsubCperp.dot(AsubB);

        if(Math.abs(f) < EPSILON) {

            // zero denominator
            return null;
        }

        Vector2f AsubC = A.subtract(C);
        float d = DsubCperp.dot(AsubC);

        if(f > 0) {

            if(d < 0 || d > f)
                return null;
        }
        else {

            if(d > 0 || d < f)
                return null;
        }

        Vector2f BsubA = B.subtract(A);
        Vector2f BsubAperp = BsubA.perp();
        float e = BsubAperp.dot(AsubC);

        if(f > 0) {

            if(e < 0 || e > f)
                return null;
        }
        else {

            if(e > 0 || e < f)
                return null;
        }

        return new float[] {d / f, e / f};
    }

    @Override
    protected void render(Graphics g) {

        super.render(g);

        g.drawString("Left-Click to place points", 20, 35);
        g.drawString("Right-Click to clear points", 20, 50);
        g.setColor(intersect == null ? Color.RED : Color.GREEN);
        drawLine(g, A, B);
        drawLine(g, C, D);
        drawCrossHairs(g);
    }

    private void drawLine(Graphics g, Vector2f v0, Vector2f v1) {

        if(v0 != null) {

            Matrix3x3f view = getViewportTransform();
            Vector2f va = view.multiply(v0);

            if(v1 == null) {

                g.fillRect((int) va.x, (int) va.y, 1, 1);
            }
            else {

                Vector2f vb = view.multiply(v1);
                g.drawLine((int) va.x, (int) va.y, (int) vb.x, (int) vb.y);
            }
        }
    }

    private void drawCrossHairs(Graphics g) {

        if(intersect != null) {

            Matrix3x3f view = getViewportTransform();
            Vector2f intView = view.multiply(intersect);
            g.setColor(Color.BLACK);
            int x = (int) intView.x;
            int y = (int) intView.y;
            g.drawLine(x, y - 20, x, y + 20);
            g.drawLine(x - 20, y, x + 20, y);
        }
    }

    public static void main(String[] args) {

        launchApp(new LineLineIntersectionExample());
    }
}

package com.jmlearning.randomthings.gamingprogramming.intersection;

import com.jmlearning.randomthings.gamingprogramming.utils.Matrix3x3f;
import com.jmlearning.randomthings.gamingprogramming.utils.SimpleFramework;
import com.jmlearning.randomthings.gamingprogramming.utils.Utility;
import com.jmlearning.randomthings.gamingprogramming.utils.Vector2f;

import java.awt.*;
import java.awt.event.KeyEvent;

public class RectRectOverlapExample extends SimpleFramework {

    private Vector2f[] rect;
    private Vector2f[] rect0;
    private Vector2f rect0Position;
    private float rect0Angle;
    private Vector2f[] rect1;
    private Vector2f rect1Position;
    private float rect1Angle;
    private boolean intersection;

    public RectRectOverlapExample() {

        appWidth = 640;
        appHeight = 640;
        appSleep = 10L;
        appTitle = "Rect Rect Overlap";
        appBackground = Color.WHITE;
        appFPSColor = Color.BLACK;
    }

    @Override
    protected void initialize() {

        super.initialize();

        rect = new Vector2f[] {new Vector2f(-0.25f, 0.25f),
                new Vector2f(0.25f, 0.25f),
                new Vector2f(0.25f, -0.25f),
                new Vector2f(-0.25f, -0.25f)};

        rect0 = new Vector2f[rect.length];
        rect0Position = new Vector2f();
        rect0Angle = 0.0f;
        rect1 = new Vector2f[rect.length];
        rect1Position = new Vector2f();
        rect1Angle = 0.0f;
    }

    @Override
    protected void processInput(float delta) {

        super.processInput(delta);

        // convert mouse coordinates
        rect1Position = getWorldMousePosition();

        // rotate rectangles
        if(keyboard.keyDown(KeyEvent.VK_A))
            rect0Angle += (float)(Math.PI / 4.0 * delta);

        if(keyboard.keyDown(KeyEvent.VK_S))
            rect0Angle -= (float)(Math.PI / 4.0 * delta);

        if(keyboard.keyDown(KeyEvent.VK_Q))
            rect1Angle += (float)(Math.PI / 4.0 * delta);

        if(keyboard.keyDown(KeyEvent.VK_W))
            rect1Angle -= (float)(Math.PI / 4.0 * delta);
    }

    protected void updateObjects(float delta) {

        super.updateObjects(delta);

        Matrix3x3f matrix = Matrix3x3f.identity();
        matrix = matrix.multiply(Matrix3x3f.rotate(rect0Angle));
        matrix = matrix.multiply(Matrix3x3f.translate(rect0Position));

        for(int i = 0; i < rect.length; ++i) {

            rect0[i] = matrix.multiply(rect[i]);
        }

        matrix = Matrix3x3f.identity();
        matrix = matrix.multiply(Matrix3x3f.rotate(rect1Angle));
        matrix = matrix.multiply(Matrix3x3f.translate(rect1Position));

        for(int i = 0; i < rect.length; ++i) {

            rect1[i] = matrix.multiply(rect[i]);
        }

        intersection = rectRectIntersection(rect0, rect1);
    }

    private boolean rectRectIntersection(Vector2f[] A, Vector2f[] B) {

        Vector2f N0 = A[0].subtract(A[1]).divide(2.0f);
        Vector2f N1 = A[1].subtract(A[2]).divide(2.0f);
        Vector2f CA = A[0].add(A[2]).divide(2.0f);

        float D0 = N0.len();
        float D1 = N1.len();
        N1 = N1.divide(D1);
        N0 = N0.divide(D0);

        Vector2f N2 = B[0].subtract(B[1]).divide(2.0f);
        Vector2f N3 = B[1].subtract(B[2]).divide(2.0f);
        Vector2f CB = B[0].add(B[2]).divide(2.0f);

        float D2 = N2.len();
        float D3 = N3.len();
        N2 = N2.divide(D2);
        N3 = N3.divide(D3);

        Vector2f C = CA.subtract(CB);

        float DA = D0;
        float DB = D2 * Math.abs(N2.dot(N0));
        DB += D3 * Math.abs(N3.dot(N0));

        if(DA + DB < Math.abs(C.dot(N0)))
            return false;

        DA = D1;
        DB = D2 * Math.abs(N2.dot(N1));
        DB += D3 * Math.abs(N3.dot(N1));

        if(DA + DB < Math.abs(C.dot(N1)))
            return false;

        DA = D2;
        DB = D0 * Math.abs(N0.dot(N2));
        DB += D1 * Math.abs(N1.dot(N2));

        if(DA + DB < Math.abs(C.dot(N2)))
            return false;

        DA = D3;
        DB = D0 * Math.abs(N0.dot(N3));
        DB += D1 * Math.abs(N1.dot(N3));

        if(DA + DB < Math.abs(C.dot(N3)))
            return false;

        return true;
    }

    @Override
    protected void render(Graphics g) {

        super.render(g);

        g.drawString("Intersection: " + intersection, 20, 35);
        g.drawString("[A, S] keys used to rotate rect 1", 20, 50);
        g.drawString("[Q, W] keys used to rotate rect 2", 20, 65);

        g.setColor(intersection ? Color.BLUE : Color.BLACK);
        Matrix3x3f view = getViewportTransform();

        for(int i = 0; i < rect0.length; ++i) {

            rect0[i] = view.multiply(rect0[i]);
        }
        Utility.drawPolygon(g, rect0);

        for(int i = 0; i < rect1.length; ++i) {

            rect1[i] = view.multiply(rect1[i]);
        }
        Utility.drawPolygon(g, rect1);
    }

    public static void main(String[] args) {

        launchApp(new RectRectOverlapExample());
    }
}

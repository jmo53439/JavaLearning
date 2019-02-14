package com.jmlearning.randomthings.gamingprogramming.prototype;

import com.jmlearning.randomthings.gamingprogramming.utils.Matrix3x3f;
import com.jmlearning.randomthings.gamingprogramming.utils.SimpleFramework;
import com.jmlearning.randomthings.gamingprogramming.utils.Utility;
import com.jmlearning.randomthings.gamingprogramming.utils.Vector2f;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class ScreenWrapExample extends SimpleFramework {

    private Vector2f position;
    private Vector2f[] polygon;
    private ArrayList<Vector2f[]> renderList;
    private PolygonWrapper wrapper;

    public ScreenWrapExample() {

        appBorderScale = 0.9f;
        appWidth = 640;
        appHeight = 640;
        appMaintainRatio = true;
        appTitle = "Screen Wrap Example";
        appBackground = Color.WHITE;
        appFPSColor = Color.BLACK;
    }

    @Override
    protected void initialize() {

        super.initialize();
        mouse.setRelative(true);
//        renderList = new ArrayList <Vector2f[]>();
        renderList = new ArrayList <>();
        wrapper = new PolygonWrapper(appWorldWidth, appWorldHeight);
        polygon = new Vector2f[] {new Vector2f(-0.125f, 0.125f),
                new Vector2f(0.125f, 0.125f),
                new Vector2f(0.125f, -0.125f),
                new Vector2f(-0.125f, -0.125f)};
        position = new Vector2f();
    }

    @Override
    protected void processInput(float delta) {

        super.processInput(delta);

        if(mouse.isRelative()) {

            Vector2f v = getRelativeWorldMousePosition();
            position = position.add(v);
        }
        else {

            position = getWorldMousePosition();
        }

        if(keyboard.keyDownOnce(KeyEvent.VK_SPACE)) {

            mouse.setRelative(!mouse.isRelative());

            if(mouse.isRelative()) {

                position = new Vector2f();
            }
        }
    }

    @Override
    protected void updateObjects(float delta) {

        super.updateObjects(delta);
        renderList.clear();
        position = wrapper.wrapPosition(position);
        Vector2f[] world = transform(polygon, Matrix3x3f.translate(position));
        renderList.add(world);
        wrapper.wrapPolygon(world, renderList);
    }

    private Vector2f[] transform(Vector2f[] polygon, Matrix3x3f matrix) {

        Vector2f[] copy = new Vector2f[polygon.length];

        for(int i = 0; i < polygon.length; ++i) {

            copy[i] = matrix.multiply(polygon[i]);
        }

        return copy;
    }

    @Override
    protected void render(Graphics g) {

        super.render(g);
        g.drawString("Press [Space] to toggle mouse", 20, 35);
        Matrix3x3f view = getViewportTransform();

        for(Vector2f[] toRender : renderList) {

            for(int i = 0; i < toRender.length; ++i) {

                toRender[i] = view.multiply(toRender[i]);
            }

            Utility.drawPolygon(g, toRender);
        }
    }

    public static void main(String[] args) {

        launchApp(new ScreenWrapExample());
    }
}

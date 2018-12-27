package com.jmlearning.simplegames.simplefrogger;

import java.awt.*;

public class Car {

    private double x;
    private double y;
    private double width;
    private double dx;
    private Color color;

    public Car(double x, double y, double width, double dx) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.dx = dx;
        color = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void drawCar(StdDraw stdDraw) {

        stdDraw.setPenColor(color);
        stdDraw.filledRectangle(x, y, width, width / 3);
    }

    public void moveCar() {

        x += dx;
    }

    public boolean didHitFrog(Frog frog) {

        double fx = frog.getX();
        double fy = frog.getY();

        double maxX = x + width;
        double minX = x - width;
        double maxY = y + width / 3;
        double minY = y - width / 3;

        boolean didHit = (fx <= maxX && fx >= minX && fy <= maxY && fy >= minY);
        return didHit;
    }
}

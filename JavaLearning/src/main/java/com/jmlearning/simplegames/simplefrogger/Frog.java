package com.jmlearning.simplegames.simplefrogger;

public class Frog {

    private double x;
    private double y;
    private double width;
    private double stepWidth;
    private int numLives;

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;

    public Frog(double x, double y) {

        this.x = x;
        this.y = y;
        width = .01;
        stepWidth = .005;
        numLives = 4;
    }

    public double getX() {

        return x;
    }

    public double getY() {

        return y;
    }

    public int getNumLives() {

        return numLives;
    }

    public void drawFrog(StdDraw stdDraw) {

        stdDraw.setPenColor(stdDraw.GREEN);
        stdDraw.filledCircle(x, y, width);
    }

    public void moveFrog(int direction) {

        switch(direction) {

            case LEFT:

                if(x > 0)

                    x -= stepWidth;

                break;

            case RIGHT:

                if(x < 1)

                    x += stepWidth;

                break;

            case UP:

                if(y < 1)

                    y += stepWidth;

                break;

            case DOWN:

                if(y > 0)

                    y -= stepWidth;

                break;
        }
    }
}

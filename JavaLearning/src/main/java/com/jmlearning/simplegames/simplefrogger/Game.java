package com.jmlearning.simplegames.simplefrogger;

import java.awt.event.KeyEvent;

public class Game {

    static Frog frog = null;
    static Lane[] lanes = null;

    public void setup() {

        frog = new Frog(.5, 0);
        lanes = new Lane[10];
        lanes[0] = new Lane(0.1);
        lanes[1] = new Lane(0.2);
        lanes[2] = new Lane(0.3);
        lanes[3] = new Lane(0.4);
        lanes[4] = new Lane(0.5);
        lanes[5] = new Lane(0.6);
        lanes[6] = new Lane(0.7);
        lanes[7] = new Lane(0.8);
        lanes[8] = new Lane(0.9);
        lanes[9] = new Lane(1.0);
    }

    public void draw() {

        StdDraw.clear(StdDraw.WHITE);

        frog.drawFrog(new StdDraw());

        if(StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) {

            frog.moveFrog(Frog.LEFT);
        }
        else if(StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) {

            frog.moveFrog(Frog.RIGHT);
        }
        else if(StdDraw.isKeyPressed(KeyEvent.VK_UP)) {

            frog.moveFrog(Frog.UP);
        }
        else if(StdDraw.isKeyPressed(KeyEvent.VK_DOWN)) {

            frog.moveFrog(Frog.DOWN);
        }

        for(int i = 0; i < lanes.length; i++) {

            lanes[i].drawLane();
            lanes[i].stepCars();
        }

        for(int i = 0; i < lanes.length; i++) {

            if(lanes[i].didHitFrog(frog))

                StdDraw.clear(StdDraw.RED);
        }
    }

    public void mousePressed() {

    }

    public void mouseClicked() {

    }
}

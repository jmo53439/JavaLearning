package com.jmlearning.simplegames.simplefrogger;

public class Frogger {

    public static void main(String[] args) {

        Game game = new Game();
        game.setup();
        boolean justPressedMouse = false;

        while(true) {

            game.draw();

            if(StdDraw.mousePressed()) {

                if(!justPressedMouse) {

                    game.mouseClicked();
                }

                game.mousePressed();
                justPressedMouse = true;
            }
            else {

                justPressedMouse = false;
            }

            StdDraw.show(20);

            if(!(true))

                break;
        }
    }
}

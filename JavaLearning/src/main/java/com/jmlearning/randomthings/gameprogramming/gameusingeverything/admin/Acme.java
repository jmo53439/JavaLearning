package com.jmlearning.randomthings.gamingprogramming.gameusingeverything.admin;

import com.jmlearning.randomthings.gamingprogramming.gameusingeverything.FinishedProduct;
import com.jmlearning.randomthings.gamingprogramming.gameusingeverything.object.Ship;
import com.jmlearning.randomthings.gamingprogramming.utils.Utility;

import java.awt.*;

public class Acme {

    private FinishedProduct game;
    private Ship ship;

    public Acme(FinishedProduct game) {

        this.game = game;
    }

    public void setShip(Ship ship) {

        this.ship = ship;
    }

    public void drawScore(Graphics2D g, int score) {

        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        String toShow = "" + score;

        while(toShow.length() < 6) {

            toShow = "0" + toShow;
        }

        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.GREEN);
        Utility.drawCenteredString(g, game.getScreenWidth(), 0, toShow);
    }
}

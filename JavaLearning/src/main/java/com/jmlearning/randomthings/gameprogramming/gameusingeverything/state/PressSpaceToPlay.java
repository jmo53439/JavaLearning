package com.jmlearning.randomthings.gamingprogramming.gameusingeverything.state;

import com.jmlearning.randomthings.gamingprogramming.utils.Matrix3x3f;
import com.jmlearning.randomthings.gamingprogramming.utils.Utility;

import java.awt.*;

public class PressSpaceToPlay extends AttractState {

    @Override
    protected AttractState getState() {

        return new HighScore();
    }

    public void render(Graphics2D g, Matrix3x3f view) {

        super.render(g, view);

        int width = game.getScreenWidth();
        int height = game.getScreenHeight();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.setColor(Color.GREEN);
        String[] msg = {

                "S P A C E  R O C K S",
                "",
                "",
                "",
                "P R E S S  S P A C E  T O  P L A Y",
                "",
                "P R E S S  E S C  T O  E X I T"
        };

        Utility.drawCenteredString(g, width, height / 3, msg);
    }
}

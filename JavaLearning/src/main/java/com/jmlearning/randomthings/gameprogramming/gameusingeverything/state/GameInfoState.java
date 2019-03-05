package com.jmlearning.randomthings.gamingprogramming.gameusingeverything.state;

import com.jmlearning.randomthings.gamingprogramming.utils.Matrix3x3f;
import com.jmlearning.randomthings.gamingprogramming.utils.Utility;

import java.awt.*;

public class GameInfoState extends AttractState {

    private static final String[] gameInfo = {

            "Space Rocks - version 2.0",
            "Programmed by: Tim Wright",
            "",
            "Special thanks to:",
            "Michaela Wright",
            "Destiny Tamboer",
            "Jimmi Wright",
            "",
            "Went through and manually coded everything by: Joseph Moy"
    };

    @Override
    protected AttractState getState() {

        return new PressSpaceToPlay();
    }

    @Override
    public void render(Graphics2D g, Matrix3x3f view) {

        super.render(g, view);

        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.setColor(Color.GREEN);
        Utility.drawCenteredString(g, game.getScreenWidth(),
                game.getScreenHeight() / 3, gameInfo);
    }
}

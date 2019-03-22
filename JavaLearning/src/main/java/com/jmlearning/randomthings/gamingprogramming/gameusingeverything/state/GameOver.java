package com.jmlearning.randomthings.gamingprogramming.gameusingeverything.state;

import com.jmlearning.randomthings.gamingprogramming.gameusingeverything.object.Asteroid;
import com.jmlearning.randomthings.gamingprogramming.utils.Matrix3x3f;
import com.jmlearning.randomthings.gamingprogramming.utils.Utility;

import java.awt.*;
import java.util.List;

public class GameOver extends AttractState {

    GameState state;

    public GameOver(List<Asteroid> asteroids, GameState state) {

        super(asteroids);
        this.state = state;
    }

    @Override
    protected float getWaitTime() {

        return 3.0f;
    }

    @Override
    protected AttractState getState() {

        if(highScoreManager.newHighScore(state)) {

            return new EnterHighScoreName(state);
        }
        else {

            return new HighScore();
        }
    }
    
    @Override
    public void render(Graphics2D g, Matrix3x3f view) {
        
        super.render(g, view);
        
        acme.drawScore(g, state.getScore());
        Utility.drawCenteredString(g, game.getScreenWidth(),
                game.getScreenHeight() / 3, "G A M E O V E R");
    }
}

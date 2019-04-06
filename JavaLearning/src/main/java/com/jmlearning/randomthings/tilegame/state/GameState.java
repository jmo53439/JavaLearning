package com.jmlearning.randomthings.tilegame.states;

import com.jmlearning.randomthings.tilegame.gfx.Assets;

import java.awt.*;

public class GameState extends State {
    
    public GameState() {
    
    }
    
    @Override
    public void tick() {
    
    }
    
    @Override
    public void render(Graphics g) {
    
        g.drawImage(Assets.dirt, 0, 0, null);
    }
}

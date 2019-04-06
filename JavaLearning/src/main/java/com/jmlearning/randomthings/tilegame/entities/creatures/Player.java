package com.jmlearning.randomthings.tilegame.entities.creatures;

import com.jmlearning.randomthings.tilegame.gfx.Assets;

import java.awt.*;

public class Player extends Creature {
    
    public Player(float x, float y) {
        
        super(x, y);
    }
    
    @Override
    public void tick() {
    
    }
    
    @Override
    public void render(Graphics g) {
    
        g.drawImage(Assets.player, (int) x, (int) y, null);
    }
}

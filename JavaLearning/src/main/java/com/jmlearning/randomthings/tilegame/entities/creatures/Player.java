package com.jmlearning.randomthings.tilegame.entities.creatures;

import com.jmlearning.randomthings.tilegame.Handler;
import com.jmlearning.randomthings.tilegame.gfx.Assets;

import java.awt.*;

public class Player extends Creature {
    
    public Player(Handler handler, float x, float y) {
        
        super(handler, x, y, DEFAULT_CREATURE_WIDTH, DEFAULT_CREATURE_HEIGHT);
    }
    
    @Override
    public void tick() {
    
        getInput();
        move();
        handler.getGameCamera().centerOnEntity(this);
    }
    
    private void getInput() {
        
        xMove = 0;
        yMove = 0;
        
        if(handler.getKeyManager().up)
            yMove = -speed;
        
        if(handler.getKeyManager().down)
            yMove = speed;
        
        if(handler.getKeyManager().left)
            xMove = -speed;
        
        if(handler.getKeyManager().right)
            xMove = speed;
    }
    
    @Override
    public void render(Graphics g) {
    
        g.drawImage(Assets.player, (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()),
                width, height, null);
    }
}

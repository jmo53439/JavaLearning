package com.jmlearning.randomthings.tilegame.entities.creatures;

import com.jmlearning.randomthings.tilegame.Handler;
import com.jmlearning.randomthings.tilegame.gfx.Animation;
import com.jmlearning.randomthings.tilegame.gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Creature {
    
    private Animation animateUp, animateDown, animateLeft, animateRight;
    
    public Player(Handler handler, float x, float y) {
        
        super(handler, x, y, DEFAULT_CREATURE_WIDTH, DEFAULT_CREATURE_HEIGHT);
        
        bounds.x = 22;
        bounds.y = 44;
        bounds.width = 19;
        bounds.height = 19;
        
        animateUp = new Animation(500, Assets.playerUp);
        animateDown = new Animation(500, Assets.playerDown);
        animateLeft = new Animation(500, Assets.playerLeft);
        animateRight = new Animation(500, Assets.playerRight);
    }
    
    @Override
    public void tick() {
    
        // movement
        getInput();
        move();
        handler.getGameCamera().centerOnEntity(this);
        
        // animations
        animateUp.tick();
        animateDown.tick();
        animateLeft.tick();
        animateRight.tick();
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
    
        g.drawImage(getCurrentAnimationFrame(),
                (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()),
                width, height, null);
                
//        g.setColor(Color.RED);
//        g.fillRect((int)(x + bounds.x - handler.getGameCamera().getxOffset()),
//                (int)(y + bounds.y - handler.getGameCamera().getyOffset()),
//                bounds.width, bounds.height);
    }
    
    private BufferedImage getCurrentAnimationFrame() {
        
        if(xMove < 0) {
            
            return animateLeft.getCurrentFrame();
        }
        else if(xMove > 0) {
            
            return animateRight.getCurrentFrame();
        }
        else if(yMove < 0) {
            
            return animateUp.getCurrentFrame();
        }
        else {
            
            return animateDown.getCurrentFrame();
        }
    }
}

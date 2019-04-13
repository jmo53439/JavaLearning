package com.jmlearning.randomthings.tilegame.entities.creatures;

import com.jmlearning.randomthings.tilegame.Handler;
import com.jmlearning.randomthings.tilegame.entities.Entity;
import com.jmlearning.randomthings.tilegame.gfx.Animation;
import com.jmlearning.randomthings.tilegame.gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Creature {
    
    private Animation animateUp, animateDown, animateLeft, animateRight;
    private long lastAttackTimer, attackCooldown = 800, attackTimer = attackCooldown;
    
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
        
        checkAttacks();
    }
    
    private void checkAttacks() {
        
        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();
        
        if(attackTimer < attackCooldown)
            return;
        
        Rectangle cb = getCollisionBounds(0, 0);
        Rectangle ar = new Rectangle();
        int arSize = 20;
        ar.width = arSize;
        ar.height = arSize;
        
        if(handler.getKeyManager().aUp) {
            
            ar.x = cb.x + cb.width / 2 - arSize / 2;
            ar.y = cb.y - arSize;
        }
        else if(handler.getKeyManager().aDown) {
            
            ar.x = cb.x + cb.width / 2 - arSize / 2;
            ar.y = cb.y + cb.height;
        }
        else if(handler.getKeyManager().aLeft) {
    
            ar.x = cb.x - arSize;
            ar.y = cb.y + cb.height / 2 - arSize / 2;
        }
        else if(handler.getKeyManager().aRight) {
    
            ar.x = cb.x + cb.width;
            ar.y = cb.y + cb.height / 2 - arSize / 2;
        }
        else {
            
            return;
        }
        
        attackTimer = 0;
        
        for(Entity e : handler.getWorld().getEntityManager().getEntities()) {
            
            if(e.equals(this))
                continue;
            
            if(e.getCollisionBounds(0, 0).intersects(ar))
                e.hurt(1);
            
            return;
        }
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
    
    @Override
    public void die() {
        
        System.out.println("You Lose");
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

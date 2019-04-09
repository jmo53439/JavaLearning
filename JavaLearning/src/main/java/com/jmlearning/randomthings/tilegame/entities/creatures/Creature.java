package com.jmlearning.randomthings.tilegame.entities.creatures;

import com.jmlearning.randomthings.tilegame.Handler;
import com.jmlearning.randomthings.tilegame.entities.Entity;

import static com.jmlearning.randomthings.tilegame.tiles.Tile.TILE_HEIGHT;
import static com.jmlearning.randomthings.tilegame.tiles.Tile.TILE_WIDTH;

public abstract class Creature extends Entity {
    
    public static final int DEFAULT_HP = 10;
    public static final float DEFAULT_SPD = 3.0f;
    public static final int DEFAULT_CREATURE_WIDTH = 64,
            DEFAULT_CREATURE_HEIGHT = 64;
    
    protected int health;
    protected float speed;
    protected float xMove, yMove;
    
    public Creature(Handler handler, float x, float y, int width, int height) {
        
        super(handler, x, y, width, height);
        health = DEFAULT_HP;
        speed = DEFAULT_SPD;
        xMove = 0;
        yMove = 0;
    }
    
    public void move() {
        
        x += xMove;
        y += yMove;
    }
    
    public void moveX() {
        
        if(xMove > 0) {
            
            int tx = (int)(x + xMove + bounds.x + bounds.width) / TILE_WIDTH;
            
            if(!collisionWithTile(tx, (int)(y + bounds.y) / TILE_HEIGHT) &&
                    !collisionWithTile(tx, (int)(y + bounds.y + bounds.height) / TILE_HEIGHT)) {
                
                x += xMove;
            }
        }
        else if(xMove < 0) {
            
            int tx = (int)(x + xMove + bounds.x) / TILE_WIDTH;
            
            if(!collisionWithTile(tx, (int)(y + bounds.y) / TILE_HEIGHT) &&
                    !collisionWithTile(tx, (int)(y + bounds.y + bounds.height) / TILE_HEIGHT)) {
                
                x += xMove;
            }
        }
    }
    
    public void moveY() {
    
        if(yMove < 0) {
            
            int ty = (int)(y + yMove + bounds.y) / TILE_HEIGHT;
            
            if(!collisionWithTile((int)(x + bounds.x) / TILE_WIDTH, ty) &&
                    !collisionWithTile((int)(x + bounds.x + bounds.width) / TILE_WIDTH, ty)) {
                
                y += yMove;
            }
        }
        else if(yMove > 0) {
            
            int ty = (int)(y + yMove + bounds.y + bounds.height) / TILE_HEIGHT;
            
            if(!collisionWithTile((int)(x + bounds.x) / TILE_WIDTH, ty) &&
                    !collisionWithTile((int)(x + bounds.x + bounds.width) / TILE_WIDTH, ty)) {
                
                y += yMove;
            }
        }
    }
    
    protected boolean collisionWithTile(int x, int y) {
        
        return handler.getWorld().getTile(x, y).isSolid();
    }
    
    public int getHealth() {
        
        return health;
    }
    
    public void setHealth(int health) {
        
        this.health = health;
    }
    
    public float getSpeed() {
        
        return speed;
    }
    
    public void setSpeed(float speed) {
        
        this.speed = speed;
    }
    
    public float getxMove() {
        
        return xMove;
    }
    
    public void setxMove(float xMove) {
        
        this.xMove = xMove;
    }
    
    public float getyMove() {
        
        return yMove;
    }
    
    public void setyMove(float yMove) {
        
        this.yMove = yMove;
    }
}

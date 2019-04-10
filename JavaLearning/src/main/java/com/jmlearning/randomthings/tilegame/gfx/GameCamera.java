package com.jmlearning.randomthings.tilegame.gfx;

import com.jmlearning.randomthings.tilegame.Handler;
import com.jmlearning.randomthings.tilegame.entities.Entity;

import static com.jmlearning.randomthings.tilegame.tiles.Tile.TILE_HEIGHT;
import static com.jmlearning.randomthings.tilegame.tiles.Tile.TILE_WIDTH;

public class GameCamera {
    
    private Handler handler;
    private float xOffset, yOffset;
    
    public GameCamera(Handler handler, float xOffset, float yOffset) {
        
        this.handler = handler;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
    
    public void centerOnEntity(Entity e) {
        
        xOffset = e.getX() - handler.getWidth() / 2 + e.getWidth() / 2;
        yOffset = e.getY() - handler.getHeight() / 2 + e.getHeight() / 2;
        checkBlankSpace();
    }
    
    public void move(float xAmt, float yAmt) {
        
        xOffset += xAmt;
        yOffset += yAmt;
        checkBlankSpace();
    }
    
    public void checkBlankSpace() {
        
        if(xOffset < 0) {
        
            xOffset = 0;
        }
        else if(xOffset > handler.getWorld().getWidth() * TILE_WIDTH - handler.getWidth()) {
            
            xOffset = handler.getWorld().getWidth() * TILE_WIDTH - handler.getWidth();
        }
        
        if(yOffset < 0) {
            
            yOffset = 0;
        }
        else if(yOffset > handler.getWorld().getHeight() * TILE_HEIGHT - handler.getHeight()) {
            
            yOffset = handler.getWorld().getHeight() * TILE_HEIGHT - handler.getHeight();
        }
    }
    
    public float getxOffset() {
        
        return xOffset;
    }
    
    public void setxOffset(float xOffset) {
        
        this.xOffset = xOffset;
    }
    
    public float getyOffset() {
        
        return yOffset;
    }
    
    public void setyOffset(float yOffset) {
        
        this.yOffset = yOffset;
    }
}

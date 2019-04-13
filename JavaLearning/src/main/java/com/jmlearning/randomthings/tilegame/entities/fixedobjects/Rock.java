package com.jmlearning.randomthings.tilegame.entities.fixedobjects;

import com.jmlearning.randomthings.tilegame.Handler;
import com.jmlearning.randomthings.tilegame.gfx.Assets;
import com.jmlearning.randomthings.tilegame.items.Item;

import java.awt.*;

import static com.jmlearning.randomthings.tilegame.tiles.Tile.TILE_HEIGHT;
import static com.jmlearning.randomthings.tilegame.tiles.Tile.TILE_WIDTH;

public class Rock extends StaticEntity {
    
    public Rock(Handler handler, float x, float y) {
        
        super(handler, x, y, TILE_WIDTH, TILE_HEIGHT);
        
        bounds.x = 3;
        bounds.y = (int)(height / 2f);
        bounds.width = width - 6;
        bounds.height = (int)(height - height / 2f);
    }
    
    @Override
    public void tick() {
    
    }
    
    @Override
    public void render(Graphics g) {
    
        g.drawImage(Assets.rock, (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()),
                width, height, null);
    }
    
    @Override
    public void die() {
    
        handler.getWorld()
                .getItemManager()
                .addItem(Item.rockItem.createNew((int) x, (int) y));
    }
}

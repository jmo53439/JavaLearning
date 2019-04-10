package com.jmlearning.randomthings.tilegame.entities.fixedobjects;

import com.jmlearning.randomthings.tilegame.Handler;
import com.jmlearning.randomthings.tilegame.gfx.Assets;

import java.awt.*;

import static com.jmlearning.randomthings.tilegame.tiles.Tile.TILE_HEIGHT;
import static com.jmlearning.randomthings.tilegame.tiles.Tile.TILE_WIDTH;

public class Tree extends StaticEntity {
    
    public Tree(Handler handler, float x, float y) {
        
        super(handler, x, y, TILE_WIDTH, TILE_HEIGHT * 2);
    }
    
    @Override
    public void tick() {
    
    }
    
    @Override
    public void render(Graphics g) {
    
        g.drawImage(Assets.tree,
                (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()),
                width, height, null);
    }
}

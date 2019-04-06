package com.jmlearning.randomthings.tilegame.tiles;

import com.jmlearning.randomthings.tilegame.gfx.Assets;

public class RockTile extends Tile {
    
    public RockTile(int id) {
        
        super(Assets.stone, id);
    }
    
    @Override
    public boolean isSolid() {
        
        return true;
    }
}

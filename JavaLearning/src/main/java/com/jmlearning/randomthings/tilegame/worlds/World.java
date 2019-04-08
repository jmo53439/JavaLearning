package com.jmlearning.randomthings.tilegame.worlds;

import com.jmlearning.randomthings.tilegame.Game;
import com.jmlearning.randomthings.tilegame.tiles.Tile;
import com.jmlearning.randomthings.tilegame.utils.Utils;

import java.awt.*;

import static com.jmlearning.randomthings.tilegame.tiles.Tile.TILE_HEIGHT;
import static com.jmlearning.randomthings.tilegame.tiles.Tile.TILE_WIDTH;
import static com.jmlearning.randomthings.tilegame.tiles.Tile.dirtTile;

public class World {
    
    private int width, height;
    private int spawnX, spawnY;
    private int[][] tiles;
    private Game game;
    
    public World(Game game, String path) {
        
        this.game = game;
        loadWorld(path);
    }
    
    public void tick() {
    
    }
    
    public void render(Graphics g) {
        
//        for(int y = 0; y < height; y++) {
//
//            for(int x = 0; x < width; x++) {
//
//                getTile(x, y).render(g, x * Tile.TILE_WIDTH, y * Tile.TILE_HEIGHT);
//            }
//        }
        
        int xStart = (int) Math.max(0, game.getGameCamera().getxOffset() / TILE_WIDTH);
        int xEnd = (int) Math.min(width, (game.getGameCamera().getxOffset() +
                game.getWidth()) / TILE_WIDTH + 1);
        int yStart = (int) Math.max(0, game.getGameCamera().getyOffset() / TILE_HEIGHT);
        int yEnd = (int) Math.min(height, (game.getGameCamera().getyOffset() +
                game.getHeight()) / TILE_HEIGHT + 1);
        
        for(int y = yStart; y < yEnd; y++) {
            
            for(int x = xStart; x < xEnd; x++) {
            
                getTile(x, y)
                        .render(g, (int)(x * TILE_WIDTH - game.getGameCamera().getxOffset()),
                                (int)(y * TILE_HEIGHT - game.getGameCamera().getyOffset()));
            }
        }
    }
    
    public Tile getTile(int x, int y) {
        
        Tile t = Tile.tiles[tiles[x][y]];
        
        if(t == null)
            return dirtTile;
        
        return t;
    }
    
    private void loadWorld(String path) {
        
        String file = Utils.loadFileAsString(path);
        String[] tokens = file.split("\\s+");
        width = Utils.parseInt(tokens[0]);
        height = Utils.parseInt(tokens[1]);
        spawnX = Utils.parseInt(tokens[2]);
        spawnY = Utils.parseInt(tokens[3]);
        tiles = new int[width][height];
        
        for(int y = 0; y < height; y++) {
            
            for(int x = 0; x < width; x++) {
            
                tiles[x][y] = Utils.parseInt(tokens[(x + y * width) + 4]);
            }
        }
    }
}

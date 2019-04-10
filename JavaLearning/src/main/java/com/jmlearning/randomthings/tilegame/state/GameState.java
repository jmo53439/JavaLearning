package com.jmlearning.randomthings.tilegame.states;

import com.jmlearning.randomthings.tilegame.Handler;
import com.jmlearning.randomthings.tilegame.worlds.World;

import java.awt.*;

public class GameState extends State {
    
    private World world;
    
    public GameState(Handler handler) {
    
        super(handler);
        
        world = new World(handler, "res/worlds/world1.txt");
        handler.setWorld(world);
    }
    
    @Override
    public void tick() {
    
        world.tick();
    }
    
    @Override
    public void render(Graphics g) {
    
        world.render(g);
    }
}

package com.jmlearning.randomthings.tilegame.states;

import com.jmlearning.randomthings.tilegame.Handler;
import com.jmlearning.randomthings.tilegame.entities.creatures.Player;
import com.jmlearning.randomthings.tilegame.worlds.World;

import java.awt.*;

public class GameState extends State {
    
    private Player player;
    private World world;
    
    public GameState(Handler handler) {
    
        super(handler);
        player = new Player(handler, 100, 100);
        world = new World(handler, "res/worlds/world1.txt");
        handler.setWorld(world);
    }
    
    @Override
    public void tick() {
    
        world.tick();
        player.tick();
    }
    
    @Override
    public void render(Graphics g) {
    
        world.render(g);
        player.render(g);
    }
}

package com.jmlearning.randomthings.tilegame.states;

import com.jmlearning.randomthings.tilegame.Game;
import com.jmlearning.randomthings.tilegame.entities.creatures.Player;
import com.jmlearning.randomthings.tilegame.worlds.World;

import java.awt.*;

public class GameState extends State {
    
    private Player player;
    private World world;
    
    public GameState(Game game) {
    
        super(game);
        player = new Player(game, 100, 100);
        world = new World("res/worlds/world1.txt");
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

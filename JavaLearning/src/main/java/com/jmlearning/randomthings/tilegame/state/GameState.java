package com.jmlearning.randomthings.tilegame.states;

import com.jmlearning.randomthings.tilegame.Game;
import com.jmlearning.randomthings.tilegame.entities.creatures.Player;

import java.awt.*;

public class GameState extends State {
    
    private Player player;
    
    public GameState(Game game) {
    
        super(game);
        player = new Player(game, 100, 100);
    }
    
    @Override
    public void tick() {
    
        player.tick();
    }
    
    @Override
    public void render(Graphics g) {
    
        player.render(g);
    }
}

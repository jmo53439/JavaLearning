package com.jmlearning.randomthings.tilegame.states;

import com.jmlearning.randomthings.tilegame.Game;

import java.awt.*;

public abstract class State {
    
    private static State currentState = null;
    protected Game game;
    
    public State(Game game) {
        
        this.game = game;
    }
    
    public abstract void tick();
    public abstract void render(Graphics g);
    
    public static State getState() {
        
        return currentState;
    }
    
    public static void setState(State state) {
        
        currentState = state;
    }
}

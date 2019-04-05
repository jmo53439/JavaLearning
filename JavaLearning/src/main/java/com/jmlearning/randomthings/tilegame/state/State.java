package com.jmlearning.randomthings.tilegame.states;

import java.awt.*;

public abstract class State {
    
    private static State currentState = null;
    
    public static State getState() {
        
        return currentState;
    }
    
    public static void setState(State state) {
        
        currentState = state;
    }
    
    public abstract void tick();
    public abstract void render(Graphics g);
}

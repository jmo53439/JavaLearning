package com.jmlearning.randomthings.chess.game;

public class GameTimer {
    
    private static GameTimer myInstance = new GameTimer();
    
    private GameTimer() {
    
    }
    
    public static GameTimer getInstance() {
        
        return myInstance;
    }
}

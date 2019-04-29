package com.jmlearning.randomthings.textgame.creatures.player;

public class Pouch {
    
    private int coins;
    
    public Pouch() {
        
        coins = 0;
    }
    
    public int getCoins() {
        
        return coins;
    }
    
    public void setCoins(int coins) {
        
        if(coins >= 0)
            this.coins = coins;
    }
    
    public void addCoins(int coins) {
        
        if(coins > 0)
            this.coins += coins;
    }
    
    public void removeCoins(int coins) {
        
        if(coins > 0) {
            
            this.coins -= coins;
            
            if(coins < 0)
                coins = 0;
        }
    }
}

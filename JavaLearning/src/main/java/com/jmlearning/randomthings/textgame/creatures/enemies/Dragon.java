package com.jmlearning.randomthings.textgame.creatures.enemies;

public class Dragon extends Enemy {
    
    public Dragon() {
        
        super();
        
        this.setName(this.getClass().getSimpleName());
        this.setHealth(RANDOM.nextInt(TIER4_MAX_HP - TIER4_MIN_HP) + TIER4_MIN_HP);
    }
    
    @Override
    public int atk() {
        
        return RANDOM.nextInt(TIER4_MAX_DMG);
    }
    
    @Override
    public void takeDmg(int dmg) {
    
        this.setHealth(this.getHealth() - dmg);
    }
}

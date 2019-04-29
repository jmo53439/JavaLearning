package com.jmlearning.randomthings.textgame.creatures.enemies;

public class Snake extends Enemy {
    
    public Snake() {
        
        super();
        
        this.setName(this.getClass().getSimpleName());
        this.setHealth(RANDOM.nextInt(TIER1_MAX_HP - TIER1_MIN_HP) + TIER1_MIN_HP);
    }
    
    @Override
    public int atk() {
        
        return RANDOM.nextInt(TIER1_MAX_DMG);
    }
    
    @Override
    public void takeDmg(int dmg) {
        
        this.setHealth(this.getHealth() - dmg);
    }
}

package com.jmlearning.randomthings.textgame.creatures.enemies;

public class Zombie extends Enemy {
    
    public Zombie() {
        
        super();
        
        this.setName(this.getClass().getSimpleName());
        this.setHealth(RANDOM.nextInt(TIER3_MAX_HP - TIER3_MIN_HP) + TIER3_MIN_HP);
    }
    
    @Override
    public int atk() {
        
        return RANDOM.nextInt(TIER3_MAX_DMG);
    }
    
    @Override
    public void takeDmg(int dmg) {
        
        this.setHealth(this.getHealth() - dmg);
    }
}

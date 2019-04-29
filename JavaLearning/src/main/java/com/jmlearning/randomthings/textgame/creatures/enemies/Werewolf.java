package com.jmlearning.randomthings.textgame.creatures.enemies;

public class Werewolf extends Enemy {
    
    public Werewolf() {
        
        super();
        
        this.setName(this.getClass().getSimpleName());
        this.setHealth(RANDOM.nextInt(TIER2_MAX_HP - TIER2_MIN_HP) + TIER2_MIN_HP);
    }
    
    @Override
    public int atk() {
        
        return RANDOM.nextInt(TIER2_MAX_DMG);
    }
    
    @Override
    public void takeDmg(int dmg) {
    
        this.setHealth(this.getHealth() - dmg);
    }
}

package com.jmlearning.randomthings.textgame.creatures.enemies;

import java.util.Random;

public abstract class Enemy {
    
    public static final String[] ENEMY_NAMES = {
        
            // TIER 1
            "Bat",
            "Slime",
            "Snake",
            "Spider",
            
            // TIER 2
            "Ghost",
            "Goblin",
            "Skeleton",
            "Werewolf",
            
            // TIER 3
            "Vampire",
            "Warrior",
            "Witch",
            "Zombie",
            
            // TIER 4
            "Dragon",
            "Giant"
    };
    
    public static final int TIER1_MAX_DMG = 2;
    public static final int TIER2_MAX_DMG = 8;
    public static final int TIER3_MAX_DMG = 14;
    public static final int TIER4_MAX_DMG = 20;
    
    public static final int TIER1_MAX_HP = 25;
    public static final int TIER2_MAX_HP = 50;
    public static final int TIER3_MAX_HP = 75;
    public static final int TIER4_MAX_HP = 100;
    
    public static final int TIER1_MIN_HP = 1;
    public static final int TIER2_MIN_HP = 25;
    public static final int TIER3_MIN_HP = 50;
    public static final int TIER4_MIN_HP = 75;
    
    public static final Random RANDOM = new Random();
    
    protected int health;
    protected String name;
    protected String type;
    
    public Enemy() {
        
        name = ENEMY_NAMES[RANDOM.nextInt(ENEMY_NAMES.length)];
    }
    
    public abstract int atk();
    public abstract void takeDmg(int dmg);
    
    public int getHealth() {
        
        return health;
    }
    
    public void setHealth(int health) {
        
        this.health = health;
    }
    
    public String getName() {
        
        return name;
    }
    
    public void setName(String name) {
        
        this.name = name;
    }
    
    public String getType() {
        
        return type;
    }
    
    public void setType(String type) {
        
        this.type = type;
    }
}

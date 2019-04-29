package com.jmlearning.randomthings.textgame.creatures.player;

import com.jmlearning.randomthings.textgame.items.armor.Armor;
import com.jmlearning.randomthings.textgame.items.armor.ArmorFactory;
import com.jmlearning.randomthings.textgame.items.weapons.Sword;
import com.jmlearning.randomthings.textgame.items.weapons.SwordFactory;

import java.util.Random;

public class Player {
    
    public static final int DEFAULT_NUMBER_OF_POTIONS = 3;
    public static final long DELAY = 1000;
    public static final int POTION_HEALING = 30;
    public static final int FULL_HP = 100;
    public static final int BASE_ATK_DMG = 25;
    public static final String NO_NAME = "";
    public static Random RANDOM = new Random();
    
    private Armor armor;
    private int atkDmg;
    private int enemiesDefeated;
    private boolean hasArmor;
    private boolean hasSword;
    private int health;
    private Pouch pouch;
    private String name;
    private int potionsRemaining;
    private Sword sword;
    
    public Player() {
    
        name = NO_NAME;
        hasArmor = false;
        armor = null;
        hasSword = false;
        health = FULL_HP;
        potionsRemaining = DEFAULT_NUMBER_OF_POTIONS;
        enemiesDefeated = 0;
        sword = null;
        pouch = new Pouch();
    }
    
    public int health() {
        
        return health;
    }
    
    public void setHealth(int hp) {
        
        if(hp > 0 && hp <= FULL_HP)
            health = hp;
    }
    
    public int getPotions() {
        
        return potionsRemaining;
    }
    
    public void setNumberOfPotions(int potions) {
        
        if(potions >= 0)
            potionsRemaining = potions;
    }
    
    public Sword getSword() {
        
        return sword;
    }
    
    public Armor getArmor() {
        
        return armor;
    }
    
    public Pouch getPouch() {
        
        return pouch;
    }
    
    public boolean hasSword() {
        
        return sword != null;
    }
    
    public boolean hasArmor() {
        
        return armor != null;
    }
    
    public int enemiesDefeated() {
        
        return enemiesDefeated;
    }
    
    public void setEnemiesDefeated(int enemiesDefeated) {
        
        this.enemiesDefeated = enemiesDefeated;
    }
    
    public String getName() {
        
        return name;
    }
    
    public void setName(String name) {
        
        this.name = name;
    }
    
    public int atk() {
    
        if(this.hasSword()) {
            
            sword.useSword();
            int dmg = RANDOM.nextInt(BASE_ATK_DMG) + sword.getDmgIncrease();
            
            if(sword.getDurabilityPoints() <= 0) {
                
                System.out.println("\nYour " + sword.getName() + " broke.");
                this.sword = null;
                
                try {
                    
                    Thread.sleep(DELAY);
                }
                catch(InterruptedException e) {
                    
                    System.out.println("The game experienced an Interrupted Exception.");
                    System.out.println("The game data could not be saved.");
                    System.out.println("Please restart the game.");
                    System.exit(0);
                }
            }
            
            return dmg;
        }
        
        return RANDOM.nextInt(BASE_ATK_DMG);
    }
    
    public void takeDmg(int dmg) {
    
        if(this.hasArmor()) {
            
            armor.useArmor();
            health = health - Math.max(dmg - armor.dmgMitigated(), 0);
            
            if(armor.durabilityPoints() <= 0) {
                
                System.out.println("\nYour " + armor.name() + " broke.");
                
                try {
                    
                    Thread.sleep(DELAY);
                }
                catch(InterruptedException e) {
    
                    System.out.println("The game experienced an Interrupted Exception.");
                    System.out.println("The game data could not be saved.");
                    System.out.println("Please restart the game.");
                    System.exit(0);
                }
                
                armor = null;
            }
        }
        else {
        
            health = health - dmg;
        }
    }
    
    public void usePotion() {
    
        if(potionsRemaining <= 0)
            return;
        
        health = health + POTION_HEALING;
        potionsRemaining--;
    }
    
    public void addPotion(int potions) {
    
        potionsRemaining = potionsRemaining + potions;
    }
    
    public void incrementEnemiesDefeated() {
        
        enemiesDefeated++;
    }
    
    public void addSword(String type) {
    
        if(type == null)
            return;
        
        sword = SwordFactory.createSword(type);
    }
    
    public void addArmor(String type) {
        
        if(type == null)
            return;
        
        armor = ArmorFactory.createArmor(type);
    }
    
    public String getData() {
    
        return name + " " + this.hasSword() + " " + hasSword + " " + hasArmor() +
                " " + enemiesDefeated + " " + health + " " + potionsRemaining +
                " " + pouch.getCoins();
    }
    
    public void reset() {
    
        health = FULL_HP;
        potionsRemaining = DEFAULT_NUMBER_OF_POTIONS;
        enemiesDefeated = 0;
        sword = null;
        hasArmor = false;
        hasSword = false;
    }
}

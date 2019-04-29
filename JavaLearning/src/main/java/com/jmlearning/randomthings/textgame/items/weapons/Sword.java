package com.jmlearning.randomthings.textgame.items.weapons;

public abstract class Sword {
    
    private int dmgIncrease;
    private int durabilityPoints;
    private String name;
    
    public int getDmgIncrease() {
        
        return dmgIncrease;
    }
    
    public int getDurabilityPoints() {
        
        return durabilityPoints;
    }
    
    public String getName() {
        
        return name;
    }
    
    public void setName(String name) {
        
        this.name = name;
    }
    
    public void useSword() {
        
        durabilityPoints--;
    }
    
    public void repairSword(int pointsToHeal) {
        
        durabilityPoints = durabilityPoints + pointsToHeal;
    }
}

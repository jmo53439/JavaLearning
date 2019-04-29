package com.jmlearning.randomthings.textgame.items.armor;

public abstract class Armor {
    
    private String name;
    private int durabilityPoints;
    private int dmgMitigated;
    
    public String name() {
        
        return name;
    }
    
    public void setName(String name) {
        
        this.name = name;
    }
    
    public int durabilityPoints() {
        
        return durabilityPoints;
    }
    
    public int dmgMitigated() {
        
        return dmgMitigated;
    }
    
    public void useArmor() {
    
        durabilityPoints--;
    }
    
    public void repairArmor(int pointsToHeal) {
    
        durabilityPoints = durabilityPoints + pointsToHeal;
    }
}

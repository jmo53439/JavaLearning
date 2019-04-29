package com.jmlearning.randomthings.textgame.items.weapons;

public class SwordFactory {
    
    public static Sword createSword(String type) {
        
        if(SwordType.WOOD.getValue().equals(type))
            return new WoodSword();
        
        if(SwordType.METAL.getValue().equals(type))
            return new MetalSword();
        
        if(SwordType.GOLD.getValue().equals(type))
            return new GoldSword();
        
        return new WoodSword();
    }
}

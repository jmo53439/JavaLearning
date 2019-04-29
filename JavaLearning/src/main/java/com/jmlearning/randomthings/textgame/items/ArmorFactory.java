package com.jmlearning.randomthings.textgame.items.armor;

public class ArmorFactory {
    
    public static Armor createArmor(String type) {
        
        if(type == null || ArmorType.LEATHER.getValue().equals(type))
            return new LeatherArmor();
        
        if(ArmorType.IRON.getValue().equals(type))
            return new IronArmor();
        
        if(ArmorType.GOLD.getValue().equals(type))
            return new GoldArmor();
        
        return new LeatherArmor();
    }
}

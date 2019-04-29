package com.jmlearning.randomthings.textgame.items.armor;

public enum ArmorType {
    
    LEATHER("leather"),
    IRON("iron"),
    GOLD("gold");
    
    private String value;
    
    ArmorType(String value) {
        
        this.value = value;
    }
    
    public String getValue() {
        
        return value;
    }
    
    public static ArmorType getArmorType(String value) {
        
        for(ArmorType armor : ArmorType.values()) {
            
            if(armor.value.equalsIgnoreCase(value))
                return armor;
        }
        
        return null;
    }
}

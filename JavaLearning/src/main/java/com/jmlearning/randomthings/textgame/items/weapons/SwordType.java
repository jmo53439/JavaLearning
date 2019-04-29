package com.jmlearning.randomthings.textgame.items.weapons;

public enum SwordType {
    
    WOOD("wood"),
    METAL("metal"),
    GOLD("gold");
    
    private String value;
    
    SwordType(String value) {
        
        this.value = value;
    }
    
    public String getValue() {
        
        return value;
    }
    
    public static SwordType getSwordType(String value) {
        
        for(SwordType sword : SwordType.values()) {
            
            if(sword.value.equalsIgnoreCase(value))
                return sword;
        }
        
        return null;
    }
}

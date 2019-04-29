package com.jmlearning.randomthings.textgame.wordinput;

public enum Confirmation {
    
    YES("yes"),
    YUP("yup"),
    OK("ok"),
    OKAY("okay"),
    SURE("sure"),
    YA("ya"),
    OFCOURSE("ofcourse");
    
    private String value;
    
    Confirmation(String value) {
        
        this.value = value;
    }
    
    public static boolean isConfirmation(String value) {
        
        for(Confirmation c : Confirmation.values())
            return c.value.equalsIgnoreCase(value);
        
        return false;
    }
    
    public String getValue() {
        
        return value;
    }
}

package com.jmlearning.randomthings.uno.cards;

import com.jmlearning.randomthings.uno.view.UNOCard;

import java.awt.*;

public class WildCard extends UNOCard {
    
    private int function = 0;
    private Color chosenColor;
    
    public WildCard() {
    
    }
    
    public WildCard(String cardValue) {
        
        super(BLACK, WILD, cardValue);
    }
    
    public void useWildColor(Color wildColor) {
        
        chosenColor = wildColor;
    }
    
    public Color getWildColor() {
        
        return chosenColor;
    }
}

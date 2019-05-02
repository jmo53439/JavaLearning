package com.jmlearning.randomthings.uno.cards;

import com.jmlearning.randomthings.uno.view.UNOCard;

import java.awt.*;

public class ActionCard extends UNOCard {
    
    private int function = 0;
    
    public ActionCard() {
    
    }
    
    public ActionCard(Color cardColor, String cardValue) {
        
        super(cardColor, ACTION, cardValue);
    }
}

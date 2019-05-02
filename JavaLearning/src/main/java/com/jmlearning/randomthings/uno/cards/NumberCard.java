package com.jmlearning.randomthings.uno.cards;

import com.jmlearning.randomthings.uno.view.UNOCard;

import java.awt.*;

public class NumberCard extends UNOCard {
    
    public NumberCard() {
    
    }
    
    public NumberCard(Color cardColor, String cardValue) {
        
        super(cardColor, NUMBERS, cardValue);
    }
}

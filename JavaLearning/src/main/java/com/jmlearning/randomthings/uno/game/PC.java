package com.jmlearning.randomthings.uno.game;

import com.jmlearning.randomthings.uno.cards.WildCard;
import com.jmlearning.randomthings.uno.interfaces.GameConstants;
import com.jmlearning.randomthings.uno.view.UNOCard;

import java.awt.*;
import java.awt.event.MouseEvent;

public class PC extends Player implements GameConstants {
    
    public PC() {
        
        setName("PC");
        super.setCards();
    }
    
    public PC(Player player) {
    
    }
    
    public boolean play(UNOCard topCard) {
    
        boolean done = false;
        Color color = topCard.getColor();
        String value = topCard.getValue();
        
        if(topCard.getType() == WILD)
            color = ((WildCard) topCard).getWildColor();
        
        for(UNOCard card : getAllCards()) {
            
            if(card.getColor().equals(color) || card.getValue().equals(value)) {
    
                MouseEvent doPress = new MouseEvent(card, MouseEvent.MOUSE_PRESSED,
                        System.currentTimeMillis(), (int) MouseEvent.MOUSE_EVENT_MASK,
                        5, 5, 1, true);
                card.dispatchEvent(doPress);
                
                MouseEvent doRelease = new MouseEvent(card, MouseEvent.MOUSE_RELEASED,
                        System.currentTimeMillis(), (int) MouseEvent.MOUSE_EVENT_MASK,
                        5, 5, 1, true);
                card.dispatchEvent(doRelease);
                
                done = true;
                break;
            }
        }
        
        if(!done) {
            
            for(UNOCard card : getAllCards()) {
                
                if(card.getType() == WILD) {
                    
                    MouseEvent doPress = new MouseEvent(card, MouseEvent.MOUSE_PRESSED,
                            System.currentTimeMillis(), (int) MouseEvent.MOUSE_EVENT_MASK,
                            5, 5, 1, true);
                    card.dispatchEvent(doPress);
                    
                    MouseEvent doRelease = new MouseEvent(card, MouseEvent.MOUSE_RELEASED,
                            System.currentTimeMillis(), (int) MouseEvent.MOUSE_EVENT_MASK,
                            5, 5, 1, true);
                    card.dispatchEvent(doRelease);
                    
                    done = true;
                    break;
                }
            }
        }
        
        if(getTotalCards() == 1 || getTotalCards() == 2) {
            
            saysUNO();
        }
        
        return done;
    }
}

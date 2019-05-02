package com.jmlearning.randomthings.uno.cards;

import com.jmlearning.randomthings.uno.controllers.MyCardListener;
import com.jmlearning.randomthings.uno.interfaces.GameConstants;
import com.jmlearning.randomthings.uno.view.UNOCard;

import java.awt.*;
import java.util.LinkedList;

public class CardDeck implements GameConstants {
    
    private final LinkedList<ActionCard> actionCards;
    private final LinkedList<NumberCard> numberCards;
    private final LinkedList<WildCard> wildCards;
    private LinkedList<UNOCard> unoCards;
    
    public CardDeck() {
    
        actionCards = new LinkedList <>();
        numberCards = new LinkedList <>();
        wildCards = new LinkedList <>();
        unoCards = new LinkedList <>();
        
        addCards();
        addCardListener(CARD_LISTENER);
    }
    
    private void addCards() {
        
        for(Color color : UNO_COLORS) {
            
            // add 76 numbered cards no zeroes
            for(int num : UNO_NUMBERS) {
                
                int i = 0;
                
                do {
                    
                    unoCards.add(new NumberCard(color, Integer.toString(num)));
                    i++;
                }
                while(num != 0 && i < 2);
            }
            
            // add 24 action cards
            for(String type : ActionTypes) {
                
                for(int i = 0; i < 2; i++) {
                    
                    unoCards.add(new ActionCard(color, type));
                }
            }
            
            for(String type : WildTypes) {
                
                for(int i = 0; i < 4; i++) {
                
                    unoCards.add(new WildCard(type));
                }
            }
        }
    
        for(String type : WildTypes) {
        
            for(int i = 0; i < 4; i++) {
            
                unoCards.add(new WildCard(type));
            }
        }
    }
    
    public void addCardListener(MyCardListener listener) {
        
        for(UNOCard card : unoCards) {
            
            card.addMouseListener(listener);
        }
    }
    
    public LinkedList<UNOCard> getCards() {
        
        return unoCards;
    }
}

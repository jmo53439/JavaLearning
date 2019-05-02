package com.jmlearning.randomthings.uno.game;

import com.jmlearning.randomthings.uno.cards.CardDeck;
import com.jmlearning.randomthings.uno.interfaces.GameConstants;
import com.jmlearning.randomthings.uno.view.UNOCard;

import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

public class Dealer implements GameConstants {
    
    private CardDeck cardDeck;
    private Stack<UNOCard> cardStack;
    
    public Dealer() {
        
        this.cardDeck = new CardDeck();
    }
    
    // shuffle
    public Stack<UNOCard> shuffle() {
    
        LinkedList<UNOCard> deckOfCards = cardDeck.getCards();
        LinkedList<UNOCard> shuffledCards = new LinkedList <>();
        
        while(!deckOfCards.isEmpty()) {
            
            int totalCards = deckOfCards.size();
            Random rand = new Random();
            int pos = (Math.abs(rand.nextInt())) % totalCards;
            UNOCard randomCard = deckOfCards.get(pos);
            deckOfCards.remove(pos);
            shuffledCards.add(randomCard);
        }
        
        cardStack = new Stack <>();
        
        for(UNOCard card : shuffledCards) {
    
            cardStack.add(card);
        }
        
        return cardStack;
    }
    
    // spread cards to players
    public void spreadOut(Player[] players) {
    
        for(int i = 1; i < FIRST_HAND; i++) {
            
            for(Player p : players) {
                
                p.obtainCard(cardStack.pop());
            }
        }
    }
    
    public UNOCard getCard() {
        
        return cardStack.pop();
    }
}

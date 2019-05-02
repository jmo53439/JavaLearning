package com.jmlearning.randomthings.uno.game;

import com.jmlearning.randomthings.uno.view.UNOCard;

import java.util.LinkedList;

public class Player {
    
    private String name = null;
    private boolean isMyTurn = false;
    private boolean saidUNO = false;
    private LinkedList<UNOCard> myCards;
    private int playedCards = 0;
    
    public Player() {
        
        myCards = new LinkedList <>();
    }
    
    public Player(String player) {
        
        setName(player);
        myCards = new LinkedList <>();
    }
    
    public String getName() {
        
        return this.name;
    }
    
    public void setName(String newName) {
        
        name = newName;
    }
    
    public boolean isMyTurn() {
        
        return isMyTurn;
    }
    
    public void toggleTurn() {
    
//        isMyTurn = (isMyTurn) ? false : true;
        isMyTurn = !isMyTurn;
    }
    
    public boolean getSaidUNO() {
        
        return saidUNO;
    }
    
    public void saysUNO() {
        
        saidUNO = true;
    }
    
    public void setSaidUNOFalse() {
        
        saidUNO = false;
    }
    
    public boolean hasCards() {
    
//        return (myCards.isEmpty()) ? false : true;
        return !myCards.isEmpty();
    }
    
    public LinkedList<UNOCard> getAllCards() {
        
        return myCards;
    }
    
    public int getTotalCards() {
        
        return myCards.size();
    }
    
    public void setCards() {
        
        myCards = new LinkedList <>();
    }
    
    public int totalPlayedCards() {
        
        return playedCards;
    }
    
    public boolean hasCard(UNOCard thisCard) {
        
        return myCards.contains(thisCard);
    }
    
    public void obtainCard(UNOCard card) {
    
        myCards.add(card);
    }
    
    public void removeCard(UNOCard thisCard) {
        
        myCards.remove(thisCard);
        playedCards++;
    }
}

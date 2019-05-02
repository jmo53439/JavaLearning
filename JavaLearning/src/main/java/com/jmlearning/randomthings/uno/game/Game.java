package com.jmlearning.randomthings.uno.game;

import com.jmlearning.randomthings.uno.cards.WildCard;
import com.jmlearning.randomthings.uno.interfaces.GameConstants;
import com.jmlearning.randomthings.uno.view.UNOCard;

import javax.swing.*;
import java.util.Stack;

public class Game implements GameConstants {
    
    private Player[] players;
    private boolean isOver;
    private int gameMode;
    private PC pc;
    private Dealer dealer;
    private Stack<UNOCard> cardStack;
    
    public Game(int mode) {
        
        gameMode = mode;
        
        // create players
        String name = (gameMode == MANUAL) ?
                JOptionPane.showInputDialog("Player 1") : "PC";
        String name2 = JOptionPane.showInputDialog("Player 2");
        
        if(gameMode == vsPC) {
            
            pc = new PC();
        }
        
        Player p1 = (gameMode == vsPC) ? pc : new Player(name);
        Player p2 = new Player(name2);
        p2.toggleTurn();
        players = new Player[] {p1, p2};
        
        // create dealer
        dealer = new Dealer();
        cardStack = dealer.shuffle();
        dealer.spreadOut(players);
        
        isOver = false;
    }
    
    public Player[] getPlayers() {
        
        return players;
    }
    
    public UNOCard getCard() {
        
        return dealer.getCard();
    }
    
    public void removePlayedCard(UNOCard playedCard) {
        
        for(Player p : players) {
            
            if(p.hasCard(playedCard)) {
                
                p.removeCard(playedCard);
                
                if(p.getTotalCards() == 1 && !p.getSaidUNO()) {
                    
                    infoPanel.setError(p.getName() + " forgot to say UNO");
                    p.obtainCard(getCard());
                    p.obtainCard(getCard());
                }
                else if(p.getTotalCards() > 2) {
                    
                    p.setSaidUNOFalse();
                }
            }
        }
    }
    
    public void drawCard(UNOCard topCard) {
        
        boolean canPlay = false;
        
        for(Player p : players) {
            
            if(p.isMyTurn()) {
                
                UNOCard newCard = getCard();
                p.obtainCard(newCard);
                canPlay = canPlay(topCard, newCard);
                break;
            }
        }
        
        if(!canPlay) {
            
            switchTurn();
        }
    }
    
    public void drawPlus(int times) {
        
        for(Player p : players) {
            
            if(!p.isMyTurn()) {
                
                for(int i = 1; i <= times; i++) {
                    
                    p.obtainCard(getCard());
                }
            }
        }
    }
    
    private boolean canPlay(UNOCard topCard, UNOCard newCard) {
        
        // color or value matches
        if(topCard.getColor().equals(newCard.getColor()) ||
                topCard.getValue().equals(newCard.getValue())) {
            
            return true;
        }
        else if(topCard.getType() == WILD) {
    
            return ((WildCard) topCard).getWildColor().equals(newCard.getColor());
        }
        else if(newCard.getType() == WILD)
            return true;
        
        return false;
    }
    
    public void switchTurn() {
        
        for(Player p : players) {
            
            p.toggleTurn();
        }
        
        whoseTurn();
    }
    
    public void whoseTurn() {
        
        for(Player p : players) {
            
            if(p.isMyTurn()) {
                
                infoPanel.updateText(p.getName() + "'s Turn");
                System.out.println(p.getName() + "'s Turn");
            }
        }
        
        infoPanel.setDetail(playedCardSize(), remainingCards());
        infoPanel.repaint();
    }
    
    public int[] playedCardSize() {
    
        int[] nr = new int[2];
        int i = 0;
        
        for(Player p : players) {
            
            nr[i] = p.totalPlayedCards();
            i++;
        }
        
        return nr;
    }
    
    public int remainingCards() {
        
        return cardStack.size();
    }
    
    public boolean isOver() {
        
        if(cardStack.isEmpty()) {
            
            isOver = true;
            return isOver;
        }
        
        for(Player p : players) {
            
            if(!p.hasCards()) {
                
                isOver = true;
                break;
            }
        }
        
        return isOver;
    }
    
    public void checkUNO() {
        
        for(Player p : players) {
            
            if(p.isMyTurn()) {
                
                if(p.getTotalCards() == 1 && !p.getSaidUNO()) {
                    
                    infoPanel.setError(p.getName() + " forgot to say UNO");
                    p.obtainCard(getCard());
                    p.obtainCard(getCard());
                }
            }
        }
    }
    
    public void setSaidUNO() {
        
        for(Player p : players) {
            
            if(p.isMyTurn()) {
                
                if(p.getTotalCards() == 2) {
                    
                    p.saysUNO();
                    infoPanel.setError(p.getName() + " said UNO");
                }
            }
        }
    }
    
    public boolean isPCsTurn() {
        
        if(pc.isMyTurn()) {
            
            return true;
        }
        
        return false;
    }
    
    public void playPC(UNOCard topCard) {
        
        if(pc.isMyTurn()) {
            
            boolean done = pc.play(topCard);
            
            if(!done) {
                
                drawCard(topCard);
            }
        }
    }
}

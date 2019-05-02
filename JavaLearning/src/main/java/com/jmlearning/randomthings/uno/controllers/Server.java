package com.jmlearning.randomthings.uno.controllers;

import com.jmlearning.randomthings.uno.cards.WildCard;
import com.jmlearning.randomthings.uno.game.Game;
import com.jmlearning.randomthings.uno.game.Player;
import com.jmlearning.randomthings.uno.interfaces.GameConstants;
import com.jmlearning.randomthings.uno.view.Session;
import com.jmlearning.randomthings.uno.view.UNOCard;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Server implements GameConstants {
    
    private Game game;
    private Session session;
    private Stack<UNOCard> playedCards;
    public boolean canPlay;
    private int mode;
    
    public Server() {
        
        mode = requestMode();
        game = new Game(mode);
        playedCards = new Stack <>();
        UNOCard firstCard = game.getCard();
        modifyFirstCard(firstCard);
        playedCards.add(firstCard);
        session = new Session(game, firstCard);
        game.whoseTurn();
        canPlay = true;
    }
    
    private int requestMode() {
    
        Object[] options = {"vs PC", "Manual", "Cancel"};
        int n = JOptionPane.showOptionDialog(null,
                "Choose a Game Mode to Play", "Game Mode",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
        
        if(n == 2)
            System.exit(1);
        
        return GAME_MODES[n];
    }
    
    private void modifyFirstCard(UNOCard firstCard) {
    
        firstCard.removeMouseListener(CARD_LISTENER);
        
        if(firstCard.getType() == WILD) {
            
            int random = new Random().nextInt() % 4;
            
            try {
                
                ((WildCard) firstCard).useWildColor(UNO_COLORS[Math.abs(random)]);
            }
            catch(Exception e) {
    
                System.out.println("Issue with modifyFirstCard()");
            }
        }
    }
    
    public Session getSession() {
        
        return this.session;
    }
    
    public void playThisCard(UNOCard clickedCard) {
        
        // check player turn
        if(!isPlayersTurn(clickedCard)) {
            
            infoPanel.setError("It is not your turn");
            infoPanel.repaint();
        }
        else {
            
            // card validation
            if(isValidMove(clickedCard)) {
            
                clickedCard.removeMouseListener(CARD_LISTENER);
                playedCards.add(clickedCard);
                game.removePlayedCard(clickedCard);
                
                switch(clickedCard.getType()) {
                    
                    case ACTION:
                        performAction(clickedCard);
                        break;
                        
                    case WILD:
                        performWild((WildCard) clickedCard);
                        break;
                        
                    default:
                        break;
                }
                
                game.switchTurn();
                session.updatePanel(clickedCard);
                checkResults();
            }
            else {
                
                infoPanel.setError("Invalid Move");
                infoPanel.repaint();
            }
        }
        
        if(mode == vsPC && canPlay) {
            
            if(game.isPCsTurn()) {
                
                game.playPC(peekTopCard());
            }
        }
    }
    
    private void checkResults() {
        
        if(game.isOver()) {
            
            canPlay = false;
            infoPanel.updateText("Game Over");
        }
    }
    
    public boolean isPlayersTurn(UNOCard clickedCard) {
        
        for(Player p : game.getPlayers()) {
            
            if(p.hasCard(clickedCard) && p.isMyTurn()) {
                
                return true;
            }
        }
        
        return false;
    }
    
    public boolean isValidMove(UNOCard playedCard) {
        
        UNOCard topCard = peekTopCard();
        
        if(playedCard.getColor().equals(topCard.getColor()) ||
                playedCard.getValue().equals(topCard.getValue())) {
            
            return true;
        }
        else if(playedCard.getType() == WILD) {
            
            return true;
        }
        else if(topCard.getType() == WILD) {
    
            Color color = ((WildCard) topCard).getWildColor();
            
            if(color.equals(playedCard.getColor())) {
                
                return true;
            }
        }
        
        return false;
    }
    
    private void performAction(UNOCard actionCard) {
        
        if(actionCard.getValue().equals(DRAW2PLUS)) {
            
            game.drawPlus(2);
        }
        else if(actionCard.getValue().equals(REVERSE)) {
            
            game.switchTurn();
        }
        else if(actionCard.getValue().equals(SKIP)) {
            
            game.switchTurn();
        }
    }
    
    private void performWild(WildCard functionCard) {
    
        if(mode == 1 && game.isPCsTurn()) {
            
            int rand = new Random().nextInt() % 4;
            functionCard.useWildColor(UNO_COLORS[Math.abs(rand)]);
        }
        else {
    
            ArrayList<String> colors = new ArrayList <>();
            colors.add("RED");
            colors.add("BLUE");
            colors.add("GREEN");
            colors.add("YELLOW");
            
            String chosenColor = (String) JOptionPane.showInputDialog(
                    null, "Choose a Color", "Wild Card Color",
                    JOptionPane.DEFAULT_OPTION, null, colors.toArray(), null);
            functionCard.useWildColor(UNO_COLORS[colors.indexOf(chosenColor)]);
        }
        
        if(functionCard.getValue().equals(W_DRAW4PLUS))
            game.drawPlus(4);
    }
    
    public void requestCard() {
        
        game.drawCard(peekTopCard());
        
        if(mode == vsPC && canPlay) {
            
            if(game.isPCsTurn()) {
                
                game.playPC(peekTopCard());
            }
        }
        
        session.refreshPanel();
    }
    
    public UNOCard peekTopCard() {
        
        return playedCards.peek();
    }
    
    public void submitSaidUNO() {
        
        game.setSaidUNO();
    }
}

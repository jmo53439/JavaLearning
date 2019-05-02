package com.jmlearning.randomthings.uno.view;

import com.jmlearning.randomthings.uno.game.Player;
import com.jmlearning.randomthings.uno.interfaces.GameConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerPanel extends JPanel implements GameConstants {
    
    private Player player;
    private String name;
    private Box myLayout;
    private JLayeredPane cardHolder;
    private Box controlPanel;
    private JButton draw;
    private JButton sayUNO;
    private JLabel nameLabel;
    private MyButtonHandler handler;
    
    public PlayerPanel(Player newPlayer) {
    
        setPlayer(newPlayer);
        myLayout = Box.createHorizontalBox();
        cardHolder = new JLayeredPane();
        cardHolder.setPreferredSize(new Dimension(600, 175));
        
        setCards();
        setControlPanel();
        
        myLayout.add(cardHolder);
        myLayout.add(Box.createHorizontalStrut(400));
        myLayout.add(controlPanel);
        add(myLayout);
        
        handler = new MyButtonHandler();
        draw.addActionListener(BUTTON_LISTENER);
        draw.addActionListener(handler);
        
        sayUNO.addActionListener(BUTTON_LISTENER);
        sayUNO.addActionListener(handler);
    }
    
    public Player getPlayer() {
        
        return player;
    }
    
    public void setPlayer(Player player) {
        
        this.player = player;
        setPlayerName(player.getName());
    }
    
    public void setPlayerName(String playerName) {
        
        this.name = playerName;
    }
    
    public void setCards() {
        
        cardHolder.removeAll();
        Point origin = getPoint(cardHolder.getWidth(), player.getTotalCards());
        int offset = calculateOffset(cardHolder.getWidth(), player.getTotalCards());
        int i = 0;
        
        for(UNOCard card : player.getAllCards()) {
        
            card.setBounds(origin.x, origin.y, card.CARD_SIZE.width, card.CARD_SIZE.height);
            cardHolder.add(card, i++);
            cardHolder.moveToFront(card);
            origin.x += offset;
        }
        
        repaint();
    }
    
    private Point getPoint(int width, int totalCards) {
        
        Point p = new Point(0, 20);
        
        if(totalCards >= 8) {
            
            return p;
        }
        else {
            
            p.x = (width - calculateOffset(width, totalCards) * totalCards) / 2;
        }
        
        return p;
    }
    
    private int calculateOffset(int width, int totalCards) {
        
        int offset = 71;
        
        if(totalCards <= 8) {
            
            return offset;
        }
        else {
            
            double o = (width - 100) / (totalCards - 1);
            return (int) o;
        }
    }
    
    private void setControlPanel() {
        
        draw = new JButton("Draw");
        sayUNO = new JButton("Say UNO");
        nameLabel = new JLabel(name);
        
        draw.setBackground(new Color(79, 129, 189));
        draw.setFont(new Font("Arial", Font.BOLD, 20));
        draw.setFocusable(false);
        
        sayUNO.setBackground(new Color(149, 55, 53));
        sayUNO.setFont(new Font("Arial", Font.BOLD, 20));
        sayUNO.setFocusable(false);
        
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 15));
        
        controlPanel = Box.createVerticalBox();
        controlPanel.add(nameLabel);
        controlPanel.add(draw);
        controlPanel.add(Box.createVerticalStrut(15));
        controlPanel.add(sayUNO);
    }
    
    class MyButtonHandler implements ActionListener {
    
        @Override
        public void actionPerformed(ActionEvent e) {
        
            if(player.isMyTurn()) {
                
                if(e.getSource() == draw) {
                    
                    BUTTON_LISTENER.drawCard();
                }
                else if(e.getSource() == sayUNO) {
                    
                    BUTTON_LISTENER.sayUNO();
                }
            }
        }
    }
}

package com.jmlearning.randomthings.uno.view;

import com.jmlearning.randomthings.uno.interfaces.CardInterface;
import com.jmlearning.randomthings.uno.interfaces.UNOConstants;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

public class UNOCard extends JPanel implements CardInterface, UNOConstants {
    
    private Color cardColor = null;
    private String value = null;
    private int type = 0;
    
    private Border defaultBorder = BorderFactory.createEtchedBorder(
            WHEN_FOCUSED, Color.WHITE, Color.GRAY);
    private Border focusedBorder = BorderFactory.createEtchedBorder(
            WHEN_FOCUSED, Color.BLACK, Color.GRAY);
    
    public UNOCard() {
    
    }
    
    public UNOCard(Color cardColor, int cardType, String cardValue) {
        
        this.cardColor = cardColor;
        this.type = cardType;
        this.value = cardValue;
        this.setPreferredSize(CARD_SIZE);
        this.setBorder(defaultBorder);
        this.addMouseListener(new MouseHandler());
    }
    
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g;
        
        int cardWidth = CARD_SIZE.width;
        int cardHeight = CARD_SIZE.height;
        
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, cardWidth, cardHeight);
        int margin = 5;
        g2d.setColor(cardColor);
        g2d.fillRect(margin, margin, cardWidth - 2 * margin,
                cardHeight - 2 * margin);
        g2d.setColor(Color.WHITE);
        AffineTransform org = g2d.getTransform();
        g2d.rotate(45, cardWidth * 3 / 4, cardHeight * 3 / 4);
        g2d.fillOval(0, cardHeight / 4, cardWidth * 3 / 5, cardHeight);
        g2d.setTransform(org);
        
        Font defaultFont = new Font("Helvetica", Font.BOLD, cardWidth / 2 + 5);
        FontMetrics fm = this.getFontMetrics(defaultFont);
        
        int strWidth = fm.stringWidth(value) / 2;
        int fontHeight  = defaultFont.getSize() / 3;
        
        g2d.setColor(cardColor);
        g2d.setFont(defaultFont);
        g2d.drawString(value, cardWidth / 2 - strWidth, cardHeight / 2 + fontHeight);
        
        defaultFont = new Font("Helvetica", Font.ITALIC, cardWidth / 5);
        fm = this.getFontMetrics(defaultFont);
        strWidth = fm.stringWidth(value) / 2;
        fontHeight = defaultFont.getSize() / 3;
        g2d.setColor(Color.WHITE);
        g2d.setFont(defaultFont);
        g2d.drawString(value, 2 * margin, 5 * margin);
    }
    
    @Override
    public Color getColor() {
        
        return cardColor;
    }
    
    @Override
    public void setColor(Color newColor) {
    
        this.cardColor = newColor;
    }
    
    @Override
    public String getValue() {
        
        return value;
    }
    
    @Override
    public void setValue(String newValue) {
    
        this.value = newValue;
    }
    
    @Override
    public int getType() {
        
        return type;
    }
    
    @Override
    public void setType(int newType) {
    
        this.type = newType;
    }
    
    class MouseHandler extends MouseAdapter {
        
        public void mouseEntered(MouseEvent e) {
            
            setBorder(focusedBorder);
        }
        
        public void mouseExited(MouseEvent e) {
            
            setBorder(defaultBorder);
        }
    }
}

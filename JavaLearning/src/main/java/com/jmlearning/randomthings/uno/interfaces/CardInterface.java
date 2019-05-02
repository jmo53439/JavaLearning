package com.jmlearning.randomthings.uno.interfaces;

import java.awt.*;

public interface CardInterface {
    
    int WIDTH = 50;
    int HEIGHT = 75;
    Dimension SMALL = new Dimension(WIDTH, HEIGHT);
    Dimension MEDIUM = new Dimension(WIDTH * 2, HEIGHT * 2);
    Dimension BIG = new Dimension(WIDTH * 3, HEIGHT * 3);
    
    // default card size
    Dimension CARD_SIZE = MEDIUM;
    
    // default offset
    int OFFSET = 71;
    
    Color getColor();
    void setColor(Color newColor);
    
    String getValue();
    void setValue(String newValue);
    
    int getType();
    void setType(int newType);
}

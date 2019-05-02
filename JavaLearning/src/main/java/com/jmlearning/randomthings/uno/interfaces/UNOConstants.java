package com.jmlearning.randomthings.uno.interfaces;

import java.awt.*;

public interface UNOConstants {
    
    // colors
    Color RED = new Color(192, 80, 77);
    Color BLUE = new Color(31, 73, 125);
    Color GREEN = new Color(0, 153, 0);
    Color YELLOW = new Color(255, 204, 0);
    Color BLACK = new Color(0, 0, 0);
    
    // types
    int NUMBERS = 1;
    int ACTION = 2;
    int WILD = 3;
    
    // action card characters
    Character charREVERSE = (char) 8634;
    Character charSKIP = (char) Integer.parseInt("2718", 16);
    
    // action card functions
    String REVERSE = charREVERSE.toString();
    String SKIP = charSKIP.toString();
    String DRAW2PLUS = "2+";
    
    // wild card functions
    String W_COLOR_PICKER = "W";
    String W_DRAW4PLUS = "4+";
}

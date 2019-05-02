package com.jmlearning.randomthings.uno.interfaces;

import com.jmlearning.randomthings.uno.controllers.MyButtonListener;
import com.jmlearning.randomthings.uno.controllers.MyCardListener;
import com.jmlearning.randomthings.uno.view.InfoPanel;

import java.awt.*;

import static java.awt.Color.*;

public interface GameConstants extends UNOConstants {
    
    int TOTAL_CARDS = 108;
    int FIRST_HAND = 8;
    
    Color[] UNO_COLORS = {RED, BLUE, GREEN, YELLOW};
    Color WILD_CARD_COLOR = BLACK;
    
    int[] UNO_NUMBERS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    String[] ActionTypes = {REVERSE, SKIP, DRAW2PLUS};
    String[] WildTypes = {W_COLOR_PICKER, W_DRAW4PLUS};
    
    int vsPC = 1;
    int MANUAL = 2;
    
    int[] GAME_MODES = {vsPC, MANUAL};
    
    MyCardListener CARD_LISTENER = new MyCardListener();
    MyButtonListener BUTTON_LISTENER = new MyButtonListener();
    InfoPanel infoPanel = new InfoPanel();
}

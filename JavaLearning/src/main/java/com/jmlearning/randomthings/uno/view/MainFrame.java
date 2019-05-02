package com.jmlearning.randomthings.uno.view;

import com.jmlearning.randomthings.uno.controllers.Server;
import com.jmlearning.randomthings.uno.interfaces.GameConstants;

import javax.swing.*;

public class MainFrame extends JFrame implements GameConstants {
   
    private Session mainPanel;
    private Server server;
    
    public MainFrame() {
    
        server = new Server();
        CARD_LISTENER.setServer(server);
        BUTTON_LISTENER.setServer(server);
        mainPanel = server.getSession();
        add(mainPanel);
    }
}

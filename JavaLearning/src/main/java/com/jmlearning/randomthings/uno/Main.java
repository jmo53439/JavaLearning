package com.jmlearning.randomthings.uno;

import com.jmlearning.randomthings.uno.view.MainFrame;

import javax.swing.*;

import static javax.swing.WindowConstants.*;

public class Main {
    
    public static void main(String[] args) {
    
        SwingUtilities.invokeLater(new Runnable() {
    
            @Override
            public void run() {
        
                JFrame frame = new MainFrame();
                frame.setVisible(true);
                frame.setResizable(false);
                frame.setLocation(200, 100);
                frame.pack();
                frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            }
        });
    }
}

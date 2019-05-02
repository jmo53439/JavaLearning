package com.jmlearning.randomthings.chess.ui;

import com.jmlearning.randomthings.chess.game.Core;
import com.jmlearning.randomthings.chess.game.GameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AwaitDialog extends JFrame {
    
    private GameModel gameModel;
    private JPanel waitingPanel;
    private JProgressBar waitingProgressBar;
    private JLabel waitingLabel;
    private JPanel buttonPanel;
    private JButton cancelButton;
    
    public AwaitDialog(GameModel gameModel) {
        
        super("Please Wait...");
        this.gameModel = gameModel;
        loadWaitingInterface();
    }
    
    public void loadErrorInterface() {
        
        waitingProgressBar.setVisible(false);
        waitingLabel.setText("~Connection Error~");
        cancelButton.setEnabled(true);
    }
    
    private void loadWaitingInterface() {
        
        initWaitingPanel();
        initButtonPanel();
        
        this.setLayout(new BorderLayout());
        this.add(waitingPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.PAGE_END);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(Core.getLaunchFrame());
        this.setVisible(true);
    }
    
    private void initWaitingPanel() {
    
        waitingLabel = new JLabel("Waiting for Connection...");
        waitingProgressBar = new JProgressBar(0, 100);
        waitingProgressBar.setIndeterminate(true);
        waitingPanel = new JPanel(new GridLayout(2, 1));
        waitingPanel.add(waitingLabel);
        waitingPanel.add(waitingProgressBar);
        waitingPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        waitingPanel.setPreferredSize(new Dimension(300, 100));
    }
    
    private void initButtonPanel() {
    
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
    
            @Override
            public void actionPerformed(ActionEvent e) {
        
                gameModel.getGameFrame().dispose();
                Core.getLaunchFrame().setVisible(true);
            }
        });
        
        cancelButton.setEnabled(false);
        buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPanel.add(cancelButton, BorderLayout.EAST);
    }
}

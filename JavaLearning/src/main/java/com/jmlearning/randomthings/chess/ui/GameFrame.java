package com.jmlearning.randomthings.chess.ui;

import com.jmlearning.randomthings.chess.game.Core;
import com.jmlearning.randomthings.chess.game.GameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class GameFrame extends JFrame implements Observer {
    
    private GameModel gameModel;
    private JMenuBar menuBar;
    private JMenu gameMenu;
    private JMenuItem newGameMenuItem;
    private JMenuItem saveGameMenuItem;
    private JMenuItem preferencesMenuItem;
    private JMenuItem exitMenuItem;
    private JMenu helpMenu;
    private JMenuItem customPiecesMenuItem;
    private JMenuItem aboutMenuItem;
    private JPanel boardPanel;
    private JPanel timerPanel;
    private JPanel controlPanel;
    private JPanel moveHistoryPanel;
    
    public GameFrame(GameModel gameModel) {
        
        this.boardPanel = gameModel.getBoardPanel();
        this.timerPanel = gameModel.getTimerPanel();
        this.controlPanel = gameModel.getControlPanel();
        this.moveHistoryPanel = gameModel.getMoveHistoryPanel();
        loadInterface();
        gameModel.addObserver(this);
    }
    
    private void loadInterface() {
        
        initMenuBar();
        initPanels();
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(Core.getLaunchFrame());
        this.setVisible(true);
    }
    
    private void initMenuBar() {
        
        newGameMenuItem = new JMenuItem("New Game");
        newGameMenuItem.addActionListener(new ActionListener() {
    
            @Override
            public void actionPerformed(ActionEvent e) {
        
            }
        });
        
        saveGameMenuItem = new JMenuItem("Save Game");
        saveGameMenuItem.addActionListener(new ActionListener() {
    
            @Override
            public void actionPerformed(ActionEvent e) {
        
            }
        });
        
        preferencesMenuItem = new JMenuItem("Preferences");
        preferencesMenuItem.addActionListener(new ActionListener() {
    
            @Override
            public void actionPerformed(ActionEvent e) {
        
            }
        });
        
        exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
    
            @Override
            public void actionPerformed(ActionEvent e) {
        
                int userChoice = JOptionPane.showConfirmDialog(getContentPane(),
                        "Um are you sure?", "Confirm Exit",
                        JOptionPane.YES_NO_OPTION);
                
                if(userChoice == JOptionPane.YES_OPTION) {
                    
                    System.exit(0);
                }
            }
        });
        
        gameMenu = new JMenu("Game");
        gameMenu.add(newGameMenuItem);
        gameMenu.add(saveGameMenuItem);
        gameMenu.add(preferencesMenuItem);
        gameMenu.addSeparator();
        gameMenu.add(exitMenuItem);
        
        customPiecesMenuItem = new JMenuItem("Custom Pieces...");
        customPiecesMenuItem.addActionListener(new ActionListener() {
    
            @Override
            public void actionPerformed(ActionEvent e) {
        
            }
        });
        
        aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.addActionListener(new ActionListener() {
    
            @Override
            public void actionPerformed(ActionEvent e) {
        
            }
        });
        
        helpMenu = new JMenu("Help");
        helpMenu.add(customPiecesMenuItem);
        helpMenu.add(aboutMenuItem);
        helpMenu.addSeparator();
        
        menuBar = new JMenuBar();
        menuBar.add(gameMenu);
        menuBar.add(helpMenu);
        menuBar.setVisible(true);
        this.setJMenuBar(menuBar);
    }
    
    private void initPanels() {
    
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        this.setLayout(gridBagLayout);
        
        // board panel
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 4;
        gridBagLayout.setConstraints(boardPanel, gridBagConstraints);
        this.add(boardPanel);
        
        // timer panel
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagLayout.setConstraints(timerPanel, gridBagConstraints);
        this.add(timerPanel);
    
        // control panel
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagLayout.setConstraints(controlPanel, gridBagConstraints);
        this.add(controlPanel);
    
        // move history panel
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 2;
        gridBagLayout.setConstraints(moveHistoryPanel, gridBagConstraints);
        this.add(moveHistoryPanel);
    }
    
    public void showCheckDialog() {
        
        JOptionPane.showMessageDialog(this, "Validating...",
                "Check", JOptionPane.WARNING_MESSAGE);
    }
    
    @Override
    public void update(Observable o, Object arg) {
    
    }
}

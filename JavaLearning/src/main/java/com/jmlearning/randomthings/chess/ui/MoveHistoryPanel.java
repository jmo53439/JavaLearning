package com.jmlearning.randomthings.chess.ui;

import com.jmlearning.randomthings.chess.game.GameModel;
import com.jmlearning.randomthings.chess.game.Move;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class MoveHistoryPanel extends JPanel implements Observer {
    
    private GameModel gameModel;
    private JScrollPane moveHistoryScrollPane;
    private JTextArea moveHistoryTextArea;
    private String moveHistoryContent;
    
    public MoveHistoryPanel(GameModel gameModel) {
        
        this.gameModel = gameModel;
        init();
    }
    
    private void init() {
        
        moveHistoryContent = new String("Game Start!\n");
        moveHistoryTextArea = new JTextArea(moveHistoryContent);
        moveHistoryTextArea.setBackground(Color.GRAY);
        moveHistoryScrollPane = new JScrollPane(moveHistoryTextArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        moveHistoryScrollPane.setBorder(
                BorderFactory.createTitledBorder("Move History"));
        moveHistoryScrollPane.setViewportView(moveHistoryTextArea);
        moveHistoryScrollPane.setPreferredSize(new Dimension(300, 400));
        this.add(moveHistoryScrollPane);
    }
    
    public void printMove(Move move) {
        
        String newMoveEntry = "";
        newMoveEntry += move.getPiece().getColor().toString() + " ";
        newMoveEntry += move.getPiece().getType().toString() + ": ";
        newMoveEntry += move.getOriginFile();
        newMoveEntry += move.getOriginRank() + " - ";
        newMoveEntry += move.getDestinationFile();
        newMoveEntry += move.getDestinationRank() + " ";
        
        if(move.getCapturedPiece() != null) {
            
            newMoveEntry += "Captures ";
            newMoveEntry += move.getCapturedPiece().getType().toString();
        }
        
        newMoveEntry += "\n";
        
        moveHistoryContent += newMoveEntry;
        
        SwingUtilities.invokeLater(new Runnable() {
    
            @Override
            public void run() {
        
                moveHistoryTextArea.setText(moveHistoryContent);
            }
        });
    }
    
    public void deleteLastMove() {
        
        moveHistoryContent = moveHistoryContent.substring(
                0, moveHistoryContent.lastIndexOf('\n'));
        SwingUtilities.invokeLater(new Runnable() {
    
            @Override
            public void run() {
        
                moveHistoryTextArea.setText(moveHistoryContent);
            }
        });
    }
    
    @Override
    public void update(Observable o, Object arg) {
    
    }
    
//    public static void main(String[] args) {
//
//        JFrame testFrame = new JFrame("MoveHistoryPanel Test");
//        MoveHistoryPanel testMoveHistoryPanel = new MoveHistoryPanel(new GameModel());
//        testFrame.add(testMoveHistoryPanel);
//
//        for(int i = 0; i < 30; i++) {
//
//            testMoveHistoryPanel.printMove(new Move(
//                    new Pawn(Piece.Color.WHITE), new Pawn(Piece.Color.BLACK),
//                    'a', i, 'a', 5));
//        }
//
//        testFrame.pack();
//        testFrame.setVisible(true);
//    }
}

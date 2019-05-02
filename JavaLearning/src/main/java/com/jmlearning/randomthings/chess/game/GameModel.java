package com.jmlearning.randomthings.chess.game;

import com.jmlearning.randomthings.chess.board.Board;
import com.jmlearning.randomthings.chess.pieces.Piece;
import com.jmlearning.randomthings.chess.ui.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import static com.jmlearning.randomthings.chess.game.Preferences.*;

public class GameModel extends Observable {
    
    private Preferences preferences;
    private GameFrame gameFrame;
    private BoardPanel boardPanel;
    private TimerPanel timerPanel;
    private ControlPanel controlPanel;
    private MoveHistoryPanel moveHistoryPanel;
    private AwaitDialog waitingDialog;
    private Timer whiteTimer;
    private Timer blackTimer;
    private NetworkHandler networkHandler;
    private String opponentName;
    
    public GameModel() {
        
        this.preferences = Core.getPreferences();
        init();
    }
    
    private void init() {
        
        initTimers();
        initUIComponents();
        
        if(preferences.getGameMode().equals(GameMode.ONLINE)) {
            
            initNetworkHandler();
            
            if(NetworkMode.HOST.equals(preferences.getNetworkMode())) {
                
                gameFrame.setVisible(false);
                waitingDialog.setVisible(true);
            }
        }
    }
    
    private void initTimers() {
    
        whiteTimer = new Timer(1000, new ActionListener() {
    
            @Override
            public void actionPerformed(ActionEvent e) {
        
                timerPanel.whiteTimerStart();
            }
        });
        
        blackTimer = new Timer(1000, new ActionListener() {
    
            @Override
            public void actionPerformed(ActionEvent e) {
        
                timerPanel.blackTimerStart();
            }
        });
    }
    
    private void initUIComponents() {
    
        boardPanel = new BoardPanel(this);
        timerPanel = new TimerPanel(this);
        controlPanel = new ControlPanel(this);
        moveHistoryPanel = new MoveHistoryPanel(this);
        gameFrame = new GameFrame(this);
        
        if(GameMode.ONLINE.equals(preferences.getGameMode()) &&
                NetworkMode.HOST.equals(preferences.getNetworkMode())) {
            
            waitingDialog = new AwaitDialog(this);
        }
    }
    
    private void initNetworkHandler() {
    
        networkHandler = new NetworkHandler(this);
        new Thread(networkHandler).start();
    }
    
    public void onHandShake() {
        
        if(waitingDialog != null) {
            
            waitingDialog.setVisible(false);
            waitingDialog.dispose();
        }
        
        gameFrame.setVisible(true);
    }
    
    public void onMoveRequest(char originFile, int originRank, char destinationFile, int destinationRank) {
        
        switch(preferences.getGameMode()) {
            
            case ONLINE:
                onOutboundRemoteMoveRequest(originFile, originRank, destinationFile, destinationRank);
                break;
                
            case OFFLINE:
                onLocalMoveRequest(originFile, originRank, destinationFile, destinationRank);
                break;
        }
    }
    
    private void onLocalMoveRequest(char originFile, int originRank,
             char destinationFile, int destinationRank) {
        
        Move move = new Move(originFile, originRank, destinationFile, destinationRank);
        
        if(MoveValidator.validateMove(move))
            executeMove(move);
    }
    
    public boolean onOutboundRemoteMoveRequest(char originFile, int originRank,
             char destinationFile, int destinationRank) {
    
        Move move = new Move(originFile, originRank, destinationFile, destinationRank);
        
        switch(preferences.getNetworkMode()) {
            
            case HOST:
                if(MoveValidator.validateMove(move)) {
                    
                    executeMove(move);
                    
                    if(!networkHandler.sendMoveMessage(move)) {
                        
                        return false;
                    }
                    
                    return true;
                }
                else {
                    
                    return false;
                }
                
            case CLIENT:
                if(!networkHandler.sendMoveMessage(move)) {
                    
                    return false;
                }
                
                return true;
        }
        
        return false;
    }
    
    public boolean onInboundRemoteMoveRequest(char originFile, int originRank,
             char destinationFile, int destinationRank) {
    
        Move move = new Move(originFile, originRank, destinationFile, destinationRank);
        
        switch(preferences.getNetworkMode()) {
            
            case HOST:
                if(MoveValidator.validateMove(move)) {
                    
                    executeMove(move);
                    
                    return true;
                }
                else {
                    
                    return false;
                }
                
            case CLIENT:
                executeMove(move);
                
                return true;
        }
        
        return false;
    }
    
    private void executeMove(Move move) {
        
        MoveLogger.addMove(move);
        Board.executeMove(move);
        moveHistoryPanel.printMove(move);
        boardPanel.executeMove(move);
        switchTimer(move);
        
        if(MoveValidator.isCheckMove(move)) {
         
            // checkmate
        }
        
        gameFrame.showCheckDialog();
    }
    
    public void onUndoRequest() {
        
        Move lastMove = MoveLogger.undoLastMove();
        
        if(lastMove != null) {
            
            if(MoveValidator.validateUndo(lastMove)) {
                
                boardPanel.executeUndo(lastMove);
                moveHistoryPanel.deleteLastMove();
                Board.executeUndo(lastMove);
                switchTimer(lastMove);
            }
        }
    }
    
    public Piece queryPiece(char file, int rank) {
        
        return Board.getSquare(file, rank).getCurrentPiece();
    }
    
    private void switchTimer(Move move) {
        
        if(move.getPiece().getColor().equals(Piece.Color.BLACK)) {
            
            whiteTimer.start();
            blackTimer.stop();
        }
        else {
            
            blackTimer.start();
            whiteTimer.stop();
        }
    }
    
    public GameFrame getGameFrame() {
        
        return gameFrame;
    }
    
    public BoardPanel getBoardPanel() {
        
        return boardPanel;
    }
    
    public TimerPanel getTimerPanel() {
        
        return timerPanel;
    }
    
    public ControlPanel getControlPanel() {
        
        return controlPanel;
    }
    
    public MoveHistoryPanel getMoveHistoryPanel() {
        
        return moveHistoryPanel;
    }
    
    public AwaitDialog getWaitingDialog() {
        
        return waitingDialog;
    }
    
    public void setOpponentName(String opponentName) {
        
        this.opponentName = opponentName;
    }
}

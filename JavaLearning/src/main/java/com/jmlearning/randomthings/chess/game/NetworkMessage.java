package com.jmlearning.randomthings.chess.game;

import com.jmlearning.randomthings.chess.pieces.Piece;
import twitter4j.JSONObject;

import java.io.Serializable;

import static com.jmlearning.randomthings.chess.game.Preferences.*;

public class NetworkMessage implements Serializable {
    
    public enum Type {
        
        HANDSHAKE, MOVE, UNDO, MOVE_RESPONSE,
        UNDO_RESPONSE, DISCONNECT
    }
    
    private Type type;
    
    private String playerName;
    private Piece.Color color;
    private NetworkMode networkMode;
    private TimerMode timerMode;
    private int timeLimit;
    private boolean usingCustomPieces;
    private boolean boardReversed;
    
    private char originFile;
    private int originRank;
    private char destinationFile;
    private int destinationRank;
    private boolean moveValid;
    private boolean undoAccepted;
    
    public NetworkMessage(Type type) {
        
        this.type = type;
    }
    
    public NetworkMessage(String networkMessageString) {
        
        parseNetworkMessage(networkMessageString);
    }
    
    private void parseNetworkMessage(String networkMessageString) {
    
        JSONObject networkMessageJSON = new JSONObject(networkMessageString);
        this.type = Type.valueOf(networkMessageJSON.getString("type"));
        
        switch(type) {
            
            case HANDSHAKE:
                playerName = networkMessageJSON.getString("player_name");
                timerMode = TimerMode.valueOf(
                        networkMessageJSON.getString("timer_mode"));
                
                if(timerMode.equals(TimerMode.COUNTDOWN)) {
                    
                    timeLimit = networkMessageJSON.getInt("time_limit");
                }
                
                usingCustomPieces = networkMessageJSON.getBoolean("using_custom_pieces");
                break;
                
            case MOVE:
                originFile = (char) networkMessageJSON.getInt("origin_file");
                originRank = networkMessageJSON.getInt("origin_rank");
                destinationFile = (char) networkMessageJSON.getInt("destination_file");
                destinationRank = networkMessageJSON.getInt("destination_rank");
                break;
                
            case MOVE_RESPONSE:
                moveValid = networkMessageJSON.getBoolean("move_valid");
                break;
                
            case UNDO:
                break;
                
            case UNDO_RESPONSE:
                break;
                
            case DISCONNECT:
                break;
        }
    }
    
    public void setMove(char originFile, int originRank,
                        char destinationFile, int destinationRank) {
        
        this.originFile = originFile;
        this.originRank = originRank;
        this.destinationFile = destinationFile;
        this.destinationRank = destinationRank;
    }
    
    public Type getType() {
        
        return type;
    }
    
    public void setType(Type type) {
        
        this.type = type;
    }
    
    public String getPlayerName() {
        
        return playerName;
    }
    
    public void setPlayerName(String playerName) {
        
        this.playerName = playerName;
    }
    
    public Piece.Color getColor() {
        
        return color;
    }
    
    public void setColor(Piece.Color color) {
        
        this.color = color;
    }
    
    public NetworkMode getNetworkMode() {
        
        return networkMode;
    }
    
    public void setNetworkMode(NetworkMode networkMode) {
        
        this.networkMode = networkMode;
    }
    
    public TimerMode getTimerMode() {
        
        return timerMode;
    }
    
    public void setTimerMode(TimerMode timerMode) {
        
        this.timerMode = timerMode;
    }
    
    public int getTimeLimit() {
        
        return timeLimit;
    }
    
    public void setTimeLimit(int timeLimit) {
        
        this.timeLimit = timeLimit;
    }
    
    public boolean isUsingCustomPieces() {
        
        return usingCustomPieces;
    }
    
    public void setUsingCustomPieces(boolean usingCustomPieces) {
        
        this.usingCustomPieces = usingCustomPieces;
    }
    
    public boolean isBoardReversed() {
        
        return boardReversed;
    }
    
    public void setBoardReversed(boolean boardReversed) {
        
        this.boardReversed = boardReversed;
    }
    
    public char getOriginFile() {
        
        return originFile;
    }
    
    public void setOriginFile(char originFile) {
        
        this.originFile = originFile;
    }
    
    public int getOriginRank() {
        
        return originRank;
    }
    
    public void setOriginRank(int originRank) {
        
        this.originRank = originRank;
    }
    
    public char getDestinationFile() {
        
        return destinationFile;
    }
    
    public void setDestinationFile(char destinationFile) {
        
        this.destinationFile = destinationFile;
    }
    
    public int getDestinationRank() {
        
        return destinationRank;
    }
    
    public void setDestinationRank(int destinationRank) {
        
        this.destinationRank = destinationRank;
    }
    
    public boolean isMoveValid() {
        
        return moveValid;
    }
    
    public void setMoveValid(boolean moveValid) {
        
        this.moveValid = moveValid;
    }
    
    public boolean isUndoAccepted() {
        
        return undoAccepted;
    }
    
    public void setUndoAccepted(boolean undoAccepted) {
        
        this.undoAccepted = undoAccepted;
    }
    
    @Override
    public String toString() {
        
        JSONObject networkMessageJSON = new JSONObject();
        networkMessageJSON.put("type", type.toString());
        
        switch(type) {
        
            case HANDSHAKE:
                networkMessageJSON.put("player_name", playerName);
                networkMessageJSON.put("timer_mode", timerMode.toString());
                
                if(timerMode.equals(TimerMode.COUNTDOWN)) {
                    
                    networkMessageJSON.put("time_limit", timeLimit);
                }
                
                networkMessageJSON.put("using_custom_pieces", usingCustomPieces);
                break;
                
            case MOVE:
                networkMessageJSON.put("origin_file", originFile);
                networkMessageJSON.put("origin_rank", originRank);
                networkMessageJSON.put("destination_file", destinationFile);
                networkMessageJSON.put("destination_rank", destinationRank);
                break;
                
            case MOVE_RESPONSE:
                networkMessageJSON.put("move_valid", moveValid);
                break;
                
            case UNDO:
                break;
                
            case UNDO_RESPONSE:
                networkMessageJSON.put("undo_accepted", undoAccepted);
                
            case DISCONNECT:
                break;
        }
        
        return networkMessageJSON.toString();
    }
}

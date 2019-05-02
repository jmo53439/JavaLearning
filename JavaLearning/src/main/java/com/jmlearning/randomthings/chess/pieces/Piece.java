package com.jmlearning.randomthings.chess.pieces;

import com.jmlearning.randomthings.chess.game.Move;

public abstract class Piece {
    
    protected Color color;
    protected Type type;
    protected boolean capture;
    
    public enum Color {
        
        WHITE, BLACK
    }
    
    public enum Type {
        
        KING, QUEEN, BISHOP, KNIGHT, ROOK, PAWN, CANNON, SHIELD
    }
    
    public Piece(Color color) {
        
        this.color = color;
        this.capture = false;
    }
    
    public abstract boolean validateMove(Move move);
    
    public String getImageFileName() {
        
        String fileName = "/pieces/";
        
        switch(color) {
            
            case WHITE:
                fileName += "white_";
                break;
                
            case BLACK:
                fileName += "black_";
                break;
        }
        
        switch(type) {
            
            case KING:
                fileName += "king";
                break;
                
            case QUEEN:
                fileName += "queen";
                break;
                
            case BISHOP:
                fileName += "bishop";
                break;
                
            case KNIGHT:
                fileName += "knight";
                break;
                
            case ROOK:
                fileName += "rook";
                break;
                
            case PAWN:
                fileName += "pawn";
                break;
                
            case CANNON:
                fileName += "cannon";
                break;
                
            case SHIELD:
                fileName += "shield";
                break;
        }
        
        fileName += ".png";
        
        return fileName;
    }
    
    public Color getColor() {
        
        return color;
    }
    
    public Type getType() {
        
        return type;
    }
    
    public boolean getCapture() {
        
        return this.capture;
    }
    
    public void setCapture(boolean isCaptured) {
        
        this.capture = isCaptured;
    }
}

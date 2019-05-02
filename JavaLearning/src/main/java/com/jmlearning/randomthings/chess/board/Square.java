package com.jmlearning.randomthings.chess.board;

import com.jmlearning.randomthings.chess.pieces.Piece;

public class Square {
    
    private int xPos;
    private int yPos;
    private char file;
    private int rank;
    private Piece currentPiece;
    
    public Square(int xPos, int yPos) {
        
        this.xPos = xPos;
        this.yPos = yPos;
        this.currentPiece = null;
    }
    
    public Square(int xPos, int yPos, Piece initialPiece) {
        
        this.xPos = xPos;
        this.yPos = yPos;
        this.currentPiece = initialPiece;
    }
    
    public Square(char file, int rank) {
        
        this.file = file;
        this.rank = rank;
    }
    
    public int getX() {
        
        return this.xPos;
    }
    
    public int getY() {
        
        return this.yPos;
    }
    
    public Piece getCurrentPiece() {
        
        return this.currentPiece;
    }
    
    public void setCurrentPiece(Piece piece) {
        
        this.currentPiece = piece;
    }
}

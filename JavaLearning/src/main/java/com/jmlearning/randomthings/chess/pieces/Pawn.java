package com.jmlearning.randomthings.chess.pieces;

import com.jmlearning.randomthings.chess.game.Move;

public class Pawn extends Piece {
    
    public Pawn(Color color) {
        
        super(color);
        this.type = Type.PAWN;
    }
    
    @Override
    public boolean validateMove(Move move) {
        
        Piece.Color color = move.getPiece().getColor();
        
        // diagonal capture
        switch(color) {
            
            case WHITE:
                if(Math.abs(move.getDestinationFile() - move.getOriginFile()) == 1 &&
                        move.getDestinationRank() - move.getOriginRank() == 1 &&
                        move.getCapturedPiece() != null &&
                        Color.BLACK.equals(move.getCapturedPiece().getColor()) &&
                        !move.getCapturedPiece().getType().equals(Type.SHIELD)) {
                    
                    return true;
                }
                
                break;
                
            case BLACK:
                if(Math.abs(move.getDestinationFile() - move.getOriginFile()) == 1 &&
                        move.getDestinationRank() - move.getOriginRank() == -1 &&
                        move.getCapturedPiece() != null &&
                        Color.WHITE.equals(move.getCapturedPiece().getColor())) {
                        
                    return true;
                }
                
                break;
        }
        
        // init executeMove
        switch(color) {
        
            case WHITE:
                if(move.getOriginRank() == 2 &&
                        move.getDestinationFile() == move.getOriginFile() &&
                        move.getDestinationRank() - move.getOriginRank() <= 2 &&
                        move.getDestinationRank() - move.getOriginRank() >= 1 &&
                        move.getCapturedPiece() == null) {
                    
                    return true;
                }
                
                break;
                
            case BLACK:
                if(move.getOriginRank() == 7 &&
                        move.getDestinationFile() == move.getOriginFile() &&
                        move.getDestinationRank() - move.getOriginRank() >= -2 &&
                        move.getDestinationRank() - move.getOriginRank() <= -1 &&
                        move.getCapturedPiece() == null) {
        
                    return true;
                }
                
                break;
        }
        
        switch(color) {
            
            case WHITE:
                if(move.getDestinationFile() == move.getOriginFile() &&
                        move.getDestinationRank() - move.getOriginRank() == 1 &&
                        move.getCapturedPiece() == null) {
                    
                    return true;
                }
                
                break;
                
            case BLACK:
                if(move.getDestinationFile() == move.getOriginFile() &&
                        move.getDestinationRank() - move.getOriginRank() == -1 &&
                        move.getCapturedPiece() == null) {
        
                    return true;
                }
    
                break;
        }
        
        return false;
    }
}

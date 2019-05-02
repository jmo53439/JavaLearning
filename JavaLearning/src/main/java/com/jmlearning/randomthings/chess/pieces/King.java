package com.jmlearning.randomthings.chess.pieces;

import com.jmlearning.randomthings.chess.game.Move;

public class King extends Piece {
    
    public King(Color color) {
        
        super(color);
        this.type = Type.KING;
    }
    
    @Override
    public boolean validateMove(Move move) {
        
        if((move.getCapturedPiece() == null) || (move.getCapturedPiece() != null &&
                !move.getPiece().getColor().equals(move.getCapturedPiece().getColor()) &&
                !move.getCapturedPiece().getType().equals(Type.SHIELD))) {
            
            if(Math.abs(move.getDestinationFile() - move.getOriginFile()) <= 1 &&
                    Math.abs(move.getDestinationRank() - move.getOriginRank()) <= 1) {
                
                return true;
            }
        }
        
        return false;
    }
}

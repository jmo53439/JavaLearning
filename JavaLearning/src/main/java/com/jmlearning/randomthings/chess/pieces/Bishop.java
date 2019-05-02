package com.jmlearning.randomthings.chess.pieces;

import com.jmlearning.randomthings.chess.game.Move;

public class Bishop extends Piece {
    
    public Bishop(Color color) {
        
        super(color);
        this.type = Type.BISHOP;
    }
    
    @Override
    public boolean validateMove(Move move) {
        
        if((move.getCapturedPiece() == null) || (move.getCapturedPiece() != null &&
                !move.getPiece().getColor().equals(move.getCapturedPiece().getColor()) &&
                !move.getCapturedPiece().getType().equals(Type.SHIELD))) {
            
            if(Math.abs(move.getDestinationFile() - move.getOriginFile()) ==
                    Math.abs(move.getDestinationRank() - move.getOriginRank())) {
                
                return true;
            }
        }
        
        return false;
    }
}

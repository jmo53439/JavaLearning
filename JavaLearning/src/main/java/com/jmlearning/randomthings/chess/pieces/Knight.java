package com.jmlearning.randomthings.chess.pieces;

import com.jmlearning.randomthings.chess.game.Move;

public class Knight extends Piece {
    
    public Knight(Color color) {
        
        super(color);
        this.type = Type.KNIGHT;
    }
    
    @Override
    public boolean validateMove(Move move) {
        
        if((move.getCapturedPiece() == null) || (move.getCapturedPiece() != null &&
                !move.getPiece().getColor().equals(move.getCapturedPiece().getColor()) &&
                !move.getCapturedPiece().getType().equals(Type.SHIELD))) {
            
            if((Math.abs(move.getDestinationFile() - move.getOriginFile()) == 1 &&
                    Math.abs(move.getDestinationRank() - move.getOriginRank()) == 2) ||
                    (Math.abs(move.getDestinationFile() - move.getOriginFile()) == 2 &&
                            Math.abs(move.getDestinationRank() - move.getOriginRank()) == 1)) {
                
                return true;
            }
        }
        
        return false;
    }
}

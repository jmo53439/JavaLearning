package com.jmlearning.randomthings.chess.pieces;

import com.jmlearning.randomthings.chess.game.Move;

public class Rook extends Piece {
    
    public Rook(Color color) {
        
        super(color);
        this.type = Type.ROOK;
    }
    
    @Override
    public boolean validateMove(Move move) {
        
        if((move.getCapturedPiece() == null) || (move.getCapturedPiece() != null &&
                !move.getPiece().getColor().equals(move.getCapturedPiece().getColor()) &&
                !move.getCapturedPiece().getType().equals(Type.SHIELD))) {
            
            if(move.getDestinationFile() == move.getOriginFile() &&
                    move.getDestinationRank() != move.getOriginRank()) {
                
                return true;
            }
            
            if(move.getDestinationFile() != move.getOriginFile() &&
                    move.getDestinationRank() == move.getOriginRank()) {
                
                return true;
            }
        }
        
        return false;
    }
}

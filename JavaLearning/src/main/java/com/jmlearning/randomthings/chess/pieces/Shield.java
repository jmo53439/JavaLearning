package com.jmlearning.randomthings.chess.pieces;

import com.jmlearning.randomthings.chess.game.Move;

public class Shield extends Piece {
    
    public Shield(Color color) {
        
        super(color);
        this.type = Type.SHIELD;
    }
    
    @Override
    public boolean validateMove(Move move) {
        
        if(move.getCapturedPiece() == null) {
            
            if(Math.abs(move.getDestinationFile() - move.getOriginFile()) <= 1 &&
                    Math.abs(move.getDestinationRank() - move.getOriginRank()) <= 1) {
                
                return true;
            }
        }
        
        return false;
    }
}

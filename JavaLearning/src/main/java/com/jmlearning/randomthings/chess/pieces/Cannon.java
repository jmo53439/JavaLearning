package com.jmlearning.randomthings.chess.pieces;

import com.jmlearning.randomthings.chess.board.Board;
import com.jmlearning.randomthings.chess.game.Move;

public class Cannon extends Piece {
    
    public Cannon(Color color) {
        
        super(color);
        this.type = Type.CANNON;
    }
    
    @Override
    public boolean validateMove(Move move) {
        
        int fileDirection = Integer.signum(move.getDestinationFile() - move.getOriginFile());
        int rankDirection = Integer.signum(move.getDestinationRank() - move.getOriginRank());
        
        if(move.getCapturedPiece() == null) {
            
            if(move.getDestinationFile() == move.getOriginFile() &&
                    move.getDestinationRank() != move.getOriginRank()) {
                
                for(int rank = move.getOriginRank() + rankDirection;
                    rank != move.getDestinationRank(); rank += rankDirection) {
                    
                    if(Board.getSquare(move.getOriginFile(), rank).getCurrentPiece() != null) {
                        
                        return false;
                    }
                }
            }
            
            if(move.getDestinationFile() != move.getOriginFile() &&
                    move.getDestinationRank() == move.getOriginRank()) {
                
                for(char file = (char)(move.getOriginFile() + fileDirection);
                    file != move.getDestinationFile(); file += fileDirection) {
                    
                    if(Board.getSquare(file, move.getOriginRank()).getCurrentPiece() != null) {
                        
                        return false;
                    }
                }
            }
        }
        
        if(move.getCapturedPiece() != null && !move.getCapturedPiece()
                .getType()
                .equals(Type.SHIELD)) {
            
            int hurdleCount = 0;
            
            if(move.getDestinationFile() - move.getOriginFile() == 0 &&
                    move.getDestinationRank() - move.getOriginRank() != 0) {
                
                for(int rank = move.getOriginRank() + rankDirection;
                    rank != move.getDestinationRank(); rank += rankDirection) {
                    
                    if(Board.getSquare(move.getOriginFile(), rank).getCurrentPiece() != null) {
                        
                        hurdleCount++;
                    }
                }
            }
            
            if(move.getDestinationFile() - move.getOriginFile() != 0 &&
                    move.getDestinationRank() - move.getOriginRank() == 0) {
                
                for(char file = (char)(move.getOriginFile() + fileDirection);
                    file != move.getDestinationFile(); file += fileDirection) {
                    
                    if(Board.getSquare(file, move.getOriginRank()).getCurrentPiece() != null) {
                        
                        hurdleCount++;
                    }
                }
            }
            
            if(hurdleCount == 1) {
                
                return Board.getSquare(move.getDestinationFile(), move.getDestinationRank())
                        .getCurrentPiece() != null;
            }
        }
        
        return false;
    }
}

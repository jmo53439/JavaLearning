package com.jmlearning.randomthings.chess.game;

import com.jmlearning.randomthings.chess.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class MoveLogger {
    
    private static List<MoveRound> moveHistory;
    private static List<Move> moveRoundBuffer;
    private static MoveLogger moveLoggerInstance = new MoveLogger();
    
    private MoveLogger() {
        
        init();
    }
    
    private void init() {
        
        moveHistory = new ArrayList <>();
        moveRoundBuffer = new ArrayList <>();
    }
    
    public static void addMove(Move move) {
        
        moveRoundBuffer.add(move);
        
        if(moveRoundBuffer.size() == 2) {
    
            moveHistory.add(new MoveRound(
                    moveRoundBuffer.get(0), moveRoundBuffer.get(1)));
            moveRoundBuffer.clear();
        }
    }
    
    public static int getCount() {
        
        return moveHistory.size();
    }
    
    public static boolean isFullRound() {
        
        return moveRoundBuffer.size() == 0;
    }
    
    public static Move getLastMove() {
        
        if(moveRoundBuffer.size() == 0) {
            
            return moveHistory.get(moveHistory.size() - 1).getMove(Piece.Color.BLACK);
        }
        else {
            
            return moveRoundBuffer.get(0);
        }
    }
    
    public static Move undoLastMove() {
        
        if(moveRoundBuffer.size() == 0) {
            
            if(moveHistory.size() > 0) {
                
                MoveRound currentRound = moveHistory.remove(moveHistory.size() - 1);
                moveRoundBuffer.add(currentRound.getMove(Piece.Color.WHITE));
                
                return currentRound.getMove(Piece.Color.BLACK);
            }
            else {
                
                return null;
            }
        }
        else {
            
            return moveRoundBuffer.remove(0);
        }
    }
    
    public static MoveLogger getInstance() {
        
        return moveLoggerInstance;
    }
    
    private static class MoveRound {
        
        private Move whiteMove;
        private Move blackMove;
        
        public MoveRound(Move whiteMove, Move blackMove) {
            
            this.whiteMove = whiteMove;
            this.blackMove = blackMove;
        }
        
        public Move getMove(Piece.Color color) {
            
            switch(color) {
                
                case WHITE:
                    return whiteMove;
                    
                case BLACK:
                    return blackMove;
                    
                default:
                    return null;
            }
        }
    }
}

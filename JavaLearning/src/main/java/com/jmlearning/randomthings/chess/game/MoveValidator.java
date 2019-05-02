package com.jmlearning.randomthings.chess.game;

import com.jmlearning.randomthings.chess.board.Board;
import com.jmlearning.randomthings.chess.pieces.Piece;
import com.jmlearning.randomthings.chess.pieces.PieceSet;

import java.util.List;

public class MoveValidator {
    
    private static MoveValidator myInstance = new MoveValidator();
    private static Piece.Color currentMoveColor;
    
    private MoveValidator() {
    
        currentMoveColor = Piece.Color.WHITE;
    }
    
    public static boolean validateMove(Move move, boolean ignoreColorCheck) {
        
        if(move.getDestinationFile() < 'a' || move.getDestinationFile() > 'h' ||
                move.getDestinationRank() < 1 || move.getDestinationRank() > 8) {
            
            return false;
        }
        
        if(move.getPiece() == null) {
            
            return false;
        }
        
        if(!move.getPiece().getColor().equals(currentMoveColor) &&
                !ignoreColorCheck) {
            
            return false;
        }
        
        if(move.getCapturedPiece() != null) {
            
            if(move.getPiece().getColor().equals(move.getCapturedPiece().getColor()))
                return false;
        }
        
        if(!move.getPiece().validateMove(move)) {
            
            return false;
        }
        
        if(!validateClearPath(move)) {
            
            return false;
        }
        
        currentMoveColor = currentMoveColor.equals(Piece.Color.WHITE) ?
                Piece.Color.BLACK : Piece.Color.WHITE;
        return true;
    }
    
    private static boolean validateClearPath(Move move) {
        
        // ignore cannon
        if(move.getPiece().getType().equals(Piece.Type.CANNON)) {
            
            return true;
        }
        
        int fileDirection = Integer.signum(
                move.getDestinationFile() - move.getOriginFile());
        int rankDirection = Integer.signum(
                move.getDestinationRank() - move.getOriginRank());
        
        // different moves
        if(Math.abs(move.getDestinationFile() - move.getOriginFile()) <= 1 &&
                Math.abs(move.getDestinationRank() - move.getOriginRank()) <= 1) {
            
            return true;
        }
        
        if((Math.abs(move.getDestinationFile() - move.getOriginFile()) == 1 &&
                Math.abs(move.getDestinationRank() - move.getOriginRank()) == 2) ||
                (Math.abs(move.getDestinationFile() - move.getOriginFile()) == 2 &&
                        Math.abs(move.getDestinationRank() - move.getOriginRank()) == 1)) {
            
            return true;
        }
        
        if(Math.abs(move.getDestinationFile() - move.getOriginFile()) ==
                Math.abs(move.getDestinationRank() - move.getOriginRank())) {
            
            for(int file = move.getOriginFile() + fileDirection, rank = move.getOriginRank() + rankDirection;
                file != move.getDestinationFile() && rank != move.getDestinationRank();
                file += fileDirection, rank += rankDirection) {
                
                if(Board.getSquare((char) file, rank).getCurrentPiece() != null) {
                    
                    return false;
                }
            }
            
            return true;
        }
        
        if(move.getDestinationFile() - move.getOriginFile() == 0 &&
                move.getDestinationRank() - move.getOriginRank() != 0) {
            
            for(int rank = move.getOriginRank() + rankDirection;
                rank != move.getDestinationRank(); rank += rankDirection) {
                
                if(Board.getSquare(move.getOriginFile(), rank).getCurrentPiece() != null) {
                    
                    return false;
                }
            }
            
            return true;
        }
        
        if(move.getDestinationFile() - move.getOriginFile() != 0 &&
                move.getDestinationRank() - move.getOriginRank() == 0) {
            
            for(char file = (char)(move.getOriginFile() + fileDirection);
                file != move.getDestinationFile(); file += fileDirection) {
                
                if(Board.getSquare(file, move.getOriginRank()).getCurrentPiece() != null) {
                    
                    return false;
                }
            }
            
            return true;
        }
        
        return false;
    }
    
    public static boolean validateMove(Move move) {
        
        return validateMove(move, false);
    }
    
    public static boolean validateUndo(Move move) {
        
        if(move.getPiece().getColor().equals(currentMoveColor))
            return false;
        
        currentMoveColor = currentMoveColor.equals(Piece.Color.WHITE) ?
                Piece.Color.BLACK : Piece.Color.WHITE;
        
        return true;
    }
    
    public static boolean isCheckMove(Move move) {
        
        Piece piece = move.getPiece();
        
        return validateMove(new Move(piece, move.getDestinationFile(),
                move.getDestinationRank(), PieceSet.getOpponentKingFile(piece.getColor()),
                PieceSet.getOpponentKingRank(piece.getColor())), true);
    }
    
    public static boolean isCheckMate(Move move) {
    
        Piece piece = move.getPiece();
        boolean isCheckMate = true;
        
        char opponentKingFile = PieceSet.getOpponentKingFile(piece.getColor());
        int opponentKingRank = PieceSet.getOpponentKingRank(piece.getColor());
        Piece opponentKing = Board
                .getSquare(opponentKingFile, opponentKingRank)
                .getCurrentPiece();
        
        // possible king moves
        isCheckMate &= !validateKingDodgeMove(
                move, opponentKing, -1, +1);
        isCheckMate &= !validateKingDodgeMove(
                move, opponentKing, 0, +1);
        isCheckMate &= !validateKingDodgeMove(
                move, opponentKing, +1, +1);
        isCheckMate &= !validateKingDodgeMove(
                move, opponentKing, +1, 0);
        isCheckMate &= !validateKingDodgeMove(
                move, opponentKing, +1, -1);
        isCheckMate &= !validateKingDodgeMove(
                move, opponentKing, 0, -1);
        isCheckMate &= !validateKingDodgeMove(
                move, opponentKing, -1, -1);
        isCheckMate &= !validateKingDodgeMove(
                move, opponentKing, -1, 0);
        
        if(!isCheckMate)
            return false;
        
        Piece.Color opponentColor = piece.getColor().equals(Piece.Color.WHITE) ?
                Piece.Color.BLACK : Piece.Color.WHITE;
        List<Piece> opponentPieces = PieceSet.getPieces(opponentColor);
        
        for(Piece currentPiece : opponentPieces) {
            
            if(!PieceSet.getCapturedPieces(opponentColor).contains(currentPiece)) {
            
            }
        }
        
        return isCheckMate;
    }
    
    private static boolean validateKingDodgeMove(Move move, Piece king,
            int dodgeMoveFileDiff, int dodgeMoveRankDiff) {
        
        char kingFile = PieceSet.getOpponentKingFile(move.getPiece().getColor());
        int kingRank = PieceSet.getOpponentKingRank(move.getPiece().getColor());
        
        if(validateMove(new Move(king, kingFile, kingRank, (char)(kingFile + dodgeMoveFileDiff),
                kingRank + dodgeMoveRankDiff))) {
        
            return validateMove(new Move(move.getPiece(), king, move.getDestinationFile(),
                    move.getDestinationRank(), (char)(kingFile + dodgeMoveFileDiff),
                    kingRank + dodgeMoveRankDiff));
        }
        
        return false;
    }
    
    private static boolean validateBlockDodgeMove() {
        
        return false;
    }
    
    public static MoveValidator getInstance() {
        
        return myInstance;
    }
}

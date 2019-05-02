package com.jmlearning.randomthings.chess.pieces;

import java.util.*;

public class PieceSet {
    
    private static Map<Piece.Color, Map<Piece.Type, List<Piece>>> pieceSet = null;
    private static Map<Piece.Color, Stack<Piece>> capturedPieceSet;
    
    private static char whiteKingFile;
    private static int whiteKingRank;
    private static char blackKingFile;
    private static int blackKingRank;
    
    private static PieceSet pieceSetInstance = new PieceSet();
    
    private PieceSet() {
        
        init();
    }
    
    private static void init() {
        
        initPieceSet();
        initCapturedPieceSet();
        initKingsCoordinates();
    }
    
    public static PieceSet getInstance() {
        
        return pieceSetInstance;
    }
    
    public static List<Piece> getPieces(Piece.Color color) {
        
        List<Piece> piecesOfSameColor = new ArrayList <>();
        
        for(Map.Entry<Piece.Type, List<Piece>> piecesEntry : pieceSet.get(color).entrySet()) {
            
            for(Piece piece : piecesEntry.getValue()) {
                
                piecesOfSameColor.add(piece);
            }
        }
        
        return piecesOfSameColor;
    }
    
    public static List<Piece> getPieces(Piece.Color color, Piece.Type type) {
        
        return pieceSet.get(color).get(type);
    }
    
    public static List<Piece> getCapturedPieces(Piece.Color color) {
        
        return capturedPieceSet.get(color);
    }
    
    public static void addCapturedPiece(Piece piece) {
    
        piece.setCapture(true);
        capturedPieceSet.get(piece.getColor()).push(piece);
    }
    
    public static char getOpponentKingFile(Piece.Color color) {
        
        if(color.equals(Piece.Color.WHITE)) {
            
            return blackKingFile;
        }
        else {
            
            return whiteKingFile;
        }
    }
    
    public static int getOpponentKingRank(Piece.Color color) {
        
        if(color.equals(Piece.Color.WHITE)) {
            
            return blackKingRank;
        }
        else {
            
            return whiteKingRank;
        }
    }
    
    private static void initKingsCoordinates() {
        
        whiteKingFile = blackKingFile = 'd';
        whiteKingRank = 1;
        blackKingRank = 8;
    }
    
    public static void setKingCoordinates(Piece.Color color, char file, int rank) {
        
        if(color.equals(Piece.Color.WHITE)) {
            
            whiteKingFile = file;
            whiteKingRank = rank;
        }
        else {
            
            blackKingFile = file;
            blackKingRank = rank;
        }
    }
    
    private static void initPieceSet() {
    
        pieceSet = new LinkedHashMap <>();
    
        Map<Piece.Type, List<Piece>> whitePieces = new LinkedHashMap <>();
        Map<Piece.Type, List<Piece>> blackPieces = new LinkedHashMap <>();
    
        List<Piece> whitePawns = new ArrayList <>();
        List<Piece> blackPawns = new ArrayList <>();
        
        for (int i = 0; i < 8; i++) {
            
            whitePawns.add(new Pawn(Piece.Color.WHITE));
            blackPawns.add(new Pawn(Piece.Color.BLACK));
        }
    
        List<Piece> whiteRooks = new ArrayList <>();
        List<Piece> blackRooks = new ArrayList <>();
        
        for (int i = 0; i < 2; i++) {
            
            whiteRooks.add(new Rook(Piece.Color.WHITE));
            blackRooks.add(new Rook(Piece.Color.BLACK));
        }
    
        List<Piece> whiteKnights = new ArrayList <>();
        List<Piece> blackKnights = new ArrayList <>();
        
        for (int i = 0; i < 2; i++) {
            
            whiteKnights.add(new Knight(Piece.Color.WHITE));
            blackKnights.add(new Knight(Piece.Color.BLACK));
        }
    
        List<Piece> whiteBishops = new ArrayList <>();
        List<Piece> blackBishops = new ArrayList <>();
        
        for (int i = 0; i < 2; i++) {
            
            whiteBishops.add(new Bishop(Piece.Color.WHITE));
            blackBishops.add(new Bishop(Piece.Color.BLACK));
        }
    
        List<Piece> whiteKing = new ArrayList <>();
        List<Piece> blackKing = new ArrayList <>();
        whiteKing.add(new King(Piece.Color.WHITE));
        blackKing.add(new King(Piece.Color.BLACK));
    
        List<Piece> whiteQueen = new ArrayList <>();
        List<Piece> blackQueen = new ArrayList <>();
        whiteQueen.add(new Queen(Piece.Color.WHITE));
        blackQueen.add(new Queen(Piece.Color.BLACK));
    
        List<Piece> whiteCannon = new ArrayList <>();
        List<Piece> blackCannon = new ArrayList <>();
        whiteCannon.add(new Cannon(Piece.Color.WHITE));
        blackCannon.add(new Cannon(Piece.Color.BLACK));
    
        List<Piece> whiteShield = new ArrayList <>();
        List<Piece> blackShield = new ArrayList <>();
        whiteShield.add(new Shield(Piece.Color.WHITE));
        blackShield.add(new Shield(Piece.Color.BLACK));
    
        whitePieces.put(Piece.Type.PAWN, whitePawns);
        whitePieces.put(Piece.Type.ROOK, whiteRooks);
        whitePieces.put(Piece.Type.KNIGHT, whiteKnights);
        whitePieces.put(Piece.Type.BISHOP, whiteBishops);
        whitePieces.put(Piece.Type.KING, whiteKing);
        whitePieces.put(Piece.Type.QUEEN, whiteQueen);
        whitePieces.put(Piece.Type.CANNON, whiteCannon);
        whitePieces.put(Piece.Type.SHIELD, whiteShield);
        blackPieces.put(Piece.Type.PAWN, blackPawns);
        blackPieces.put(Piece.Type.ROOK, blackRooks);
        blackPieces.put(Piece.Type.KNIGHT, blackKnights);
        blackPieces.put(Piece.Type.BISHOP, blackBishops);
        blackPieces.put(Piece.Type.KING, blackKing);
        blackPieces.put(Piece.Type.QUEEN, blackQueen);
        blackPieces.put(Piece.Type.CANNON, blackCannon);
        blackPieces.put(Piece.Type.SHIELD, blackShield);
    
        pieceSet.put(Piece.Color.WHITE, whitePieces);
        pieceSet.put(Piece.Color.BLACK, blackPieces);
    }
    
    private static void initCapturedPieceSet() {
    
        capturedPieceSet = new LinkedHashMap <>();
        Stack<Piece> whiteCapturedPieces = new Stack <>();
        Stack<Piece> blackCapturedPieces = new Stack <>();
        capturedPieceSet.put(Piece.Color.WHITE, whiteCapturedPieces);
        capturedPieceSet.put(Piece.Color.BLACK, blackCapturedPieces);
    }
}

package com.jmlearning.randomthings.chess.ui;

import com.jmlearning.randomthings.chess.game.Core;
import com.jmlearning.randomthings.chess.game.GameModel;
import com.jmlearning.randomthings.chess.game.Move;
import com.jmlearning.randomthings.chess.pieces.Piece;
import com.jmlearning.randomthings.chess.pieces.PieceSet;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

public class BoardPanel extends JPanel implements Observer {
    
    public static final int SQUARE_DIMENSION = 100;
    private GameModel gameModel;
    private boolean boardReversed;
    private boolean usingCustomPieces;
    private JLayeredPane boardLayeredPane;
    private JPanel boardPanel;
    private JPanel[][] squarePanels;
    
    public BoardPanel(GameModel gameModel) {
        
        super(new BorderLayout());
        this.gameModel = gameModel;
        this.boardReversed = Core.getPreferences().isBoardReversed();
        this.usingCustomPieces = Core.getPreferences().isUsingCustomPieces();
        initBoardLayeredPane();
        initSquares();
        initPieces();
        gameModel.addObserver(this);
    }
    
    public void submitMoveRequest(char originFile, int originRank, char destinationFile, int destinationRank) {
        
        if(getSquarePanel(originFile, originRank).getComponent(0) != null) {
            
            getSquarePanel(originFile, originRank).getComponent(0).setVisible(true);
            gameModel.onMoveRequest(originFile, originRank, destinationFile, destinationRank);
        }
    }
    
    public void executeMove(Move move) {
    
        JPanel originSquarePanel = getSquarePanel(move.getOriginFile(), move.getOriginRank());
        JPanel destinationSquarePanel = getSquarePanel(move.getDestinationFile(), move.getDestinationRank());
        destinationSquarePanel.removeAll();
        destinationSquarePanel.add(originSquarePanel.getComponent(0));
        destinationSquarePanel.repaint();
        originSquarePanel.removeAll();
        originSquarePanel.repaint();
    }
    
    public void executeUndo(Move move) {
    
        JPanel originSquarePanel = getSquarePanel(move.getOriginFile(), move.getOriginRank());
        JPanel destinationSquarePanel = getSquarePanel(move.getDestinationFile(), move.getDestinationRank());
        originSquarePanel.add(destinationSquarePanel.getComponent(0));
        destinationSquarePanel.removeAll();
        
        if(move.getCapturedPiece() != null) {
            
            destinationSquarePanel.add(getPieceImageLabel(move.getCapturedPiece()));
        }
        
        originSquarePanel.repaint();
        destinationSquarePanel.repaint();
    }
    
    public void preDrag(char originFile, int originRank, int dragX, int dragY) {
    
        Piece originPiece = gameModel.queryPiece(originFile, originRank);
        
        if(originPiece != null) {
            
            getSquarePanel(originFile, originRank).getComponent(0).setVisible(false);
            JLabel draggedPieceImageLabel = getPieceImageLabel(originPiece);
            draggedPieceImageLabel.setLocation(dragX, dragY);
            draggedPieceImageLabel.setSize(SQUARE_DIMENSION, SQUARE_DIMENSION);
            boardLayeredPane.add(draggedPieceImageLabel, JLayeredPane.DRAG_LAYER);
        }
    }
    
    public void executeDrag(int dragX, int dragY) {
        
        JLabel draggedPieceImageLabel = (JLabel) boardLayeredPane
                .getComponentsInLayer(JLayeredPane.DRAG_LAYER)[0];
        
        if(draggedPieceImageLabel != null)
            draggedPieceImageLabel.setLocation(dragX, dragY);
    }
    
    public void postDrag() {
    
        JLabel draggedPieceImageLabel = (JLabel) boardLayeredPane
                .getComponentsInLayer(JLayeredPane.DRAG_LAYER)[0];
        boardLayeredPane.remove(draggedPieceImageLabel);
        boardLayeredPane.repaint();
    }
    
    private void initBoardLayeredPane() {
        
        boardPanel = new JPanel(new GridLayout(8, 8));
        boardPanel.setBounds(0, 0, 800, 800);
        boardLayeredPane = new JLayeredPane();
        boardLayeredPane.setPreferredSize(new Dimension(800, 800));
        boardLayeredPane.add(boardPanel, JLayeredPane.DEFAULT_LAYER);
        DragDropListener dragDropPieceListener = new DragDropListener(this);
        boardLayeredPane.addMouseListener(dragDropPieceListener);
        boardLayeredPane.addMouseMotionListener(dragDropPieceListener);
        boardLayeredPane.setVisible(true);
        this.add(boardLayeredPane, BorderLayout.CENTER);
    }
    
    private void initSquares() {
        
        squarePanels = new JPanel[8][8];
        
        if(boardReversed) {
        
            for(int r = 0; r < 8; r++) {
                
                for(int f = 7; f >= 0; f--) {
                    
                    initSingleSquarePanel(f, r);
                }
            }
        }
        else {
            
            for(int r = 7; r >= 0; r--) {
                
                for(int f = 0; f < 8; f++) {
                    
                    initSingleSquarePanel(f, r);
                }
            }
        }
    }
    
    private void initPieces() {
    
        // pawns
        Iterator<Piece> whitePawnsIterator = PieceSet
                .getPieces(Piece.Color.WHITE, Piece.Type.PAWN)
                .iterator();
        Iterator<Piece> blackPawnsIterator = PieceSet
                .getPieces(Piece.Color.BLACK, Piece.Type.PAWN)
                .iterator();
        
        for (char file = 'a'; file <= 'h'; file++) {
            
            getSquarePanel(file, 2).add(getPieceImageLabel(whitePawnsIterator.next()));
            getSquarePanel(file, 7).add(getPieceImageLabel(blackPawnsIterator.next()));
        }
    
        // rooks
        Iterator<Piece> whiteRooksIterator = PieceSet
                .getPieces(Piece.Color.WHITE, Piece.Type.ROOK)
                .iterator();
        Iterator<Piece> blackRooksIterator = PieceSet
                .getPieces(Piece.Color.BLACK, Piece.Type.ROOK)
                .iterator();
        
        getSquarePanel('a', 1).add(getPieceImageLabel(whiteRooksIterator.next()));
        getSquarePanel('h', 1).add(getPieceImageLabel(whiteRooksIterator.next()));
        getSquarePanel('a', 8).add(getPieceImageLabel(blackRooksIterator.next()));
        getSquarePanel('h', 8).add(getPieceImageLabel(blackRooksIterator.next()));
    
        // knights
        Iterator<Piece> whiteKnightsIterator = PieceSet
                .getPieces(Piece.Color.WHITE, Piece.Type.KNIGHT)
                .iterator();
        Iterator<Piece> blackKnightsIterator = PieceSet
                .getPieces(Piece.Color.BLACK, Piece.Type.KNIGHT)
                .iterator();
        
        getSquarePanel('b', 1).add(getPieceImageLabel(whiteKnightsIterator.next()));
        getSquarePanel('g', 1).add(getPieceImageLabel(whiteKnightsIterator.next()));
        getSquarePanel('b', 8).add(getPieceImageLabel(blackKnightsIterator.next()));
        getSquarePanel('g', 8).add(getPieceImageLabel(blackKnightsIterator.next()));
    
        // bishops
        Iterator<Piece> whiteBishopsIterator = PieceSet
                .getPieces(Piece.Color.WHITE, Piece.Type.BISHOP)
                .iterator();
        Iterator<Piece> blackBishopsIterator = PieceSet
                .getPieces(Piece.Color.BLACK, Piece.Type.BISHOP)
                .iterator();
        
        getSquarePanel('c', 1).add(getPieceImageLabel(whiteBishopsIterator.next()));
        getSquarePanel('f', 1).add(getPieceImageLabel(whiteBishopsIterator.next()));
        getSquarePanel('c', 8).add(getPieceImageLabel(blackBishopsIterator.next()));
        getSquarePanel('f', 8).add(getPieceImageLabel(blackBishopsIterator.next()));
    
        // queens
        Iterator<Piece> whiteQueenIterator = PieceSet
                .getPieces(Piece.Color.WHITE, Piece.Type.QUEEN)
                .iterator();
        Iterator<Piece> blackQueenIterator = PieceSet
                .getPieces(Piece.Color.BLACK, Piece.Type.QUEEN)
                .iterator();
        
        getSquarePanel('d', 1).add(getPieceImageLabel(whiteQueenIterator.next()));
        getSquarePanel('d', 8).add(getPieceImageLabel(blackQueenIterator.next()));
    
        // kings
        Iterator<Piece> whiteKingIterator = PieceSet
                .getPieces(Piece.Color.WHITE, Piece.Type.KING)
                .iterator();
        Iterator<Piece> blackKingIterator = PieceSet
                .getPieces(Piece.Color.BLACK, Piece.Type.KING)
                .iterator();
        
        getSquarePanel('e', 1).add(getPieceImageLabel(whiteKingIterator.next()));
        getSquarePanel('e', 8).add(getPieceImageLabel(blackKingIterator.next()));
    
        if(usingCustomPieces) {
            
            // cannons
            Iterator<Piece> whiteCannonIterator = PieceSet
                    .getPieces(Piece.Color.WHITE, Piece.Type.CANNON)
                    .iterator();
            Iterator<Piece> blackCannonIterator = PieceSet
                    .getPieces(Piece.Color.BLACK, Piece.Type.CANNON)
                    .iterator();
            
            getSquarePanel('a', 1).removeAll();
            getSquarePanel('a', 1).add(getPieceImageLabel(whiteCannonIterator.next()));
            getSquarePanel('h', 8).removeAll();
            getSquarePanel('h', 8).add(getPieceImageLabel(blackCannonIterator.next()));
        
            // shields
            Iterator<Piece> whiteShieldIterator = PieceSet
                    .getPieces(Piece.Color.WHITE, Piece.Type.SHIELD)
                    .iterator();
            Iterator<Piece> blackShieldIterator = PieceSet
                    .getPieces(Piece.Color.BLACK, Piece.Type.SHIELD)
                    .iterator();
            
            getSquarePanel('f', 1).removeAll();
            getSquarePanel('f', 1).add(getPieceImageLabel(whiteShieldIterator.next()));
            getSquarePanel('c', 8).removeAll();
            getSquarePanel('c', 8).add(getPieceImageLabel(blackShieldIterator.next()));
        }
    }
    
    private void initSingleSquarePanel(int f, int r) {
        
        squarePanels[f][r] = new JPanel(new GridLayout(1, 1));
        squarePanels[f][r].setPreferredSize(new Dimension(SQUARE_DIMENSION, SQUARE_DIMENSION));
        squarePanels[f][r].setSize(new Dimension(SQUARE_DIMENSION, SQUARE_DIMENSION));
        squarePanels[f][r].setBackground(f % 2 == r % 2 ? Color.GRAY : Color.WHITE);
        boardPanel.add(squarePanels[f][r]);
    }
    
    private JLabel getPieceImageLabel(Piece piece) {
        
        Image pieceImage = new ImageIcon(getClass()
                .getResource(piece.getImageFileName()))
                .getImage();
        pieceImage = pieceImage.getScaledInstance(
                SQUARE_DIMENSION, SQUARE_DIMENSION, Image.SCALE_SMOOTH);
//        JLabel pieceImageLabel = new JLabel(new ImageIcon(pieceImage));

//        return pieceImageLabel;
    
        return new JLabel(new ImageIcon(pieceImage));
    }
    
    public JPanel getSquarePanel(char file, int rank) {
        
        file = Character.toLowerCase(file);
        
        if(file < 'a' || file > 'h' || rank < 1 || rank > 8) {
            
            return null;
        }
        else {
            
            return squarePanels[file - 'a'][rank - 1];
        }
    }
    
    public boolean isBoardReversed() {
        
        return boardReversed;
    }
    
    @Override
    public void update(Observable o, Object arg) {
    
        executeMove((Move) arg);
    }
}

package com.jmlearning.randomthings.chess.ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class DragDropListener implements MouseListener, MouseMotionListener {
    
    private BoardPanel boardPanel;
    private boolean dragging;
    private char originFile;
    private int originRank;
    private int dragOffsetX;
    private int dragOffsetY;
    
    public DragDropListener(BoardPanel boardPanel) {
        
        this.boardPanel = boardPanel;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    
        // not used
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
    
        originFile = calcFile(e);
        originRank = calcRank(e);
        
        if(boardPanel.isBoardReversed()) {
            
            dragOffsetX = e.getPoint().x - boardPanel.SQUARE_DIMENSION * ('h' - calcFile(e));
            dragOffsetY = e.getPoint().y - boardPanel.SQUARE_DIMENSION * (calcRank(e) - 1);
        }
        else {
            
            dragOffsetX = e.getPoint().x - boardPanel.SQUARE_DIMENSION * (calcFile(e) - 'a');
            dragOffsetY = e.getPoint().y - boardPanel.SQUARE_DIMENSION * (8 - calcRank(e));
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
    
        if(dragging) {
    
            boardPanel.postDrag();
            boardPanel.submitMoveRequest(originFile, originRank, calcFile(e), calcRank(e));
        }
        
        dragging = false;
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
    
        // not used
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
    
        // not used
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
    
        if(dragging) {
    
            boardPanel.executeDrag(e.getPoint().x - dragOffsetX,
                    e.getPoint().y - dragOffsetY);
        }
        else {
            
            boardPanel.preDrag(originFile, originRank, e.getPoint().x - dragOffsetX,
                    e.getPoint().y - dragOffsetY);
            dragging = true;
        }
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
    
        // not used
    }
    
    private char calcFile(MouseEvent e) {
        
        if(boardPanel.isBoardReversed()) {
            
            return (char)('h' - e.getPoint().x / boardPanel.SQUARE_DIMENSION);
        }
        else {
            
            return (char)('a' + e.getPoint().x / boardPanel.SQUARE_DIMENSION);
        }
    }
    
    private int calcRank(MouseEvent e) {
    
        if(boardPanel.isBoardReversed()) {
            
            return 1 + e.getPoint().y / boardPanel.SQUARE_DIMENSION;
        }
        else {
            
            return 8 - e.getPoint().y / boardPanel.SQUARE_DIMENSION;
        }
    }
}

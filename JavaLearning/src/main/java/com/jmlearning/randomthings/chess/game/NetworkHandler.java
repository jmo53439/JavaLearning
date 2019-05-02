package com.jmlearning.randomthings.chess.game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkHandler implements Runnable {
    
    private GameModel gameModel;
    private Preferences preferences;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private boolean dispatchEnabled;
    
    public NetworkHandler(GameModel gameModel) {
        
        this.gameModel = gameModel;
    }
    
    @Override
    public void run() {
    
        init();
        
        if(handshake()) {
            
            gameModel.onHandShake();
            new Thread(new MessageDispatch()).start();
        }
    }
    
    private void init() {
        
        preferences = Core.getPreferences();
        
        switch(preferences.getNetworkMode()) {
            
            case HOST:
                initInbound();
                break;
                
            case CLIENT:
                initOutbound();
                break;
        }
    }
    
    private void initInbound() {
        
        try {
            
            serverSocket = new ServerSocket(preferences.getPort(), 10);
            clientSocket = serverSocket.accept();
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            outputStream.flush();
        }
        catch(IOException e) {
            
            gameModel.getWaitingDialog().loadErrorInterface();
        }
    }
    
    private void initOutbound() {
        
        try {
            
            clientSocket = new Socket(preferences.getHostIP(), preferences.getPort());
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            outputStream.flush();
        }
        catch(IOException e) {
            
            e.printStackTrace();
        }
    }
    
    private synchronized boolean handshake() {
        
        switch(preferences.getNetworkMode()) {
            
            case HOST:
                return handshakeInbound();
                
            case CLIENT:
                return handshakeOutbound() ;
        }
        
        return false;
    }
    
    private synchronized boolean handshakeInbound() {
        
        NetworkMessage handshakeMessage = null;
        
        try {
            
            handshakeMessage = (NetworkMessage) inputStream.readObject();
        }
        catch(ClassNotFoundException | IOException e) {
            
            // end connection
            return false;
        }
        
        if(NetworkMessage.Type.HANDSHAKE.equals(handshakeMessage.getType())) {
            
            gameModel.setOpponentName(handshakeMessage.getPlayerName());
        }
        else {
            
            // end connection
            return false;
        }
        
        NetworkMessage responseHandshakeMessage = new NetworkMessage(NetworkMessage.Type.HANDSHAKE);
        responseHandshakeMessage.setPlayerName(preferences.getPlayerName());
        responseHandshakeMessage.setTimerMode(preferences.getTimerMode());
        
        if(preferences.getTimerMode().equals(Preferences.TimerMode.COUNTDOWN)) {
            
            responseHandshakeMessage.setTimeLimit(preferences.getTimeLimit());
        }
        
        responseHandshakeMessage.setUsingCustomPieces(preferences.isUsingCustomPieces());
        
        try {
            
            outputStream.writeObject(responseHandshakeMessage);
            outputStream.flush();
        }
        catch(IOException e) {
            
            // end connection
            return false;
        }
        
        System.out.println("Handshake - Inbound: Success");
        return true;
    }
    
    private synchronized boolean handshakeOutbound() {
        
        NetworkMessage handshakeMessage = new NetworkMessage(NetworkMessage.Type.HANDSHAKE);
        handshakeMessage.setPlayerName(preferences.getPlayerName());
        
        try {
            
            outputStream.writeObject(handshakeMessage);
            outputStream.flush();
        }
        catch(IOException e) {
            
            // end connection
            return false;
        }
        
        NetworkMessage responseHandshakeMessage = null;
        
        try {
            
            responseHandshakeMessage = (NetworkMessage) inputStream.readObject();
        }
        catch(ClassNotFoundException | IOException e) {
            
            // end connection
            return false;
        }
        
        if(NetworkMessage.Type.HANDSHAKE.equals(responseHandshakeMessage.getType())) {
        
            gameModel.setOpponentName(responseHandshakeMessage.getPlayerName());
            preferences.setTimerMode(responseHandshakeMessage.getTimerMode());
            
            if(responseHandshakeMessage.getTimerMode().equals(Preferences.TimerMode.COUNTDOWN)) {
    
                preferences.setTimeLimit(responseHandshakeMessage.getTimeLimit());
            }
    
            preferences.setUsingCustomPieces(responseHandshakeMessage.isUsingCustomPieces());
        }
        else {
            
            return false;
        }
        
        System.out.println("Handshake - Outbound: Success");
        return true;
    }
    
    public synchronized boolean sendMoveMessage(Move move) {
        
        dispatchEnabled = false;
        NetworkMessage moveMessage = new NetworkMessage(NetworkMessage.Type.MOVE);
        moveMessage.setMove(move.getOriginFile(), move.getOriginRank(),
                move.getDestinationFile(), move.getDestinationRank());
        
        try {
    
            outputStream.writeObject(moveMessage.toString());
            outputStream.flush();
        }
        catch(IOException e) {
            
            dispatchEnabled = true;
            return false;
        }
        
        NetworkMessage responseMoveMessage = null;
        
        try {
            
            responseMoveMessage = new NetworkMessage((String) inputStream.readObject());
        }
        catch(ClassNotFoundException | IOException e) {
            
            dispatchEnabled = true;
            return false;
        }
        
        if(NetworkMessage.Type.MOVE_RESPONSE.equals(responseMoveMessage.getType())) {
            
            dispatchEnabled = true;
            return responseMoveMessage.isMoveValid();
        }
        else {
            
            dispatchEnabled = true;
            return false;
        }
    }
    
    private synchronized void onMoveMessage(NetworkMessage moveMessage) {
        
        dispatchEnabled = false;
        System.out.println("MoveMessage - Inbound: Received");
        NetworkMessage responseMoveMessage = new NetworkMessage(NetworkMessage.Type.MOVE_RESPONSE);
        
        if(gameModel.onInboundRemoteMoveRequest(moveMessage.getOriginFile(),
                moveMessage.getOriginRank(), moveMessage.getDestinationFile(),
                moveMessage.getDestinationRank())) {
            
            responseMoveMessage.setMoveValid(true);
        }
        else {
            
            responseMoveMessage.setMoveValid(false);
        }
        
        try {
            
            outputStream.writeObject(responseMoveMessage.toString());
            outputStream.flush();
            dispatchEnabled = true;
        }
        catch(IOException e) {
        
        }
    }
    
    public boolean onUndoMessage(NetworkMessage undoMessage) {
        
        return true;
    }
    
    private class MessageDispatch implements Runnable {
    
        NetworkMessage receivedMessage = null;
        
        @Override
        public void run() {
        
            do {
                
                try {
                    
                    receivedMessage = new NetworkMessage((String) inputStream.readObject());
                    System.out.println("MessageDispatch: Message Received with Type [" +
                            receivedMessage.getType().toString() + "]");
                    
                    switch(receivedMessage.getType()) {
                        
                        case MOVE:
                            onMoveMessage(receivedMessage);
                            break;
                            
                        case UNDO:
                            onUndoMessage(receivedMessage);
                            break;
                            
                        case DISCONNECT:
                            break;
                            
                        default:
                            break;
                    }
                }
                catch(ClassNotFoundException | IOException e) {
                
                }
            }
            while(true);
        }
    }
}

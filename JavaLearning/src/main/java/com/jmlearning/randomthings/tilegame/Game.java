package com.jmlearning.randomthings.tilegame;

import com.jmlearning.randomthings.tilegame.display.Display;
import com.jmlearning.randomthings.tilegame.gfx.Assets;
import com.jmlearning.randomthings.tilegame.gfx.GameCamera;
import com.jmlearning.randomthings.tilegame.input.KeyManager;
import com.jmlearning.randomthings.tilegame.input.MouseManager;
import com.jmlearning.randomthings.tilegame.states.GameState;
import com.jmlearning.randomthings.tilegame.states.MenuState;
import com.jmlearning.randomthings.tilegame.states.State;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {
    
    private Display display;
    public int width, height;
    public String title;
    private boolean running = false;
    private Thread thread;
    private BufferStrategy bs;
    private Graphics g;
    public State gameState;
    public State menuState;
    private KeyManager keyManager;
    private MouseManager mouseManager;
    private GameCamera gameCamera;
    private Handler handler;
    
    public Game(String title, int width, int height) {
        
        this.width = width;
        this.height = height;
        this.title = title;
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
    }
    
    @Override
    public void run() {
    
        init();
        
        int fps = 60;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long currentTime;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;
        
        while(running) {
            
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / timePerTick;
            timer += currentTime - lastTime;
            lastTime = currentTime;
            
            if(delta >= 1) {
                
                tick();
                render();
                ticks++;
                delta--;
            }
            
            if(timer >= 1000000000) {
                
                System.out.println("Ticks and Frames: " + ticks);
                ticks = 0;
                timer = 0;
            }
        }
        
        stop();
    }
    
    private void init() {
      
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);
        display.getFrame().addMouseListener(mouseManager);
        display.getFrame().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);
        Assets.init();
    
        handler = new Handler(this);
        gameCamera = new GameCamera(handler, 0, 0);
        gameState = new GameState(handler);
        menuState = new MenuState(handler);
        State.setState(menuState);
    }
    
    private void tick() {
    
        keyManager.tick();
        
        if(State.getState() != null) {
            
            State.getState().tick();
        }
    }
    
    private void render() {
    
        bs = display.getCanvas().getBufferStrategy();
        
        if(bs == null) {
            
            display.getCanvas().createBufferStrategy(3);
            
            return;
        }
        
        g = bs.getDrawGraphics();
        g.clearRect(0, 0, width, height);
        
        if(State.getState() != null) {
            
            State.getState().render(g);
        }
        
        bs.show();
        g.dispose();
    }
    
    public KeyManager getKeyManager() {
        
        return keyManager;
    }
    
    public MouseManager getMouseManager() {
        
        return mouseManager;
    }
    
    public GameCamera getGameCamera() {
        
        return gameCamera;
    }
    
    public int getWidth() {
        
        return width;
    }
    
    public int getHeight() {
        
        return height;
    }
    
    public synchronized void start() {
        
        if(running)
            return;
        
        running = true;
        thread = new Thread(this);
        thread.start();
    }
    
    public synchronized void stop() {
        
        if(!running)
            return;
        
        running = false;
        
        try {
            
            thread.join();
        }
        catch(InterruptedException e) {
            
            e.printStackTrace();
        }
    }
}

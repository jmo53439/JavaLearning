package com.jmlearning.randomthings.tilegame;

import com.jmlearning.randomthings.tilegame.display.Display;
import com.jmlearning.randomthings.tilegame.gfx.Assets;
import com.jmlearning.randomthings.tilegame.input.KeyManager;
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
    private State gameState;
    private State menuState;
    private KeyManager keyManager;
    
    public Game(String title, int width, int height) {
        
        this.width = width;
        this.height = height;
        this.title = title;
        keyManager = new KeyManager();
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
        Assets.init();
        
        gameState = new GameState(this);
        menuState = new MenuState(this);
        State.setState(gameState);
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

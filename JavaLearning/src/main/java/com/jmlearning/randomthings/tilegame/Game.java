package com.jmlearning.randomthings.tilegame;

import com.jmlearning.randomthings.tilegame.display.Display;

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
    
    public Game(String title, int width, int height) {
        
        this.width = width;
        this.height = height;
        this.title = title;
    }
    
    @Override
    public void run() {
    
        init();
        
        while(running) {
        
            tick();
            render();
        }
        
        stop();
    }
    
    private void init() {
      
        display = new Display(title, width, height);
    }
    
    private void tick() {
    
    }
    
    private void render() {
    
        bs = display.getCanvas().getBufferStrategy();
        
        if(bs == null) {
            
            display.getCanvas().createBufferStrategy(3);
            
            return;
        }
        
        g = bs.getDrawGraphics();
        g.clearRect(0, 0, width, height);
        
        g.setColor(Color.RED);
        g.fillRect(10, 50, 50, 70);
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, 10, 10);
        
        bs.show();
        g.dispose();
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

package com.jmlearning.randomthings.raytracer;

import com.jmlearning.randomthings.raytracer.core.Controller;
import com.jmlearning.randomthings.raytracer.core.Scene;
import com.jmlearning.randomthings.raytracer.core.Viewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    
    private boolean alive;
    
    public static void main(String[] args) {
        
        new Main().run();
    }
    
    private void run() {
    
        EventQueue.invokeLater(() -> {
    
            Controller controller = new Controller(new Viewer(480, 272), new Scene());
            
            Thread tick = new Thread(() -> {
               
                alive = true;
                
                while(alive)
                    controller.step();
            });
    
            JFrame frame = new JFrame();
    
            frame.setResizable(false);
            frame.setTitle("Ray Tracer");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.getContentPane().add(controller.getView());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
    
            frame.addWindowListener(new WindowAdapter() {
    
                @Override
                public void windowClosing(WindowEvent e) {
        
                    alive = false;
                    super.windowClosing(e);
                }
            });
    
            tick.start();
        });
    }
}

package com.jmlearning.randomthings.tilegame.states;

import com.jmlearning.randomthings.tilegame.Handler;
import com.jmlearning.randomthings.tilegame.gfx.Assets;
import com.jmlearning.randomthings.tilegame.ui.ClickListener;
import com.jmlearning.randomthings.tilegame.ui.UIImageButton;
import com.jmlearning.randomthings.tilegame.ui.UIManager;

import java.awt.*;

public class MenuState extends State {
    
    private UIManager uiManager;
    
    public MenuState(Handler handler) {
    
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUIManager(uiManager);
        
        uiManager.addObject(new UIImageButton(200, 200, 128, 64, Assets.btnStart, new ClickListener() {
    
            @Override
            public void onClick() {
        
                handler.getMouseManager().setUIManager(null);
                State.setState(handler.getGame().gameState);
            }
        }));
    }
    
    @Override
    public void tick() {
    
        uiManager.tick();
    }
    
    @Override
    public void render(Graphics g) {
    
        uiManager.render(g);
    }
}

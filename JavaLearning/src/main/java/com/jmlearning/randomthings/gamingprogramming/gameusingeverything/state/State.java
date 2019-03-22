package com.jmlearning.randomthings.gamingprogramming.gameusingeverything.state;

import com.jmlearning.randomthings.gamingprogramming.gameusingeverything.FinishedProduct;
import com.jmlearning.randomthings.gamingprogramming.utils.Matrix3x3f;

import java.awt.*;

public class State {

    protected StateController controller;
    protected FinishedProduct game;

    protected StateController getController() {

        return controller;
    }

    public void setController(StateController controller) {

        this.controller = controller;
        game = (FinishedProduct) controller.getAttribute("gamingprogramming");
    }

    public void enter() {

    }

    public void processInput(float delta) {

    }

    public void updateObjects(float delta) {

    }

    public void render(Graphics2D g, Matrix3x3f view) {

    }

    public void exit() {
        
    }
}

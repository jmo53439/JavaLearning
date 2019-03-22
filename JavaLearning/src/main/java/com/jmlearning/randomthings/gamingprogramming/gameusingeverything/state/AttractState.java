package com.jmlearning.randomthings.gamingprogramming.gameusingeverything.state;

import com.jmlearning.randomthings.gamingprogramming.gameusingeverything.admin.Acme;
import com.jmlearning.randomthings.gamingprogramming.gameusingeverything.admin.HighScoreManager;
import com.jmlearning.randomthings.gamingprogramming.gameusingeverything.object.Asteroid;
import com.jmlearning.randomthings.gamingprogramming.gameusingeverything.object.AsteroidFactory;
import com.jmlearning.randomthings.gamingprogramming.utils.KeyboardInput;
import com.jmlearning.randomthings.gamingprogramming.utils.Matrix3x3f;
import com.jmlearning.randomthings.gamingprogramming.utils.Sprite;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Vector;

public abstract class AttractState extends State {

    private List<Asteroid> asteroids;
    private float time;
    private Sprite background;
    private AsteroidFactory factory;
    protected Acme acme;
    protected KeyboardInput keys;
    protected HighScoreManager highScoreManager;

    public AttractState() {

    }

    public AttractState(List<Asteroid> asteroids) {

        this.asteroids = asteroids;
    }

    @Override
    public void enter() {

        highScoreManager = (HighScoreManager) controller.getAttribute("score");
        keys = (KeyboardInput) controller.getAttribute("keys");
        background = (Sprite) controller.getAttribute("background");
        factory = (AsteroidFactory) controller.getAttribute("factory");
        acme = (Acme) controller.getAttribute("ACME");

        if(asteroids == null) {

            asteroids = new Vector <>();
            asteroids.add(factory.getLargeAsteroid());
            asteroids.add(factory.getMediumAsteroid());
            asteroids.add(factory.getSmallAsteroid());
        }

        time = 0.0f;
    }

    @Override
    public void updateObjects(float delta) {

        time += delta;

        if(shouldChangeState()) {

            AttractState state = getState();
            state.setAsteroids(asteroids);
            getController().setState(state);

            return;
        }

        for(Asteroid a : asteroids) {

            a.update(delta);
        }
    }

    protected boolean shouldChangeState() {

        return time > getWaitTime();
    }

    protected float getWaitTime() {

        return 5.0f;
    }

    public List<Asteroid> getAsteroids() {

        return asteroids;
    }

    private void setAsteroids(List<Asteroid> asteroids) {

        this.asteroids = asteroids;
    }

    protected abstract AttractState getState();

    @Override
    public void processInput(float delta) {

        if(keys.keyDownOnce(KeyEvent.VK_ESCAPE))
            game.shutDownGame();

        if(keys.keyDownOnce(KeyEvent.VK_SPACE)) {

            GameState state = new GameState();
            state.setLevel(1);
            state.setLevel(2);
            getController().setState(new LevelStarting(state));
        }
    }

    @Override
    public void render(Graphics2D g, Matrix3x3f view) {

        background.render(g, view);

        for(Asteroid a : asteroids) {

            a.draw(g, view);
        }
    }
}

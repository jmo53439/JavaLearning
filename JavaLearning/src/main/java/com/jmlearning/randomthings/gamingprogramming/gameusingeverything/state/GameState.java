package com.jmlearning.randomthings.gamingprogramming.gameusingeverything.state;

import com.jmlearning.randomthings.gamingprogramming.gameusingeverything.object.Asteroid.Size;

public class GameState {

    private int level;
    private int lives;
    private int score;

    public int getLevel() {

        return level;
    }

    public void setLevel(int level) {

        this.level = level;
    }

    public int getLives() {

        return lives;
    }

    public void setLives(int lives) {

        this.lives = lives;
    }

    public int getScore() {

        return score;
    }

    public void updateScore(Size size) {

        switch(size) {

            case Small:
                score += 500;
                break;

            case Medium:
                score += 300;
                break;

            case Large:
                score += 100;
                break;
        }
    }
}
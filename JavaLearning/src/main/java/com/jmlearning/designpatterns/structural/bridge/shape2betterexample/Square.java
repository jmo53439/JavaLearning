package com.jmlearning.designpatterns.structural.bridge.shape2betterexample;

public class Square extends Shape {

    public Square(Color color) {

        super(color);
    }

    @Override
    public void applyColor() {

        color.applyColor();
    }
}

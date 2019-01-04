package com.jmlearning.designpatterns.structural.bridge.shape2betterexample;

public class Circle extends Shape {

    public Circle(Color color) {
        
        super(color);
    }

    @Override
    public void applyColor() {

        color.applyColor();
    }
}

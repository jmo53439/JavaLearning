package com.jmlearning.designpatterns.structural.bridge.shape1badexample;

public class RedCircle extends Circle {

    @Override
    public void applyColor() {

        System.out.println("Applying red color");
    }
}

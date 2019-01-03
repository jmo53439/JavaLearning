package com.jmlearning.designpatterns.behavioral.visitor.visitorbad;

public class Wheel implements AtvPart {

    @Override
    public double calculateShipping() {
        return 12;
    }
}

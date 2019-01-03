package com.jmlearning.designpatterns.behavioral.visitor.visitorbad;

public class Fender implements AtvPart {

    @Override
    public double calculateShipping() {
        return 3;
    }
}

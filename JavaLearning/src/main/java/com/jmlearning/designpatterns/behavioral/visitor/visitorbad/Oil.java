package com.jmlearning.designpatterns.behavioral.visitor.visitorbad;

public class Oil implements AtvPart {

    @Override
    public double calculateShipping() {
        return 9;
    }
}

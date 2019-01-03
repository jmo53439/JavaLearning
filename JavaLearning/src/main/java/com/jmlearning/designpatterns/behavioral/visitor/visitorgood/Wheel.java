package com.jmlearning.designpatterns.behavioral.visitor.visitorgood;

public class Wheel implements AtvPart {

    @Override
    public void accept(AtvPartVisitor visitor) {

        visitor.visit(this);
    }
}

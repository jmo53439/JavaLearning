package com.jmlearning.designpatterns.behavioral.visitor.visitorgood;

public class Oil implements AtvPart {

    @Override
    public void accept(AtvPartVisitor visitor) {

        visitor.visit(this);
    }
}

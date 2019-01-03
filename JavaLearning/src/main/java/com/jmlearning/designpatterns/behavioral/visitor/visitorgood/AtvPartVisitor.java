package com.jmlearning.designpatterns.behavioral.visitor.visitorgood;

public interface AtvPartVisitor {

    void visit(Fender fender);
    void visit(Oil oil);
    void visit(Wheel wheel);

    void visit(PartsOrder partsOrder);
}

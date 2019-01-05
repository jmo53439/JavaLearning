package com.jmlearning.designpatterns.structural.flyweight;

public class FlyweightInventoryDemo {

    public static void main(String[] args) {

        InventorySystem ins = new InventorySystem();

        ins.takeOrder("Roomba", 221);
        ins.takeOrder("Bose Headphones", 361);
        ins.takeOrder("Samsung TV", 432);
        ins.takeOrder("Samsung TV", 323);
        ins.takeOrder("Roomba", 563);
        ins.takeOrder("Bose Headphones", 321);
        ins.takeOrder("Roomba", 234);
        ins.takeOrder("Samsung TV", 54);
        ins.takeOrder("Roomba", 34);
        ins.takeOrder("Bose Headphones", 365);
        ins.takeOrder("Samsung TV", 332);
        ins.takeOrder("Roomba", 456);

        ins.process();

        System.out.println(ins.report());
    }
}

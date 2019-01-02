package com.jmlearning.designpatterns.behavioral.state;

public class FanHighState extends State {

    private Fan fan;

    public FanHighState(Fan fan) {

        this.fan = fan;
    }

    @Override
    public void handleRequest() {

        System.out.println("Turning fan off...");
        fan.setState(fan.getFanOffState());
    }

    public String toString() {

        return "Fan is high";
    }
}

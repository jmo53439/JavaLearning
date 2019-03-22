package com.jmlearning.randomthings.gamingprogramming.threads;

public class WaitNotifyExample implements FakeHardwareListener {

    public WaitNotifyExample() {

    }

    public void runTest() throws Exception {

        FakeHardware hardware = new FakeHardware("name");
        hardware.addListener(this);

        synchronized(this) {

            hardware.turnOn();

            while(!hardware.isOn()) {

                wait();
            }
        }

        System.out.println("Hardware is On");

        synchronized(this) {

            hardware.start(1000, 4);

            while(!hardware.isRunning()) {

                wait();
            }
        }

        System.out.println("Hardware is Running");

        synchronized(this) {

            while(hardware.isRunning()) {

                wait();
            }
        }

        System.out.println("Hardware has Stopped");

        synchronized(this) {

            hardware.turnOff();

            while(hardware.isOn()) {

                wait();
            }
        }
    }

    @Override
    public synchronized void event(
            FakeHardware source, FakeHardware.FakeHardwareEvent event) {

        System.out.println("Got Event: " + event);
        notifyAll();
    }

    public static void main(String[] args) throws Exception {

        new WaitNotifyExample().runTest();
    }
}

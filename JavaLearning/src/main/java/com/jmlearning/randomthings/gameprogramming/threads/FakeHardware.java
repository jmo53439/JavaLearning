package com.jmlearning.randomthings.gamingprogramming.threads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class FakeHardware {

    private static final int SLEEP_MIN = 100;
    private static final int SLEEP_MAX = 500;

    public enum FakeHardwareEvent {

        START, STOP, ON, OFF;
    }

    private volatile boolean on = false;
    private volatile boolean running = false;
    private String name;
    private List<FakeHardwareListener> listeners = Collections.synchronizedList(
            new ArrayList <FakeHardwareListener>());

    public FakeHardware(String name) {

        this.name = name;
    }

    public boolean addListener(FakeHardwareListener listener) {

        return listeners.add(listener);
    }

    public boolean isOn() {

        return on;
    }

    public boolean isRunning() {

        return running;
    }

    private void sleep() {

        int random = new Random().nextInt(SLEEP_MAX - SLEEP_MIN + 1);
        sleep(random + SLEEP_MIN);
    }

    private void sleep(int ms) {

        try {

            Thread.sleep(ms);
        }
        catch(InterruptedException ex) {

        }
    }

    public void turnOn() {

        new Thread(new Runnable() {

            @Override
            public void run() {

                sleep();
                setOn();
            }
        }).start();
    }

    private synchronized void setOn() {

        if(!on) {

            on = true;
            fireEvent(FakeHardwareEvent.ON);
        }
    }

    public void turnOff() {

        new Thread(new Runnable() {

            @Override
            public void run() {

                sleep();
                setOff();
            }
        }).start();
    }

    private synchronized void setOff() {

        if(on) {

            setStop();
            on = false;
            fireEvent(FakeHardwareEvent.OFF);
        }
    }

    public void start(final int timeMS, final int slices) {

        new Thread(new Runnable() {

            @Override
            public void run() {

                sleep();
                setStart(timeMS, slices);
            }
        }).start();
    }

    public void stop() {

        new Thread(new Runnable() {

            @Override
            public void run() {

                sleep();
                setStop();
            }
        }).start();
    }

    private void setStart(int timeMS, int slices) {

        synchronized(this) {

            if(on && !running) {

                running = true;
                fireEvent(FakeHardwareEvent.START);
            }
        }

        if(running) {

            runTask(timeMS, slices);
            running = false;
            fireEvent(FakeHardwareEvent.STOP);
        }
    }

    private synchronized void setStop() {

        if(running) {

            running = false;
        }
    }

    private void runTask(int timeMS, int slices) {

        int sleep = timeMS / slices;

        for(int i = 0; i < slices; ++i) {

            if(!running)
                return;

            System.out.println(name + "[" + (i + 1) + "/" + slices + "]");
            sleep(sleep);
        }
    }

    private void fireEvent(FakeHardwareEvent event) {

        synchronized(listeners) {

            for(FakeHardwareListener listener : listeners) {

                listener.event(this, event);
            }
        }
    }
}

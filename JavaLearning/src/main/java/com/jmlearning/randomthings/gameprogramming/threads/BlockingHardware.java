package com.jmlearning.randomthings.gamingprogramming.threads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingHardware {

    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private volatile boolean on = false;
    private volatile boolean started = false;
    private FakeHardware hardware;
    private List<BlockingHardwareListener> listeners = Collections.synchronizedList(
            new ArrayList <BlockingHardwareListener>());

    public BlockingHardware(String name) {

        hardware = new FakeHardware(name);
        hardware.addListener(new FakeHardwareListener() {

            @Override
            public void event(
                    FakeHardware source, FakeHardware.FakeHardwareEvent event) {

            }
        });
    }

    public boolean addListener(BlockingHardwareListener listener) {

        return listeners.add(listener);
    }

    public void start(int ms, int slices) {

        lock.lock();

        try {

            hardware.start(ms, slices);

            while(!started) {

                condition.await();
            }

            System.out.println("It Started");
        }
        catch(InterruptedException e) {

            e.printStackTrace();
        }
        finally {

            lock.unlock();
        }
    }

    public void stop() {

        lock.lock();

        try {

            hardware.stop();

            while(started) {

                condition.await();
            }
        }
        catch(InterruptedException ex) {

            ex.printStackTrace();
        }
        finally {

            lock.unlock();
        }
    }

    public void turnOn() {

        lock.lock();

        try {

            hardware.turnOn();

            while(!on) {

                condition.await();
            }

            System.out.println("Turned On");
        }
        catch(InterruptedException ex) {

            ex.printStackTrace();
        }
        finally {

            lock.unlock();
        }
    }

    public void turnOff() {

        lock.lock();

        try {

            hardware.turnOff();

            while(on) {

                condition.await();
            }

            System.out.println("Turned Off");
        }
        catch(InterruptedException ex) {

            ex.printStackTrace();
        }
        finally {

            lock.unlock();
        }
    }

    protected void handleHardwareEvent(
            FakeHardware source, FakeHardware.FakeHardwareEvent event) {

        boolean wasStarted = started;
        lock.lock();

        try {

            if(event == FakeHardware.FakeHardwareEvent.ON) {

                on = true;
            }
            else if(event == FakeHardware.FakeHardwareEvent.OFF) {

                on = false;
            }
            else if(event == FakeHardware.FakeHardwareEvent.START) {

                started = true;
            }
            else if(event == FakeHardware.FakeHardwareEvent.STOP) {

                started = false;
            }

            condition.signalAll();
        }
        finally {

            lock.unlock();
        }

        if(wasStarted && !started) {

            fireTaskFinished();
        }
    }

    private void fireTaskFinished() {

        synchronized(listeners) {

            for(BlockingHardwareListener listener : listeners) {

                listener.taskFinished();
            }
        }
    }
}

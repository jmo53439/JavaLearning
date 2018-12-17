package com.jmlearning.randomthings;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class CircularBuffer {

    private char[] buffer;
    public final int bufferSize;
    private int writeIndex = 0;
    private int readIndex = 0;
    private AtomicInteger readableData = new AtomicInteger(0);

    public CircularBuffer(int bufferSize) {

        if(!isPowerOfTwo(bufferSize)) {

            throw new IllegalArgumentException();
        }

        this.bufferSize = bufferSize;
        buffer = new char[bufferSize];
    }

    private boolean isPowerOfTwo(int i) {

        return (i & (i -1)) == 0;
    }

    private int getTrueIndex(int i) {

        return i % bufferSize;
    }

    public Character readOutChar() {

        Character result = null;

        if(readableData.get() > 0) {

            result = new Character(buffer[getTrueIndex(readIndex)]);
            readableData.decrementAndGet();
            readIndex++;
        }

        return result;
    }

    public boolean writeToCharBuffer(char c) {

        boolean result = false;

        if(readableData.get() < bufferSize) {

            buffer[getTrueIndex(writeIndex)] = c;
            readableData.incrementAndGet();
            writeIndex++;
            result = true;
        }

        return result;
    }

    private static class testWriteWorker implements Runnable {

        String alphabet = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        CircularBuffer buffer;

        public testWriteWorker(CircularBuffer cb) {

            this.buffer = cb;
        }

        private char getRandomChar() {

            return alphabet.charAt(random.nextInt(alphabet.length()));
        }

        public void run() {

            while(!Thread.interrupted()) {

                if(!buffer.writeToCharBuffer(getRandomChar())) {

                    Thread.yield();

                    try {

                        Thread.sleep(10);
                    }
                    catch(InterruptedException e) {

                        return;
                    }
                }
            }
        }
    }

    private static class testReadWorker implements Runnable {

        CircularBuffer buffer;

        public testReadWorker(CircularBuffer cb) {

            this.buffer = cb;
        }

        public void run() {

            System.out.println("Printing Buffer:");

            while(!Thread.interrupted()) {

                Character c = buffer.readOutChar();

                if(c != null) {

                    System.out.print(c.charValue());
                }
                else {

                    Thread.yield();

                    try {

                        Thread.sleep(10);
                    }
                    catch(InterruptedException e) {

                        System.out.println();
                        return;
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        int bufferSize = 1024;
        CircularBuffer cb = new CircularBuffer(bufferSize);

        Thread writeThread = new Thread(new testWriteWorker(cb));
        Thread readThread = new Thread(new testReadWorker(cb));
        readThread.start();
        writeThread.start();

        Thread.sleep(10000);

        writeThread.interrupt();
        readThread.interrupt();
    }
}
package com.jmlearning.datastructures;

public class BasicQueue<X> {

    private X[] data;
    private int front;
    private int end;

    public BasicQueue() {

        this(1000);
    }

    public BasicQueue(int size) {

        this.front = -1;
        this.end = -1;
        data = (X[]) new Object[size];
    }

    public int size() {

        // if the queue is empty return 0
        if(front == -1 && end == -1) {

            return 0;
        }
        else {

            return end - front + 1;
        }
    }

    public void enQueue(X item) {

        // see if the queue is full
        if((end + 1) % data.length == front) {

            throw new IllegalStateException("The Queue is full");
        }
        else if(size() == 0) {

            front++;
            end++;
            data[end] = item;
        }
    }

    public X deQueue() {

        X item = null;

        // if the queue is empty we cant dequeue anything
        if(size() == 0) {

            throw new IllegalStateException("Cant dequeue because the queue is empty");
        }
        else if(front == end) {

            item = data[front];
            data[front] = null; // for garbage collection/free up memory
            front = -1;
            end = -1;
        }
        // otherwise, grab the front of the queue, return, and adjust front pointer
        else {

            item = data[front];
            data[front] = null; // for garbage collection/free up memory
            front++;
        }

        return item;
    }

    public boolean contains(X item) {

        boolean found = false;

        if(size() == 0) {

            return found;
        }

        for(int i = front; i < end; i++) {

            if(data[i].equals(item)) {

                found = true;
                break;
            }
        }

        return found;
    }

    public X access(int position) {

        if(size() == 0 || position > size()) {

            throw new IllegalArgumentException("No items in the queue" +
                    " or the position is greater than what the queue holds");
        }

        int trueIndex = 0;

        for(int i = front; i < end; i++) {

            if(trueIndex == position) {

                return data[i];
            }

            trueIndex++;
        }

        throw new IllegalArgumentException("Could not get queue item at position: " + position);
    }
}

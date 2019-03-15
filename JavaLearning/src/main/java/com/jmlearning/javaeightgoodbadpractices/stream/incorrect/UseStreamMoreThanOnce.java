package com.jmlearning.javaeightgoodbadpractices.stream.incorrect;

import com.jmlearning.javaeightgoodbadpractices.Annotations.Bad;

import java.util.Arrays;
import java.util.stream.IntStream;

public class UseStreamMoreThanOnce {
    
    @Bad
    public void streamIsClosedAfterTerminalOperation() {
     
        // IllegalStateException: Stream has already been operated upon or closed 
        int[] array = new int[] {1, 2};
        IntStream stream = Arrays.stream(array);
        stream.forEach(System.out::println);
        array[0] = 2;
        stream.forEach(System.out::println);
    }
}

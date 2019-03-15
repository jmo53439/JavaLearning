package com.jmlearning.javaeightgoodbadpractices.stream.incorrect;

import com.jmlearning.javaeightgoodbadpractices.Annotations.Bad;
import com.jmlearning.javaeightgoodbadpractices.Annotations.Good;

import java.util.stream.IntStream;

public class InfiniteStreams {
    
    @Bad
    public void infinite() {
    
        IntStream.iterate(0, i -> i + 1)
                .forEach(System.out::println);
    }
    
    @Good
    public void betterWay() {
        
        IntStream.iterate(0, i -> i + 1)
                .limit(10)
                .forEach(System.out::println);
    }
    
    @Bad
    public void stillInfinite() {
        
        IntStream.iterate(0, i -> (i + 1) % 2)
                .distinct()
                .limit(10)
                .forEach(System.out::println);
    }
    
    @Good
    public void anotherBetterWay() {
        
        IntStream.iterate(0, i -> (i + 1) % 2)
                .limit(10)
                .distinct()
                .forEach(System.out::println);
    }
}

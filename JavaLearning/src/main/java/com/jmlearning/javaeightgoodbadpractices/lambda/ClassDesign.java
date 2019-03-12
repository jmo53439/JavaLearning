package com.jmlearning.javaeightgoodbadpractices.lambda;

import com.jmlearning.javaeightgoodbadpractices.Annotations;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ClassDesign {
    
    @Annotations.Bad
    static class AmbiguousOverloadedMethods {
        
        interface AmbiguousService<T> {
            
            <R> R process(Function<T, R> fn);
            T process(UnaryOperator<T> fn);
        }
        
        public void usage(AmbiguousService<String> service) {
            
            service.process(String :: toUpperCase);
        }
    }
    
    @Annotations.Good
    static class SeparateSpecializedMethods {
        
        interface ClearService<T> {
            
            <R> R convert(Function<T, R> rn);
            T process(UnaryOperator<T> fn);
        }
        
        public void usage(ClearService<String> service) {
            
            service.convert(String :: toUpperCase);
        }
    }
}

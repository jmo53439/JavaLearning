package com.jmlearning.javaeightgoodbadpractices.lambda;

import com.jmlearning.javaeightgoodbadpractices.Annotations;

import java.util.Optional;

public class LambdasAreNotAlwaysTheBestOption {
    
    @Annotations.Ugly
    class LambdaUsageNotNeeded {
        
        public void processAndPrint(String name) {
    
            Optional.ofNullable(name).map(
                    s -> s.toUpperCase()).map(
                            s -> doProcess(s)).ifPresent(
                                    s -> System.out.print(s));
        }
    
        private String doProcess(String name) {
        
            return "Mr. " + name;
        }
    }
    
    @Annotations.Good
    class MethodReferenceUsage {
        
        public void processAndPrint(String name) {
            
            Optional.ofNullable(name).map(
                    String :: toUpperCase).map(
                            this :: doProcess).ifPresent(
                                    System.out :: println);
        }
        
        private String doProcess(String name) {
            
            return "Mr. " + name;
        }
    }
}

package com.jmlearning.javaeightgoodbadpractices.optional;

import com.jmlearning.javaeightgoodbadpractices.Annotations.Good;
import com.jmlearning.javaeightgoodbadpractices.Annotations.Ugly;

import java.util.Optional;
import java.util.stream.Stream;

public class IfStatementsIsNotAlwaysABadThing {
    
    @Ugly
    class CombiningSomeOptionals {
        
        public Optional<Integer> sum(Optional<Integer> first, Optional<Integer> second) {
            
            return Stream.of(first, second)
                    .filter(Optional :: isPresent)
                    .map(Optional :: get)
                    .reduce(Integer :: sum);
        }
    }
    
    @Ugly
    class CleverMapGame {
        
        public Optional<Integer> sum(Optional<Integer> first, Optional<Integer> second) {
            
            return first.map(b -> second.map(a -> b + a).orElse(b)).map(Optional :: of).orElse(second);
        }
    }
    
    @Good
    class ClearAndConcise {
        
        public Optional<Integer> sum(Optional<Integer> first, Optional<Integer> second) {
            
            if(!first.isPresent() && !second.isPresent()) {
                
                return Optional.empty();
            }
            
            return Optional.of(first.orElse(0) + second.orElse(0));
        }
    }
}

package com.jmlearning.javaeightgoodbadpractices.stream;

import com.jmlearning.javaeightgoodbadpractices.Annotations;
import com.jmlearning.javaeightgoodbadpractices.Annotations.Good;
import com.jmlearning.javaeightgoodbadpractices.Annotations.Ugly;

import java.util.List;

public class UntypedStreamsCouldBeConverted {
    
    @Ugly
    class ProcessOnlyValuesOfSpecialType {
        
        public int countDoubleNaNa(List numbers) {
            
            int count = 0;
            
            for(Object e : numbers) {
                
                if(e instanceof Double) {
                    
                    Double d = (Double) e;
                    
                    if(d.isNaN())
                        count++;
                }
            }
            
            return count;
        }
    }
    
    @Good
    class TypeOfStreamCouldBeChanged {
        
        public int countDoubleNaNs(List numbers) {
            
            return (int) numbers.stream()
                    .filter(Double.class::isInstance)
                    .mapToDouble(Double.class::cast)
                    .filter(Double::isNaN)
                    .count();
        }
    }
}

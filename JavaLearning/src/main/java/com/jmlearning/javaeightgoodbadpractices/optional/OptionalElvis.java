package com.jmlearning.javaeightgoodbadpractices.optional;

import com.jmlearning.javaeightgoodbadpractices.Annotations.Good;
import com.jmlearning.javaeightgoodbadpractices.Annotations.Ugly;
import com.jmlearning.javaeightgoodbadpractices.User;


import static java.util.Optional.ofNullable;

public class OptionalElvis {
    
    @Ugly
    class BeforeJava8 {
    
        public String getUserName(User user) {
            
            return (user != null && user.getName() != null) ? user.getName() : "Default";
        }
    }
    
    @Ugly
    class UsingOptionalIsPresent {
    
        public String getUserName(User user) {
            
            if(ofNullable(user).isPresent()) {
                
                if(ofNullable(user.getName()).isPresent()) {
                    
                    return user.getName();
                }
            }
            
            return "Default";
        }
    }
    
    @Good
    class UsingOrElse {
        
        String getUserName(User user) {
            
            return ofNullable(user).map(User :: getName).orElse("Default");
        }
    }
}

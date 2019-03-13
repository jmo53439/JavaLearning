package com.jmlearning.javaeightgoodbadpractices.stream;

import com.jmlearning.javaeightgoodbadpractices.Annotations.Good;
import com.jmlearning.javaeightgoodbadpractices.Annotations.Ugly;
import com.jmlearning.javaeightgoodbadpractices.User;

import java.util.Collection;
import java.util.Objects;

import static java.util.Optional.ofNullable;

public class OldCodeWithNewConstructs {
    
    @Ugly
    class NoMoreThanOldLoopWithIf {
        
        public void registerUsers(Collection<User> users) {
    
            users.stream().forEach(user -> ofNullable(user).ifPresent(u -> {
                
                // register user
            }));
        }
    }
    
    @Good
    class NewStreamStyleWithMethodReference {
        
        public void registerUsers(Collection<User> users) {
    
            users.stream().filter(Objects::nonNull).forEach(this::registerUser);
        }
    
        private void registerUser(User user) {
        
            // register user
        }
    }
}

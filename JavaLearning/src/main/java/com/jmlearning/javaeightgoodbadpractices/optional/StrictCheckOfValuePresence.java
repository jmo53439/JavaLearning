package com.jmlearning.javaeightgoodbadpractices.optional;

import com.jmlearning.javaeightgoodbadpractices.Annotations.Good;
import com.jmlearning.javaeightgoodbadpractices.Annotations.Ugly;
import com.jmlearning.javaeightgoodbadpractices.User;

import java.util.Optional;

public class StrictCheckOfValuePresence {
    
    @Ugly
    class ManualCheckForPresenceToThrowException {
        
        public String getUserName(Long userId) {
    
            Optional<User> user = findById(userId);
            
            if(user.isPresent())
                return user.get().getName();
            
            throw new IllegalStateException("User not found");
        }
        
        public void deleteUser(Long userId) {
            
            Optional<User> user = findById(userId);
            
            if(user.isPresent())
                delete(user.get());
        }
    
        private void delete(User user) {
        
            // delete from db
        }
    }
    
    @Good
    class OrElseThrowUsage {
        
        public String getUserName(Long userId) {
            
            return findById(userId).orElseThrow(
                    () -> new IllegalStateException("User not found")).getName();
        }
        
        public void deleteUser(Long userId) {
            
            findById(userId).ifPresent(this :: delete);
        }
        
        private void delete(User user) {
            
            // delete from db
        }
    }
    
    private Optional<User> findById(Long userId) {
        
        // query db
        return Optional.of(new User(5L, "Meow", 34));
    }
}

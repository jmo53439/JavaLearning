package com.jmlearning.javaeightgoodbadpractices.stream;

import com.jmlearning.javaeightgoodbadpractices.Annotations.Good;
import com.jmlearning.javaeightgoodbadpractices.Annotations.Ugly;
import com.jmlearning.javaeightgoodbadpractices.User;

import java.util.List;

public class SkipAndLimitOnListsIsWaste {
 
    @Ugly
    class SkipSomeElementsAndThenTakeSomeForProcessing {
        
        public void registerUsers(List<User> users) {
    
            users.stream()
                    .skip(5)
                    .limit(10)
                    .forEach(SkipAndLimitOnListsIsWaste.this::registerUser);
        }
    }
    
    @Good
    class SublistDoesNotWasteProcessingTime {
        
        public void registerUsers(List<User> users) {
    
            users.subList(5, 15)
                    .forEach(SkipAndLimitOnListsIsWaste.this::registerUser);
        }
    }
    
    private void registerUser(User user) {
        
        // register user
    }
}

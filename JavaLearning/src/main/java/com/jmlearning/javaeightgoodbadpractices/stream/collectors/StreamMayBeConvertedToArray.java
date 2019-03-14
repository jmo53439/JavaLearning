package com.jmlearning.javaeightgoodbadpractices.stream.collectors;

import com.jmlearning.javaeightgoodbadpractices.Annotations.Good;
import com.jmlearning.javaeightgoodbadpractices.Annotations.Ugly;
import com.jmlearning.javaeightgoodbadpractices.User;

import java.util.List;
import java.util.stream.Collectors;

public class StreamMayBeConvertedToArray {
    
    @Ugly
    class ConvertToArrayViaList {
        
        public String[] getUserNames(List<User> users) {
            
            List<String> names = users.stream()
                    .map(User::getName)
                    .collect(Collectors.toList());
            
            return names.toArray(new String[names.size()]);
        }
    }
    
    @Good
    class ConvertToArrayDirectly {
        
        public String[] getUserNames(List<User> users) {
            
            return users.stream()
                    .map(User::getName)
                    .toArray(String[]::new);
        }
    }
}

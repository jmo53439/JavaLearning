package com.jmlearning.javaeightgoodbadpractices.lambda.collections;

import com.jmlearning.javaeightgoodbadpractices.Annotations;
import com.jmlearning.javaeightgoodbadpractices.User;

import java.util.HashMap;
import java.util.Map;
//import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class MapIterating {
    
    @Annotations.Ugly
    class UsingOldEntrySet {
        
        public Map<String, String> getUserNames(Map<String, User> users) {
            
            Map<String, String> userNames = new HashMap <>();
            users.entrySet().forEach(
                    user -> userNames.put(
                            user.getKey(), user.getValue().getName()));
            
            return userNames;
        }
    }
    
    @Annotations.Good
    class UsingMapForEach {
        
        public Map<String, String> getUserNames(Map<String, User> users) {
            
            Map<String, String> userNames = new HashMap <>();
            users.forEach((key, value) -> userNames.put(key, value.getName()));
            
            return userNames;
        }
    }
    
    @Annotations.Good
    class UsingMapTransform {
        
        public Map<String, String> getUserNames(Map<String, User> users) {
            
            return users.entrySet().stream().collect(
                    toMap(Map.Entry :: getKey, entry 
                            -> entry.getValue().getName()));
        }
    }
}

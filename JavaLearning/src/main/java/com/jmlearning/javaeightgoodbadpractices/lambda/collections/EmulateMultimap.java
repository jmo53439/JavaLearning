package com.jmlearning.javaeightgoodbadpractices.lambda.collections;

import com.jmlearning.javaeightgoodbadpractices.Annotations;
import com.jmlearning.javaeightgoodbadpractices.User;

import java.util.*;

public class EmulateMultimap {
   
    private final Map<String, Set<User>> usersByRole = new HashMap <>();
    
    @Annotations.Ugly
    class ManuallyInsertSetOnFirstValueForKey {
        
        public void addUser(User user) {
            
            user.getRoles().forEach(r -> {
                Set<User> usersInRole = usersByRole.get(r.getName());
                
                if(usersInRole == null) {
                    
                    usersInRole = new HashSet <>();
                    usersByRole.put(r.getName(), usersInRole);
                }
                
                usersInRole.add(user);
            });
        }
        
        public Set<User> getUsersInRole(String role) {
            
            Set<User> users = usersByRole.get(role);
            
            return users == null ? Collections.emptySet() : users;
        }
    }
    
    @Annotations.Good
    class ComputeEmptySetIfKeyIsAbsent {
        
        public void addUser(User user) {
    
            user.getRoles().forEach(
                    r -> usersByRole.computeIfAbsent(
                            r.getName(), k -> new HashSet <>()).add(user));
        }
        
        public Set<User> getUsersInRole(String role) {
            
            return usersByRole.getOrDefault(role, Collections.emptySet());
        }
    }
}

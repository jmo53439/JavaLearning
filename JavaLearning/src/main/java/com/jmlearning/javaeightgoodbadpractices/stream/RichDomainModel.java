package com.jmlearning.javaeightgoodbadpractices.stream;

import com.jmlearning.javaeightgoodbadpractices.Annotations.Good;
import com.jmlearning.javaeightgoodbadpractices.Annotations.Ugly;
import com.jmlearning.javaeightgoodbadpractices.Role;
import com.jmlearning.javaeightgoodbadpractices.User;

import java.util.ArrayList;
import java.util.List;

public class RichDomainModel {
    
    @Ugly
    class PoorDomainModelCausesComplexDataAccessCode {
        
        private final List<User> users = new ArrayList <>();
        
        public User findUserInRole(String roleName) {
            
            for(User user : users) {
    
                for(Role role : user.getRoles()) {
        
                    if(roleName.equals(role.getName()))
                        return user;
                }
            }
            
            return null;
        }
    }
    
    @Ugly
    class StreamVersionIsntMuchBetter {
        
        private final List<User> users = new ArrayList <>();
        
        public User findUserInRole(String roleName) {
            
            return users.stream()
                    .filter(user -> user.getRoles().stream()
                            .map(Role::getName)
                            .anyMatch(roleName::equals))
                    .findAny()
                    .orElse(null);
        }
    }
    
    @Good
    class RichDomainModelCouldSimplifyAccessCode {
        
        private final List<BetterUser> users = new ArrayList<>();
        
        public User findUserInRole(String roleName) {
            
            return users.stream()
                    .filter(user -> user.hasRole(roleName))
                    .findAny()
                    .orElse(null);
        }
        
        class BetterUser extends User {
            
            BetterUser(long id, String name, int age) {
                
                super(id, name, age);
            }
            
            boolean hasRole(String roleName) {
                
                return getRoles().stream()
                        .map(Role::getName)
                        .anyMatch(roleName::equals);
            }
        }
    }
}

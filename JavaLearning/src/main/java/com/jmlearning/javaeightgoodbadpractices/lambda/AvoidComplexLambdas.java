package com.jmlearning.javaeightgoodbadpractices.lambda;

import com.jmlearning.javaeightgoodbadpractices.Annotations;
import com.jmlearning.javaeightgoodbadpractices.Permission;
import com.jmlearning.javaeightgoodbadpractices.Role;
import com.jmlearning.javaeightgoodbadpractices.User;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AvoidComplexLambdas {

   private final Set<User> users = new HashSet <>();
    
    @Annotations.Ugly
    class UsingComplexLambdaInPlace {
        
        public Set<User> findEditors() {
            
            return users.stream().filter(
                    u -> u.getRoles().stream().anyMatch(
                            r -> r.getPermissions().contains(
                                    Permission.EDIT))).collect(Collectors.toSet());
        }
    }
    
    @Annotations.Good
    class ComplexityExtractedToMethodReference {
    
        public Set<User> checkPermission(Permission permission) {
            
            return users.stream().filter(hasPermission(Permission.EDIT))
                    .collect(Collectors.toSet());
        }
        
        private Predicate<User> hasPermission(Permission permission) {
            
            return user -> user.getRoles().stream().map(
                    Role :: getPermissions).anyMatch(
                            permissions -> permissions.contains(permission));
        }
        
        private boolean hasEditPermission(User user) {
            
            return hasPermission(Permission.EDIT).test(user);
        }
    }
}

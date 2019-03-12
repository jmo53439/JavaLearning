package com.jmlearning.javaeightgoodbadpractices.lambda.collections;

import com.jmlearning.javaeightgoodbadpractices.Annotations;
import com.jmlearning.javaeightgoodbadpractices.Permission;
import com.jmlearning.javaeightgoodbadpractices.User;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class RemoveElementWithIterator {
    
    private final Set<User> users = new HashSet <>();
    
    @Annotations.Ugly
    class ManuallyRemoveElementWithIteratorRemove {
        
        public void removeUsersWithPermission(Permission permission) {
    
            Iterator<User> iterator = users.iterator();
            
            while(iterator.hasNext()) {
                
                User user = iterator.next();
                
                if(user.getRoles().stream().anyMatch(
                        r -> r.getPermissions().contains(permission))) {
                    
                    iterator.remove();
                }
            }
        }
    }
    
    @Annotations.Good
    class RemoveWithPredicate {
        
        public void removeUsersWithPermission(Permission permission) {
            
            users.removeIf(
                    user -> user.getRoles().stream().anyMatch(
                            r -> r.getPermissions().contains(permission)));
        }
    }
}

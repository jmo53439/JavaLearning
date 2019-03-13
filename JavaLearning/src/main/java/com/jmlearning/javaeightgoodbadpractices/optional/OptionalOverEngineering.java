package com.jmlearning.javaeightgoodbadpractices.optional;

import com.jmlearning.javaeightgoodbadpractices.Annotations;
import com.jmlearning.javaeightgoodbadpractices.Annotations.Good;
import com.jmlearning.javaeightgoodbadpractices.Annotations.Ugly;
import com.jmlearning.javaeightgoodbadpractices.Role;

import java.util.Optional;

public class OptionalOverEngineering {
    
    @Ugly
    class NullProtectionOverEngineering {
    
        public Role copyRole(Role role) {
            
            Role copy = new Role();
            Optional.ofNullable(role.getName()).ifPresent(copy :: setName);
            copy.setPermissions(role.getPermissions());
            
            return copy;
        }
    }
    
    @Good
    class SimpleConditionalCopying {
        
        public Role copyRole(Role role) {
            
            Role copy = new Role();
            
            if(role.getName() != null) {
    
                copy.setName(role.getName());
            }
            copy.setPermissions(role.getPermissions());
            
            return copy;
        }
    }
}

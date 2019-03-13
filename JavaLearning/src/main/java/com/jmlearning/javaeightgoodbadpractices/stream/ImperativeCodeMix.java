package com.jmlearning.javaeightgoodbadpractices.stream;

import com.jmlearning.javaeightgoodbadpractices.Annotations.Good;
import com.jmlearning.javaeightgoodbadpractices.Annotations.Ugly;
import com.jmlearning.javaeightgoodbadpractices.Permission;
import com.jmlearning.javaeightgoodbadpractices.Role;
import com.jmlearning.javaeightgoodbadpractices.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ImperativeCodeMix {
    
    private static final String ADMIN_ROLE = "admin";
    
    private final List<User> users = new ArrayList <>();
    
    @Ugly
    class TooVerboseMixOfStreamOperationsAndImperativeCode {
        
        public boolean hasAdmin() {
            
            return users.stream().map(u -> {
                
                if(u == null) {
                    
                    throw new NullPointerException();
                }
                
                return u;
            })
            .flatMap(u -> u.getRoles().stream())
            .map(Role :: getName).anyMatch(name -> ADMIN_ROLE.equals(name));
        }
    }
    
    @Good
    class NiceAndCleanStreamOperationsChain {
    
        public boolean hasAdmin(Permission permission) {
        
            return users.stream()
                    .map(Objects :: requireNonNull)
                    .flatMap(u -> u.getRoles().stream())
                    .map(Role :: getName)
                    .anyMatch(ADMIN_ROLE :: equals);
        }
    }
}

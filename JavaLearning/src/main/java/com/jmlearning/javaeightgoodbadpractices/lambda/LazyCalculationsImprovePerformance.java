package com.jmlearning.javaeightgoodbadpractices.lambda;

import com.jmlearning.javaeightgoodbadpractices.Annotations;
import com.jmlearning.javaeightgoodbadpractices.User;

import java.util.Set;
import java.util.function.Supplier;

public class LazyCalculationsImprovePerformance {
    
    @Annotations.Ugly
    static class LoggingWithAdditionalCheckToAvoidCalculations {
        
        private static final Log LOG = null;
        
        public void sendWelcomeEmailToUsers(Set<User> users) {
            
            if(LOG.isDebugEnabled())
                LOG.debug("Emails have been sent for users: " + users);
        }
        
        interface Log {
            
            void debug(String message);
            boolean isDebugEnabled();
        }
    }
    
    @Annotations.Good
    static class PassLambdaToLazyCalculateValueForLogMessage {
        
        private static final Log LOG = null;
        
        public void sendWelcomeEmailToUsers(Set<User> users) {
            
            LOG.debug(() -> "Emails have been sent for users: " + users);
        }
        
        interface Log {
            
            void debug(String message);
            boolean isDebugEnabled();
            
            default void debug(Supplier<String> message) {
                
                if(isDebugEnabled())
                    debug(message.get());
            }
        }
    }
}

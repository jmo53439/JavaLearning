package com.jmlearning.javaeightgoodbadpractices.stream;

import com.jmlearning.javaeightgoodbadpractices.Annotations.Good;
import com.jmlearning.javaeightgoodbadpractices.Annotations.Ugly;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class DoNotNeglectDataStructures {
    
    @Ugly
    class UnnecessaryUseOfNestedStreamOperations {
        
        public List<Order> filterOrdersByStatuses(List<Order> orders, Set<Status> statuses) {
            
            return orders.stream()
                    .filter(order -> statuses.stream()
                            .anyMatch(order.getStatus() :: equals))
                    .collect(toList());
        }
    }
    
    @Good
    class UseOfDataStructure {
        
        public List<Order> filterOrdersByStatuses(List<Order> orders, Set<Status> statuses) {
            
            return orders.stream()
                    .filter(order -> statuses.contains(order.getStatus()))
                    .collect(toList());
        }
    }
    
    @Ugly
    class StateIsStoredInBadDataStructure {
        
        private final List<Order> orders = new ArrayList <>();
        
        public void placeOrder(Order order) {
    
            orders.add(order);
        }
    
        public List<Order> getOrdersInStatus(Status status) {
        
            return orders.stream()
                    .filter(order -> order.getStatus() == status)
                    .collect(toList());
        }
    }
    
    @Good
    class InternalDataStructureMayBeOptimizedForAccessMethods {
        
        private final Map<Status, List<Order>> orders = new EnumMap <>(Status.class);
        
        public void placeOrder(Order order) {
            
            orders.computeIfAbsent(
                    order.getStatus(), status -> new ArrayList <>()).add(order);
        }
        
        public List<Order> getOrdersInStatus(Status status) {
            
            return orders.get(status);
        }
    }
    
    class Order {
    
        private Status status = Status.ACTIVE;
        
        Status getStatus() {
            
            return status;
        }
        
        void setStatus(Status status) {
            
            this.status = status;
        }
    }
    
    enum Status {
        
        ACTIVE, SUSPENDED, CLOSED
    }
}

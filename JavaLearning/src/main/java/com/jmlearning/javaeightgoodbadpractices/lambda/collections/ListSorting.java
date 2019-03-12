package com.jmlearning.javaeightgoodbadpractices.lambda.collections;

import com.jmlearning.javaeightgoodbadpractices.Annotations;
import com.jmlearning.javaeightgoodbadpractices.User;

//import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparing;

public class ListSorting {
    
    @Annotations.Ugly
    class UsingCustomComparator {
        
        public void sortUsersById(List<User> users) {
    
            users.sort((x, y) -> Long.compare(x.getId(), y.getId()));
        }
    }
    
    @Annotations.Good
    class UsingExistingPredefinedComparator {
        
        public void sortUsersById(List<User> users) {
    
            users.sort(comparing(User :: getId));
        }
    }
}

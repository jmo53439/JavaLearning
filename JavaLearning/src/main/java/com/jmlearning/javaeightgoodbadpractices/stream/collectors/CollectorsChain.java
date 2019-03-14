package com.jmlearning.javaeightgoodbadpractices.stream.collectors;

import com.jmlearning.javaeightgoodbadpractices.Annotations;
import com.jmlearning.javaeightgoodbadpractices.Annotations.Good;
import com.jmlearning.javaeightgoodbadpractices.Annotations.Ugly;
import com.jmlearning.javaeightgoodbadpractices.User;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

public class CollectorsChain {
    
    @Ugly
    class GroupByAndTransformResultingMap {
    
        public Map<String, Integer> getMaxAgeByUserName(List<User> users) {
            
            return users.stream()
                    .collect(groupingBy(User::getName))
                    .entrySet().stream()
                    .collect(toMap(
                            Map.Entry::getKey, e -> e.getValue().stream()
                                    .map(User::getAge)
                                    .reduce(0, Integer::max)));
        }
    }
    
    @Ugly
    class GroupByWithMaxCollectorUnwrappingOptionalWithFinisher {
        
        public Map<String, Integer> getMaxAgeByUserName(List<User> users) {
            
            return users.stream()
                    .collect(groupingBy(User::getName,
                            collectingAndThen(maxBy(comparing(User::getAge)),
                                    user -> user.get().getAge())));
        }
    }
    
    @Good
    class CollectToMapWithMergeFunction {
    
        public Map<String, Integer> getMaxAgeByUserName(List<User> users) {
        
            return users.stream()
                    .collect(toMap(
                            User::getName,
                            User::getAge,
                            Integer::max));
        }
    }
    
    @Good
    class ApplyReduceCollectorAsDownstream {
        
        public Map<String, Integer> getMaxAgeByUserName(List<User> users) {
            
            return users.stream()
                    .collect(groupingBy(User::getName,
                            mapping(User::getAge,
                                    reducing(0, Integer::max))));
        }
    }
}

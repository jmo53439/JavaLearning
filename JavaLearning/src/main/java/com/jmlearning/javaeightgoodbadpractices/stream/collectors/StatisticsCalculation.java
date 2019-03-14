package com.jmlearning.javaeightgoodbadpractices.stream.collectors;

import com.jmlearning.javaeightgoodbadpractices.Annotations.Good;
import com.jmlearning.javaeightgoodbadpractices.Annotations.Ugly;
import com.jmlearning.javaeightgoodbadpractices.User;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.summarizingInt;

public class StatisticsCalculation {
    
    @Ugly
    class IterateThroughValuesSeveralTimes {
        
        public void printNameStats(List<User> users) {
            
            getNameLengthStream(users)
                    .max()
                    .ifPresent(max -> System.out.println("MAX: " + max));
            getNameLengthStream(users)
                    .min()
                    .ifPresent(min -> System.out.println("MIN: " + min));
        }
    
        private IntStream getNameLengthStream(List<User> users) {
        
            return users.stream()
                    .mapToInt(u -> u.getName().length());
        }
    }
    
    @Good
    class CalculateStatisticsInSingleRunWithCollector {
        
        public void registerUsers(List<User> users) {
    
            IntSummaryStatistics statistics = users.stream()
                    .collect(summarizingInt(u -> u.getName().length()));
            System.out.println("MAX: " + statistics.getMax());
            System.out.println("MIN: " + statistics.getMin());
        }
    }
}

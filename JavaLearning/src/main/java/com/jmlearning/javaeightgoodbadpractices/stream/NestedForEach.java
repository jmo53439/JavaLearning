package com.jmlearning.javaeightgoodbadpractices.stream;

import com.jmlearning.javaeightgoodbadpractices.Annotations.Good;
import com.jmlearning.javaeightgoodbadpractices.Annotations.Ugly;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class NestedForEach {

    @Ugly
    class NestedForEachWithExternalCollection {
        
        public Set<String> getPromotionRuleNames(List<BusinessTransaction> transactions) {
            
            Set<String> ruleNamesWithPromotion = new HashSet <>();
            transactions.forEach(transaction -> transaction.getRules().stream()
                    .filter(BusinessRule::isPromotion)
                    .forEach(rule -> ruleNamesWithPromotion.add(rule.getRuleName())));
            
            return ruleNamesWithPromotion;
        }
    }
    
    @Good
    class StreamOperationsChain {
        
        public Set<String> getPromotionRuleNames(List<BusinessTransaction> transactions) {
            
            return transactions.stream()
                    .flatMap(t -> t.getRules().stream())
                    .filter(BusinessRule::isPromotion)
                    .map(BusinessRule::getRuleName)
                    .collect(toSet());
        }
    }
    
    class BusinessTransaction {
        
        List<BusinessRule> getRules() {
            
            return new ArrayList<>();
        }
    }
    
    class BusinessRule {
        
        String getRuleName() {
            
            return "";
        }
        
        boolean isPromotion() {
            
            return false;
        }
    }
}

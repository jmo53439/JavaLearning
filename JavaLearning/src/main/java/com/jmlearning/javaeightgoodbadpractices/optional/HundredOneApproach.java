package com.jmlearning.javaeightgoodbadpractices.optional;

import com.jmlearning.javaeightgoodbadpractices.Annotations;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

public class HundredOneApproach {
    
    @Annotations.Ugly
    class OldImperativeStyle {
        
        public String getPersonCarInsuranceName(Person person) {
            
            String name = "Unknown";
            
            if(ofNullable(person).isPresent()) {
                
                if(person.getCar().isPresent()) {
                    
                    if(person.getCar().get().getInsurance().isPresent()) {
                        
                        name = person.getCar().get().getInsurance().get().getName();
                    }
                }
            }
            
            return name;
        }
    }
    
    @Annotations.Ugly
    class UsingIfPresentInSameImperativeWay {
    
        public String getPersonCarInsuranceName(Person person) {
            
            final StringBuilder builder = new StringBuilder();
            ofNullable(person).ifPresent(
                    p -> p.getCar().ifPresent(
                            c -> c.getInsurance().ifPresent(
                                    i -> builder.append(i.getName()))));
            
            return builder.toString();
        }
    }
    
    @Annotations.Bad
    class UsingMapWithUncheckedGet {
        
        public String getPersonCarInsuranceName(Person person) {
            
            return ofNullable(person)
                    .map(Person :: getCar)
                    .map(car -> car.get().getInsurance())
                    .map(insurance -> insurance.get().getName())
                    .orElse("Unknown");
        }
    }
    
    @Annotations.Ugly
    class UsingMapWithOrElseEmptyObjectToFixUncheckedGet {
        
        public String getPersonCarInsuranceName(Person person) {
            
            return ofNullable(person)
                    .map(Person :: getCar)
                    .map(car -> car.orElseGet(Car :: new).getInsurance())
                    .map(insurance -> insurance.orElseGet(Insurance :: new).getName())
                    .orElse("Unknown");
        }
    }
    
    @Annotations.Good
    class UsingFlatMap {
        
        public String getCarInsuranceNameFromPersonUsingFlatMap(Person person) {
            
            return ofNullable(person)
                    .flatMap(Person :: getCar)
                    .flatMap(Car :: getInsurance)
                    .map(Insurance :: getName)
                    .orElse("Unknown");
        }
    }
    
    class Person {
        
        Optional<Car> getCar() {
            
            return empty();
        }
    }
    
    class Car {
        
        Optional<Insurance> getInsurance() {
            
            return empty();
        }
    }
    
    class Insurance {
        
        String getName() {
            
            return "";
        }
    }
}

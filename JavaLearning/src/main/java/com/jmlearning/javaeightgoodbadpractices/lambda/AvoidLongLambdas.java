package com.jmlearning.javaeightgoodbadpractices.lambda;

import com.jmlearning.javaeightgoodbadpractices.Annotations;
import com.jmlearning.javaeightgoodbadpractices.User;
import com.jmlearning.javaeightgoodbadpractices.UserDto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AvoidLongLambdas {
    
    @Annotations.Ugly
    class LongLambdaInPlace {
        
        public List<UserDto> convertToDto(List<User> users) {
            
            return users.stream().map(user -> {
                
                UserDto dto = new UserDto();
                dto.setId(user.getId());
                dto.setName(user.getName());
                
                return dto;
                
            }).collect(Collectors.toList());
        }
    }
    
    @Annotations.Good
    class MethodReferenceInsteadOfLambda {
        
        private final Function<User, UserDto> toDto = this :: convertToDto;
        
        public List<UserDto> convertToDto(List<User> users) {
            
            return users.stream().map(toDto).collect(Collectors.toList());
        }
    
        private UserDto convertToDto(User user) {
        
            UserDto dto = new UserDto();
            dto.setId(user.getId());
            dto.setName(user.getName());
            
            return dto;
        }
    }
}

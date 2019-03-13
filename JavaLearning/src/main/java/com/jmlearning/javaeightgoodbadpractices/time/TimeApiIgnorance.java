package com.jmlearning.javaeightgoodbadpractices.time;

import com.jmlearning.javaeightgoodbadpractices.Annotations.Good;
import com.jmlearning.javaeightgoodbadpractices.Annotations.Ugly;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import static java.time.temporal.ChronoUnit.DAYS;

public class TimeApiIgnorance {
    
    @Ugly
    class AddDayInPreJava8Style {
        
        public Date tomorrow() {
    
            Calendar now = Calendar.getInstance();
            now.add(Calendar.DAY_OF_MONTH, 1);
            
            return now.getTime();
        }
    }
    
    @Ugly
    class AddDayInefficient {
        
        public LocalDate tomorrow() {
            
            return LocalDate.now().plus(1, DAYS);
        }
    }
    
    @Good
    class AddDayInJava8Style {
        
        public LocalDate tomorrow() {
            
            return LocalDate.now().plusDays(1);
        }
    }
}

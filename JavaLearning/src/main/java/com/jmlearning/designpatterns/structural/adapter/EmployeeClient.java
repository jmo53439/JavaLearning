package com.jmlearning.designpatterns.structural.adapter;

import java.util.ArrayList;
import java.util.List;

public class EmployeeClient {

    public List<Employee> getEmployeeList() {

        List<Employee> employees = new ArrayList <>();

        Employee employeeFromDB = new EmployeeDB("1234", "Joe", "Smith",
                "joesmith@iamlearning.com");

        employees.add(employeeFromDB);

        // this will not work. this is where the adapter comes into play

//    Employee employeeFromLdap = new EmployeeLdap("meow", "Smith", "Jane",
//            "janesmith@iamlearning.com");

        EmployeeLdap employeeFromLdap = new EmployeeLdap("meow", "Smith", "Jane",
                "janesmith@iamlearning.com");

        employees.add(new EmployeeAdapterLdap(employeeFromLdap));
        
        EmployeeCSV employeeFromCSV = new EmployeeCSV("567,Sherlock,Holmes,sherlockholmes@islearningtocode.com");

        employees.add(new EmployeeAdapterCSV(employeeFromCSV));

        return employees;
    }
}

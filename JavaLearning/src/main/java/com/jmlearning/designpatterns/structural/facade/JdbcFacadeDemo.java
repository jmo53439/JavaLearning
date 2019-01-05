package com.jmlearning.designpatterns.structural.facade;

import java.util.List;

public class JdbcFacadeDemo {

    public static void main(String[] args) {

        JdbcFacade jdbcFacade = new JdbcFacade();
        jdbcFacade.createTable();

        System.out.println("Table Created.");

        jdbcFacade.insertIntoTable();

        System.out.println("Record Inserted.");

        List<Address> addresses = jdbcFacade.getAddresses();

        for(Address address : addresses) {

            System.out.println(address.getId() + " " + address.getStreetName() + " " + address.getCity());
        }
    }
}

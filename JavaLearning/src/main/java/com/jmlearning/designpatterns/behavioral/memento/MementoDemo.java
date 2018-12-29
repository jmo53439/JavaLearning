package com.jmlearning.designpatterns.behavioral.memento;

public class MementoDemo {

    public static void main(String[] args) {

        Caretaker caretaker = new Caretaker();

        Employee emp = new Employee();

        emp.setName("Joe Smith");
        emp.setAddress("111 Main Street");
        emp.setPhone("888-123-4567");

        System.out.println("Employee before save:                     " + emp);
        caretaker.save(emp);

        emp.setPhone("888-345-6789");
        caretaker.save(emp);
        System.out.println("Employee after changed phone number save: " + emp);

        emp.setPhone("888-678-9012"); // we haven't called save
        caretaker.revert(emp);
        System.out.println("Reverts to last save point:               " + emp);

        caretaker.revert(emp);
        System.out.println("Reverted to original:                     " + emp);
    }
}

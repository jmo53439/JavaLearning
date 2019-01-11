package com.jmlearning.designpatterns.creational.abstractfactory;

public class AmexGoldCreditCard extends CreditCard implements Validator {

    @Override
    public boolean isValid(CreditCard creditCard) {

        return false;
    }
}

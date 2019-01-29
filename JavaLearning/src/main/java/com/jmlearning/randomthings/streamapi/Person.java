package com.jmlearning.randomthings.streamapi;

public class Person {

    private String name;
    private int age;
    private boolean hasValidDriversLicense;

    public Person() {

    }

    Person(String name, int age, boolean hasValidDriversLicense) {

        this.name = name;
        this.age = age;
        this.hasValidDriversLicense = hasValidDriversLicense;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public int getAge() {

        return age;
    }

    public void setAge(int age) {

        this.age = age;
    }

    public boolean hasValidDriversLicense() {

        return hasValidDriversLicense;
    }

    public void setValidDriversLicense(boolean validDriversLicense) {

        this.hasValidDriversLicense = hasValidDriversLicense;
    }

    @Override
    public boolean equals(Object o) {

        if(this == o)
            return true;

        if(!(o instanceof Person))
            return false;

        Person person = (Person) o;

        return !(name != null ? !name.equals(person.name) : person.name != null);
    }

    @Override
    public int hashCode() {

        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {

        final StringBuilder sb = new StringBuilder("Person: ");

        sb.append("name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", Drivers License=").append(hasValidDriversLicense);

        return sb.toString();
    }
}

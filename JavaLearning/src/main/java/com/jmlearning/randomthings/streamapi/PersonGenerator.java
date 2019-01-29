package com.jmlearning.randomthings.streamapi;

import java.util.*;

public class PersonGenerator {

    private static final Random RANDOM = new Random();

    private static final String[] NAMES = {"Michael Jordan", "Allen Iverson", "Carl Malone",
            "Anthony Davis", "Lebron James", "Derrick Rose", "Ray Allen", "Stephen Curry", "Jason Williams"};

    public static Set<Person> randomGenerator() {

        Set<Person> people = new HashSet <>();

        //for(int i = 0; i < NAMES.length; i++) {
        //
        //            people.add(new Person(NAMES[i], RANDOM.nextInt(50), RANDOM.nextBoolean()));
        //        }
        for(String NAME : NAMES) {

            people.add(new Person(NAME, RANDOM.nextInt(50), RANDOM.nextBoolean()));
        }

        return people;
    }

    public static Set<Set<Person>> randomGeneratorDifficult() {

        Set<Set<Person>> difficultSet = new HashSet <>();
        Set<Person> first = new HashSet <>();
        Set<Person> second = new HashSet <>();
        Set<Person> third = new HashSet <>();

        for(int i = 0; i < 4; i++) {

            first.add(new Person(NAMES[i], RANDOM.nextInt(50), RANDOM.nextBoolean()));
        }

        for(int i = 4; i < 6; i++) {

            second.add(new Person(NAMES[i], RANDOM.nextInt(50), RANDOM.nextBoolean()));
        }

        for(int i = 6; i < NAMES.length; i++) {

            third.add(new Person(NAMES[i], RANDOM.nextInt(50), RANDOM.nextBoolean()));
        }

        difficultSet.add(first);
        difficultSet.add(second);
        difficultSet.add(third);

        return difficultSet;
    }

    public static Person single() {

        return new Person(NAMES[RANDOM.nextInt(NAMES.length - 1)],
                RANDOM.nextInt(50), RANDOM.nextBoolean());
    }

    public static List<Person> common() {

        List<Person> people = new ArrayList <>(randomGenerator());
        people.addAll(randomGenerator());

        return people;
    }
}

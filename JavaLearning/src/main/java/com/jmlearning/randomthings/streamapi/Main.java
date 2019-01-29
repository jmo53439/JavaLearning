package com.jmlearning.randomthings.streamapi;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        Set<Person> personSet = PersonGenerator.randomGenerator();
        List<Person> commonPeople = PersonGenerator.common();
        Set<Set<Person>> difficultSet = PersonGenerator.randomGeneratorDifficult();

        // print
        showSource(personSet);
        showDifficultSource(difficultSet);

        // iterate
        forEachExample(personSet);
        forEachOrderedExample(personSet);

        // map, flat, reduce
        mapExample(personSet);
        flatMapExample(difficultSet);
        reduceExample(personSet);

        // debug
        peekExample(personSet);
        limitWithDebugExample(personSet);
        skipWithDebugExample(personSet);

        // collection
        ofExample(PersonGenerator.single(), PersonGenerator.single());
        toArrayExample(personSet);
        iterateExample();
        limitExample(personSet);
        skipExample(personSet);
        distinctExample(commonPeople.toArray(new Person[commonPeople.size()]));
        collectExample(personSet);
        sortedExample(personSet);

        // filter/search
        filterExample(personSet);
        matchExample(personSet);
        findExample(personSet);

        // statistics
        extremumsExample(personSet);
        countExample(personSet);
    }

    private static void iterateExample() {

        System.out.println("Iterate Example: \n");
        Stream.iterate(0, n -> n + 1).limit(5).forEach(System.out:: println);
        printSeperator();
    }

    private static void printSeperator() {

        System.out.println();
        System.out.println("---------------------------------");
        System.out.println();
    }

    private static void showSource(Set<Person> personSet) {

        System.out.println("Source Set: \n");
        personSet.forEach(System.out :: println);
        printSeperator();
    }

    private static void showDifficultSource(Set<Set<Person>> difficultSet) {

        System.out.println("Flat Map Source Set: \n");
        difficultSet.forEach(System.out :: println);
        printSeperator();
    }

    private static void filterExample(Set<Person> personSet) {

        System.out.println("Filter Example: \n");
        personSet.stream().filter(Person :: hasValidDriversLicense).forEach(System.out :: println);
        printSeperator();
    }

    private static void mapExample(Set<Person> personSet) {

        System.out.println("Map Example: \n");
        personSet.stream().mapToInt(Person :: getAge).forEach(age -> System.out.println("Age: " + age));
        printSeperator();
    }

    private static void flatMapExample(Set<Set<Person>> difficultPersonSet) {

        System.out.println("Flat-Map Example: \n");
        difficultPersonSet.stream().flatMap(Set :: stream).forEach(System.out :: println);
        printSeperator();
    }

    private static void reduceExample(Set<Person> personSet) {

        System.out.println("Reduce Example: \n");
        System.out.println(personSet.stream().map(Person :: getName).reduce(
                "", (val, name) -> val += name + "; "));
        printSeperator();
    }

    private static void collectExample(Set<Person> personSet) {

        System.out.println("Collect Example: \n");
        Set<Person> young = personSet.stream().filter(p -> p.getAge() < 18).collect(Collectors.toSet());
        //young.stream().forEach(System.out :: println);
        young.forEach(System.out :: println);
        printSeperator();
    }

    private static void ofExample(Person...persons) {

        System.out.println("Of Method Example: \n");
        Stream.of(persons).forEach(System.out :: println);
        printSeperator();
    }

    private static void toArrayExample(Set<Person> personSet) {

        System.out.println(Arrays.toString(personSet.parallelStream().toArray(Person[] :: new)));
    }

    private static void distinctExample(Person...persons) {

        System.out.println("Distinct Method Example: \n");
        Stream.of(persons).parallel().distinct().forEach(System.out :: println);
        printSeperator();
    }

    private static void sortedExample(Set<Person> personSet) {

        System.out.println("Sorted Example: \n");

        //personSet.stream().sorted((p1, p2) -> ((p1.getAge() < p2.getAge()) ? -1
        //               : ((p1.getAge() == p2.getAge()) ? 0 : 1))).forEach(System.out :: println);
        personSet.stream().sorted((p1, p2) -> (Integer.compare(p1.getAge(), p2.getAge()))).forEach(System.out :: println);
        printSeperator();
    }

    private static void peekExample(Set<Person> personSet) {

        try {

            System.out.println("Peek Example: \n");
            personSet.stream().filter(p -> p.getAge() > 30)
                    .map(Person :: getName)
                    .peek(p -> System.out.println(p + " is older than 30"))
                    .forEach(System.out :: println);
        }
        finally {

            printSeperator();
        }
    }

    private static void limitExample(Set<Person> personSet) {

        System.out.println("Limit Example: \n");
        personSet.stream().limit(5).forEach(System.out :: println);
        printSeperator();
    }

    private static void skipExample(Set<Person> personSet) {

        System.out.println("Skip Example: \n");
        personSet.stream().peek(System.out :: println).skip(5).forEach(System.out :: println);
        printSeperator();
    }

    private static void matchExample(Set<Person> personSet) {

        System.out.println(personSet.stream().noneMatch(
                p -> p.getAge() == 25 && p.getName().equals("Allen Iverson") &&
                        p.hasValidDriversLicense()) ? "No Matches Found" : "Matches Found");
    }

    private static void findExample(Set<Person> personSet) {

        System.out.println("Result Find Any (No Parallel): " + personSet.stream().findAny().get());
        System.out.println("Result Find Any (With Parallel): " + personSet.parallelStream().findAny().get());
        System.out.println("Result Find First (No Parallel): " + personSet.stream().findFirst().get());
        System.out.println("Result Find First (With Parallel): " + personSet.stream().parallel().findFirst().get());
    }

    private static void forEachExample(Set<Person> personSet) {

        System.out.println("ForEach Example \n");
        personSet.stream().parallel().forEach(System.out :: println);
        printSeperator();
    }

    private static void forEachOrderedExample(Set<Person> personSet) {

        System.out.println("ForEach Ordered Example: \n");
        personSet.stream().parallel().forEachOrdered(System.out :: println);
        printSeperator();
    }

    private static void countExample(Set<Person> personSet) {

        //System.out.println("Element Count in Stream: " + personSet.stream().count());
        System.out.println("Element Count in Stream: " + (long) personSet.size());
    }

    private static void extremumsExample(Set<Person> personSet) {

        System.out.println("Max Person Age: " + personSet.stream()
                .max((p1, p2) -> ((p1.getAge() < p2.getAge()) ? -1 :
                        ((p1.getName().length() == p2.getName().length()) ? 0 : 1)))
                .get().getAge());

        //System.out.println("Min Person Name: " + personSet.stream()
        //                .min((p1, p2) -> ((p1.getName().length() < p2.getName().length()) ? -1 :
        //                        ((p1.getName().length() == p2.getName().length()) ? 0 : 1)))
        //                .get().getAge());
        System.out.println("Min Person Name: " + personSet.stream()
                .min((p1, p2) -> (Integer.compare(p1.getName().length(), p2.getName().length())))
                .get().getAge());
    }

    private static void limitWithDebugExample(Set<Person> personSet) {

        System.out.println("Limit with Debug Example: \n");
        personSet.stream().peek(System.out :: println).limit(5).forEach(System.out :: println);
        printSeperator();
    }

    private static void skipWithDebugExample(Set<Person> personSet) {

        System.out.println("Peek with Debug Example: \n");
        personSet.stream().peek(System.out :: println).skip(5).forEach(System.out :: println);
        printSeperator();
    }
}

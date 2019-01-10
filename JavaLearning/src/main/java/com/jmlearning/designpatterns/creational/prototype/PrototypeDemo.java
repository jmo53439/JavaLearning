package com.jmlearning.designpatterns.creational.prototype;

public class PrototypeDemo {

    public static void main(String[] args) {

        Registry registry = new Registry();

        Movie movie = (Movie) registry.createItem("Movie");
        movie.setTitle("Java Design Patterns");

        System.out.println(movie);
        System.out.println(movie.getRuntime());
        System.out.println(movie.getTitle());
        System.out.println(movie.getUrl());

        Movie anotherMovie = (Movie) registry.createItem("Movie");
        anotherMovie.setTitle("Wanna Learn Unity");

        System.out.println(anotherMovie);
        System.out.println(anotherMovie.getRuntime());
        System.out.println(anotherMovie.getTitle());
        System.out.println(anotherMovie.getUrl());
    }
}

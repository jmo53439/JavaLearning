package com.jmlearning.concurrentpatterns.concurrentcollections;

import com.jmlearning.concurrentpatterns.concurrentcollections.model.Actor;
import com.jmlearning.concurrentpatterns.concurrentcollections.model.Movie;
import com.jmlearning.concurrentpatterns.concurrentcollections.model.MovieReader;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapParallelPattern {

    public static void main(String[] args) {

        ConcurrentHashMap<Actor, Set<Movie>> map = new ConcurrentHashMap <Actor, Set<Movie>>();

        MovieReader reader = new MovieReader();
        reader.addActorsToMap(map);

        System.out.println("# Actors = " + map.size());

        int maxMoviesForOneActor = map.reduce(10, (actor, movies) -> movies.size(), Integer :: max);
        System.out.println("Max Movies for One Actor = " + maxMoviesForOneActor);

        Actor mostSeenActor = map.search(10, (actor, movies) -> movies.size() == maxMoviesForOneActor ? actor : null);
        System.out.println("Most Seen Actor = " + mostSeenActor);

        int numberOfMovieReferences = map.reduce(10, (actor, movies) -> movies.size(), Integer :: sum);
        System.out.println("Average Movies per Actor = " + numberOfMovieReferences/map.size());
    }
}

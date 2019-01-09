package com.jmlearning.designpatterns.structural.proxy;

public class TwitterDemo {

    public static void main(String[] args) {

        TwitterService service = (TwitterService)SecurityProxy.newInstance(new TwitterServiceImpl());

        // Will throw exception. finish 'TwitterServiceImpl' @ home
        System.out.println(service.getTimeline("meow"));

        service.postToTimeline("meow", "Some message that should'nt go through");
    }
}

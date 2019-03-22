package com.jmlearning.randomthings.gamingprogramming.filesandresources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ClasspathResources {

    public ClasspathResources() {

    }

    public void runTest() {

        System.out.println("Don't forget to add the ./res/asserts/lib folder to the class path");

        System.out.println();
        System.out.println("***************************");
        System.out.println("ClassLoader - Absolute Path");
        System.out.println();

        InputStream in = ClassLoader.getSystemResourceAsStream(
                "com/jmlearning/randomthings/gamingprogramming/filesandresources/Test1.txt");
        printResource(in);

        System.out.println();
        System.out.println("***************************");
        System.out.println("getClass() - Relative Path");
        System.out.println();

        in = getClass().getResourceAsStream("Test2.txt");
        printResource(in);

        System.out.println();
        System.out.println("***************************");
        System.out.println("getClass() - Absolute Path");
        System.out.println();

        in = getClass().getResourceAsStream(
                "/com/jmlearning/randomthings/gamingprogramming/filesandresources/Test2.txt");
        printResource(in);

        System.out.println();
        System.out.println("***************************");
        System.out.println("getClass() - Absolute Path");
        System.out.println();

        in = ClasspathResources.class.getResourceAsStream("Test3.txt");
        printResource(in);

        in = getClass().getResourceAsStream("do/what/now");

        if(in == null) {

            System.out.println();
            System.out.println("***************************");
            System.out.println("Um, file not found?");
        }

        in = ClassLoader.getSystemResourceAsStream("/do/what/now");

        if(in == null) {

            System.out.println("File not found again");
        }
    }

    private void printResource(InputStream in) {

        try {

            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = null;

            while((line = bufferedReader.readLine()) != null) {

                System.out.println(line);
            }
        }
        catch(IOException e) {

            e.printStackTrace();
        }
        finally {

            try {

                in.close();
            }
            catch(Exception ex) {

            }
        }
    }

    public static void main(String[] args) {

        new ClasspathResources().runTest();
    }
}

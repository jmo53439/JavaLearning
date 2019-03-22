package com.jmlearning.randomthings.gamingprogramming.filesandresources;

import com.jmlearning.randomthings.gamingprogramming.utils.ResourceLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceLoaderExample {

    public ResourceLoaderExample() {

    }

    public void runTest() {

        Class<?> cls = ResourceLoaderExample.class;

        // absolute resource
        String filePath = "not/used";
        String resPath = "/com/jmlearning/randomthings/gamingprogramming/filesandresources/Test1.txt";
        InputStream in = ResourceLoader.load(cls, filePath, resPath);
        printResource(in);

        // relative resource
        filePath = "not/used";
        resPath = "Test2.txt";
        in = ResourceLoader.load(cls, filePath, resPath);
        printResource(in);

        // change when i get home
        // absolute path
        filePath = "C:/Users/o047833/IdeaProjects" +
                "/RandomJava/res/assets/lib/gamingprogramming/filesandresources/Test3.txt";
        resPath = "/not/available";
        in = ResourceLoader.load(cls, filePath, resPath);
        printResource(in);

        // relative path
        filePath = "res/assets/lib/gamingprogramming/filesandresources/Test3.txt";
        resPath = "/not/available";
        in = ResourceLoader.load(cls, filePath, resPath);
        printResource(in);

//        // exception when both null
//        filePath = "is/null";
//        resPath = "is/also/null";
//        in = ResourceLoader.load(cls,filePath,resPath);
//        printResource(in);
    }

    private void printResource(InputStream in) {

        try {

            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(reader);
            String line = null;

            while((line = br.readLine()) != null) {

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
            catch(Exception e) {

            }
        }
    }

    public static void main(String[] args) {

        new ResourceLoaderExample().runTest();
    }
}

package com.jmlearning.randomthings.gamingprogramming.filesandresources;

import java.io.*;
import java.util.Random;

public class WritingDataToFiles {

    private Random random = new Random();

    public void runTest() {

        writeOutBytes("./res/assets/text/byte-file.bin");
        writeOutStrings("./res/assets/text/string-file.txt");
    }

    public void writeOutBytes(String fileName) {

        System.out.println();
        System.out.println();
        System.out.println("********************");
        File file = new File(fileName);
        OutputStream out = null;

        try {

            out = new FileOutputStream(file);

            for(int i = 0; i < 1000; ++i) {

                out.write(random.nextInt(256));
            }
        }
        catch(FileNotFoundException fex) {

            fex.printStackTrace();
        }
        catch(IOException ioex) {

            ioex.printStackTrace();
        }
        finally {

            try {

                out.close();
                System.out.println("Wrote: " + file.getPath());
            }
            catch(Exception ex) {

            }
        }
    }

    public void writeOutStrings(String fileName) {

        System.out.println();
        System.out.println();
        System.out.println("********************");
        String[] strings = {"This is a test.",
                "Im not really sure what to write here.",
                "But i just bought a wrx and i love it.",
                "My buddy has an evo and i wanna race him.",
                "There are three mods i waanna do to my car.",
                "Get a cobb accessport,", ",get an intake",
                "and get a catback exhaust."};

        File file = new File(fileName);
        PrintWriter out = null;

        try {

            out = new PrintWriter(file);

            for(String string : strings) {

                out.println(string);
            }
        }
        catch(FileNotFoundException fex) {

            fex.printStackTrace();
        }
        finally {

            try {

                out.close();
                System.out.println("Wrote: " + file.getPath());
            }
            catch(Exception e) {

            }
        }
    }

    public static void main(String[] args) {

        new WritingDataToFiles().runTest();
    }
}

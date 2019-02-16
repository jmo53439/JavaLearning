package com.jmlearning.randomthings.gamingprogramming.filesandresources;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FilesAndDirectories {

    public FilesAndDirectories() {

    }

    public void runTest() {

        String dir = "*Insert Path Here*";
        File file = new File(dir);
        displayInfo(0, file);
    }

    private void displayInfo(int depth, File file) {

        boolean executable = file.canExecute();
        boolean readable = file.canRead();
        boolean writable = file.canWrite();
        boolean hidden = file.isHidden();
        boolean directory = file.isDirectory();

        long lastModified = file.lastModified();
        long length = file.length();

        String name = file.getName();
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < depth; ++i) {

            sb.append("|");
        }

        if(directory) {

            sb.append("+ ");
        }

        if(name.isEmpty()) {

            sb.append(".");
        }
        else {

            sb.append(name);
        }

        // last modified
        sb.append("\t\t");
        Date date = new Date(lastModified);
        sb.append(new SimpleDateFormat().format(date));
        sb.append("\t\t");

        // size in kb
        long kb = length / 1024;
        DecimalFormat format = new DecimalFormat();
        format.setGroupingUsed(true);
        sb.append(format.format(kb));
        sb.append(" KB");

        // add flags
        sb.append("\t\t");

        if(hidden)
            sb.append(".");

        if(readable)
            sb.append("R");

        if(writable)
            sb.append("W");

        if(executable)
            sb.append("X");

        // print to cmd line
        System.out.println(sb.toString());
        File[] children = file.listFiles();

        if(children != null) {

            for(File child : children) {

                displayInfo(depth + 1, child);
            }
        }
    }

    public static void main(String[] args) {

        new FilesAndDirectories().runTest();
    }
}

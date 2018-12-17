package com.jmlearning.sorts;


import java.util.Random;

public class BubbleSort {

     static int[] sort(int[] sequence) {

        // BubbleSort
        for(int i = 0; i < sequence.length; i++)

            for(int j = 0; j < sequence.length - 1; j++)

                if(sequence[j] > sequence[j + 1]) {

                    sequence[j] = sequence[j] + sequence[j + 1];
                    sequence[j + 1] = sequence[j] - sequence[j + 1];
                    sequence[j] = sequence[j] - sequence[j + 1];
                }

         return sequence;
    }

     static void printSequence(int[] sortedSequence) {

        for(int i = 0; i < sortedSequence.length; i++)
            System.out.print(sortedSequence[i] + " ");
    }

    public static void main(String[] args) {

        System.out.println("Sorting Randomly Generated Numbers using BubbleSort");

        Random random = new Random();
        int N = 20;
        int[] sequence = new int[N];

        for(int i = 0; i < N; i++)

            sequence[i] = Math.abs(random.nextInt(1000));

        System.out.println("\nOriginal Sequence: ");
        printSequence(sequence);

        System.out.println("\nSorted Sequence: ");
        printSequence(sort(sequence));
    }
}
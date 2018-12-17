package com.jmlearning.randomthings;

import java.util.Scanner;

class FloydTriangle {

    public static void main(String args[]) {

        int n, num = 1, c, d;
        Scanner in = new Scanner(System.in);

        System.out.println("Enter the number of rows you want");
        n = in.nextInt();

        System.out.println("Floyds Triangle : ");

        for(c = 1; c <= n; c++) {

            for(d = 1; d <= c; d++) {

                System.out.print(num + "");
                num++;
            }

            System.out.println();
        }
    }
}
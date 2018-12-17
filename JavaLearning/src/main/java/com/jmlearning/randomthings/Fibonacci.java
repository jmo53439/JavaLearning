package com.jmlearning.randomthings;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Fibonacci {

    private static Map<Integer, Integer> map = new HashMap <Integer, Integer>();

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        System.out.println(fibMemo(n));
        System.out.println(fibBottomUp(n));
    }

    private static int fibMemo(int n) {

        if(map.containsKey(n)) {

            return map.get(n);
        }

        int f;

        if(n <= 2) {

            f = 1;
        }
        else {

            f = fibMemo(n - 1) + fibMemo(n - 2);
            map.put(n, f);
        }

        return f;
    }

    private static int fibBottomUp(int n) {

        Map<Integer, Integer> fib = new HashMap <Integer, Integer>();

        for(int i = 1; i < n; i++) {

            int f = 1;

            if(i <= 2) {

                f = 1;
            }
            else {

                f = fib.get(i - 1) + fib.get(i - 2);
            }

            fib.put(i, f);
        }

        return fib.get(n);
    }

    private static int fibOptimized(int n) {

        if( n == 0) {

            return 0;
        }

        int prev = 0, res = 1, next;

        for(int i = 2; i < n; i++) {

            next = prev + res;
            prev = res;
            res = next;
        }

        return res;
    }
}
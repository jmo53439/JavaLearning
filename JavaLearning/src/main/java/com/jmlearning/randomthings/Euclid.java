package com.jmlearning.randomthings;

import java.math.BigInteger;
import java.util.*;

public class Euclid {

    public static int gcd(int a, int b) {

        return b == 0 ? Math.abs(a) : gcd(b, a % b);
    }

    public static int gcdTwo(int a, int b) {

        while(b != 0) {

            int t = b;
            b = a % b;
            a = t;
        }

        return Math.abs(a);
    }

    public static int lcm(int a, int b) {

        return Math.abs(a / gcd(a, b) * b);
    }

    public static long[] euclid(long a, long b) {

        long x = 1, y = 0, x1 = 0, y1 = 1;

        while(b!= 0) {

            long q = a / b;
            long _x1 = x1;
            long _y1 = y1;
            long _b = b;

            x1 = x - q * x1;
            y1 = y - q * y1;
            b = a - q * b;
            x = _x1;
            y = _y1;
            a = _b;
        }

        return a > 0 ? new long[] {a, x, y} : new long[] {-a, -x, -y};
    }

    public static long[] euclid2(long a, long b) {

        if(b == 0)
            return a > 0 ? new long[]{a, 1, 0} : new long[]{-a, -1, 0};
        long[] r = euclid(b, a % b);
        return new long[] {r[0], r[2], r[1] - a / b * r[2]};
    }

    public static int mod(long a, int m) {

        int A = (int) (a % m);
        return a >= 0 ? A : A + m;
    }

    public static int modInverse(int a, int m) {

        a = mod(a, m);
        return a == 0 ? 0 : mod((1 - (long) modInverse(m % a, a) * m) / a, m);
    }

    public static int modInverse2(int a, int m) {

        return mod(euclid(a, m)[1], m);
    }

    public static int[] generateInverses(int p) {

        int[] res = new int[p];
        res[1] = 1;

        for(int i = 2; i < p; ++i)
            res[i] = (p - (p / i) * res[p % i] % p) % p;
        return res;
    }

    public static BigInteger garnerRestore(int[] a, int[] p) {

        int[] x = a.clone();

        for(int i = 0; i < x.length; ++i)

            for(int j = 0; j < i; ++j)

                x[i] = mod(BigInteger.valueOf(p[j]).modInverse(
                        BigInteger.valueOf(p[i])).longValue() * (x[i] - x[j]), p[i]);

        BigInteger res = BigInteger.valueOf(x[0]);
        BigInteger m = BigInteger.ONE;

        for(int i = 1; i < x.length; i++) {

            m = m.multiply(BigInteger.valueOf(p[i - 1]));
        }

        return res;
    }

    public static int simpleRestore(int[] a, int[] p) {

        int res = 0;

        for(int i = 0, m = 1; i < a.length; i++, m *= p[i])

            while(res % p[i] != a[i])

                res += m;

        return res;
    }

    public static void main(String[] args) {

        Random rand = new Random(1);

        for(int steps = 0; steps < 10000; steps++) {

            int a = rand.nextInt(20) - 10;
            int b = rand.nextInt(20) - 10;

            BigInteger xa = BigInteger.valueOf(a);
            BigInteger xb = BigInteger.valueOf(b);

            long gcd1 = gcd(a, b);
            long gcdTwo = gcdTwo(a, b);
            long gcd = xa.gcd(xb).longValue();
            long[] euclid1 = euclid(a, b);
            long[] euclid2 = euclid2(a, b);

            int inv1 = 0;
            int inv2 = 0;
            int inv = 0;

            if(gcd == 1 && b > 0) {

                inv1 = modInverse(a, b);
                inv2 = modInverse2(a, b);
                inv = xa.modInverse(xb).intValue();
            }

            if(gcd1 != gcd || gcdTwo != gcd || !Arrays.equals(euclid1, euclid2) || euclid1[0] != gcd || inv1 != inv
                    || inv2 != inv) {

                System.err.println(a + " " + b);
            }
        }

        long a = 6;
        long b = 9;
        long[] res = euclid(a, b);

        System.out.println(res[1] + " * (" + a + ") " + " + " + res[2] + " * (" + b + ") = gcd(" + a + "," + b + ") = "
                + res[0]);

        System.out.println(Arrays.toString(generateInverses(7)));
    }
}
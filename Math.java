package com.company.LinAlg;

/**
 * Created by Brandon on 10/27/2020, 11:28 PM.
 */
public class Math {
    public static int abs(int n) {
        if (n > 0) {
            return n;
        } else {
            return -n;
        }
    }
    // Finds gcd of i and j (want my own implementation to make sure negatives are dealt with correctly and to show off)
    public static int gcd(int i, int j) {
        // Take absolute values
        int iPos = Math.abs(i);
        int jPos = Math.abs(j);
        if (iPos == jPos && iPos == 0) {
            return 0;
        } else if (iPos == 0) {
            return jPos;
        } else if (jPos == 0) {
            return iPos;
        } else if (iPos > jPos) {
            return gcd(iPos%jPos, jPos);
        } else {
            return gcd(iPos, jPos%iPos);
        }
    }
    // Finds gcd of integers in an integer array arr
    public static int gcdArr(int[] arr) {
        int gcd = Math.abs(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            gcd = gcd(gcd,arr[i]);
        }
        return gcd;
    }

}
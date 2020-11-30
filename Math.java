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

    // Checks if an integer is in a range (inclusive)
    public static boolean isInRange (int num, int min, int max) {
        if (num <= max && num >= min) {
            return true;
        }
        return false;
    }

    // Returns the smaller of the two numbers.
    public static int min(int i, int j) {
        if (i < j) {
            return i;
        } else {
            return j;
        }
    }

    // Returns the larger of the two numbers.
    public static int max(int i, int j) {
        if (i > j) {
            return i;
        } else {
            return j;
        }
    }

    // Returns the number of digits in a number
    public static int digitNum(int i) {
        int digits = 0;
        if (i < 0) {
            digits++;
        }
        i = Math.abs(i);
        while (i >= 10) {
            i = i / 10;
            digits++;
        }
        digits++;
        return digits;
    }
    public static Vector multiply(Matrix matrix, Vector vector) {
        if(vector.N != matrix.N) {
            throw new IllegalArgumentException("Vector is not correct size");
        }
        Vector result = new Vector(matrix.M);
        for (int i = 0; i < matrix.M; i++) {
            int temp = 0;
            for (int j = 0; j < matrix.N; j++) {
                temp += matrix.vals[i][j] * vector.vals[j];
            }
            result.setValue(temp, i);
        }
        return result;
    }
}

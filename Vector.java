package com.company.LinAlg;

/**
 * Created by Brandon on 11/30/2020, 10:34 AM.
 */
public class Vector {
    public int N;
    public int[] vals;
    public Vector(int N) {
        // N is number of rows
        this.N = N;
        vals = new int[N];
    }
    public void setValue(int val, int i) {
        this.vals[i] = val;
    }
    public String toString() {
        String str = "";
        for (int i = 0; i < this.N; i++) {
            str += (vals[i] + "\n");
        }
        return str;
    }

    // Takes dot product of two integer vectors
    public static int dot(Vector v1, Vector v2) {
        int res = 0;
        for (int i = 0; i < v1.N; i++) {
            res += v1.vals[i]*v2.vals[i];
        }
        return res;
    }

    // Adds two integer vectors
    public static Vector add(Vector v1, Vector v2) {
        Vector res = new Vector(v1.N);
        for (int i = 0; i < v1.N; i++) {
            res.setValue(v1.vals[i] + v2.vals[i], i);
        }
        return res;
    }

    // Multiply an integer vector by a constant
    public static Vector mult(Vector v, int sc) {
        Vector res = new Vector(v.N);
        for (int i = 0; i < v.N; i++) {
            res.setValue(v.vals[i]*sc, i);
        }
        return res;
    }
}

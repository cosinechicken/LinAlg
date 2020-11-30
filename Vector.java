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
}

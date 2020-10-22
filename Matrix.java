package com.company.LinAlg;

import static com.company.LinAlg.LinAlg.showPivots;

/**
 * Created by Brandon on 10/22/2020, 10:53 AM.
 */
public class Matrix {
    private int[][] vals;
    // Position of leftmost nonzero number in each row
    private int[] pivots;
    private int M;
    private int N;
    public Matrix(int M, int N) {
        // M is number of rows, N is number of columns
        this.M = M;
        this.N = N;
        vals = new int[M][N];
        pivots = new int[M];
        for (int i = 0; i < M; i++) {
            pivots[i] = N;
        }
    }
    private Matrix(int[][] vals) {
        M = vals.length;
        N = vals[0].length;
        this.vals = new int[M][N];
        pivots = new int[M];
        for (int i = 0; i < M; i++) {
            boolean isNonZero = false;
            for (int j = 0; j < N; j++) {
                this.vals[i][j] = vals[i][j];
                if (vals[i][j] != 0 && !isNonZero) {
                    isNonZero = true;
                    pivots[i] = j;
                }
            }
            if (!isNonZero) {
                pivots[i] = N;
            }
        }
    }
    public void setValue(int val, int i, int j) {
        this.vals[i][j] = val;
        // FInd pivot (inefficient, should improve)
        boolean isNonZero = false;
        for (int k = 0; k < N; k++) {
            this.vals[i][k] = vals[i][k];
            if (vals[i][k] != 0 && !isNonZero) {
                isNonZero = true;
                pivots[i] = k;
            }
        }
        if (!isNonZero) {
            pivots[i] = N;
        }
    }
    public String toString() {
        String str = "";
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                str += vals[i][j];
                str += "\t";
            }
            str += "\n";
        }
        if (showPivots) {
            str += "\n";
            for (int i = 0; i < M; i++) {
                str += (pivots[i] + "\n");
            }
        }
        return str;
    }
    // Swap rows i and j (0-based indices)
    public void swap(int i, int j) {
        int temp;
        for (int k = 0; k < N; k++) {
            temp = vals[i][k];
            vals[i][k] = vals[j][k];
            vals[j][k] = temp;
        }
        temp = pivots[i];
        pivots[i] = pivots[j];
        pivots[j] = temp;
    }
    // Scale values in row i by some integer scale
    public void scale(int i, int scale) {
        for (int j = 0; j < N; j++) {
            vals[i][j] *= scale;
        }
        if (scale == 0) {
            pivots[i] = N;
        }
    }
    // Add row i to row j
    public void addRow(int i, int j) {
        for (int k = 0; k < N; k++) {
            vals[j][k] += vals[i][k];
        }
        // Find pivot (inefficient)
        boolean isNonZero = false;
        for (int k = 0; k < N; k++) {
            this.vals[j][k] = vals[j][k];
            if (vals[j][k] != 0 && !isNonZero) {
                isNonZero = true;
                pivots[j] = k;
            }
        }
        if (!isNonZero) {
            pivots[j] = N;
        }
    }
    // Divides by gcd of numbers in row i and makes the first nonzero entry positive
    public int descale(int i) {
        int gcd = gcdArr(vals[i]);
        int factor = gcd;
        if (gcd != 0) {
            for (int j = 0; j < N; j++) {
                vals[i][j] /= gcd;
            }
            // Make leftmost nonzero variable positive
            // Since gcd != 0 some variable is nonzero

            if (vals[i][pivots[i]] < 0) {
                factor *= -1;
                for (int j = 0; j < N; j++) {
                    vals[i][j] *= -1;
                }
            }
        } else if (gcd == 0) {
            factor = 1;
        }
        return factor;

    }
    // Adds a scalar multiple of row i to row j so that vals[j][pivots[i]] = 0 (or vice versa)
    // Also helper method for toREF()
    public void cancel(int i, int j) {
        // (a,b) = (i,j) if pivots[i] > pivots[j] or pivots[i] = pivots[j] and i < j, and (j,i) otherwise
        int a;
        int b;
        if (pivots[i] > pivots[j] || (pivots[i] == pivots[j] && i < j)) {
            a = i;
            b = j;
        } else {
            a = j;
            b = i;
        }
        // Now begin cancelling
        this.descale(a);
        this.descale(b);

        // Fraction written in form scaleA_N/scaleA_D
        int scaleA_N = 1;
        int scaleA_D = 1;
        scaleA_N *= vals[b][pivots[a]];
        scaleA_D *= -vals[a][pivots[a]];
        int scaleB_N = scaleA_D;

        this.scale(a,scaleA_N);
        this.scale(b,scaleA_D);
        this.addRow(a, b);
        this.descale(a);
        int scaleB_D = this.descale(b);

        // Simplify fractions
        int gcdA = gcd(scaleA_N, scaleA_D);
        scaleA_N /= gcdA;
        scaleA_D /= gcdA;
        if (scaleA_D < 0) {
            scaleA_N *= -1;
            scaleA_D *= -1;
        }

        int gcdB = gcd(scaleB_N, scaleB_D);
        scaleB_N /= gcdB;
        scaleB_D /= gcdB;
        if (scaleB_D < 0) {
            scaleB_N *= -1;
            scaleB_D *= -1;
        }

        System.out.println("R_" + (b+1) + " -> (R_" + (b+1) + " + " + scaleA_N + "/" + scaleA_D + "* R_" + (a+1) + ") * (" + scaleB_N + "/" + scaleB_D + ")");
    }
    // Helper methods

    // Finds gcd of i and j (want my own implementation to make sure negatives are dealt with correctly and to show off)
    private int gcd(int i, int j) {
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
    private int gcdArr(int[] arr) {
        int gcd = Math.abs(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            gcd = gcd(gcd,arr[i]);
        }
        return gcd;
    }

    // REF stands for Row Echelon Form [I]
    public void toREF() {

    }
}

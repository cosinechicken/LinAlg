package com.company.LinAlg;

import java.util.ArrayList;

import static com.company.LinAlg.LinAlg.showPivots;

/**
 * Created by Brandon on 10/22/2020, 10:53 AM.
 */
public class Matrix {
    public int[][] vals;
    // Position of leftmost nonzero number in each row
    public int[] pivots;
    public int M;
    public int N;
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
        ArrayList<Integer> colDigits = new ArrayList<>();
        for (int j = 0; j < N; j++) {
            colDigits.add(0);
            for (int i = 0; i < M; i++) {
                if (colDigits.get(j) < Math.digitNum(this.vals[i][j])) {
                    colDigits.set(j, Math.digitNum(this.vals[i][j]));
                }
            }
        }
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                str += vals[i][j];
                for (int k = Math.digitNum(this.vals[i][j]); k <= Math.max(colDigits.get(j), 2); k++) {
                    str += " ";
                }
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
    // Swap rows i and j (1-based indices)
    // Prints if print == true
    public void swap(int i, int j, boolean print) {
        int temp;
        for (int k = 0; k < N; k++) {
            temp = vals[i][k];
            vals[i][k] = vals[j][k];
            vals[j][k] = temp;
        }
        temp = pivots[i];
        pivots[i] = pivots[j];
        pivots[j] = temp;
        if (print) {
            System.out.println("R_" + (i+1) + " <-> R_" + (j+1));
        }
    }
    // Scale values in row i by some integer scale, returns the scale
    public int scale(int i, int scale) {
        for (int j = 0; j < N; j++) {
            vals[i][j] *= scale;
        }
        if (scale == 0) {
            pivots[i] = N;
        }
        return scale;
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
        int gcd = Math.gcdArr(vals[i]);
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
        } else {
            factor = 1;
        }
        return factor;

    }
    // Adds a scalar multiple of row i to row j so that vals[j][pivots[i]] = 0 (or vice versa)
    // Also helper method for toREF()
    // Prints the string it is supposed to print if print is true
    public Fraction cancel(int i, int j, boolean print) {
        // (a,b) = (i,j) if pivots[i] > pivots[j] or pivots[i] = pivots[j] and i < j, and (j,i) otherwise
        int a, b;
        Fraction multiplier = new Fraction(1,1);
        if (pivots[i] > pivots[j] || (pivots[i] == pivots[j] && i < j)) {
            a = i;
            b = j;
        } else {
            a = j;
            b = i;
        }
        // Now begin cancelling
        // First step is scale down both rows
        int aScale = this.descale(a);
        if (aScale != 1) {
            Fraction aScaleFrac = new Fraction(1,aScale);
            multiplier.multiply(aScale);
            if (print) {
                System.out.println("R_" + (a + 1) + " -> (" + aScaleFrac + ") * R_" + (a + 1) + "\n");
                System.out.println(this.toString() + "\n");
            }
        }
        int bScale = this.descale(b);
        if (bScale != 1) {
            Fraction bScaleFrac = new Fraction(1,bScale);
            multiplier.multiply(bScale);
            if (print) {
                System.out.println("R_" + (b + 1) + " -> (" + bScaleFrac + ") * R_" + (b + 1) + "\n");
                System.out.println(this.toString() + "\n");
            }
        }

        // Fraction written in form scaleA_N/scaleA_D
        int scaleA_N = 1;
        int scaleA_D = 1;
        scaleA_N *= vals[b][pivots[a]];
        scaleA_D *= -vals[a][pivots[a]];

        this.scale(a,scaleA_N);
        this.scale(b,scaleA_D);
        multiplier.divide(scaleA_N);
        multiplier.divide(scaleA_D);
        this.addRow(a, b);
        multiplier.multiply(this.descale(a));
        int scaleB_D = this.descale(b);
        multiplier.multiply(scaleB_D);

        Fraction scaleAFrac = new Fraction(scaleA_N, scaleB_D);
        Fraction scaleBFrac = new Fraction(scaleA_D, scaleB_D);

        if (print) {
            System.out.println("R_" + (b+1) + " -> (" + scaleBFrac + ") * R_" + (b+1) + " + (" + scaleAFrac + ") * R_" + (a+1));
        }
        multiplier.simplify();
        return multiplier;
    }
    // Helper methods


    // REF stands for Row Echelon Form
    // print determines whether to print the steps
    public Fraction toREF(boolean print) {
        Fraction multiplier = new Fraction(1,1);
        // First find rows with minimum value for pivot
        ArrayList<Integer> arr = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            int pivotVal = Integer.MAX_VALUE;
            int firstIndex = -1;
            arr.clear();
            for (int j = i; j < M; j++) {
                if (pivots[j] < pivotVal) {
                    pivotVal = pivots[j];
                    arr.clear();
                    firstIndex = j;
                } else if (pivots[j] == pivotVal) {
                    arr.add(j);
                }
            }
            if (pivotVal < N) {
                if (firstIndex != i) {
                    this.swap(i, firstIndex, print);
                    multiplier.multiply(-1);
                    if (print) {
                        System.out.println();
                        System.out.println(this.toString() + "\n");
                    }
                }
                // Now we can assume the index we want to apply this.cancel() with is at i
                for (int j = 0; j < arr.size(); j++) {
                    multiplier.multiply(this.cancel(i, arr.get(j), print));
                    if (print) {
                        System.out.println();
                        System.out.println(this.toString() + "\n");
                    }
                    multiplier.simplify();
                }
            }
        }
        // At this point all nonzero rows should have distinct pivots
        for (int i = 0; i < Math.min(M, N); i++) {
            multiplier.multiply(vals[i][i]);
        }
        multiplier.simplify();
        return multiplier;
    }

    public void toRREF(boolean print) {
        String ret = "";
        this.toREF(print);
        // Go through rows and cancel with rows above them
        for (int i = M-1; i >= 0; i--) {
            if (pivots[i] < N) {
                for (int j = 0; j < i; j++) {
                    if (vals[j][pivots[i]] != 0) {
                        this.cancel(i, j, print);
                        if (print) {
                            System.out.println();
                            System.out.println(this.toString() + "\n");
                        }
                    }
                }
            }
        }
    }

    // Turns Row_i into the projection of Row_i onto Row_j, should usually only be used when i > j
    public void project(int i, int j) {
        Vector vi = this.getRow(i);
        Vector vj = this.getRow(j);
        Vector v = Vector.add(Vector.mult(vi,Vector.dot(vj,vj)), Vector.mult(vj,-Vector.dot(vi,vj)));
        setRow(v, i);
        descale(i);
    }

    // Set a row of the matrix to the vector
    public void setRow(Vector v, int i) {
        for (int j = 0; j < N; j++) {
            this.setValue(v.vals[j], i, j);
        }
    }

    // Turns a row of the matrix to the vector
    public Vector getRow(int i) {
        Vector res = new Vector(N);
        for (int j = 0; j < N; j++) {
            res.setValue(this.vals[i][j], j);
        }
        return res;
    }

    public Matrix copy() {
        Matrix ret = new Matrix(M, N);
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                ret.setValue(vals[i][j], i, j);
            }
        }
        return ret;
    }
}

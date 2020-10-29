package com.company.LinAlg;

/**
 * Created by Brandon on 10/29/2020, 1:42 PM.
 */
public class SquareMatrix extends Matrix {

//    private int[][] vals;
//    // Position of leftmost nonzero number in each row
//    private int[] pivots;
//    private int N;

    public SquareMatrix(int N) {
        super(N, N);
    }

    public Fraction computeDet() {
        Matrix copy = this.copy();
        return copy.toREF(false);
    }

}

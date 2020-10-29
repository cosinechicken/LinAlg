package com.company.LinAlg;

/**
 * Created by Brandon on 10/27/2020, 11:27 PM.
 */
public class Fraction {
    // Numerator and denominator
    private int N;
    private int D;
    public Fraction(int N, int D) {
        this.N = N;
        this.D = D;
        if (D == 0) {
            throw new IllegalArgumentException("Denominator of fraction cannot be 0");
        }
    }
    public int N() {
        return N;
    }
    public int D() {
        return D;
    }
    public void multiply(int c) {
        this.N *= c;
    }
    public void multiply(Fraction f) {
        this.N *= f.N();
        this.D *= f.D();
    }
    public void divide(int c) {
        if (c == 0) {
            throw new IllegalArgumentException("Denominator of fraction cannot be 0");
        }
        this.D *= c;
    }
    public void simplify() {
        int gcd = Math.gcd(N, D);
        N /= gcd;
        D /= gcd;
        if (D < 0) {
            N *= -1;
            D *= -1;
        }
    }
    public String toString() {
        this.simplify();
        if (D == 1) {
            return N + "";
        } else {
            return N + "/" + D;
        }
    }
}

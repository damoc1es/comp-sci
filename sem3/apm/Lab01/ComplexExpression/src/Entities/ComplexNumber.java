package Entities;

import java.util.Objects;

public class ComplexNumber {
    private int re, im;

    public int getIm() {
        return im;
    }

    public void setIm(int im) {
        this.im = im;
    }

    public int getRe() {
        return re;
    }

    public void setRe(int re) {
        this.re = re;
    }

    public ComplexNumber(int re, int im) {
        this.re = re;
        this.im = im;
    }

    @Override
    public String toString() {
        if(this.im < 0)
            return this.re + "-" + (-this.im) + "*i";
        return this.re + "+" + this.im + "*i";
    }

    public static ComplexNumber add(ComplexNumber a, ComplexNumber b) {
        return new ComplexNumber(a.re+b.re, a.im+b.im);
    }

    public static ComplexNumber subtract(ComplexNumber a, ComplexNumber b) {
        return new ComplexNumber(a.re-b.re, a.im-b.im);
    }

    public static ComplexNumber multiply(ComplexNumber n1, ComplexNumber n2) {
       int a = n1.re, b=n1.im;
       int c = n2.re, d=n2.im;
       return new ComplexNumber(a*c-b*d, a*d+b*c);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplexNumber that = (ComplexNumber) o;
        return re == that.re && im == that.im;
    }

    @Override
    public int hashCode() {
        return Objects.hash(re, im);
    }

    public static ComplexNumber divide(ComplexNumber n1, ComplexNumber n2) {
        int a = n1.re, b=n1.im;
        int c = n2.re, d=-n2.im;
        return new ComplexNumber((a*c-b*d)/(c*c+d*d), (a*d+b*c)/(c*c+d*d));
    }
}

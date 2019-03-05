package com.rs.app.util;

public class MathUtil {

    public int add(int num1, int num2) {
        return num1 + num2;
    }

    public int sub(int num1, int num2) {
        return num1 - num2;
    }

    public int mul(int num1, int num2) {
        return num1 * num2;
    }

    public int div(int num1, int num2) {
        return num1 / num2;
    }

    public int mod(int num1, int num2) {
        return num1 % num2;
    }

    public double computeCircleArea(int radius) {
        return  2 * Math.PI * radius;
    }
}

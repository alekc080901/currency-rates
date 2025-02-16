package ru.mipt.currency.server.utils;

public class MathUtils {

    public static double round(double num, int precision) {
        double scale = Math.pow(10, precision);
        return Math.round(num * scale) / scale;
    }

}

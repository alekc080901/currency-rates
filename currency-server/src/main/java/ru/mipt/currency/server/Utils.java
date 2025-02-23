package ru.mipt.currency.server;

import java.util.Random;

public class Utils {

    public static double round(double num, int precision) {
        double scale = Math.pow(10, precision);
        return Math.round(num * scale) / scale;
    }

    public static double deviate(double num, double rate) {
        Random random = new Random();
        return random.nextDouble(-rate, rate) * num + num;
    }

}

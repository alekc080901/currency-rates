package ru.mipt.currency.server.utils;

import java.util.Random;

public class RandomUtils {

    public static double deviate(double num, double rate) {
        Random random = new Random();
        return random.nextDouble(-rate, rate) * num + num;
    }
}

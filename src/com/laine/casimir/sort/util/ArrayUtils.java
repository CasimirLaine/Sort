package com.laine.casimir.sort.util;

import java.util.Random;

public final class ArrayUtils {

    private static final Random RANDOM = new Random();

    private ArrayUtils() {}

    public static int[] generateRandomArray(int size) {
        return generateRandomArray(size, Integer.MAX_VALUE);
    }

    public static int[] generateRandomArray(int size, int upperBound) {
        return generateRandomArray(size, 0, upperBound);
    }

    public static int[] generateRandomArray(int size, int lowerBound, int upperBound) {
        final int[] array = new int[size];
        for (int index = 0; index < array.length; index++) {
            array[index] = lowerBound + RANDOM.nextInt(upperBound - lowerBound);
        }
        return array;
    }

    public static int biggest(int[] array) {
        int biggest = array[0];
        for (int index = 0; index < array.length; index++) {
            final int value = array[index];
            if (value > biggest) {
                biggest = value;
            }
        }
        return biggest;
    }

    public static int lowest(int[] array) {
        int lowest = array[0];
        for (int index = 0; index < array.length; index++) {
            final int value = array[index];
            if (value < lowest) {
                lowest = value;
            }
        }
        return lowest;
    }
}

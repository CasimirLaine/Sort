package com.laine.casimir.sort.util;

import java.util.Random;

public final class ArrayUtils {

    private static final Random RANDOM = new Random();

    private ArrayUtils() {}

    public static int[] generateRandomArray(int size, int lowerBound, int upperBound) {
        final int[] array = new int[size];
        for (int index = 0; index < array.length; index++) {
            array[index] = lowerBound + RANDOM.nextInt(upperBound - lowerBound);
        }
        return array;
    }

    public static int biggest(int[] array) {
        int biggest = array[0];
        for (final int value : array) {
            if (value > biggest) {
                biggest = value;
            }
        }
        return biggest;
    }
}

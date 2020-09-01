package com.laine.casimir.sort.model;

public class GenerationSettings {

    private static final int DEFAULT_SIZE = 100;
    public static final int UPPER_BOUND = 1_000_000;
    public static final int LOWER_BOUND = 0;

    private int size = DEFAULT_SIZE;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

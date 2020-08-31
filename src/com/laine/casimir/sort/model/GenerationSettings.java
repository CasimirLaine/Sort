package com.laine.casimir.sort.model;

public class GenerationSettings {

    private static final int DEFAULT_SIZE = 100;

    private int size = DEFAULT_SIZE;
    private int upperBound = 1_000_000;
    private int lowerBound;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(int lowerBound) {
        this.lowerBound = lowerBound;
    }
}

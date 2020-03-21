package com.laine.casimir.sort.algorithm;

import java.util.function.Function;

public class Shellsort extends SortingAlgorithm {

    private int initialGapIndex = 5;
    private Function<Integer, Integer> concreteGapFunction;

    @Override
    protected void onSort() {
        for (int concreteGapIndex = initialGapIndex; concreteGapIndex > 0; concreteGapIndex--) {
            final int concreteGap = calculateConcreteGap(concreteGapIndex);
            for (int index = 0; index < length(); index++) {
                if (index == concreteGap) {
                    break;
                }
                insertionSort(index, concreteGap);
            }
        }
    }

    private void insertionSort(int startingIndex, int concreteGap) {
        for (int index = startingIndex; index < length(); index += concreteGap) {
            for (int insertionIndex = index; insertionIndex - concreteGap >= 0; insertionIndex -= concreteGap) {
                if (greater(insertionIndex - concreteGap, insertionIndex)) {
                    swap(insertionIndex, insertionIndex - concreteGap);
                } else {
                    break;
                }
            }
        }
    }

    @Override
    protected void onStop() {
    }

    public void setInitialGapIndex(int initialGapIndex) {
        this.initialGapIndex = initialGapIndex;
    }

    public void setConcreteGapFunction(Function<Integer, Integer> concreteGapFunction) {
        this.concreteGapFunction = concreteGapFunction;
    }

    private int calculateConcreteGap(int index) {
        if (concreteGapFunction != null) {
            return concreteGapFunction.apply(index);
        }
        return (int) (Math.pow(2, index) - 1);
    }
}

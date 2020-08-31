package com.laine.casimir.sort.algorithm;

public class Shellsort extends AbstractSortingAlgorithm {

    private static final int INITIAL_GAP_INDEX = 5;

    @Override
    protected void onSort() {
        for (int concreteGapIndex = INITIAL_GAP_INDEX; concreteGapIndex > 0; concreteGapIndex--) {
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

    private static int calculateConcreteGap(int index) {
        return (int) (Math.pow(2, index) - 1);
    }
}

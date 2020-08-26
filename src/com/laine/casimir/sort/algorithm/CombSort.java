package com.laine.casimir.sort.algorithm;

public class CombSort extends SortingAlgorithm {

    private double shrinkFactor = 1.3;

    @Override
    protected void onSort() {
        int sortedTail = length();
        int gap = length();
        while (true) {
            boolean isSorted = gap == 1;
            for (int index = 0; index + gap < sortedTail; index++) {
                if (greater(index, index + gap)) {
                    swap(index, index + gap);
                    isSorted = false;
                }
            }
            if (isSorted) {
                break;
            }
            if (gap == 1) {
                sortedTail--;
            }
            gap = (int) Math.max(gap / shrinkFactor, 1);
        }
    }

    public void setShrinkFactor(double shrinkFactor) {
        this.shrinkFactor = shrinkFactor;
    }
}

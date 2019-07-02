package com.laine.casimir.sort.algorithm;

public class CombSort extends SortingAlgorithm {

    private double shrinkFactor = 1.3;

    private boolean sorting;

    @Override
    protected void onSort() {
        sorting = true;
        int sortedTail = length();
        int gap = length();
        while (sorting) {
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
        if (sorting) {
            startValidationRun();
        }
        sorting = false;
    }

    @Override
    protected void onStop() {
        sorting = false;
    }

    public void setShrinkFactor(double shrinkFactor) {
        this.shrinkFactor = shrinkFactor;
    }
}

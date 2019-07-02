package com.laine.casimir.sort.algorithm;

public class BubbleSort extends SortingAlgorithm {

    private boolean sorting;

    @Override
    protected void onSort() {
        sorting = true;
        int sortedTail = length();
        while (sorting) {
            boolean isSorted = true;
            for (int index = 0; index + 1 < sortedTail; index++) {
                if (greater(index, index + 1)) {
                    swap(index, index + 1);
                    isSorted = false;
                }
            }
            if (isSorted) {
                break;
            }
            sortedTail--;
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
}

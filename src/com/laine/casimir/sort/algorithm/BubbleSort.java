package com.laine.casimir.sort.algorithm;

public class BubbleSort extends SortingAlgorithm {

    private boolean optimized;

    private boolean sorting;

    @Override
    protected void onSort() {
        sorting = true;
        final int[] selectedIndices = new int[2];
        int sortedTail = length();
        while (sorting) {
            boolean isSorted = true;
            for (int index = 0; index + 1 < sortedTail; index++) {
                selectedIndices[0] = index;
                selectedIndices[1] = index + 1;
                iterateSortListeners(sortListener -> {
                    sortListener.pointersMoved(selectedIndices);
                });
                if (greater(index, index + 1)) {
                    swap(index, index + 1);
                    isSorted = false;
                }
            }
            if (isSorted) {
                break;
            }
            if (optimized) {
                sortedTail--;
            }
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

    public void setOptimized(boolean optimized) {
        this.optimized = optimized;
    }
}

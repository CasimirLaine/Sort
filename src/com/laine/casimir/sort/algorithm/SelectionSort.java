package com.laine.casimir.sort.algorithm;

public class SelectionSort extends SortingAlgorithm {

    private boolean sorting;

    @Override
    protected void onSort() {
        sorting = true;
        final int[] selectedIndices = new int[2];
        for (int targetIndex = 0; targetIndex < length() - 1; targetIndex++) {
            if (!sorting) {
                break;
            }
            selectedIndices[0] = targetIndex;
            selectedIndices[1] = -1;
            iterateSortListeners(sortListener -> {
                sortListener.pointersMoved(selectedIndices);
            });
            int lowestIndex = targetIndex;
            Integer lowestValue = null;
            for (int index = targetIndex; index < length(); index++) {
                selectedIndices[0] = targetIndex;
                selectedIndices[1] = index;
                iterateSortListeners(sortListener -> {
                    sortListener.pointersMoved(selectedIndices);
                });
                final int value = get(index);
                if (lowestValue == null || value < lowestValue) {
                    lowestIndex = index;
                    lowestValue = value;
                }
            }
            if (lowestValue != null && lowestIndex != targetIndex) {
                swap(lowestIndex, targetIndex);
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
}

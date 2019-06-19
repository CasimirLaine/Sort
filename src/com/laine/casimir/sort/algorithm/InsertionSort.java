package com.laine.casimir.sort.algorithm;

public class InsertionSort extends SortingAlgorithm {

    private boolean sorting;

    @Override
    protected void onSort() {
        sorting = true;
        final int[] selectedIndices = new int[2];
        for (int index = 0; index < length(); index++) {
            if (!sorting) {
                break;
            }
            selectedIndices[0] = index;
            selectedIndices[1] = -1;
            iterateSortListeners(sortListener -> {
                sortListener.pointersMoved(selectedIndices);
            });
            if (index != 0) {
                for (int subIndex = index; subIndex > 0; subIndex--) {
                    selectedIndices[0] = subIndex;
                    selectedIndices[1] = subIndex - 1;
                    iterateSortListeners(sortListener -> {
                        sortListener.pointersMoved(selectedIndices);
                    });
                    if (greater(subIndex - 1, subIndex)) {
                        swap(subIndex, subIndex - 1);
                    } else {
                        break;
                    }
                }
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

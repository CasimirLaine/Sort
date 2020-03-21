package com.laine.casimir.sort.algorithm;

public class SelectionSort extends SortingAlgorithm {

    private boolean sorting;

    @Override
    protected void onSort() {
        sorting = true;
        for (int targetIndex = 0; targetIndex < length() - 1; targetIndex++) {
            if (!sorting) {
                break;
            }
            int lowestIndex = targetIndex;
            Integer lowestValue = null;
            for (int index = targetIndex; index < length(); index++) {
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
        sorting = false;
    }

    @Override
    protected void onStop() {
        sorting = false;
    }
}

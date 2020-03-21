package com.laine.casimir.sort.algorithm;

public class InsertionSort extends SortingAlgorithm {

    private boolean sorting;

    @Override
    protected void onSort() {
        sorting = true;
        for (int index = 0; index < length(); index++) {
            if (!sorting) {
                break;
            }
            if (index != 0) {
                for (int subIndex = index; subIndex > 0; subIndex--) {
                    if (greater(subIndex - 1, subIndex)) {
                        swap(subIndex, subIndex - 1);
                    } else {
                        break;
                    }
                }
            }
        }
        sorting = false;
    }

    @Override
    protected void onStop() {
        sorting = false;
    }
}

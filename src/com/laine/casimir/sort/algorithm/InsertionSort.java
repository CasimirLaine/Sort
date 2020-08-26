package com.laine.casimir.sort.algorithm;

public class InsertionSort extends SortingAlgorithm {

    @Override
    protected void onSort() {
        for (int index = 0; index < length(); index++) {
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
    }
}

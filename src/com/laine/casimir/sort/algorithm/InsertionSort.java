package com.laine.casimir.sort.algorithm;

public class InsertionSort extends AbstractSortingAlgorithm {

    @Override
    protected void onSort() {
        for (int index = 1; index < length(); index++) {
            for (int subIndex = index; subIndex > 0 && greater(subIndex - 1, subIndex); subIndex--) {
                swap(subIndex, subIndex - 1);
            }
        }
    }
}

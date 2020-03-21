package com.laine.casimir.sort.algorithm;

public class OddEvenSort extends SortingAlgorithm {

    private boolean sorting;

    @Override
    protected void onSort() {
        sorting = true;
        int offset = 0;
        while (sorting) {
            boolean isSorted = true;
            for (int index = offset; index + 1 < length(); index += 2) {
                if (greater(index, index + 1)) {
                    swap(index, index + 1);
                    isSorted = false;
                }
            }
            if (isSorted) {
                break;
            }
            offset ^= 1;
        }
        sorting = false;
    }

    @Override
    protected void onStop() {
        sorting = false;
    }
}

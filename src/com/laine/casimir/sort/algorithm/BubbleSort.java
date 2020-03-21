package com.laine.casimir.sort.algorithm;

public class BubbleSort extends SortingAlgorithm {

    @Override
    protected void onSort() {
        int sortedTail = length();
        while (true) {
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
    }

    @Override
    protected void onStop() {
    }
}

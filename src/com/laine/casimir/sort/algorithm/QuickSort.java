package com.laine.casimir.sort.algorithm;

public class QuickSort extends AbstractSortingAlgorithm {

    @Override
    protected void onSort() {
        sortRange(0, length() - 1);
    }

    private void sortRange(int minIndex, int maxIndex) {
        if (maxIndex < 0 || minIndex >= maxIndex) {
            return;
        }
        final int pivotValue = getPivotValue(minIndex, maxIndex);
        int pivotValueIndex = maxIndex;
        for (int index = minIndex + 1;
             index <= maxIndex && pivotValueIndex > index;
        ) {
            if (get(index) > pivotValue) {
                swap(index, pivotValueIndex - 1);
                swap(pivotValueIndex - 1, pivotValueIndex);
                pivotValueIndex--;
            } else {
                index++;
            }
        }
        sortRange(minIndex, pivotValueIndex - 1);
        sortRange(pivotValueIndex + 1, maxIndex);
    }

    private int getPivotValue(int minIndex, int maxIndex) {
        if (length() <= 0) {
            return 0;
        }
        final int middleIndex = (minIndex + maxIndex) / 2;
        if (greater(minIndex, middleIndex)) {
            swap(minIndex, middleIndex);
        }
        if (!greater(middleIndex, maxIndex)) {
            swap(middleIndex, maxIndex);
        }
        if (greater(minIndex, maxIndex)) {
            swap(minIndex, maxIndex);
        }
        return get(maxIndex);
    }
}

package com.laine.casimir.sort.algorithm;

public class CocktailShakerSort extends SortingAlgorithm {

    @Override
    protected void onSort() {
        int startIndex = 0;
        int endIndex = length();
        while (isSorting()) {
            for (int index = startIndex; index < endIndex - 1; index++) {
                if (!isSorting()) {
                    break;
                }
                if (greater(index, index + 1)) {
                    swap(index + 1, index);
                }
            }
            endIndex--;
            if (endIndex == 0) {
                break;
            }
            for (int index = endIndex - 1; index >= startIndex; index--) {
                if (!isSorting()) {
                    break;
                }
                if (greater(index, index + 1)) {
                    swap(index + 1, index);
                }
            }
            startIndex++;
            if (startIndex == length()) {
                break;
            }
        }
    }

    @Override
    protected void onStop() {
    }
}

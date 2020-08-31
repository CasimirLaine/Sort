package com.laine.casimir.sort.algorithm;

public class CocktailShakerSort extends AbstractSortingAlgorithm {

    @Override
    protected void onSort() {
        int endIndex = length();
        for (int startIndex = 0; startIndex != length(); startIndex++) {
            for (int index = startIndex; index < endIndex - 1; index++) {
                if (greater(index, index + 1)) {
                    swap(index + 1, index);
                }
            }
            endIndex--;
            if (endIndex == 0) {
                break;
            }
            for (int index = endIndex - 1; index >= startIndex; index--) {
                if (greater(index, index + 1)) {
                    swap(index + 1, index);
                }
            }
        }
    }
}

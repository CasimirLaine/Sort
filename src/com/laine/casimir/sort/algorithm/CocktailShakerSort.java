package com.laine.casimir.sort.algorithm;

public class CocktailShakerSort extends SortingAlgorithm {

    private boolean sorting;

    @Override
    protected void onSort() {
        sorting = true;
        int startIndex = 0;
        int endIndex = length();
        while (sorting) {
            for (int index = startIndex; index < endIndex - 1; index++) {
                if (!sorting) {
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
                if (!sorting) {
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

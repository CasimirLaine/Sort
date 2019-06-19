package com.laine.casimir.sort.algorithm;

import java.util.Random;

public class Bogosort extends SortingAlgorithm {

    private final Random random = new Random();

    private boolean sorting;

    @Override
    protected void onSort() {
        sorting = true;
        final int[] selectedIndices = new int[2];
        while (sorting) {
            boolean inOrder = true;
            for (int index = 0; index < length() - 1; index++) {
                selectedIndices[0] = index;
                selectedIndices[1] = index + 1;
                iterateSortListeners(sortListener -> {
                    sortListener.pointersMoved(selectedIndices);
                });
                if (greater(index, index + 1)) {
                    inOrder = false;
                    break;
                }
            }
            if (inOrder) {
                break;
            }
            shuffle();
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

    private void shuffle() {
        for (int index = 0; index < length(); index++) {
            final int newIndex = random.nextInt(length());
            swap(index, newIndex);
        }
    }
}

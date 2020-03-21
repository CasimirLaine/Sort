package com.laine.casimir.sort.algorithm;

public class MergeSort extends SortingAlgorithm {

    @Override
    protected void onSort() {
        final int[] sortedArray = getSortedArray();
        if (sortedArray != null) {
            for (int index = 0; index < sortedArray.length; index++) {
                set(index, sortedArray[index]);
            }
        }
    }

    private int[] getSortedArray() {
        final int[][] sizeOneArrays = new int[length()][1];
        for (int index = 0; index < sizeOneArrays.length; index++) {
            sizeOneArrays[index][0] = get(index);
        }
        return merge(sizeOneArrays);
    }

    private int[] merge(int[][] array) {
        if (array.length == 1) {
            return array[0];
        }
        {
            int originalArrayIndex = 0;
            for (int[] nestedArray : array) {
                for (int value : nestedArray) {
                    set(originalArrayIndex, value);
                    originalArrayIndex++;
                }
            }
            iterateSortListeners(sortListener -> {
                sortListener.pointersMoved(highlights.getHighlightIndices());
            });
        }
        final int[][] largerArray = createLargerArrays(array);
        assign(array, largerArray);
        return merge(largerArray);
    }

    private static int[][] createLargerArrays(int[][] smallerArrays) {
        final int[][] largerArrays = new int[Math.round((float) smallerArrays.length / 2F)][];
        for (int index = 0; index < largerArrays.length; index++) {
            int largeArraySize = smallerArrays[2 * index].length;
            if (2 * index + 1 < smallerArrays.length) {
                largeArraySize += smallerArrays[2 * index + 1].length;
            }
            largerArrays[index] = new int[largeArraySize];
        }
        return largerArrays;
    }

    private static void assign(int[][] small, int[][] large) {
        for (int index = 0; index < large.length; index++) {
            final int[] largeArray = large[index];
            final int[] smallStack1 = small[2 * index];
            final int[] smallStack2;
            if (2 * index + 1 < small.length) {
                smallStack2 = small[2 * index + 1];
            } else {
                smallStack2 = null;
            }
            int smallStackIndex1 = 0;
            int smallStackIndex2 = 0;
            for (int subIndex = 0; subIndex < largeArray.length; subIndex++) {
                final int smallerValue;
                if (smallStackIndex1 >= smallStack1.length) {
                    smallerValue = smallStack2[smallStackIndex2];
                    smallStackIndex2++;
                } else {
                    final int value1 = smallStack1[smallStackIndex1];
                    if (smallStack2 != null && smallStackIndex2 < smallStack2.length) {
                        final int value2 = smallStack2[smallStackIndex2];
                        if (value1 <= value2) {
                            smallStackIndex1++;
                            smallerValue = value1;
                        } else {
                            smallStackIndex2++;
                            smallerValue = value2;
                        }
                    } else {
                        smallStackIndex1++;
                        smallerValue = value1;
                    }
                }
                largeArray[subIndex] = smallerValue;
            }
        }
    }

    @Override
    protected void onStop() {
    }
}

package com.laine.casimir.sort.algorithm;

import java.lang.reflect.InvocationTargetException;

public enum SortType {

    BUBBLE_SORT("Bubble sort", BubbleSort.class),
    BOGOSORT("Bogosort", Bogosort.class),
    INSERTION_SORT("Insertion sort", InsertionSort.class),
    SELECTION_SORT("Selection sort", SelectionSort.class),
    MERGE_SORT("Merge sort", MergeSort.class),
    SHELLSORT("Shellsort", Shellsort.class),
    COCKTAIL_SHAKER_SORT("Cocktail shaker sort", CocktailShakerSort.class),
    ODD_EVENT_SORT("Odd-even sort", OddEvenSort.class),
    COMB_SORT("Comb sort", CombSort.class),
    QUICK_SORT("Quicksort", QuickSort.class);

    private final String title;
    private final Class<? extends AbstractSortingAlgorithm> type;

    SortType(String title, Class<? extends AbstractSortingAlgorithm> type) {
        this.title = title;
        this.type = type;
    }

    public AbstractSortingAlgorithm createSortingAlgorithm() {
        if (type != null) {
            try {
                return type.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                    | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static AbstractSortingAlgorithm createSortingAlgorithm(String key) {
        for (SortType sortType : values()) {
            if (sortType.getTitle().equals(key)) {
                return sortType.createSortingAlgorithm();
            }
        }
        return null;
    }

    public String getTitle() {
        return title;
    }
}

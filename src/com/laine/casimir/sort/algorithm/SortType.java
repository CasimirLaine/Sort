package com.laine.casimir.sort.algorithm;

public enum SortType {

    BUBBLE_SORT("Bubble sort"),
    BOGOSORT("Bogosort"),
    INSERTION_SORT("Insertion sort"),
    SELECTION_SORT("Selection sort"),
    MERGE_SORT("Merge sort"),
    SHELLSORT("Shellsort"),
    COCKTAIL_SHAKER_SORT("Cocktail shaker sort"),
    ODD_EVENT_SORT("Odd-even sort"),
    COMB_SORT("Comb sort")
    ;

    private String title;

    SortType(String title) {
        this.title = title;
    }

    public SortingAlgorithm createSortingAlgorithm() {
        switch (this) {
            case BUBBLE_SORT:
                return new BubbleSort();
            case BOGOSORT:
                return new Bogosort();
            case INSERTION_SORT:
                return new InsertionSort();
            case SELECTION_SORT:
                return new SelectionSort();
            case MERGE_SORT:
                return new MergeSort();
            case SHELLSORT:
                return new Shellsort();
            case COCKTAIL_SHAKER_SORT:
                return new CocktailShakerSort();
            case ODD_EVENT_SORT:
                return new OddEvenSort();
            case COMB_SORT:
                return new CombSort();
            default:
                return null;
        }
    }

    public static SortingAlgorithm createSortingAlgorithm(String key) {
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

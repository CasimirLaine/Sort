package com.laine.casimir.sort.algorithm;

public enum SortType {

    BUBBLE_SORT("Bubble sort"),
    BOGOSORT("Bogosort"),
    INSERTION_SORT("Insertion sort"),
    SELECTION_SORT("Selection sort"),
    ;

    private String title;

    SortType(String title) {
        this.title = title;
    }

    public SortingAlgorithm createSortingAlgorithm() {
        switch (this) {
            case BUBBLE_SORT:
                final BubbleSort bubbleSort = new BubbleSort();
                bubbleSort.setOptimized(true);
                return bubbleSort;
            case BOGOSORT:
                return new Bogosort();
            case INSERTION_SORT:
                return new InsertionSort();
            case SELECTION_SORT:
                return new SelectionSort();
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

package com.laine.casimir.sort;

public interface SortListener {

    default void pointersMoved(int[] indices) {}

    default void itemsSwapped(int fromIndex, int toIndex) {}

    default void indicesValidated(int[] indices) {}

    default void onStartSort() {}

    default void onStopSort() {}

    default void onArrayAccess() {}

    default void onComparison() {}
}

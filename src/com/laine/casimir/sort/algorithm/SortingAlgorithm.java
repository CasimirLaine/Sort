package com.laine.casimir.sort.algorithm;

import com.laine.casimir.sort.SortListener;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashSet;
import java.util.Queue;

public abstract class SortingAlgorithm implements Runnable {

    private final Queue<SortListenerQueueAction> sortListenerQueueActions = new ArrayDeque<>();
    private final Collection<SortListener> sortListeners = new HashSet<>();

    private int[] array;

    protected abstract void onSort();

    protected abstract void onStop();

    public final void sort() {
        if (!isArraySet() || length() == 0) {
            return;
        }
        iterateSortListeners(SortListener::onStartSort);
        onSort();
        iterateSortListeners(SortListener::onStopSort);
    }

    public final void stop() {
        onStop();
    }

    protected boolean greater(int firstIndex, int secondIndex) {
        final boolean greater = get(firstIndex) > get(secondIndex);
        iterateSortListeners(SortListener::onComparison);
        return greater;
    }

    protected boolean smaller(int firstIndex, int secondIndex) {
        final boolean greater = get(firstIndex) < get(secondIndex);
        iterateSortListeners(SortListener::onComparison);
        return greater;
    }

    protected int get(int index) {
        final int value = array[index];
        iterateSortListeners(SortListener::onArrayAccess);
        return value;
    }

    private void set(int index, int value) {
        array[index] = value;
    }

    protected void swap(int firstIndex, int secondIndex) {
        final int temp = get(firstIndex);
        set(firstIndex, get(secondIndex));
        set(secondIndex, temp);
        iterateSortListeners(sortListener -> {
            sortListener.itemsSwapped(firstIndex, secondIndex);
        });
    }

    protected void startValidationRun() {
        final int[] validatedIndices = new int[length()];
        for (int index = 0; index < validatedIndices.length; index++) {
            validatedIndices[index] = -1;
        }
        final int[] selectedIndices = new int[1];
        for (int index = 0; index < length(); index++) {
            selectedIndices[0] = index;
            iterateSortListeners(sortListener -> {
                sortListener.pointersMoved(selectedIndices);
            });
            if (index > 0 && greater(index - 1, index)) {
                break;
            }
            for (int validationIndex = index; validationIndex >= 0; validationIndex--) {
                validatedIndices[validationIndex] = validationIndex;
            }
            iterateSortListeners(sortListener -> {
                sortListener.indicesValidated(validatedIndices);
            });
        }
        selectedIndices[0] = -1;
        iterateSortListeners(sortListener -> {
            sortListener.pointersMoved(selectedIndices);
        });
    }

    protected int length() {
        return isArraySet() ? array.length : 0;
    }

    public boolean isArraySet() {
        return array != null;
    }

    public void setArray(int[] array) {
        this.array = array;
    }

    public void addSortListener(SortListener sortListener) {
        synchronized (this.sortListenerQueueActions) {
            this.sortListenerQueueActions.add(new SortListenerQueueAction(sortListener, false));
        }
    }

    public void removeSortListener(SortListener sortListener) {
        synchronized (this.sortListenerQueueActions) {
            this.sortListenerQueueActions.add(new SortListenerQueueAction(sortListener, true));
        }
    }

    public void removeAllSortListeners() {
        synchronized (this.sortListenerQueueActions) {
            this.sortListenerQueueActions.clear();
        }
        synchronized (this.sortListeners) {
            this.sortListeners.clear();
        }
    }

    protected void iterateSortListeners(SortListenerAction sortListenerAction) {
        synchronized (this.sortListeners) {
            synchronized (this.sortListenerQueueActions) {
                while (!this.sortListenerQueueActions.isEmpty()) {
                    final SortListenerQueueAction queueAction = this.sortListenerQueueActions.remove();
                    if (queueAction.remove) {
                        this.sortListeners.remove(queueAction.sortListener);
                    } else {
                        this.sortListeners.add(queueAction.sortListener);
                    }
                }
            }
            for (SortListener sortListener : this.sortListeners) {
                sortListenerAction.call(sortListener);
            }
        }
    }

    @Override
    public void run() {
        sort();
    }

    @FunctionalInterface
    public interface SortListenerAction {
        void call(SortListener sortListener);
    }

    public static class SortListenerQueueAction {

        private final SortListener sortListener;
        private final boolean remove;

        public SortListenerQueueAction(SortListener sortListener, boolean remove) {
            this.sortListener = sortListener;
            this.remove = remove;
        }
    }
}

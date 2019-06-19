package com.laine.casimir.sort.util;

import java.util.HashSet;
import java.util.Set;

public class ArrayHighlights {

    private final Set<Highlight> highlights = new HashSet<>();

    public void highlight(int index, int decayTime) {
        synchronized (highlights) {
            highlights.add(new Highlight(index, decayTime));
        }
    }

    public int[] getHighlightIndices() {
        synchronized (highlights) {
            highlights.removeIf(highlight -> !highlight.isActive());
        }
        return highlights
                .stream()
                .mapToInt(Highlight::getIndex)
                .toArray();
    }

    private static class Highlight {

        private final int index;
        private final long decayTime;
        private final long selectedTimeMillis;

        public Highlight(int index, long decayTime) {
            this.index = index;
            this.decayTime = decayTime;
            this.selectedTimeMillis = System.currentTimeMillis();
        }

        public boolean isActive() {
            return System.currentTimeMillis() - selectedTimeMillis < decayTime;
        }

        public int getIndex() {
            return index;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj != null && this.getClass().equals(obj.getClass())) {
                return ((Highlight) obj).index == index;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return index;
        }
    }
}

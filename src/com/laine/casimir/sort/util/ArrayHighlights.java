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
}

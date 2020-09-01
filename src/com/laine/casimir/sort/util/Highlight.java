package com.laine.casimir.sort.util;

class Highlight {

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

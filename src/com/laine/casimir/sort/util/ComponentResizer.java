package com.laine.casimir.sort.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

public class ComponentResizer {

    private final Component targetComponent;
    private final List<Component> components = new ArrayList<>();

    private final ComponentListener componentListener = new ComponentListener() {
        @Override
        public void componentResized(ComponentEvent e) {
            calculate();
        }

        @Override
        public void componentMoved(ComponentEvent e) {}

        @Override
        public void componentShown(ComponentEvent e) {}

        @Override
        public void componentHidden(ComponentEvent e) {}
    };

    private boolean widthEnabled;
    private boolean heightEnabled;

    private boolean minimum;
    private boolean preferred;
    private boolean maximum;

    public ComponentResizer(Component targetComponent) {
        this.targetComponent = targetComponent;
    }

    public ComponentResizer addComponent(Component component) {
        synchronized (components) {
            component.addComponentListener(componentListener);
            components.add(component);
        }
        calculate();
        return this;
    }

    public ComponentResizer removeComponent(Component component) {
        synchronized (components) {
            component.removeComponentListener(componentListener);
            components.remove(component);
        }
        calculate();
        return this;
    }

    public ComponentResizer setWidthEnabled(boolean widthEnabled) {
        this.widthEnabled = widthEnabled;
        calculate();
        return this;
    }

    public ComponentResizer setHeightEnabled(boolean heightEnabled) {
        this.heightEnabled = heightEnabled;
        calculate();
        return this;
    }

    public ComponentResizer setMinimum(boolean minimum) {
        this.minimum = minimum;
        calculate();
        return this;
    }

    public ComponentResizer setPreferred(boolean preferred) {
        this.preferred = preferred;
        calculate();
        return this;
    }

    public ComponentResizer setMaximum(boolean maximum) {
        this.maximum = maximum;
        calculate();
        return this;
    }

    private void calculate() {
        int newWidth = 0;
        int newHeight = 0;
        for (int index = 0; index < components.size(); index++) {
            final Component component = components.get(index);
            newWidth += component.getWidth();
            newHeight += component.getHeight();
        }
        assign(newWidth, newHeight);
    }

    private void assign(int width, int height) {
        final Dimension dimension = new Dimension();
        if (widthEnabled) {
            dimension.width = width;
        }
        if (heightEnabled) {
            dimension.height = height;
        }
        if (minimum) {
            targetComponent.setMinimumSize(dimension);
        }
        if (preferred) {
            targetComponent.setPreferredSize(dimension);
        }
        if (maximum) {
            targetComponent.setMaximumSize(dimension);
        }
    }
}

package com.laine.casimir.sort.ui;

import com.laine.casimir.sort.util.ArrayUtils;
import com.laine.casimir.sort.util.MathUtils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

public class SortingPanel extends JComponent {

    private final transient Object RENDER_LOCK = new Object();

    private final Stroke stroke = new BasicStroke(1);

    private int[] array;
    private int biggest;

    private int[] selectedIndices;
    private int[] validatedIndices;

    public SortingPanel() {
        super();
        init();
    }

    private void init() {
        setMinimumSize(new Dimension(0, 0));
        setIgnoreRepaint(false);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        synchronized (RENDER_LOCK) {
            final Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(stroke);
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            if (array == null || array.length == 0) {
                RENDER_LOCK.notifyAll();
                return;
            }
            g2d.setColor(Color.WHITE);
            final double skipModulo = MathUtils.getSkipModulo(array.length, getWidth());
            final double extraWidthModulo = MathUtils.getExtraMod(array.length, getWidth());
            for (int index = 0, x = 0; index < array.length; index++) {
                if (skipModulo != 0 && index % skipModulo < 1) {
                    continue;
                }
                int width = Math.max(getWidth() / array.length, 1);
                if (skipModulo == 0 && extraWidthModulo != 0 && index % extraWidthModulo < 1) {
                    width++;
                }
                final int height;
                final int value = array[index];
                if (biggest == 0) {
                    height = (int) ((double) value / 1.0 * getHeight());
                } else {
                    height = (int) ((double) value / (double) biggest * getHeight());
                }
                final boolean selected = isSelected(index);
                final boolean validated = isValidated(index);
                if (validated) {
                    g2d.setColor(Color.GREEN);
                } else {
                    if (selected) {
                        g2d.setColor(Color.RED);
                    } else {
                        g2d.setColor(Color.BLUE);
                    }
                }
                g2d.fillRect(x, getHeight() - height, width, height);
                if (validated) {
                    g2d.setColor(Color.GREEN);
                } else {
                    if (selected) {
                        g2d.setColor(Color.RED);
                    } else {
                        g2d.setColor(Color.WHITE);
                    }
                }
                g2d.drawRect(x, getHeight() - height, width, height);
                x += width;
            }
            RENDER_LOCK.notifyAll();
        }
    }

    public void repaintAndWait() {
        synchronized (RENDER_LOCK) {
            repaint();
            if (!SwingUtilities.isEventDispatchThread()) {
                try {
                    RENDER_LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setArray(int[] array) {
        setSelectedIndices(null);
        setValidatedIndices(null);
        this.array = array;
        updateBiggest();
        repaintAndWait();
    }

    public void setSelectedIndices(int[] selectedIndices) {
        this.selectedIndices = selectedIndices;
        if (this.selectedIndices != null) {
            Arrays.sort(this.selectedIndices);
        }
    }

    public void setValidatedIndices(int[] validatedIndices) {
        this.validatedIndices = validatedIndices;
        if (this.validatedIndices != null) {
            Arrays.sort(this.validatedIndices);
        }
    }

    private void updateBiggest() {
        if (array == null || array.length == 0) {
            biggest = 0;
        } else {
            biggest = ArrayUtils.biggest(array);
        }
    }

    private boolean isSelected(int index) {
        if (this.selectedIndices != null) {
            return Arrays.binarySearch(this.selectedIndices, index) >= 0;
        }
        return false;
    }

    private boolean isValidated(int index) {
        if (this.validatedIndices != null) {
            return Arrays.binarySearch(this.validatedIndices, index) >= 0;
        }
        return false;
    }
}

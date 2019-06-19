package com.laine.casimir.sort.ui;

import com.laine.casimir.sort.SortListener;
import com.laine.casimir.sort.algorithm.SortingAlgorithm;
import com.laine.casimir.sort.sound.SortingSound;
import com.laine.casimir.sort.util.ArrayUtils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

public class SortingPanel extends JComponent implements SortListener {

    private final transient Object LOCK = new Object();

    private final Stroke stroke = new BasicStroke(1);

    private final SortingSound sortingSound = new SortingSound();
    private boolean soundEnabled = true;

    private int[] array;
    private int biggest;
    private SortingAlgorithm sortingAlgorithm;
    private Thread sortingThread;

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
        final Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(stroke);
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        synchronized (LOCK) {
            if (array == null || array.length == 0) {
                LOCK.notifyAll();
                return;
            }
            g2d.setColor(Color.WHITE);
            final double mod;
            {
                final double maxWidthOfColumn = (double) getWidth() / (double) array.length;
                final double surplusWidth = maxWidthOfColumn - Math.floor(maxWidthOfColumn);
                final double amountOfExtraPixelColumns = Math.floor(surplusWidth * array.length);
                if (amountOfExtraPixelColumns != 0) {
                    mod = ((double) array.length / amountOfExtraPixelColumns);
                } else {
                    mod = 0;
                }
            }
            for (int index = 0, x = 0; index < array.length; index++) {
                int width = Math.max(getWidth() / array.length, 1);
                if (mod != 0 && index % mod < 1) {
                    width++;
                }
                final int value = array[index];
                final int height = (int) ((double) value / (double) biggest * getHeight());
                boolean isSelected = false;
                if (selectedIndices != null) {
                    for (int selectedIndex : selectedIndices) {
                        if (index == selectedIndex) {
                            isSelected = true;
                        }
                    }
                }
                boolean validated = false;
                if (validatedIndices != null) {
                    for (int validatedIndex : validatedIndices) {
                        if (index == validatedIndex) {
                            validated = true;
                        }
                    }
                }
                if (isSelected) {
                    g2d.setColor(Color.RED);
                } else {
                    if (validated) {
                        g2d.setColor(Color.GREEN);
                    } else {
                        g2d.setColor(Color.BLUE);
                    }
                }
                g2d.fillRect(x, getHeight() - height, width, height);
                if (isSelected) {
                    g2d.setColor(Color.RED);
                } else {
                    if (validated) {
                        g2d.setColor(Color.GREEN);
                    } else {
                        g2d.setColor(Color.WHITE);
                    }
                }
                g2d.drawRect(x, getHeight() - height, width, height);
                x += width;
            }
            LOCK.notifyAll();
        }
    }


    @Override
    public void pointersMoved(int[] indices) {
        sortingSound.playSound();
        selectedIndices = indices.clone();
        repaint();
        if (isVisible() && isShowing() && isDisplayable()) {
            synchronized (LOCK) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void itemsSwapped(int fromIndex, int toIndex) {
    }

    @Override
    public void indicesValidated(int[] indices) {
        this.validatedIndices = indices.clone();
        repaint();
        if (isVisible() && isShowing() && isDisplayable()) {
            synchronized (LOCK) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onStartSort() {
        if (soundEnabled) {
            sortingSound.createSound();
        }
        this.selectedIndices = null;
        this.validatedIndices = null;
        repaint();
    }

    @Override
    public void onStopSort() {
        stopSort();
        repaint();
    }

    public void setArray(int[] array) {
        if (sortingThread != null && sortingThread.isAlive()) {
            return;
        }
        this.selectedIndices = null;
        this.validatedIndices = null;
        this.array = array;
        updateBiggest();
        repaint();
    }

    public void sort(SortingAlgorithm sortingAlgorithm) {
        this.sortingAlgorithm = sortingAlgorithm;
        sortingAlgorithm.addSortListener(this);
        sortingAlgorithm.setArray(array);
        if (sortingThread == null || !sortingThread.isAlive()) {
            sortingThread = new Thread(sortingAlgorithm);
            sortingThread.start();
        }
        repaint();
    }

    public void stopSort() {
        if (sortingAlgorithm != null) {
            sortingAlgorithm.stop();
            sortingAlgorithm.removeSortListener(this);
        }
        sortingSound.destroySound();
        sortingThread = null;
        sortingAlgorithm = null;
    }

    public void setSoundEnabled(boolean soundEnabled) {
        this.soundEnabled = soundEnabled;
        if (soundEnabled) {
            sortingSound.createSound();
        } else {
            sortingSound.destroySound();
        }
    }

    private void updateBiggest() {
        if (array == null || array.length == 0) {
            biggest = 0;
        } else {
            biggest = ArrayUtils.biggest(array);
        }
    }
}

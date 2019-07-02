package com.laine.casimir.sort.ui;

import com.laine.casimir.sort.SortListener;
import com.laine.casimir.sort.SortingController;
import com.laine.casimir.sort.algorithm.SortType;
import com.laine.casimir.sort.algorithm.SortingAlgorithm;
import com.laine.casimir.sort.model.GenerationSettings;
import com.laine.casimir.sort.util.ArrayUtils;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

public class MainSwing {

    private static final int DEFAULT_WINDOW_WIDTH = 500;
    private static final int DEFAULT_WINDOW_HEIGHT = 500;

    private final GenerationSettings generationSettings = new GenerationSettings();

    private final JFrame frame = new JFrame();

    private final InfoPanel infoPanel = new InfoPanel();
    private final ControlPanel controlPanel = new ControlPanel(generationSettings);
    private final SortingPanel sortingPanel = new SortingPanel();

    private final SortingController sortingController = new SortingController(sortingPanel);

    public MainSwing() {
        frame.setTitle("Sort");
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT));
        frame.setMinimumSize(new Dimension(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT));
        controlPanel.getRefreshDataButton().addActionListener(e -> refreshData());
        controlPanel.getStartSortButton().addActionListener(e -> startSort());
        controlPanel.getStopSortButton().addActionListener(e -> stopSort());
        controlPanel.getSoundButton().addActionListener(e -> {
            sortingController.setSoundEnabled(controlPanel.getSoundButton().isSelected());
        });
        frame.getContentPane().add(infoPanel, BorderLayout.NORTH);
        frame.getContentPane().add(controlPanel, BorderLayout.SOUTH);
        frame.getContentPane().add(sortingPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    public void refreshData() {
        final int[] array = ArrayUtils.generateRandomArray(
                generationSettings.getSize(),
                generationSettings.getLowerBound(),
                generationSettings.getUpperBound()
        );
        infoPanel.setArraySize(array.length);
        if (array.length == 0) {
            infoPanel.clearBiggest();
            infoPanel.clearLowest();
        } else {
            infoPanel.setBiggest(ArrayUtils.biggest(array));
            infoPanel.setLowest(ArrayUtils.lowest(array));
        }
        sortingController.setArray(array);
    }

    public void startSort() {
        infoPanel.setComparisons(0);
        infoPanel.setArrayAccesses(0);
        infoPanel.setSwaps(0);
        infoPanel.clearSwapLog();
        final SortingAlgorithm sortingAlgorithm = SortType.createSortingAlgorithm(
                controlPanel.getAlgorithmComboBox().getSelectedItem().toString());
        sortingAlgorithm.addSortListener(new SortListener() {

            private int comparisonCount;
            private int arrayAccessCount;
            private int swapCount;

            @Override
            public void itemsSwapped(int fromIndex, int toIndex) {
                infoPanel.appendLineToSwapLog(fromIndex + " <-> " + toIndex);
                swapCount++;
                infoPanel.setSwaps(swapCount);
            }

            @Override
            public void onArrayAccess() {
                arrayAccessCount++;
                infoPanel.setArrayAccesses(arrayAccessCount);
            }

            @Override
            public void onComparison() {
                comparisonCount++;
                infoPanel.setComparisons(comparisonCount);
            }

            @Override
            public void onStopSort() {
                sortingAlgorithm.removeSortListener(this);
            }
        });
        sortingController.start(sortingAlgorithm);
    }

    public void stopSort() {
        sortingController.stop();
    }

    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "True");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(MainSwing::new);
    }
}

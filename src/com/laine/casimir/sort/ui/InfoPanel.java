package com.laine.casimir.sort.ui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class InfoPanel extends JPanel {

    private final JLabel sizeLabel = new JLabel();

    private final JLabel arrayAccessesLabel = new JLabel("Array accesses: -");
    private final JLabel comparisonsLabel = new JLabel("Comparisons: -");
    private final JLabel swapsLabel = new JLabel("Swaps: -");

    public InfoPanel() {
        setPreferredSize(new Dimension(0, 100));
        setMinimumSize(new Dimension(0, 100));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        {
            final JPanel arrayInfoPanel = new JPanel();
            arrayInfoPanel.setLayout(new BoxLayout(arrayInfoPanel, BoxLayout.Y_AXIS));
            arrayInfoPanel.setBorder(new TitledBorder("Array info"));
            arrayInfoPanel.add(sizeLabel);
            arrayInfoPanel.add(Box.createHorizontalStrut(0));
            arrayInfoPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 0));
            add(arrayInfoPanel);
        }
        {
            final JPanel algorithmInfoPanel = new JPanel();
            algorithmInfoPanel.setLayout(new BoxLayout(algorithmInfoPanel, BoxLayout.Y_AXIS));
            algorithmInfoPanel.setBorder(new TitledBorder("Algorithm info"));
            algorithmInfoPanel.add(arrayAccessesLabel);
            algorithmInfoPanel.add(comparisonsLabel);
            algorithmInfoPanel.add(swapsLabel);
            algorithmInfoPanel.add(Box.createHorizontalStrut(0));
            algorithmInfoPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 0));
            add(algorithmInfoPanel);
        }
        clearArraySize();
    }

    public void clearArraySize() {
        sizeLabel.setText("Size: -");
    }

    public void setArraySize(int size) {
        sizeLabel.setText("Size: " + size);
    }

    public void setArrayAccesses(int arrayAccesses) {
        arrayAccessesLabel.setText("Array accesses: " + arrayAccesses);
    }

    public void setComparisons(int comparisons) {
        comparisonsLabel.setText("Comparisons: " + comparisons);
    }

    public void setSwaps(int swaps) {
        swapsLabel.setText("Swaps: " + swaps);
    }
}

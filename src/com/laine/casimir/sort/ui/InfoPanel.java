package com.laine.casimir.sort.ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class InfoPanel extends JPanel {

    private final JLabel sizeLabel = new JLabel();
    private final JLabel biggestLabel = new JLabel();
    private final JLabel lowestLabel = new JLabel();

    private final JLabel arrayAccessesLabel = new JLabel("Array accesses: -");
    private final JLabel comparisonsLabel = new JLabel("Comparisons: -");
    private final JLabel swapsLabel = new JLabel("Swaps: -");

    private final JTextArea swapLog = new JTextArea();
    private final JScrollPane swapLogScrollPane = new JScrollPane(swapLog);

    public InfoPanel() {
        setPreferredSize(new Dimension(0, 100));
        setMinimumSize(new Dimension(0, 100));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        {
            final JPanel arrayInfoPanel = new JPanel();
            arrayInfoPanel.setLayout(new BoxLayout(arrayInfoPanel, BoxLayout.Y_AXIS));
            arrayInfoPanel.setBorder(new TitledBorder("Array info"));
            arrayInfoPanel.add(sizeLabel);
            arrayInfoPanel.add(biggestLabel);
            arrayInfoPanel.add(lowestLabel);
            arrayInfoPanel.add(Box.createHorizontalStrut(0));
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
            add(algorithmInfoPanel);
        }
        {
            swapLog.setLineWrap(true);
            swapLog.setWrapStyleWord(true);
            swapLog.setEditable(false);
            swapLog.setBackground(Color.BLACK);
            swapLog.setForeground(Color.WHITE);
            final JPanel swapLogPanel = new JPanel();
            swapLogPanel.setLayout(new BoxLayout(swapLogPanel, BoxLayout.Y_AXIS));
            swapLogPanel.setBorder(new TitledBorder("Swap log"));
            swapLogPanel.add(swapLogScrollPane);
            add(swapLogPanel);
        }
        clearArraySize();
        clearBiggest();
        clearLowest();
    }

    public void clearArraySize() {
        sizeLabel.setText("Size: -");
    }

    public void setArraySize(int size) {
        sizeLabel.setText("Size: " + size);
    }

    public void clearBiggest() {
        biggestLabel.setText("Biggest: -");
    }

    public void setBiggest(int biggest) {
        biggestLabel.setText("Biggest: " + biggest);
    }

    public void clearLowest() {
        lowestLabel.setText("Lowest: -");
    }

    public void setLowest(int lowest) {
        lowestLabel.setText("Lowest: " + lowest);
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

    public void appendLineToSwapLog(String text) {
        if (!swapLog.getText().isBlank()) {
            swapLog.append("\n");
        }
        swapLog.append(text);
        swapLog.setCaretPosition(swapLog.getDocument().getLength());
    }

    public void clearSwapLog() {
        swapLog.setText("");
    }
}

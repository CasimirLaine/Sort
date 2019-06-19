package com.laine.casimir.sort.ui;

import com.laine.casimir.sort.algorithm.SortType;
import com.laine.casimir.sort.model.GenerationSettings;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.beans.PropertyChangeListener;
import java.text.Format;
import java.text.NumberFormat;
import java.util.function.Supplier;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.plaf.metal.MetalToggleButtonUI;

public class ControlPanel extends JPanel {

    private final JFormattedTextField arraySizeTextField = new JFormattedTextField(((Supplier<Format>) () -> {
        final NumberFormat numberFormat = NumberFormat.getIntegerInstance();
        numberFormat.setGroupingUsed(false);
        numberFormat.setParseIntegerOnly(true);
        return numberFormat;
    }).get());

    private final JButton refreshDataButton = new JButton("Refresh data");
    private final JComboBox<String> algorithmComboBox = new JComboBox<>();
    private final JButton startSortButton = new JButton("Start sort");
    private final JButton stopSortButton = new JButton("Stop sort");
    private final JToggleButton soundButton = new JToggleButton("Sound", true);

    private GenerationSettings generationSettings;

    public ControlPanel(GenerationSettings generationSettings) {
        this.generationSettings = generationSettings;
        setPreferredSize(new Dimension(0, 100));
        setMinimumSize(new Dimension(0, 100));
        arraySizeTextField.setValue(generationSettings.getSize());
        arraySizeTextField.addPropertyChangeListener(propertyChangeListener);
        {
            final SortType[] sortTypes = SortType.values();
            final String[] algorithmNames = new String[sortTypes.length];
            for (int index = 0; index < algorithmNames.length; index++) {
                algorithmNames[index] = sortTypes[index].getTitle();
            }
            algorithmComboBox.setModel(new DefaultComboBoxModel<>(algorithmNames));
        }
        final GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        {
            final JLabel arraySizeLabel = new JLabel("Array size:");
            final GridBagConstraints constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.BOTH;
            constraints.gridy = 0;
            constraints.weightx = 1;
            constraints.weighty = 1;
            add(arraySizeLabel, constraints);
            add(arraySizeTextField, constraints);
        }
        {
            final GridBagConstraints constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.BOTH;
            constraints.gridy = 0;
            constraints.weightx = 1;
            constraints.weighty = 1;
            refreshDataButton.setFocusPainted(false);
            add(refreshDataButton, constraints);
        }
        {
            final GridBagConstraints constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.BOTH;
            constraints.gridy = 0;
            constraints.weightx = 1;
            constraints.weighty = 1;
            soundButton.setFocusPainted(false);
            add(soundButton, constraints);
        }
        {
            final GridBagConstraints constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.BOTH;
            constraints.gridy = 1;
            constraints.weightx = 1;
            constraints.weighty = 1;
            add(algorithmComboBox, constraints);
        }
        {
            final GridBagConstraints constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.BOTH;
            constraints.gridy = 1;
            constraints.weightx = 1;
            constraints.weighty = 1;
            startSortButton.setFocusPainted(false);
            add(startSortButton, constraints);
        }
        {
            final GridBagConstraints constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.BOTH;
            constraints.gridy = 1;
            constraints.weightx = 1;
            constraints.weighty = 1;
            stopSortButton.setFocusPainted(false);
            add(stopSortButton, constraints);
        }
        soundButton.setUI(new MetalToggleButtonUI() {
            @Override
            protected Color getSelectColor() {
                return Color.GREEN;
            }
        });
        soundButton.setBackground(Color.RED);
    }

    public JButton getRefreshDataButton() {
        return refreshDataButton;
    }

    public JComboBox<String> getAlgorithmComboBox() {
        return algorithmComboBox;
    }

    public JButton getStartSortButton() {
        return startSortButton;
    }

    public JButton getStopSortButton() {
        return stopSortButton;
    }

    public JToggleButton getSoundButton() {
        return soundButton;
    }

    private final PropertyChangeListener propertyChangeListener = evt -> {
        if (evt.getSource() == arraySizeTextField) {
            generationSettings.setSize(((Number) arraySizeTextField.getValue()).intValue());
        }
    };
}

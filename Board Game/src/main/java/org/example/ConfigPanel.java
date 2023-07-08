package org.example;

import javax.sound.sampled.Line;
import javax.swing.*;

public class ConfigPanel extends JPanel {
    final MainFrame frame;
    JLabel dotsLabel, linesLabel;
    JSpinner dotsSpinner;
    JComboBox linesCombo;
    JButton createButton;
    public ConfigPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }
    private void init() {
        //create the label and the spinner
        dotsLabel = new JLabel("Number of dots:");
        dotsSpinner = new JSpinner(new SpinnerNumberModel(6, 3, 100, 1));
        createButton = new JButton("New Game");
        linesLabel = new JLabel("Line probability:");
        linesCombo = new JComboBox(new Double[]{0.5, 0.6, 0.7, 0.8, 0.9, 1.0});
        //create the rest of the components

        add(dotsLabel); //JPanel uses FlowLayout by default
        add(dotsSpinner);
        add(linesLabel);
        add(linesCombo);
        add(createButton);

        createButton.addActionListener(e->{
            frame.canvas.clearBlackLines();
            frame.canvas.resetLines();
            // get the selected line type
            double selectedLineType = (Double) linesCombo.getSelectedItem();

            // create the board with the specified number of dots and line type

            frame.canvas.createBoard();
        });
    }
}

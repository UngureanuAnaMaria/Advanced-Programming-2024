package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;

public class ConfigPanel extends JPanel implements Serializable {
    final MainFrame frame;
    JLabel label;
    JSpinner spinnerRows;
    JSpinner spinnerCols;
    JButton newGameBtn;
    public ConfigPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }
    private void init() {
        //create the label and the spinner
        label = new JLabel("Grid size:");
        spinnerRows = new JSpinner(new SpinnerNumberModel(10,2, 100, 1));
        spinnerCols = new JSpinner(new SpinnerNumberModel(10, 2, 100, 1));
        newGameBtn = new JButton("Create");

        newGameBtn.addActionListener(this::createGame);

        add(label); //JPanel uses FlowLayout by default
        add(spinnerRows);
        add(spinnerCols);
        add(newGameBtn);
    }

    private void createGame(ActionEvent e) {
        int rows = (int) spinnerRows.getValue();
        int cols = (int) spinnerCols.getValue();

        DrawingPanel canvas = frame.getCanvas();
        remove(canvas);

        DrawingPanel canva = new DrawingPanel(frame, rows, cols);

        add(canva, BorderLayout.CENTER);

        revalidate();
        repaint();

    }

    public int getRows() {
        return (int) spinnerRows.getValue();
    }

    public int getCols() {
        return (int) spinnerCols.getValue();
    }
}

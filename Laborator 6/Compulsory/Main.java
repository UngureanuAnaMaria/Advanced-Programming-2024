package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.BorderLayout.CENTER;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Positional Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Configuration panel
        JPanel configPanel = new JPanel();
        JLabel gridLabel = new JLabel("Grid Size:");
        //JTextField gridRowsField = new JTextField(5);
        JSpinner rowSpinner = new JSpinner(new SpinnerNumberModel(10, 2, 100, 1));
        //JTextField gridColsField = new JTextField(5);
        JSpinner colSpinner = new JSpinner(new SpinnerNumberModel(10, 2, 100, 1));
        JButton newGameBtn = new JButton("Create");
        configPanel.add(gridLabel);
        //configPanel.add(gridRowsField);
        configPanel.add(rowSpinner);
       // configPanel.add(gridColsField);
        configPanel.add(colSpinner);
        configPanel.add(newGameBtn);

        // Create a panel with buttons
        JPanel panel = new JPanel();
        JButton exitBtn = new JButton("Exit");
        JButton saveBtn = new JButton("Save");
        JButton loadBtn = new JButton("Load");
        panel.add(exitBtn);
        panel.add(saveBtn);
        panel.add(loadBtn);



        // Add action to the buttons
        newGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel canva = new JPanel();

                int rows = (Integer) rowSpinner.getValue();
                int cols = (Integer) colSpinner.getValue();
                int canvasWidth = 400, canvasHeight = 400;
                int stoneSize = 20;
                int padX = stoneSize + 10;
                int padY = stoneSize + 10;
                int cellWidth = (canvasWidth - 2 * padX) / (cols - 1);
                int cellHeight = (canvasHeight - 2 * padY) / (rows - 1);
                int boardWidth = (cols - 1) * cellWidth;
                int boardHeight = (rows - 1) * cellHeight;

                Graphics graphics = frame.getGraphics();
                Graphics2D g = (Graphics2D) graphics;
                g.setColor(Color.WHITE);
                g.fillRect(200, 100, canvasWidth, canvasHeight);
                g.setColor(Color.DARK_GRAY);

                //horizontal lines
                for (int row = 0; row < rows; row++) {
                    int x1 = padX + 200;
                    int y1 = padY + row * cellHeight + 100;
                    int x2 = padX + boardWidth + 200;
                    int y2 = y1;
                    g.drawLine(x1, y1, x2, y2);
                }

                //vertical lines
                for (int col = 0; col < cols; col++) {
                    int x1 = padX + col * cellWidth + 200;
                    int y1 = padY + 100;
                    int x2 = x1;
                    int y2 = padY + boardHeight + 100;
                    g.drawLine(x1, y1, x2, y2);
                }

                //intersections
                for (int row = 0; row < rows; row++) {
                    for (int col = 0; col < cols; col++) {
                        int x = padX + col * cellWidth + 200;
                        int y = padY + row * cellHeight + 100;
                        g.setColor(Color.LIGHT_GRAY);
                        if(rows >= 30 || cols >= 30) stoneSize = 10;
                        if(rows >= 60 || cols >= 60) stoneSize = 5;
                        g.drawOval(x - stoneSize / 2, y - stoneSize / 2, stoneSize, stoneSize);
                    }
                }
                frame.add(canva, CENTER);
            }
        });

        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Exit");
                frame.dispose();
            }
        });

        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Saved!");
            }
        });

        loadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Load!");
            }
        });


        // Set layout for the main frame
        frame.setLayout(new BorderLayout());
        frame.add(configPanel, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}

package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class ControlPanel extends JPanel implements Serializable {
    final MainFrame frame;
    JButton exitBtn = new JButton("Exit");
    JButton saveBtn = new JButton("Save");
    JButton loadBtn = new JButton("Load");
    public ControlPanel(MainFrame frame) {
        this.frame = frame; init();
    }
    private void init() {
        //change the default layout manager (just for fun)
        setLayout(new GridLayout(1, 4));
        //add all buttons
        add(exitBtn);
        add(saveBtn);
        add(loadBtn);

        //configure listeners for all buttons
        exitBtn.addActionListener(this::exitGame);
        saveBtn.addActionListener(this::saveGame);
        loadBtn.addActionListener(this::loadGame);
    }

    private void loadGame(ActionEvent e) {
        //JOptionPane.showMessageDialog(frame, "Game loaded successfully!");
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            MainFrame gameState = loadGameState(selectedFile);
            if (gameState != null) {
                JOptionPane.showMessageDialog(frame, "Game loaded successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to load game state!");
            }
        }
    }

    private MainFrame loadGameState(File file) {
        try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
            Object gameState = inputStream.readObject(); // Deserialize the game state
            if (gameState instanceof MainFrame) {
                return (MainFrame) gameState;
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid game state!");
                return null;
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Failed to load game state!");
            return null;
        }

    }


    private void saveGame(ActionEvent e) {
       // JOptionPane.showMessageDialog(frame, "Game saved successfully!");
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            boolean success = saveGameState(selectedFile);
            if (success) {
                JOptionPane.showMessageDialog(frame, "Game saved successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to save game state!");
            }
        }
    }

    private boolean saveGameState(File file) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private void exitGame(ActionEvent e) {
        frame.dispose();
    }
}

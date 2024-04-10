package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import static javax.swing.SwingConstants.*;


public class MainFrame extends JFrame implements Serializable {
    ConfigPanel configPanel;
    ControlPanel controlPanel;
    DrawingPanel canvas;

    public MainFrame() {
        super("Positional Game");
        init();
    }

    private void init() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //create the components
        configPanel = new ConfigPanel(this);
        controlPanel = new ControlPanel(this);
        canvas = new DrawingPanel(this);

        //arrange the components in the container (frame)
        //JFrame uses a BorderLayout by
        add(configPanel, BorderLayout.NORTH);
        add(canvas, CENTER); //this is BorderLayout.CENTER
        add(controlPanel, BorderLayout.SOUTH);
        //invoke the layout manager
        pack();
    }

    public DrawingPanel getCanvas() {
        return canvas;
    }

    public void saveAsImage(String fileName) {
        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        this.paint(g2d);
        try {
            File file = new File(fileName);
            ImageIO.write(image, "png", file);
            System.out.println("Image saved as " + fileName);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}

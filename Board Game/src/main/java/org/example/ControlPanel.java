package org.example;


import javax.imageio.ImageIO;
import javax.sound.sampled.Line;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ControlPanel extends JPanel {
    final MainFrame frame;
    JButton exitBtn = new JButton("Exit");
    JButton loadBtn = new JButton("Load");
    JButton saveBtn = new JButton("Save");
    JButton resetBtn = new JButton("Reset");

    //create all buttons (Load, Exit, etc.)
    public ControlPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        //change the default layout manager (just for fun)
        //setLayout(new GridLayout(1, 4));
        setLayout(new FlowLayout());
        add(exitBtn);
        add(saveBtn);
        add(resetBtn);
        add(loadBtn);
        //configure listeners for all buttons
        exitBtn.addActionListener(this::exitGame);
        resetBtn.addActionListener(this::resetGame);
        saveBtn.addActionListener(this::saveGame);
        loadBtn.addActionListener(this::loadGame);
    }

    private void exitGame(ActionEvent e) {
        frame.dispose();
    }

    private void resetGame(ActionEvent e) {
        frame.canvas.resetLines();
    }

    private void saveGame(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
        int userSelection = fileChooser.showSaveDialog(frame);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            BufferedImage img = new BufferedImage(frame.canvas.getWidth(), frame.canvas.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics g = img.getGraphics();
            frame.canvas.paint(g);
            fileToSave = new File(fileToSave.getAbsolutePath() + ".png");
            try {
                ImageIO.write(img, "png", fileToSave);
                System.out.println("Game saved");
            } catch (IOException ex) {
                System.out.println("Error: " + ex);
            }
        }
    }

    public void loadGame(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to load");
        int userSelection = fileChooser.showOpenDialog(frame);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToLoad = fileChooser.getSelectedFile();
            try {
                BufferedImage img = ImageIO.read(fileToLoad);
                frame.canvas.setImage(img);
                frame.canvas.repaint();
                frame.canvas.resetLines();
                System.out.println("Game loaded");
            } catch (IOException ex) {
                System.out.println("Error: "+ex);
            }
        }
    }
}

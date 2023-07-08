package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;


public class DrawingPanel extends JPanel {
    final MainFrame frame;
    final static int W = 800, H = 600;
    private int numVertices;
    private double edgeProbability;
    private int[] x, y;
    BufferedImage image;

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    Graphics2D graphics;
    private int currentPlayer = 1;
    final Set<Line2D> selectedLines = new HashSet<>();
    final Set<Line2D> blackLines = new HashSet<>();
    final Set<Line2D> redLines = new HashSet<>();
    final Set<Line2D> blueLines = new HashSet<>();

    public DrawingPanel(MainFrame frame) {
        this.frame = frame;
        createOffscreenImage();
        initPanel();
        createBoard();
    }

    private void initPanel() {
        setPreferredSize(new Dimension(W, H));
        setBorder(BorderFactory.createEtchedBorder());
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                Point mousePoint = new Point(mouseX, mouseY);
                double threshold = 10.0;
                for (int i = 0; i < numVertices; i++) {
                    for (int j = i + 1; j < numVertices; j++) {
                        Line2D line = new Line2D.Double(x[i], y[i], x[j], y[j]);
                        double distance = line.ptSegDist(mousePoint);
                        if (distance < threshold && !isLineSelected(i, j) && isBlackLine(line)) {

                            if (currentPlayer == 1) {
                                graphics.setColor(Color.RED);
                                redLines.add(line);
                            } else {
                                graphics.setColor(Color.BLUE);
                                blueLines.add(line);
                            }
                            graphics.setStroke(new BasicStroke(5));
                            graphics.drawLine(x[i], y[i], x[j], y[j]);
                            selectLine(line);

                            Game game = new Game(redLines, blueLines, blackLines);
                            if (game.getWinner() != -1) {
                                int winner = game.getWinner();
                                if (winner == 0) {
                                    JOptionPane.showMessageDialog(frame, "Tie game!");
                                } else if (winner == 1) {
                                    JOptionPane.showMessageDialog(frame, "Player " + "red" + " wins!");
                                } else if (winner == 2) {
                                    JOptionPane.showMessageDialog(frame, "Player " + "blue" + " wins!");
                                }
                                repaint();
                                return;
                            }
                            switch (currentPlayer) {
                                case 1:
                                    currentPlayer=2;
                                    break;
                                case 2:
                                    currentPlayer=1;
                                    break;
                            }
                            repaint();
                            return;
                        }
                    }
                }
            }
        });
    }

    private void createOffscreenImage() {
        image = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
        graphics = image.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 800, 600);
    }

    final void createBoard() {
        numVertices = (Integer) frame.configPanel.dotsSpinner.getValue();
        edgeProbability = (Double) frame.configPanel.linesCombo.getSelectedItem();

        createOffscreenImage();
        createVertices();
        drawLines();
        drawVertices();

        repaint();
    }

    private void createVertices() {
        int x0 = W / 2;
        int y0 = H / 2; //middle of the board
        int radius = H / 2 - 10; //board radius
        double alpha = 2 * Math.PI / numVertices; // the angle
        x = new int[numVertices];
        y = new int[numVertices];
        for (int i = 0; i < numVertices; i++) {
            x[i] = x0 + (int) (radius * Math.cos(alpha * i));
            y[i] = y0 + (int) (radius * Math.sin(alpha * i));
        }
    }

    private void drawLines() {
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                double random = Math.random();
                if (random <= edgeProbability) {
                    graphics.setStroke(new BasicStroke(5));
                    graphics.drawLine(x[i], y[i], x[j], y[j]);
                    Line2D blackLine = new Line2D.Double(x[i], y[i], x[j], y[j]);
                    blackLines.add(blackLine);
                }
            }
        }
    }

    private void drawVertices() {
        graphics.setColor(Color.BLACK); // set the color to black
        for (int i = 0; i < numVertices; i++) {
            graphics.fillOval(x[i] - 10, y[i] - 10, 20, 20);
        }
    }

    private boolean isLineSelected(int i, int j) {
        for (Line2D selectedLine : selectedLines) {
            if (selectedLine.getP1().equals(new Point(x[i], y[i])) && selectedLine.getP2().equals(new Point(x[j], y[j])) || selectedLine.getP1().equals(new Point(x[j], y[j])) && selectedLine.getP2().equals(new Point(x[i], y[i]))) {
                return true;
            }
        }
        return false;
    }

    private void selectLine(Line2D line) {
        selectedLines.add(line);
    }

    public void clearBlackLines() {
        blackLines.clear();
    }

    private boolean isBlackLine(Line2D line) {
        for (Line2D blackLine : blackLines) {
            if (line.getP1().equals(blackLine.getP1()) && line.getP2().equals(blackLine.getP2())) {
                return true;
            }
        }
        return false;
    }

    public void resetLines() {
        for (Line2D line : blackLines) {
            graphics.setColor(Color.BLACK);
            graphics.draw(line);
        }
        selectedLines.clear();
        currentPlayer = 1;
        redLines.clear();
        blueLines.clear();
        repaint();
    }

    @Override
    public void update(Graphics g) {
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        graphics.drawImage(image, 0, 0, this);
    }
}


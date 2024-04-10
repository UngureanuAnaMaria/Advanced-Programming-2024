package org.example;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.*;
import java.util.List;
import java.util.Random;

import static java.lang.Math.sqrt;

public class DrawingPanel extends JPanel implements Serializable {
    private final MainFrame frame;
    int rows, cols;
    int canvasWidth = 400, canvasHeight = 400;
    int boardWidth, boardHeight;
    int cellWidth, cellHeight;
    int padX, padY;
    int stoneSize = 20;
    List<Line2D> sticks;
    List<Point> stones = new ArrayList<>();
    Point lastSelectedNode;
    boolean playerTurn = true;
    int nr = 1;
    public DrawingPanel(MainFrame frame) {
        this.frame = frame;
        init(frame.configPanel.getRows(), frame.configPanel.getCols());
    }

    public DrawingPanel(MainFrame frame, int rows, int cols) {
        this.frame = frame;
        this.rows = rows;
        this.cols = cols;
        init(rows, cols);
    }

    final void init(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.padX = stoneSize + 10;
        this.padY = stoneSize + 10;
        this.cellWidth = (canvasWidth - 2 * padX) / (cols - 1);
        this.cellHeight = (canvasHeight - 2 * padY) / (rows - 1);
        this.boardWidth = (cols - 1) * cellWidth;
        this.boardHeight = (rows - 1) * cellHeight;
        setPreferredSize(new Dimension(canvasWidth, canvasHeight));
        generateSticks();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                drawStone(e.getX(), e.getY());
                repaint();
            }

        });

    }

    private void generateSticks() {
        sticks = new ArrayList<>();
        for (int row = 0; row < rows - 1; row++) {
            for (int col = 0; col < cols - 1; col++) {
                //horizontal
                if (Math.random() < 0.5) {
                    int x1 = padX + col * cellWidth;
                    int y1;
                    if(Math.random() < 0.5) {
                        //bottom
                        y1 = padY + row * cellHeight + cellHeight;
                    } else {
                        //top
                        y1 = padY + row * cellHeight;
                    }
                    int x2 = x1 + cellWidth;
                    int y2 = y1;
                    sticks.add(new Line2D.Double(x1, y1, x2, y2));//also float/single-precision point coordinates
                }
                //vertical
                if (Math.random() > 0.5) {
                    int x1;
                    if(Math.random() < 0.5) {
                        //right
                        x1 = padX + col * cellWidth + cellWidth;
                    } else {
                        //left
                        x1 = padX + col * cellWidth;
                    }
                    int y1 = padY + row * cellHeight;
                    int x2 = x1;
                    int y2 = y1 + cellHeight;
                    sticks.add(new Line2D.Double(x1, y1, x2, y2));//also float/single-precision point coordinates
                }
            }
        }

    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, canvasWidth, canvasHeight);
        paintGrid(g);
        paintSticks(g);
        paintStones(g);
    }

    private void drawStone(int x, int y) {
        stones.add(new Point(x, y));
    }


    private void paintStones(Graphics2D g) {
        Point lastSelectedNode = null;
        int redTurn = 1;

        for (Point stone : stones) {
            int x = stone.x - stoneSize/2;
            int y = stone.y - stoneSize/2;

            boolean isOnStick = false;
            boolean isConnected = false;
            boolean hasAdjSticks = false;
            for(Line2D stick : sticks) {
                int x1 = (int) Math.round(stick.getX1());
                int y1 = (int) Math.round(stick.getY1());
                int x2 = (int) Math.round(stick.getX2());
                int y2 = (int) Math.round(stick.getY2());

                if(stone.distance(new Point(x1, y1)) < (double)(stoneSize - 10) ||  stone.distance(new Point(x2, y2)) < (double)(stoneSize - 10)) {
                    isOnStick = true;
                }

                if (lastSelectedNode != null && ((stone.distance(new Point(x1, y1)) <= stoneSize && lastSelectedNode.distance(new Point(x2, y2)) <= stoneSize) ||
                        (lastSelectedNode.distance(new Point(x1, y1)) <= stoneSize && stone.distance(new Point(x2, y2)) <= stoneSize))) {
                    isConnected = true;
                }

                if(lastSelectedNode == null) {
                    isConnected = true;
                }

                Point p1 = new Point(x1, y1);
                Point p2 = new Point(x2, y2);
                if((stone.distance(new Point(x1, y1)) <= stoneSize && p2.distance(new Point(x2, y2)) <= stoneSize) ||
                        (p1.distance(new Point(x1, y1)) <= stoneSize && stone.distance(new Point(x2, y2)) <= stoneSize)) {
                    hasAdjSticks = true;
                }
            }

            if ((lastSelectedNode == null || lastSelectedNode.distance(new Point(x, y)) <= 1.1 * Math.min(cellWidth, cellHeight)) && isOnStick && isConnected && hasAdjSticks) {
                if (redTurn == 1) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.BLUE);
                }
                g.fillOval(x, y, stoneSize, stoneSize);

                redTurn = 1 - redTurn;

                lastSelectedNode = new Point(x, y);
            } else if(hasAdjSticks == false) {
                if(redTurn == 1) {
                    JOptionPane.showMessageDialog(frame, "Winner is BLUE!");
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Winner is RED!");
                    frame.dispose();
                }
            }
        }
    }

    private void paintSticks(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(3));//bolder

        for (Line2D stick : sticks) {
            g.draw(stick);
        }

    }

    private void paintGrid(Graphics2D g) {
        g.setColor(Color.DARK_GRAY);
        //horizontal lines
        for (int row = 0; row < rows; row++) {
            int x1 = padX;
            int y1 = padY + row * cellHeight;
            int x2 = padX + boardWidth;
            int y2 = y1;
            g.drawLine(x1, y1, x2, y2);
        }
        //vertical lines
        for (int col = 0; col < cols; col++) {
            int x1 = padX + col * cellWidth;
            int y1 = padY;
            int x2 = x1;
            int y2 = padY + boardHeight;
            g.drawLine(x1, y1, x2, y2);
        }
        //intersections
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = padX + col * cellWidth;
                int y = padY + row * cellHeight;
                g.setColor(Color.LIGHT_GRAY);
                g.drawOval(x - stoneSize / 2, y - stoneSize / 2, stoneSize, stoneSize);
            }
        }
    }
}

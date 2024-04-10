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
                checkForWin();
                playerTurn = false;
                if (!playerTurn) {
                    performAITurn();
                }
            }
        });

    }
    private void paintStones(Graphics2D g) {
        for (Point stone : stones) {
            int x = stone.x - stoneSize / 2;
            int y = stone.y - stoneSize / 2;

            if (playerTurn && nr % 2 != 0) {
                g.setColor(Color.RED);
                nr++;
            } else if(nr % 2 == 0){
                g.setColor(Color.BLUE);
                nr++;
            }
            g.fillOval(x, y, stoneSize, stoneSize);
            lastSelectedNode = new Point(x, y);
        }
    }


    private void performAITurn() {
        Point lastSelectedNode1 = null;
        for (Point stone : stones) {
            int x = stone.x - stoneSize / 2;
            int y = stone.y - stoneSize / 2;
            lastSelectedNode1 = new Point(x, y);
        }

        for (Line2D stick : sticks) {
            int x1 = (int) stick.getX1();
            int y1 = (int) stick.getY1();
            int x2 = (int) stick.getX2();
            int y2 = (int) stick.getY2();

            if(lastSelectedNode1.distance(new Point(x1, y1)) <= Math.min(cellWidth, cellHeight) && lastSelectedNode1.distance(new Point(x2, y2)) <= (double)(stoneSize) && !stones.contains(new Point(x1, y1))){
                int stoneX = x1;
                int stoneY = y1;
                drawStone(stoneX, stoneY);
                break;
            }  else if(lastSelectedNode1.distance(new Point(x2, y2)) <= Math.min(cellWidth, cellHeight) && lastSelectedNode1.distance(new Point(x1, y1)) <= (double)(stoneSize)  && !stones.contains(new Point(x2, y2))) {
                int stoneX = x2;
                int stoneY = y2;
                drawStone(stoneX, stoneY);
                break;
            }
        }
        playerTurn = true;
    }


    private void checkForWin() {
        boolean perfectMatching = false;
        for (Line2D stick : sticks) {
            int x1 = (int) stick.getX1();
            int y1 = (int) stick.getY1();
            int x2 = (int) stick.getX2();
            int y2 = (int) stick.getY2();
            if (x1 == x2 || y1 == y2) {
                perfectMatching = true;
                break;
            }
        }
        boolean hasAdjSticks = false;

        Point lastSelectedNode1 = null;
        for (Point stone : stones) {
            int x = stone.x - stoneSize / 2;
            int y = stone.y - stoneSize / 2;
            lastSelectedNode1 = new Point(x, y);
        }

        for (Line2D stick : sticks) {
            int x1 = (int) stick.getX1();
            int y1 = (int) stick.getY1();
            int x2 = (int) stick.getX2();
            int y2 = (int) stick.getY2();

            Point p1 = new Point(x1, y1);
            Point p2 = new Point(x2, y2);
            if ((lastSelectedNode1.distance(new Point(x1, y1)) <= stoneSize && p2.distance(new Point(x2, y2)) <= stoneSize) ||
                    (p1.distance(new Point(x1, y1)) <= stoneSize && lastSelectedNode1.distance(new Point(x2, y2)) <= stoneSize)) {
                hasAdjSticks = true;
            }
        }


        if (!perfectMatching || !hasAdjSticks) {
            if (playerTurn) {
                JOptionPane.showMessageDialog(frame, "AI wins!");
            } else {
                JOptionPane.showMessageDialog(frame, "Player wins!");
            }
            frame.dispose();
        }
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

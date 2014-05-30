package org.andrewberman.ui.unsorted;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class MakingAGrid extends JPanel {
    SquareRx[][] squares;
    final int PAD = 20;

    public MakingAGrid() {
        int ROWS = 10;
        int COLS = 10;
        squares = new SquareRx[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                squares[i][j] = new SquareRx(i, j);

            }
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        double xInc = (double) (w - 2 * PAD) / squares[0].length;
        double yInc = (double) (h - 2 * PAD) / squares.length;
        // Draw vertical grid lines.
        g2.setPaint(Color.blue);
        for (int i = 0; i <= squares[0].length; i++) {
            double x = PAD + i * xInc;
            g2.draw(new Line2D.Double(x, PAD, x, h - PAD));
        }
        // Draw horizontal grid lines.
        for (int i = 0; i <= squares.length; i++) {
            double y = PAD + i * yInc;
            g2.draw(new Line2D.Double(PAD, y, w - PAD, y));
        }
    }

    public static void main(String[] args) {
        MakingAGrid test = new MakingAGrid();
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(test);
        f.setSize(500, 500);
        f.setLocation(200, 200);
        f.setVisible(true);

    }
}

class SquareRx {
    private final int row;
    private final int col;
    private boolean occupied = false;

    public SquareRx(int r, int c) {
        row = r;
        col = c;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public boolean isOccupied() {
        return occupied;
    }
}
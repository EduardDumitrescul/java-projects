import Utility.Pair;

import javax.swing.*;
import java.awt.*;

public class GameBoardView extends JPanel {
    private int rows, columns;
    private Cell[][] cellGrid;


    public GameBoardView(int rows, int columns) {
        rows += 2;
        columns += 2;
        this.rows = rows;
        this.columns = columns;

        setLayout(new GridLayout(rows, columns, -1, -1));
        setPreferredSize(new Dimension(40*columns, 40*rows));

        cellGrid = new Cell[rows][columns];
        for(int i = 0; i < rows; i ++)
            for(int j = 0; j < columns; j ++) {
                cellGrid[i][j] = new Cell();
                if(i == 0 || i == rows - 1 || j == 0 || j == columns - 1) {
                    cellGrid[i][j].setEmpty(false);
                    cellGrid[i][j].setColor(new Color(70, 70, 70));
                }
                add(cellGrid[i][j]);
            }
    }

    public void setCellsBlocked(Pair[] x) {
        for(Pair p: x)
            cellGrid[p.getFirst()][p.getSecond()].setEmpty(false);
    }


    public void paintCells(Pair[] x, Color color) {
        for(Pair p: x)
            cellGrid[p.getFirst()][p.getSecond()].setColor(color);
    }

    public void resetCells(Pair[] x) {
        for(Pair p: x)
            cellGrid[p.getFirst()][p.getSecond()].setColor(Color.black);

        validate();
    }

    public void resetBoard() {
        for(int i = 1; i < rows - 1; i ++) {
            for(int j = 1; j < columns - 1; j ++) {
                cellGrid[i][j].setEmpty(true);
                cellGrid[i][j].setColor(Color.black);
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Cell[][] getCellGrid() {
        return cellGrid;
    }
}

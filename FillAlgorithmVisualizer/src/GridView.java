import javax.swing.*;
import java.awt.*;

public class GridView extends JPanel {
    private int rows, columns;

    private Cell[][] cellGrid;

    GridView(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        setLayout(new GridLayout(rows, columns));

        cellGrid = new Cell[rows][columns];
        for(int i = 0; i < rows; i ++)
            for(int j = 0; j < columns; j ++) {
                cellGrid[i][j] = new Cell();
                add(cellGrid[i][j]);
            }

    }

    public Cell[][] getCellGrid() {
        return cellGrid;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}

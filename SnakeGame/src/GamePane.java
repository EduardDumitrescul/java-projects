import javax.swing.*;
import java.awt.*;

public class GamePane extends JPanel {
    private Cell[][] map;
    private int rows, columns;

    public GamePane(int rows, int columns) {
        setPreferredSize(new Dimension(1280, 720));
        setLayout(new GridLayout(rows, columns));

        this.rows = rows;
        this.columns = columns;

        map = new Cell[rows][columns];
        for(int i = 0; i < rows; i ++)
            for(int j = 0; j < columns; j ++) {
                map[i][j] = new Cell();
                add(map[i][j]);
            }


    }

    public void createPoint() {
        boolean ok = false;
        while(ok == false) {
            int i = (int)(Math.random() * rows);
            int j = (int)(Math.random() * columns);
            if(map[i][j].getState() == Cell.EMPTY){
                map[i][j].setState(Cell.POINT);
                ok = true;
            }
        }
    }

    public void reset() {
        for(int i = 0; i < rows; i ++)
            for(int j = 0; j < columns; j ++)
                map[i][j].setState(Cell.EMPTY);
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public Cell[][] getMap() {
        return map;
    }
}

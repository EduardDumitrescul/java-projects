import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Timer;

public class GridController {
    private static GridView gridView;
    private static int rows, columns;
    private static Cell[][] cellGrid;
    private static Stack<Pair> stack;
    private static int di[] = {0, 1, 0, -1}, dj[] = {1, 0, -1, 0};
    private static javax.swing.Timer timer = new javax.swing.Timer(80, new TimerListener());

    public GridController(GridView gridView) {
        GridController.gridView = gridView;
        rows = gridView.getRows();
        columns = gridView.getColumns();
        cellGrid = gridView.getCellGrid();

    }

    public static void startAnimation() {
        resetGrid();

        stack = new Stack<>();

        int noSources = 0;

        for(int i = 0; i < rows; i ++)
            for(int j = 0; j < columns; j ++) {
                if(cellGrid[i][j].getState() == Cell.SOURCE) {
                    noSources ++;
                    stack.push(new Pair(i, j));
                }

            }

        if(noSources != 1){
            ControlsView.showText("Please select only one source");
            return;
        }

        timer.start();
    }

    public static class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(stack.empty()) {
                timer.stop();
            }

            nextStep();
        }
    }

    private static void nextStep() {
        boolean updated = false;

        while(stack.empty() == false && updated == false) {
            Pair pair = stack.pop();
            int i = pair.first();
            int j = pair.second();

            if(cellGrid[i][j].getState() == Cell.EMPTY) {
                cellGrid[i][j].setState(Cell.VISITED);
                updated = true;
            }


            for(int k = 0; k < 4 ; k ++) {
                int ii = i + di[k];
                int jj = j + dj[k];

                if(0 <= ii && ii < rows && 0 <= jj && jj < columns && cellGrid[ii][jj].getState() == Cell.EMPTY) {
                    stack.push(new Pair(ii, jj));
                }
        }

        }
    }

    public static void resetGrid() {
        timer.stop();
        for(int i = 0; i < rows; i ++)
            for(int j = 0; j < columns; j ++)
                if(cellGrid[i][j].getState() == Cell.VISITED)
                    cellGrid[i][j].setState(Cell.EMPTY);
    }

    public static void clearGrid() {
        timer.stop();
        for(int i = 0; i < rows; i ++)
            for(int j = 0; j < columns; j ++)
                cellGrid[i][j].setState(Cell.EMPTY);
    }

    public static void setRandomGrid() {
        clearGrid();
        for(int i = 0; i < rows; i ++)
            for(int j = 0; j < columns; j ++) {
                double value = Math.random() * 24;
                if(value  > 16)
                    cellGrid[i][j].setState(Cell.BLOCKED);
            }

        int x = (int)(Math.random() * rows);
        int y = (int)(Math.random() * columns);
        cellGrid[x][y].setState(Cell.SOURCE);
    }


    private static class Pair {
        private int a, b;
        public Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }

        public int first() {
            return a;
        }

        public int second() {
            return b;
        }
    }
}

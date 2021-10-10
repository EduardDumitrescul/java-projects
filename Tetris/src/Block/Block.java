package Block;

import Utility.Pair;

import javax.swing.table.TableStringConverter;
import java.awt.*;

public abstract class Block implements Cloneable {
    int xCenter, yCenter;
    Color color;
    int state;
    Pair[][] cells;

    public Block(int xCenter, int yCenter) {
        this.xCenter = xCenter;
        this.yCenter = yCenter;
    }

    public void moveDown() {
        xCenter ++;
    }

    public void moveLeft() {
        yCenter --;
    }

    public void moveRight() {
        yCenter ++;
    }

    public int getState() {
        return state;
    }

    public int getxCenter() {
        return xCenter;
    }

    public int getyCenter() {
        return yCenter;
    }

    public Color getColor() {
        return color;
    }

    public Pair[] getCurrentCells() {
        Pair[] x = new Pair[4];
        for(int i = 0; i < 4; i ++)
            x[i] = new Pair(xCenter + cells[state][i].getFirst(), yCenter + cells[state][i].getSecond());
        return x;
    }


    public abstract void nextState();
    public abstract Block newInstance();
}

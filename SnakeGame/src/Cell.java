import javax.swing.*;
import java.awt.*;

public class Cell extends JPanel {
    public final static int EMPTY = 0, SNAKE = 1, POINT = 2;
    private Color color[];
    private int state = 0;

    public Cell() {
        color = new Color[3];
        color[0] = Color.black;
        color[1] = Color.ORANGE;
        color[2] = Color.BLUE;

        setBackground(color[EMPTY]);
    }

    public void setState(int state) {
        this.state = state;
        setBackground(color[state]);
    }

    public int getState() {
        return state;
    }
}

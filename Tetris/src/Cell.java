import javax.swing.*;
import java.awt.*;

public class Cell extends JPanel {
    private Color color = Color.BLACK;
    private boolean empty = true;

    public Cell() {
        setBorder(BorderFactory.createLineBorder(new Color(26, 24, 24)));
        setBackground(color);
    }

    public void setColor(Color color) {
        this.color = color;
        setBackground(color);
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void copy(Cell cell) {
        this.empty = cell.empty;
        this.color = cell.color;
        setBackground(color);
    }

}

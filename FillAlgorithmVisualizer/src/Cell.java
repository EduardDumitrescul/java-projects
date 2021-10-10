import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Cell extends JPanel {
    public final static int EMPTY = 0, BLOCKED = 1, SOURCE = 2, VISITED = 3;

    private Color[] colors = new Color[]{new Color(146, 170, 171),
                                         new Color(47, 54, 63, 232),
                                         new Color(47, 197, 175),
                                         new Color(23, 76, 194)};

    private int state = 0;

    public Cell() {
        setBorder(BorderFactory.createLineBorder(Color.black));
        setState(EMPTY);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                nextState();
            }
        });
    }

    public void setState(int value) {
        state = value;
        setBackground(colors[state]);
    }

    public void nextState() {
        state = state + 1;
        if(state == 3)
            state = 0;
        setBackground(colors[state]);
    }

    public int getState() {
        return state;
    }
}

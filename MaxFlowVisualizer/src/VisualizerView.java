import javax.swing.*;
import java.awt.*;

public class VisualizerView extends JPanel {
    private JPanel mainPane;
    private JPanel eastPane;
    public VisualizerView() {
        setLayout(new BorderLayout());

        mainPane = new JPanel();
        eastPane = new JPanel();

        mainPane.setBackground(Color.RED);
        eastPane.setBackground(Color.BLUE);

        add(mainPane, BorderLayout.CENTER);
        add(eastPane, BorderLayout.EAST);
    }

    public void setMainPane(JPanel mainPane) {
        add(mainPane, BorderLayout.CENTER);
        this.mainPane = mainPane;
    }

    public void setEastPane(JPanel eastPane) {
        add(eastPane, BorderLayout.EAST);
        this.eastPane = eastPane;
    }

    public JPanel getMainPane() {
        return mainPane;
    }
}

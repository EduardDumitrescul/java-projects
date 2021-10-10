import javax.swing.*;
import java.awt.*;

public class Run {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");
        JFrame frame = new JFrame("Fill Algorithm Visualizer");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.setMinimumSize(new Dimension(1000, 720));
        frame.setResizable(true);

        frame.setLayout(new BorderLayout());

        frame.add(new FillVisualizer(), BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }
}

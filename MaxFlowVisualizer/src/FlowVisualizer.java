import javax.swing.*;
import java.awt.*;

public class FlowVisualizer{
    private static JFrame frame;
    public static void main(String[] args) {
        frame = new JFrame("Max Flow Visualizer");
        frame.setMinimumSize(new Dimension(600, 400));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        VisualizerView visualizerView = new VisualizerView();
        VisualizerController visualizerController = new VisualizerController(visualizerView);

        frame.add(visualizerView);

        frame.setVisible(true);
    }
}

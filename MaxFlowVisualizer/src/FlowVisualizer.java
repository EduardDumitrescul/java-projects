import javax.swing.*;
import java.awt.*;

public class FlowVisualizer{
    private static JFrame frame;
    public static void main(String[] args) {
        frame = new JFrame("Max Flow Visualizer");
        frame.setMinimumSize(new Dimension(1080, 720));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        ProjectInfoPane projectInfoPane = new ProjectInfoPane();
        tabbedPane.add("Prezentare Proiect", projectInfoPane);

        AlgorithmInfoPane algorithmInfoPane = new AlgorithmInfoPane();
        tabbedPane.add("Algoritmul lui Dinic", algorithmInfoPane);

        VisualizerView visualizerView = new VisualizerView();
        VisualizerController visualizerController = new VisualizerController(visualizerView);
        tabbedPane.add("Reprezentare Grafica", visualizerView);

        frame.add(tabbedPane);

        frame.setVisible(true);
    }
}

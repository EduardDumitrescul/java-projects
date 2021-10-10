import javax.swing.*;
import java.awt.*;

public class FillVisualizer extends JPanel {
    private ControlsView controlsView;
    private ControlsController controlsController;

    private GridView gridView;
    private GridController gridController;

    public FillVisualizer() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1280, 720));

        controlsView = new ControlsView();
        controlsController = new ControlsController(controlsView);

        gridView = new GridView(24, 36);
        gridController = new GridController(gridView);

        add(controlsView, BorderLayout.EAST);
        add(gridView, BorderLayout.CENTER);

    }
}

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ControlsController {
    private ControlsView controlsView;
    private JPanel startButton;
    private JPanel resetButton;
    private JPanel clearButton;
    private JPanel randomButton;

    public ControlsController(ControlsView controlsView) {
        this.controlsView = controlsView;

        startButton = controlsView.getStartButton();
        resetButton = controlsView.getResetButton();
        clearButton = controlsView.getClearButton();
        randomButton = controlsView.getRandomButton();

        addListeners();

    }

    private void addListeners() {
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                GridController.startAnimation();
            }
        });

        resetButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                GridController.resetGrid();
            }
        });

        clearButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                GridController.clearGrid();
            }
        });

        randomButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                GridController.setRandomGrid();
            }
        });

    }
}

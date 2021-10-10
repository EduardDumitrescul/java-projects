import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;

public class ControlsView extends JPanel {
    private Dimension dimension = new Dimension(200, Integer.MAX_VALUE);

    private JPanel startButton;
    private JPanel resetButton;
    private JPanel clearButton;
    private JPanel randomButton;
    private static JTextArea informationPane;

    ControlsView() {
        setPreferredSize(dimension);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        createStartButton();
        createResetButton();
        createClearButton();
        createRandomButton();
        createInformationPane();

        add(startButton);
        add(resetButton);
        add(clearButton);
        add(randomButton);
        add(informationPane);

        add(Box.createVerticalGlue());

        validate();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.translate(getWidth() - 200, getHeight() - 200);
        g.setFont(new Font("Serif", Font.PLAIN, 20));

        g.setColor(new Color(146, 170, 171));
        g.fillRect(20, 20, 30, 30);
        g.setColor(Color.black);
        g.drawRect(20, 20, 30, 30);

        g.drawString(" - empty cell", 60, 40);

        g.setColor(new Color(47, 54, 63, 232));
        g.fillRect(20, 70, 30, 30);
        g.setColor(Color.black);
        g.drawRect(20, 70, 30, 30);

        g.drawString(" - blocked cell", 60, 90);


        g.setColor(new Color(47, 197, 175));
        g.fillRect(20, 120, 30, 30);
        g.setColor(Color.black);
        g.drawRect(20, 120, 30, 30);

        g.drawString(" - source", 60, 140);
    }

    private void createStartButton() {
        startButton = new JPanel();
        startButton.setLayout(new BorderLayout());

        JLabel label = new JLabel("Start");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);

        startButton.add(label, BorderLayout.CENTER);

        startButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        startButton.setPreferredSize(new Dimension(Integer.MAX_VALUE, 100));

        startButton.setBackground(new Color(0, 144, 193));

        startButton.setBorder(BorderFactory.createLineBorder(Color.black));

    }

    private void createResetButton() {
        resetButton = new JPanel();
        resetButton.setLayout(new BorderLayout());

        JLabel label = new JLabel("Reset");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);

        resetButton.add(label, BorderLayout.CENTER);

        resetButton.setPreferredSize(new Dimension(Integer.MAX_VALUE, 100));
        resetButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        resetButton.setBackground(new Color(0, 144, 193));

        resetButton.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    private void createClearButton(){
        clearButton = new JPanel();
        clearButton.setLayout(new BorderLayout());

        JLabel label = new JLabel("Clear");
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        clearButton.add(label, BorderLayout.CENTER);

        clearButton.setPreferredSize(new Dimension(Integer.MAX_VALUE, 100));
        clearButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        clearButton.setBackground(new Color(0, 144, 193));

        clearButton.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    private void createRandomButton() {
        randomButton = new JPanel();
        randomButton.setLayout(new BorderLayout());

        JLabel label = new JLabel("Random Cells");
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        randomButton.add(label, BorderLayout.CENTER);

        randomButton.setPreferredSize(new Dimension(Integer.MAX_VALUE, 100));
        randomButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        randomButton.setBackground(new Color(0, 144, 193));

        randomButton.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    private void createInformationPane() {
        informationPane = new JTextArea();
        informationPane.setFont(new Font("Serif", Font.PLAIN, 24));
        informationPane.setEditable(false);
        informationPane.setLineWrap(true);
        informationPane.setWrapStyleWord(true);


        informationPane.setPreferredSize(new Dimension(Integer.MAX_VALUE, 100));
        informationPane.setMinimumSize(new Dimension(Integer.MAX_VALUE, 100));
        informationPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        informationPane.setBorder(BorderFactory.createLineBorder(Color.black));
    }


    public static void showText(String text) {
       informationPane.setText(text);
    }

    public static void resetInformationPane() {
        informationPane.setText(null);
    }

    public JPanel getStartButton() {
        return startButton;
    }

    public JPanel getClearButton() {
        return clearButton;
    }

    public JPanel getResetButton() {
        return resetButton;
    }

    public JPanel getRandomButton() {
        return randomButton;
    }
}

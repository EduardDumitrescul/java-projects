import jdk.jshell.spi.ExecutionControl;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.HashMap;

public class InfoView extends JPanel {
    private JLabel flowLabel = new JLabel("Flux curent: 0");
    private JLabel currentStepLabel = new JLabel();
    private DashedLinePane dashedLinePane = new DashedLinePane();
    private SolidLinePane solidLinePane = new SolidLinePane();

    private JButton startButton = new JButton("Start");
    private JButton stopButton = new JButton("Stop");


    final private String[] stepStrings = new String[]
            {"Construieste graful pe niveluri",
            "Cauta drumuri care cresc fluxul total",
            "Algoritm finalizat",
            "Apasa start pentru a incepe"};

    final public int LEVEL_GRAPH = 0, FLOW_SEARCH = 1, ALGORITHM_DONE = 2, IDLE = 3;

    public InfoView() {
        setBorder(BorderFactory.createLineBorder(Color.black, 1, false));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(flowLabel);
        add(Box.createVerticalStrut(10));
        add(currentStepLabel);

        add(Box.createVerticalStrut(10));
        add(dashedLinePane);
        add(Box.createVerticalStrut(10));
        add(solidLinePane);

        add(Box.createVerticalGlue());

        JPanel pane = new JPanel();
        pane.add(startButton);
        pane.add(stopButton);

        add(pane);


        setMinimumSize(new Dimension(180, 600));
        setPreferredSize(new Dimension(200, 600));
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getStopButton() {
        return stopButton;
    }

    public void setCurrentFlow(int value) {
        flowLabel.setText("Flux curent: " + Integer.toString(value));
    }

    public void setCurrentStep(int value) {
        currentStepLabel.setText("<html>" + stepStrings[value] + "</html>");
    }

    private class DashedLinePane extends JPanel {
        DashedLinePane() {
            repaint();
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        }

        void drawCenteredString(Graphics2D graphics2D, String string, double x, double y) {
            FontMetrics fm = graphics2D.getFontMetrics();
            double X = (double)x - fm.stringWidth(string) / 2.0;
            double Y = (double)y - fm.getHeight() / 2.0  + fm.getAscent();

            graphics2D.drawString(string, (float)X, (float)Y);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D graphics2D = (Graphics2D) g;

            BasicStroke dashed = new BasicStroke(0.4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10.0f, new float[]{10.0f}, 0.0f);
            graphics2D.setStroke(dashed);

            graphics2D.drawLine(10, getHeight() / 2, getWidth() / 2, getHeight() / 2);

            drawCenteredString(graphics2D, "muchii inutile", getWidth() / 2 + 50, getHeight() / 2);
        }
    }

    private class SolidLinePane extends JPanel {
        SolidLinePane() {
            repaint();
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        }

        void drawCenteredString(Graphics2D graphics2D, String string, double x, double y) {
            FontMetrics fm = graphics2D.getFontMetrics();
            double X = (double)x - fm.stringWidth(string) / 2.0;
            double Y = (double)y - fm.getHeight() / 2.0  + fm.getAscent();

            graphics2D.drawString(string, (float)X, (float)Y);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D graphics2D = (Graphics2D) g;

            BasicStroke solid = new BasicStroke(0.4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
            graphics2D.setStroke(solid);

            graphics2D.drawLine(10, getHeight() / 2, getWidth() / 2, getHeight() / 2);

            drawCenteredString(graphics2D, "muchii posibile", getWidth() / 2 + 50, getHeight() / 2);
        }
    }
}

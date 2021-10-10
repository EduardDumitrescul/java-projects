import javax.swing.*;
import java.awt.*;

public class StatusView extends JPanel {
    private static JLabel scoreLabel;
    private static JLabel highscoreLabel;
    private static JLabel messageLabel;

    public StatusView() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBackground(Color.black);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setPreferredSize(new Dimension(200, Integer.MAX_VALUE));

        scoreLabel = new JLabel("0");
        add(new JLabel("Score: "));
        add(scoreLabel);
        add(Box.createRigidArea(new Dimension(100, 20)));

        highscoreLabel = new JLabel("0");
        add(new JLabel("Highscore:"));
        add(highscoreLabel);
        add(Box.createRigidArea(new Dimension(100, 20)));

        messageLabel = new JLabel("");
        add(messageLabel);
    }

    public static void setScore(long score) {
        scoreLabel.setText(Long.toString(score));
    }

    public static void setHighscore(long highscore) {
        highscoreLabel.setText(Long.toString(highscore));
    }

    public static void setMessage(String text) {
        messageLabel.setText(text);
    }
}

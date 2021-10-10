import javax.swing.*;
import java.awt.*;

public class Game {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");

        JFrame frame = new JFrame("Snake");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        GamePane gamePane = new GamePane(18, 32);
        GameController gameController = new GameController(gamePane);

        frame.add(gamePane, BorderLayout.CENTER);
        frame.setBackground(Color.pink);

        frame.pack();
        frame.setVisible(true);

    }
}

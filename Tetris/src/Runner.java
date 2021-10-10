import javax.swing.*;
import java.awt.*;

public class Runner {
    private static StatusView statusView;
    private static GameBoardView gameBoardView;
    private static GameBoardController gameBoardController;

    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");

        JFrame frame = new JFrame("Tetris");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.setSize(new Dimension(669, 884));

        statusView = new StatusView();
        gameBoardView = new GameBoardView(20, 10);
        gameBoardController = new GameBoardController(gameBoardView);

        frame.add(gameBoardView, BorderLayout.CENTER);
        frame.add(statusView, BorderLayout.WEST);

        //frame.pack();
        frame.setVisible(true);
    }
}

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.ArrayDeque;
import java.util.Deque;

public class GameController {
    private GamePane gamePane;
    private Cell[][] map;
    private Timer timer;

    private Deque<Pair> snake;

    private int rows, columns;
    private boolean gameRunning = false;

    public GameController(GamePane gamePane) {
        this.gamePane = gamePane;
        map = gamePane.getMap();
        rows = gamePane.getRows();
        columns = gamePane.getColumns();

        engine();
    }
    private int direction = 0;
    private void engine() {
        final int UP = 0, LEFT = 1, DOWN = 2, RIGHT = 3;
        int di[] = new int[]{-1, 0, 1, 0};
        int dj[] = new int[]{0, -1, 0, 1};

        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pair p = snake.getFirst();
                int i = p.first(), j = p.second();
                int ii = i + di[direction], jj = j + dj[direction];

                if(!(0 <= ii && ii < rows && 0 <= jj && jj < columns)) {
                    gameRunning = false;
                    return;
                }
                if(map[ii][jj].getState() == Cell.SNAKE) {
                    gameRunning = false;
                }
                if(map[ii][jj].getState() == Cell.POINT) {
                    snake.addFirst(new Pair(ii, jj));
                    map[ii][jj].setState(Cell.SNAKE);
                    gamePane.createPoint();
                }
                if(map[ii][jj].getState() == Cell.EMPTY) {
                    snake.addFirst(new Pair(ii, jj));
                    map[ii][jj].setState(Cell.SNAKE);

                    Pair last = snake.removeLast();
                    map[last.first()][last.second()].setState(Cell.EMPTY);
                }

            }
        });

        gamePane.setFocusable(true);
        gamePane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_P) {
                    timer.stop();
                }
                if(e.getKeyCode() == KeyEvent.VK_LEFT && direction != RIGHT) {
                    if(!timer.isRunning()) {
                        timer.start();
                    }
                    direction = LEFT;
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT && direction != LEFT) {
                    if(!timer.isRunning()) {
                        timer.start();
                    }
                    direction = RIGHT;
                }
                if(e.getKeyCode() == KeyEvent.VK_UP && direction != DOWN) {
                    if(!timer.isRunning()) {
                        timer.start();
                    }
                    direction = UP;
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN && direction != UP) {
                    if(!timer.isRunning()) {
                        timer.start();
                    }
                    direction = DOWN;
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT)
                    if(gameRunning == false) {
                        gamePane.reset();
                        direction = 0;
                        snake = new ArrayDeque<>();
                        snake.add(new Pair(rows / 2, columns / 2));
                        gameRunning = true;
                        gamePane.createPoint();
                        timer.start();
                        System.out.println("OK");
                    }
            }
        });
    }
    private class Pair {
        private int a, b;
        Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }

        public int first() {
            return a;
        }
        public int second() {
            return b;
        }
    }
}

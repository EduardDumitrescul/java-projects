import Block.*;
import Utility.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.security.Key;

public class GameBoardController {
    private GameBoardView gameBoardView;
    private Cell[][] board;
    private Timer timer;
    private Block block;

    private long highscore = 0;
    private long score = 0;
    private int totalClearedLines = 0;
    private int consecutiveClearedLines = 0;

    private int rows, columns;
    private int delay = 500; // ticks per second

    public GameBoardController(GameBoardView gameBoardView) {
        this.gameBoardView = gameBoardView;

        rows = gameBoardView.getRows();
        columns = gameBoardView.getColumns();
        board = gameBoardView.getCellGrid();

        engine();
    }

    private void engine() {
        TimerListener timerListener = new TimerListener();
        timer = new Timer(delay, timerListener);

        gameBoardView.setFocusable(true);
        gameBoardView.addKeyListener(new UserControls());

        timer.start();
    }

    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            nextTick();
            if(delay > 100) {
                delay --;
                timer.setDelay(delay);
                System.out.println(delay);
            }
        }
    }



    private void nextTick() {
        // no active block
        if(block == null) {
            block = generateNewBlock();
            boolean valid = true;
            for(Pair p: block.getCurrentCells())
                if(board[p.getFirst()][p.getSecond()].isEmpty() == false)
                    valid = false;

            if(valid == false) {
                resetGame();
                return;
            }

            gameBoardView.paintCells(block.getCurrentCells(), block.getColor());
            return;
        }

        //active block
        for(Pair p: block.getCurrentCells()) {
            if(p.getFirst() + 1 == rows || !board[p.getFirst() + 1][p.getSecond()].isEmpty()) {
                gameBoardView.setCellsBlocked(block.getCurrentCells());
                clearLines();
                block = null;
                return;
            }
        }

        gameBoardView.paintCells(block.getCurrentCells(), Color.black);
        block.moveDown();
        gameBoardView.paintCells(block.getCurrentCells(), block.getColor());
    }

    private void resetGame() {
        timer.stop();
        if(highscore < score) {
            highscore = score;
            StatusView.setHighscore(highscore);
        }

        score = 0;
        consecutiveClearedLines = 0;
        gameBoardView.resetBoard();
        StatusView.setScore(0);
        block = null;
        delay = 500;
        timer.setDelay(delay);
        timer.start();
    }

    private void clearLines() {
        int numberOfClearedLines = 0;
        for(int line = rows - 2; line > 0; line --){
            boolean full = true;
            for(int col = 1; col < columns - 1; col ++)
                if(board[line][col].isEmpty() == true)
                    full = false;

            if(full == true) {
                numberOfClearedLines++;
                for (int line2 = line; line2 > 1; line2--)
                    for (int col = 1; col < columns - 1; col++)
                        board[line2][col].copy(board[line2 - 1][col]);
                for (int col = 1; col < columns - 1; col++) {
                    board[1][col].setEmpty(true);
                    board[1][col].setColor(Color.black);
                }
                line ++;
            }
        }
        if(numberOfClearedLines == 0) {
            consecutiveClearedLines = 0;
            return;
        }
        double multiplier = 0.75 + 0.25 * numberOfClearedLines;
        score = score + (int)(multiplier * Math.pow(1.1, consecutiveClearedLines) * 1000 * numberOfClearedLines);
        consecutiveClearedLines += numberOfClearedLines;

        StatusView.setScore(score);
    }

    private class UserControls extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if(block == null)
                return;
            if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                boolean moveIsPossible = true;
                for(Pair p: block.getCurrentCells())
                    if(p.getSecond() - 1 < 0 || !board[p.getFirst()][p.getSecond() - 1].isEmpty())
                        moveIsPossible = false;

                if(moveIsPossible){
                    gameBoardView.resetCells(block.getCurrentCells());
                    block.moveLeft();
                    gameBoardView.paintCells(block.getCurrentCells(), block.getColor());
                }
            }
            if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                boolean moveIsPossible = true;
                for(Pair p: block.getCurrentCells())
                    if(p.getSecond() + 1 == columns || !board[p.getFirst()][p.getSecond() + 1].isEmpty())
                        moveIsPossible = false;

                if(moveIsPossible){
                    gameBoardView.resetCells(block.getCurrentCells());
                    block.moveRight();
                    gameBoardView.paintCells(block.getCurrentCells(), block.getColor());
                }
            }
            if(e.getKeyCode() == KeyEvent.VK_UP) {
               boolean moveIsPossible = true;
               Block auxBlock = block.newInstance();
               auxBlock.nextState();

               for(Pair p: auxBlock.getCurrentCells())
                   if(!(0 <= p.getFirst() && p.getFirst() < rows && 0 <= p.getSecond() && p.getSecond() < columns) || !board[p.getFirst()][p.getSecond()].isEmpty())
                       moveIsPossible = false;
                   if(moveIsPossible) {
                       gameBoardView.resetCells(block.getCurrentCells());
                       block.nextState();
                       gameBoardView.paintCells(block.getCurrentCells(), block.getColor());
                   }

            }
            if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                nextTick();

            }
            if(e.getKeyCode() == KeyEvent.VK_P) {
                if(timer.isRunning()) {
                    timer.stop();
                    StatusView.setMessage("Paused");
                }
                else{
                    timer.start();
                    StatusView.setMessage(null);
                }
            }
        }
    }

    private Block generateNewBlock() {
        int numberOfBlocks = 7;
        switch((int)(Math.random() * numberOfBlocks)) {
            case 0: return new IBlock(2, columns / 2);
            case 1: return new JBlock(2, columns / 2);
            case 2: return new LBlock(2, columns / 2);
            case 3: return new OBlock(2, columns / 2);
            case 4: return new SBlock(2, columns / 2);
            case 5: return new ZBlock(2, columns / 2);
            case 6: return new TBlock(2, columns / 2);
        }
        return null;
    }
}

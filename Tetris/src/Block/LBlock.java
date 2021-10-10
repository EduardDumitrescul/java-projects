package Block;

import Utility.Pair;

import java.awt.*;

public class LBlock extends Block {

    public LBlock(int xCenter, int yCenter) {
        super(xCenter, yCenter);
        color = Color.ORANGE;
        cells = new Pair[4][4];

        cells[0] = new Pair[]{new Pair(0, -1), new Pair(0, 0), new Pair(0, 1), new Pair(-1, 1)};
        cells[1] = new Pair[]{new Pair(-1, 0), new Pair(0, 0), new Pair(1, 0), new Pair(1, 1)};
        cells[2] = new Pair[]{new Pair(1, -1), new Pair(0, -1), new Pair(0, 0), new Pair(0, 1)};
        cells[3] = new Pair[]{new Pair(-1, -1), new Pair(-1, 0), new Pair(0, 0), new Pair(1, 0)};
    }

    public void nextState() {
        state = state - 1;
        if(state == -1)
            state = 3;
    }

    public Block newInstance() {
        LBlock newBlock = new LBlock(xCenter, yCenter);
        newBlock.cells = this.cells;
        newBlock.color = this.color;
        newBlock.state = this.state;
        return newBlock;
    }
}

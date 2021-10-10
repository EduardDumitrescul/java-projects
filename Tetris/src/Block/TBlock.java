package Block;

import Utility.Pair;

import java.awt.*;

public class TBlock extends Block {

    public TBlock(int xCenter, int yCenter) {
        super(xCenter, yCenter);
        color = Color.MAGENTA;
        cells = new Pair[4][4];

        cells[0] = new Pair[]{new Pair(0, -1), new Pair(0, 0), new Pair(-1, 0), new Pair(0, 1)};
        cells[1] = new Pair[]{new Pair(-1, 0), new Pair(0, 0), new Pair(0, 1), new Pair(1, 0)};
        cells[2] = new Pair[]{new Pair(0, -1), new Pair(0, 0), new Pair(0, 1), new Pair(1, 0)};
        cells[3] = new Pair[]{new Pair(-1, 0), new Pair(0, 0), new Pair(0, -1), new Pair(1, 0)};
    }

    public void nextState() {
        state = state + 1;
        if(state == 4)
            state = 0;
    }

    public Block newInstance() {
        TBlock newBlock = new TBlock(xCenter, yCenter);
        newBlock.cells = this.cells;
        newBlock.color = this.color;
        newBlock.state = this.state;
        return newBlock;
    }
}

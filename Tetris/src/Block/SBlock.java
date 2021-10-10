package Block;

import Utility.Pair;

import java.awt.*;

public class SBlock extends Block {

    public SBlock(int xCenter, int yCenter) {
        super(xCenter, yCenter);
        color = Color.GREEN;
        cells = new Pair[2][4];

        cells[0] = new Pair[]{new Pair(-1, -1), new Pair(-1, 0), new Pair(0, 0), new Pair(0, 1)};
        cells[1] = new Pair[]{new Pair(1, -1), new Pair(0, -1), new Pair(0, 0), new Pair(-1, 0)};
    }

    public void nextState() {
        state = state + 1;
        if(state == 2)
            state = 0;
    }

    public Block newInstance() {
        SBlock newBlock = new SBlock(xCenter, yCenter);
        newBlock.cells = this.cells;
        newBlock.color = this.color;
        newBlock.state = this.state;
        return newBlock;
    }
}

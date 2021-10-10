package Block;

import Utility.Pair;

import java.awt.*;

public class ZBlock extends Block {

    public ZBlock(int xCenter, int yCenter) {
        super(xCenter, yCenter);
        color = Color.RED;
        cells = new Pair[2][4];

        cells[0] = new Pair[]{new Pair(0, -1), new Pair(0, 0), new Pair(-1, 0), new Pair(-1, 1)};
        cells[1] = new Pair[]{new Pair(-1, -1), new Pair(0, -1), new Pair(0, 0), new Pair(1, 0)};
    }

    public void nextState() {
        state = state + 1;
        if(state == 2)
            state = 0;
    }

    public Block newInstance() {
        ZBlock newBlock = new ZBlock(xCenter, yCenter);
        newBlock.cells = this.cells;
        newBlock.color = this.color;
        newBlock.state = this.state;
        return newBlock;
    }
}

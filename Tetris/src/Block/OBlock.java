package Block;

import Utility.Pair;

import java.awt.*;

public class OBlock extends Block {

    public OBlock(int xCenter, int yCenter) {
        super(xCenter, yCenter);
        color = Color.YELLOW;
        cells = new Pair[1][4];

        cells[0] = new Pair[]{new Pair(-1, 0), new Pair(-1, 1), new Pair(0, 0), new Pair(0, 1)};
    }

    public void nextState() {}

    public Block newInstance() {
        OBlock newBlock = new OBlock(xCenter, yCenter);
        newBlock.cells = this.cells;
        newBlock.color = this.color;
        newBlock.state = this.state;
        return newBlock;
    }
}

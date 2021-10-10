package Block;

import Utility.Pair;

import java.awt.*;

public class IBlock extends Block implements Cloneable{

    public IBlock(int xCenter, int yCenter) {
        super(xCenter, yCenter);
        color = Color.CYAN;
        cells = new Pair[2][4];

        cells[0] = new Pair[]{new Pair(0, -1), new Pair(0, 0), new Pair(0, 1), new Pair(0, 2)};
        cells[1] = new Pair[]{new Pair(-1, 0), new Pair(0, 0), new Pair(1, 0), new Pair(2, 0)};
    }

    public void nextState() {
        state = state + 1;
        if(state == 2)
            state = 0;
    }

    public IBlock newInstance() {
        IBlock newBlock = new IBlock(xCenter, yCenter);
        newBlock.cells = this.cells;
        newBlock.color = this.color;
        newBlock.state = this.state;
        return newBlock;
    }
}

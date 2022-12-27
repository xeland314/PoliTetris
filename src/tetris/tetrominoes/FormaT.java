package tetris.tetrominoes;

import java.awt.Color;

public class FormaT extends TetrominoeBase {

	private static final long serialVersionUID = 1L;
    private static int[][] silueta = {
        {1, 1, 1},
        {0, 1, 0}
    };

    public FormaT() {
        super("T", silueta, Color.MAGENTA);
    }

}
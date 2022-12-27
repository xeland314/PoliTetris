package tetris.tetrominoes;

import java.awt.Color;

public class FormaS extends TetrominoeBase {

	private static final long serialVersionUID = 1L;
    private static int[][] silueta = {
        {0, 1, 1},
        {1, 1, 0}
    };

    public FormaS() {
        super("S", silueta, Color.RED);
    }
}
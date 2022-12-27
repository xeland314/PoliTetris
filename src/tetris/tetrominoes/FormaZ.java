package tetris.tetrominoes;

import java.awt.Color;

public class FormaZ extends TetrominoeBase {

	private static final long serialVersionUID = 1L;
    private static int[][] silueta = {
        {1, 1, 0},
        {0, 1, 1}
    };

    public FormaZ() {
        super("Z", silueta, Color.GREEN);
    }
}
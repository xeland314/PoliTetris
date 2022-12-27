package tetris.tetrominoes;

import java.awt.Color;

public class FormaI extends TetrominoeBase {

	private static final long serialVersionUID = 1L;
    private static int[][] silueta = {
        {1},
        {1},
        {1},
        {1}
    };

    public FormaI() {
        super("I", silueta, Color.CYAN);
    }

}
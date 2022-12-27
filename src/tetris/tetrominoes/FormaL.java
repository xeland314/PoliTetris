package tetris.tetrominoes;

import java.awt.Color;

public class FormaL extends TetrominoeBase {

	private static final long serialVersionUID = 1L;
    private static int[][] silueta = {
            {1, 0},
            {1, 0},
            {1, 1}
    };

    public FormaL() {
        super("L", silueta, Color.ORANGE);
    }
}
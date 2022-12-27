package tetris.tetrominoes;

import java.awt.Color;

public class FormaJ extends TetrominoeBase {

	private static final long serialVersionUID = 1L;
    private static int[][] silueta = {
            {0, 1},
            {0, 1},
            {1, 1}
    };

    public FormaJ() {
        super("J", silueta, Color.BLUE);
    }
}
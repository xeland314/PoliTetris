package tetris.tetrominoes;

import java.awt.Color;

public class FormaO extends TetrominoeBase {

	private static final long serialVersionUID = 1L;
    private static int[][] silueta = {
        {1, 1},
        {1, 1}
    };

    public FormaO() {
        super("O", silueta, Color.YELLOW);
    }

    @Override
    public void rotar() {
    }
}
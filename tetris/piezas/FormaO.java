package tetris.piezas;

import java.awt.Color;

public class FormaO extends Forma {

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
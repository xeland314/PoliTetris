package tetris.piezas;

import java.awt.Color;

public class FormaS extends Forma {

    private static int[][] silueta = {
        {0, 1, 1},
        {1, 1, 0}
    };

    public FormaS() {
        super("S", silueta, Color.RED);
    }
}
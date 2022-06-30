package tetris.piezas;

import java.awt.Color;

public class FormaT extends Forma {

    private static int[][] silueta = {
        {1, 1, 1},
        {0, 1, 0}
    };

    public FormaT() {
        super("T", silueta, Color.MAGENTA);
    }

}
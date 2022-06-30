package tetris.piezas;

import java.awt.Color;

public class FormaZ extends Forma {

    private static int[][] silueta = {
        {1, 1, 0},
        {0, 1, 1}
    };

    public FormaZ() {
        super("Z", silueta, Color.GREEN);
    }
}
package tetris.piezas;

import java.awt.Color;

public class FormaJ extends Forma {

    private static int[][] silueta = {
            {0, 1},
            {0, 1},
            {1, 1}
    };

    public FormaJ() {
        super("J", silueta, Color.BLUE);
    }
}
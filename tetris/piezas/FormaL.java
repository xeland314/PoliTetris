package tetris.piezas;

import java.awt.Color;

public class FormaL extends Forma {

    private static int[][] silueta = {
            {1, 0},
            {1, 0},
            {1, 1}
    };

    public FormaL() {
        super("L", silueta, Color.ORANGE);
    }
}
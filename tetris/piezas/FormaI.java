package tetris.piezas;

import java.awt.Color;

public class FormaI extends Forma {

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
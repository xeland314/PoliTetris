package tetris.saved;
import tetris.paneles.TablaDePuntuaciones;
import tetris.paneles.Tablero;
import tetris.paneles.VisualizadorDeTetrominoes;

public class BlankTetris {

	public static void main(String[] args) {
		// Reset All
		Tablero t = new Tablero();
		VisualizadorDeTetrominoes v = new VisualizadorDeTetrominoes();
		TablaDePuntuaciones p = new TablaDePuntuaciones();
		t.setConfiguracionInicial();
		v.setConfiguracionInicial();
		p.setConfiguraciónInicial();
	}

}

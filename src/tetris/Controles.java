package tetris;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import tetris.paneles.Tablero;
import tetris.tetrominoes.TetrominoesMovibles;

public class Controles extends KeyAdapter {
    
    private TetrominoesMovibles tablero; 
    private Jugable tetris;

    public Controles(Tablero tablero, Tetris tetris){
        this.tablero = tablero;
        this.tetris = tetris;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int keycode = e.getKeyCode();

        switch (keycode) {
            case KeyEvent.VK_LEFT:
                this.tablero.moverIzquierdaTetrominoe();
                break;
            case KeyEvent.VK_RIGHT:
                this.tablero.moverDerechaTetrominoe();
                break;
            case KeyEvent.VK_DOWN:
                this.tablero.moverAbajoTetrominoe();
                break;
            case KeyEvent.VK_UP:
                this.tablero.rotarTetrominoe();
                break;
            case KeyEvent.VK_SPACE:
                this.tablero.moverHaciaElFondoElTetrominoe();
                break;
            case 'p':
                this.tetris.pausarJuego();
                break;
            case 'P':
                this.tetris.pausarJuego();
                break;
            case 'r':
                this.tetris.reiniciarJuego();
                break;
            case 'R':
                this.tetris.reiniciarJuego();
                break;
            case '1':
                this.tetris.setDificultadFácil();
                break;            
            case '2':
                this.tetris.setDificultadNormal();
                break;            
            case '3':
                this.tetris.setDificultadDifícil();
                break;
            case 't':
            	this.tetris.abrirTablaDePuntuaciones();
            	break;
            case 'T':
            	this.tetris.abrirTablaDePuntuaciones();
            	break;
            default:
                break;
        }
    } 
}
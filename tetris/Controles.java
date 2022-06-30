package tetris;

import java.awt.event.KeyEvent;

import tetris.paneles.Tablero;
import tetris.piezas.PiezasMovibles;

import java.awt.event.KeyAdapter;

public class Controles extends KeyAdapter {
    
    private PiezasMovibles tablero; 
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
                this.tablero.moverIzquierdaPieza();
                break;
            case KeyEvent.VK_RIGHT:
                this.tablero.moverDerechaPieza();
                break;
            case KeyEvent.VK_DOWN:
                this.tablero.moverAbajoPieza();
                break;
            case KeyEvent.VK_UP:
                this.tablero.rotarPieza();
                break;
            case KeyEvent.VK_SPACE:
                this.tablero.moverHaciaElFondoPieza();
                break;
            case 'p':
                this.tetris.pausarJuego();
                break;
            case 'P':
                this.tetris.pausarJuego();
                break;
            case 'i':
                this.tetris.iniciarJuego();
                break;
            case 'I':
                this.tetris.iniciarJuego();
                break;
            case KeyEvent.VK_F2:
                this.tetris.setDificultadFácil();
                break;
            case KeyEvent.VK_F3:
                this.tetris.setDificultadNormal();
                break;
            case KeyEvent.VK_F4:
                this.tetris.setDificultadDifícil();
                break;
            default:
                break;
        }
    }
}
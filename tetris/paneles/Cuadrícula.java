package tetris.paneles;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public abstract class Cuadrícula extends JPanel {

    protected int COLUMNAS, FILAS, ALTO, ANCHO, TAMAÑO_BLOQUE;
    protected Color COLOR_DE_CUADRICULA;

    public Cuadrícula(int columnas, int filas, int tamañoBloque, Color color) {
        this.COLUMNAS = columnas;
        this.FILAS = filas;
        this.TAMAÑO_BLOQUE = tamañoBloque;
        this.COLOR_DE_CUADRICULA = color;
        this.ALTO = this.TAMAÑO_BLOQUE * this.FILAS;
        this.ANCHO = this.TAMAÑO_BLOQUE * this.COLUMNAS;
    }

    public Cuadrícula() {
    }

    // Este método genera una cuadrícula en el JPanel
    @Override
    public void paint(Graphics pintor) {
        super.paint(pintor);
        pintor.setColor(this.COLOR_DE_CUADRICULA);
        for (int columna = 0; columna < this.COLUMNAS; columna++) {
            for (int fila = 0; fila < this.FILAS; fila++) {
                pintor.drawRect(
                    columna * this.TAMAÑO_BLOQUE,
                    fila * this.TAMAÑO_BLOQUE,
                    this.TAMAÑO_BLOQUE - 1,
                    this.TAMAÑO_BLOQUE - 1);
            }
        }
    }

}
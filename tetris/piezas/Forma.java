package tetris.piezas;

import java.awt.Color;
import java.lang.Cloneable;
import java.io.Serializable;

public abstract class Forma implements Movimiento, Serializable, Cloneable, Comparable<Forma> {

    private int[][] figura;     // Matriz de coordenadas
    private Color color;        // Color de la figura
    private int x, y;           // Coordenadas de la forma
    private String nombre;      // Identificador único

    public Forma(String nombre, int[][] figura, Color color) {
        this.nombre = nombre;
        this.figura = figura;
        this.color = color;
        this.x = 0;
        this.y = 0;
    }

    public Forma() {
    }

    public String getNombre() {
        return this.nombre;
    }

    public int[][] getFigura() {
        return this.figura;
    }

    public Color getColor() {
        return this.color;
    }

    public int getFilas() {
        return this.figura.length;
    }

    public int getColumnas() {
        return this.figura[0].length;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean tieneLaCasillaVacía(int fila, int columna) {
        return this.figura[fila][columna] == 0;
    }

    public void moverArriba() {
        this.y--;
    }

    public void moverAbajo() {
        this.y++;
    }

    public void moverDerecha() {
        this.x++;
    }

    public void moverIzquierda() {
        this.x--;
    }

    public void rotar() {
        int filas = getColumnas();
        int columnas = getFilas();
        int[][] tempFigura = new int[filas][columnas];
        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {
                tempFigura[fila][columna] = this.figura[columnas - columna - 1][fila];
            }
        }
        this.figura = tempFigura;
    }

    @Override
    public int compareTo(Forma pieza) {
        return this.getNombre().compareTo(pieza.getNombre());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Forma f = (Forma) super.clone();
        return f;
    }

}
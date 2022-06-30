package tetris.paneles;

import tetris.piezas.*;
import tetris.piezas.PiezasMovibles;
import tetris.Puntuación;
import tetris.dificultades.*;

import java.awt.Graphics;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class Tablero extends Cuadrícula implements PiezasMovibles {

    private Forma pieza;
    private Color[][] casillas;
    private boolean estáEnJuego;
    private Puntuación puntuación;
    private Color CASILLA_VACÍA = Color.WHITE;

    public Tablero() {
        super(10, 22, 25, Color.LIGHT_GRAY);
        this.setBounds(50, 0, this.ANCHO, this.ALTO);
        this.casillas = new Color[this.FILAS][this.COLUMNAS];
        this.llenarTableroDeCasillasVacías();
        this.estáEnJuego = true;
        this.inicializarTablero();
    }

    public void crearConfiguracionInicial() {
        this.pieza = new FormaO();
        this.puntuación = new Puntuación(new Normal());
        this.guardarTablero();
    }

    /*
    public static void main(String[] args) {
        Tablero t = new Tablero();
        t.crearConfiguracionInicial();
    }
    */
    
    private void llenarTableroDeCasillasVacías() {
        for(int fila = 0; fila < this.FILAS; fila++){
            for(int columna = 0; columna < this.COLUMNAS;columna++){
                this.casillas[fila][columna] = CASILLA_VACÍA;
            }
        }
    }

    public void setPieza(Forma pieza) {
        this.pieza = pieza;
        this.pieza.setX(this.COLUMNAS / 2);
    }

    public void setDificultad(Dificultad dificultad) {
        this.puntuación.setDificultad(dificultad);
    }

    public void resetPuntuación(Dificultad dificultad) {
        this.puntuación = new Puntuación(dificultad);
    }

    public Puntuación getPuntuación() {
        return this.puntuación;
    }

    public Forma getPieza() {
        return this.pieza;
    }

    private boolean esUnaCasillaVacía(int fila, int columna){
        return casillas[fila][columna] == null || casillas[fila][columna] == CASILLA_VACÍA;
    }

    public void removerFilas() {
        boolean esUnaFilaLlena;
        for (int fila = this.FILAS - 1; fila >= 0; fila--) {
            esUnaFilaLlena = true;
            for (int columna = 0; columna < this.COLUMNAS; columna++) {
                if (esUnaCasillaVacía(fila, columna)) {
                    esUnaFilaLlena = false;
                    break;
                }
            }
            if (esUnaFilaLlena) {
                this.borrarFila(fila);
                this.moverUnaFilaHaciaAbajo(fila);
                this.borrarFila(0);
                fila++;
                repaint();
                this.puntuación.aumentarFilasDestruidas();
            }
        }
    }

    private void moverUnaFilaHaciaAbajo(int filas) {
        for (int fila = filas; fila > 0; fila--) {
            for (int columna = 0; columna < this.COLUMNAS; columna++) {
                this.casillas[fila][columna] = this.casillas[fila - 1][columna];
            }
        }
    }

    private void borrarFila(int fila) {
        for (int columna = 0; columna < this.COLUMNAS; columna++) {
            this.casillas[fila][columna] = CASILLA_VACÍA;
        }
    }

    private boolean sePuedeMoverA(int desplazamientoX, int desplazamientoY) {
        int columnas = this.pieza.getColumnas();
        int filas = this.pieza.getFilas();
        for (int columna = 0; columna < columnas; columna++) {
            for (int fila = filas - 1; fila >= 0; fila--) {
                if (!this.pieza.tieneLaCasillaVacía(fila, columna)) {
                    int x = columna + pieza.getX() + desplazamientoX;
                    int y = fila + pieza.getY() + desplazamientoY;
                    if (y < 0 || y >= this.FILAS || !esUnaCasillaVacía(y, x)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean sePuedeMoverAbajo() {
        if (pieza.getY() + pieza.getFilas() == this.FILAS) {
            return false;
        }
        return sePuedeMoverA(0, 1);
    }

    public boolean puedeMoverLaPiezaAbajo() {
        return sePuedeMoverAbajo();
    }

    private boolean sePuedeMoverALaIzquierda() {
        if (pieza.getX() == 0) {
            return false;
        }
        return sePuedeMoverA(-1, 0);
    }

    private boolean sePuedeMoverALaDerecha() {
        if ((pieza.getX() + pieza.getColumnas() == this.COLUMNAS)) {
            return false;
        }
        return sePuedeMoverA(1, 0);
    }
    
    private boolean sePuedeRotarLaPieza(){
        return (pieza.getX() + pieza.getFilas()) <= this.COLUMNAS;
    }


    @Override
    protected void paintComponent(Graphics pintor) {
        super.paintComponent(pintor);
        this.dibujarFondo(pintor);
        this.dibujarPieza(pintor);
        this.guardarTablero();
    }

    public void guardarPieza() {
        for (int fila = 0; fila < this.pieza.getFilas(); fila++) {
            for (int columna = 0; columna < this.pieza.getColumnas(); columna++) {
                if (!pieza.tieneLaCasillaVacía(fila, columna)) {
                    casillas[fila + pieza.getY()][columna + pieza.getX()] = this.pieza.getColor();
                }
            }
        }
    }

    private void dibujarFondo(Graphics pintor) {
        for (int fila = 0; fila < this.FILAS; fila++) {
            for (int columna = 0; columna < this.COLUMNAS; columna++) {
                if (!esUnaCasillaVacía(fila, columna)) {
                    pintor.setColor(casillas[fila][columna]);
                    pintor.fillRect(columna * TAMAÑO_BLOQUE, fila * TAMAÑO_BLOQUE, TAMAÑO_BLOQUE, TAMAÑO_BLOQUE);
                }
            }
        }
    }

    private void dibujarPieza(Graphics pintor) {
        pintor.setColor(pieza.getColor());
        for (int fila = 0; fila < pieza.getFilas(); fila++) {
            for (int columna = 0; columna < pieza.getColumnas(); columna++) {
                if (!pieza.tieneLaCasillaVacía(fila, columna)) {
                    pintor.fillRect((columna + pieza.getX()) * TAMAÑO_BLOQUE, (fila + pieza.getY()) * TAMAÑO_BLOQUE,
                            TAMAÑO_BLOQUE, TAMAÑO_BLOQUE);
                }
            }
        }
    }

    @Override
    public void moverAbajoPieza() {
        if (this.sePuedeMoverAbajo()) {
            this.pieza.moverAbajo();
            repaint();
        }
    }

    @Override
    public void moverIzquierdaPieza() {
        if (this.sePuedeMoverALaIzquierda()) {
            this.pieza.moverIzquierda();
            this.repaint();
        }
    }

    @Override
    public void moverDerechaPieza() {
        if (this.sePuedeMoverALaDerecha()) {
            this.pieza.moverDerecha();
            this.repaint();
        }
    }

    @Override
    public void moverHaciaElFondoPieza() {
        while (this.sePuedeMoverAbajo()) {
            this.pieza.moverAbajo();
            this.repaint();
        }
    }

    @Override
    public void rotarPieza() {
        if(this.sePuedeRotarLaPieza()){
            this.pieza.rotar();
            this.repaint();
        }
    }

    public boolean estáLaPiezaFueraDelLímite() {
        for (int columna = 0; columna < this.COLUMNAS; columna++) {
            if ((this.casillas[0][columna] != CASILLA_VACÍA)||(this.casillas[1][columna] != CASILLA_VACÍA)) {
                return true;
            }
        }
        return false;
    }

    public void limpiar() {
        for (int fila = 0; fila < this.FILAS; fila++) {
            this.borrarFila(fila);
        }
        this.repaint();
    }

    public boolean estáPausado() {
        return this.estáEnJuego;
    }

    public void pausar() {
        this.estáEnJuego = (this.estáEnJuego) ? false : true;
    }


	private void guardarTablero(){
		try {
            ObjectOutputStream archivo = new ObjectOutputStream(
                new FileOutputStream("tetris/configuración/Tablero.dat"));
            archivo.writeObject(this.casillas);
            archivo.close();
        } catch (Exception e) {
            System.out.println( e.getMessage() );
        }
        try {
            ObjectOutputStream archivo = new ObjectOutputStream(
                new FileOutputStream("tetris/configuración/Puntuación.dat"));
            archivo.writeObject(this.puntuación);
            archivo.close();
        } catch (Exception e) {
            System.out.println( e.getMessage() );
        }
        try {
            ObjectOutputStream archivo = new ObjectOutputStream(
                new FileOutputStream("tetris/configuración/Pieza.dat"));
            archivo.writeObject(this.pieza);
            archivo.close();
        } catch (Exception e) {
            System.out.println( e.getMessage() );
        }
	}

	private void inicializarTablero(){
		try {
            ObjectInputStream archivo = new ObjectInputStream(
                new FileInputStream("tetris/configuración/Tablero.dat"));
			this.casillas = (Color[][]) archivo.readObject();
            archivo.close();
		}  catch (Exception e) {
            System.out.println( e.getMessage() );
        }
        try {
            ObjectInputStream archivo = new ObjectInputStream(
                new FileInputStream("tetris/configuración/Puntuación.dat"));
			this.puntuación = (Puntuación) archivo.readObject();
            archivo.close();
		}  catch (Exception e) {
            System.out.println( e.getMessage() );
        }
        try {
            ObjectInputStream archivo = new ObjectInputStream(
                new FileInputStream("tetris/configuración/Pieza.dat"));
			this.pieza = (Forma) archivo.readObject();
            archivo.close();
		}  catch (Exception e) {
            System.out.println( e.getMessage() );
        }
	}


}
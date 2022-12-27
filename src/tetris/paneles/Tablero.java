package tetris.paneles;

import tetris.Puntuación;
import tetris.dificultades.*;
import tetris.tetrominoes.*;

import java.awt.Graphics;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class Tablero extends Cuadrícula implements TetrominoesMovibles {

	private static final long serialVersionUID = 1L;
    private TetrominoeBase tetrominoe;
    private Color[][] casillas;
    private boolean estáEnJuego;
    private Puntuación puntuación;
    private Color CASILLA_VACÍA = null; // Sin importar lo que suceda, ¡NO ASIGNES UN COLOR! Para eso está el fondo :3

    public Tablero() {
        super(10, 22, 25, Color.LIGHT_GRAY);
        this.setBounds(50, 0, this.ANCHO, this.ALTO);
        this.casillas = new Color[this.FILAS][this.COLUMNAS];
        this.llenarTableroDeCasillasVacías();
        this.inicializarTablero();        
        this.estáEnJuego = true;
    }
   
    public void setConfiguracionInicial() {
        this.tetrominoe = new FormaO();
        this.puntuación = new Puntuación(new Normal());
        this.llenarTableroDeCasillasVacías();
        this.guardarTablero();
        System.out.println("Nuevo tablero en blanco generado.");
    }
    
    private void llenarTableroDeCasillasVacías() {
        for(int fila = 0; fila < this.FILAS; fila++){
            for(int columna = 0; columna < this.COLUMNAS; columna++){
                this.casillas[fila][columna] = CASILLA_VACÍA;
            }
        }
    }
    
    public void setPieza(TetrominoeBase tetrominoe) {
        this.tetrominoe = tetrominoe;
        this.tetrominoe.setX(this.COLUMNAS / 2);
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

    public TetrominoeBase getPieza() {
        return this.tetrominoe;
    }

    private boolean esUnaCasillaVacía(int fila, int columna){
        return casillas[fila][columna] == null || casillas[fila][columna] == CASILLA_VACÍA;
    }

    public void removerFilas() {
        this.puntuación.aumentarPiezasUsadas();
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

    private boolean puedeMoverseA(int desplazamientoX, int desplazamientoY) {
        int columnas = this.tetrominoe.getColumnas();
        int filas = this.tetrominoe.getFilas();
        for (int columna = 0; columna < columnas; columna++) {
            for (int fila = filas - 1; fila >= 0; fila--) {
                if (!this.tetrominoe.tieneLaCasillaVacía(fila, columna)) {
                    int x = columna + tetrominoe.getX() + desplazamientoX;
                    int y = fila + tetrominoe.getY() + desplazamientoY;
                    if (y < 0 || y >= this.FILAS || !this.esUnaCasillaVacía(y, x)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean puedeMoverseAbajo() {
        if (tetrominoe.getY() + tetrominoe.getFilas() == this.FILAS) {
            return false;
        }
        return puedeMoverseA(0, 1);
    }

    public boolean puedeMoverLaPiezaAbajo() {
        return puedeMoverseAbajo();
    }

    private boolean puedeMoverseALaIzquierda() {
        if (tetrominoe.getX() == 0) {
            return false;
        }
        return puedeMoverseA(-1, 0);
    }

    private boolean puedeMoverseALaDerecha() {
        if ((tetrominoe.getX() + tetrominoe.getColumnas() == this.COLUMNAS)) {
            return false;
        }
        return puedeMoverseA(1, 0);
    }
    
    private boolean puedeRotarLaPieza(){
        return (tetrominoe.getX() + tetrominoe.getFilas()) <= this.COLUMNAS;
    }
    
    @Override
    protected void paintComponent(Graphics pintor) {
        super.paintComponent(pintor);
        this.dibujarFondo(pintor);
        this.dibujarPieza(pintor);
        this.guardarTablero();
    }

    public void guardarPieza() {
        for (int fila = 0; fila < this.tetrominoe.getFilas(); fila++) {
            for (int columna = 0; columna < this.tetrominoe.getColumnas(); columna++) {
                if (!tetrominoe.tieneLaCasillaVacía(fila, columna)) {
                    casillas[fila + tetrominoe.getY()][columna + tetrominoe.getX()] = this.tetrominoe.getColor();
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
        pintor.setColor(tetrominoe.getColor());
        for (int fila = 0; fila < tetrominoe.getFilas(); fila++) {
            for (int columna = 0; columna < tetrominoe.getColumnas(); columna++) {
                if (!tetrominoe.tieneLaCasillaVacía(fila, columna)) {
                    pintor.fillRect((columna + tetrominoe.getX()) * TAMAÑO_BLOQUE, (fila + tetrominoe.getY()) * TAMAÑO_BLOQUE,
                            TAMAÑO_BLOQUE, TAMAÑO_BLOQUE);
                }
            }
        }
    }

    @Override
    public void moverAbajoTetrominoe() {
        if (this.puedeMoverseAbajo()) {
            this.tetrominoe.moverAbajo();
            this.repaint();
        }
    }

    @Override
    public void moverIzquierdaTetrominoe() {
        if (this.puedeMoverseALaIzquierda()) {
            this.tetrominoe.moverIzquierda();
            this.repaint();
        }
    }

    @Override
    public void moverDerechaTetrominoe() {
        if (this.puedeMoverseALaDerecha()) {
            this.tetrominoe.moverDerecha();
            this.repaint();
        }
    }

    @Override
    public void moverHaciaElFondoElTetrominoe() {
        while (this.puedeMoverseAbajo()) {
            this.tetrominoe.moverAbajo();
            this.repaint();
        }
    }

    @Override
    public void rotarTetrominoe() {
        if(this.puedeRotarLaPieza()){
            this.tetrominoe.rotar();
            this.repaint();
        }
    }

    public boolean puedeAgregarOtroTetrominoe() {
    	boolean sePuedeAgregarOtroTetrominoe = true;
        for (int columna = 0; columna < this.COLUMNAS; columna++) {
            if (!this.esUnaCasillaVacía(0, columna) || !this.esUnaCasillaVacía(1, columna)) {
               sePuedeAgregarOtroTetrominoe &= false;
            }
        }
        return sePuedeAgregarOtroTetrominoe;
    }

    public void limpiar() {
        for (int fila = 0; fila < this.FILAS; fila++) {
            this.borrarFila(fila);
        }
        this.repaint();
    }

    public boolean estáPausado() {
        return !this.estáEnJuego;
    }

    public void pausar() {
        this.estáEnJuego = !this.estáEnJuego;
    }


	private void guardarTablero(){
		try {
            ObjectOutputStream archivo = new ObjectOutputStream(
                new FileOutputStream("src/tetris/saved/Tablero.dat")
            );
            archivo.reset();
            archivo.writeObject(this.casillas);
            archivo.close();
        } catch (Exception e) {
            System.out.println( e.getMessage() );
        }
        try {
            ObjectOutputStream archivo2 = new ObjectOutputStream(
                new FileOutputStream("src/tetris/saved/Puntuacion.dat")
            );
            archivo2.reset();
            archivo2.writeObject(this.puntuación);
            archivo2.close();
        } catch (Exception e) {
            System.out.println( e.getMessage() );
        }
        try {
            ObjectOutputStream archivo3 = new ObjectOutputStream(
                new FileOutputStream("src/tetris/saved/Tetrominoe.dat")
            );
            archivo3.reset();
            archivo3.writeObject(this.tetrominoe);
            archivo3.close();
        } catch (Exception e) {
            System.out.println( e.getMessage() );
        }
	}

	private void inicializarTablero(){
		try {
            ObjectInputStream archivo = new ObjectInputStream(
                new FileInputStream("src/tetris/saved/Tablero.dat")
            );
			this.casillas = (Color[][]) (archivo.readObject());
            archivo.close();
            System.out.println("Tablero cargado correctamente.");
		}  catch (Exception e) {
            System.out.println( e.getMessage() );
        }
        try {
            ObjectInputStream archivo2 = new ObjectInputStream(
                new FileInputStream("src/tetris/saved/Puntuacion.dat")
            );
			this.puntuación = (Puntuación) (archivo2.readObject());
            archivo2.close();
            System.out.println("Puntuación cargada correctamente.");
		}  catch (Exception e) {
            System.out.println( e.getMessage() );
        }
        try {
            ObjectInputStream archivo3 = new ObjectInputStream(
                new FileInputStream("src/tetris/saved/Tetrominoe.dat")
            );
			this.tetrominoe = (TetrominoeBase) (archivo3.readObject());
            archivo3.close();
            System.out.println("Tetrominoe cargado correctamente.");
		}  catch (Exception e) {
            System.out.println(e.getMessage());
        }
	}


}
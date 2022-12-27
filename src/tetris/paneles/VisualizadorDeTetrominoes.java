package tetris.paneles;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import tetris.tetrominoes.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class VisualizadorDeTetrominoes extends Cuadrícula {
	
	private static final long serialVersionUID = 1L;
	
	private TetrominoeBase tetrominoeActual;
	private ArrayList<TetrominoeBase> tetrominoes;

	public VisualizadorDeTetrominoes() {
		super(6, 6, 25, Color.LIGHT_GRAY);
		this.setBounds(386, 37, this.ALTO, this.ANCHO);
		this.cargarPiezaGuardada();
        this.inicializarPiezas();
	}

    public void setConfiguracionInicial() {
        this.tetrominoeActual = new FormaT();
        this.guardarPieza();
        System.out.println("Pieza nueva creada aleatoriamente.");
    }

    public TetrominoeBase getPieza() {
        TetrominoeBase tetrominoeAnterior = this.tetrominoeActual;
        this.elegirNuevaPiezaAlAzar();
        this.guardarPieza();
        this.repaint();
        return tetrominoeAnterior;
    }

	private void elegirNuevaPiezaAlAzar() {
		Random r = new Random();
		int numeroAlAzar = Math.abs(r.nextInt()) % this.tetrominoes.size();
		try {
            this.tetrominoeActual = (TetrominoeBase) this.tetrominoes.get(numeroAlAzar).clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
	}

	private void inicializarPiezas() {
		this.tetrominoes = new ArrayList<TetrominoeBase>();
		this.tetrominoes.add(new FormaI());
		this.tetrominoes.add(new FormaJ());
		this.tetrominoes.add(new FormaL());
		this.tetrominoes.add(new FormaO());
		this.tetrominoes.add(new FormaS());
		this.tetrominoes.add(new FormaT());
		this.tetrominoes.add(new FormaZ());
	}

	@Override
    protected void paintComponent(Graphics pintor) {
        super.paintComponent(pintor);
		this.mostrarSiguientePieza(pintor);
    }

	private void mostrarSiguientePieza(Graphics pintor){
        this.tetrominoeActual.setX(1);
        this.tetrominoeActual.setY(1);
        pintor.setColor(this.tetrominoeActual.getColor());
		for (int fila = 0; fila < this.tetrominoeActual.getFilas(); fila++) {
            for (int columna = 0; columna < this.tetrominoeActual.getColumnas(); columna++) {
                if (!this.tetrominoeActual.tieneLaCasillaVacía(fila, columna)) {
                    pintor.fillRect(
						(columna + this.tetrominoeActual.getX()) * this.TAMAÑO_BLOQUE,
						(fila + this.tetrominoeActual.getY()) * this.TAMAÑO_BLOQUE,
						this.TAMAÑO_BLOQUE, 
						this.TAMAÑO_BLOQUE
					);
                }
            }
        }
	}

	private void guardarPieza() {
        try {
            ObjectOutputStream archivo = new ObjectOutputStream(
                new FileOutputStream("src/tetris/saved/SiguienteTetrominoe.dat")
            );
            archivo.reset();
            archivo.writeObject(this.tetrominoeActual);
            archivo.close();
        } catch (Exception e) {
            System.out.println( e.getMessage() );
        }
	}

	private void cargarPiezaGuardada() {
        try {
            ObjectInputStream archivo = new ObjectInputStream(
                new FileInputStream("src/tetris/saved/SiguienteTetrominoe.dat")
            );
			this.tetrominoeActual = (TetrominoeBase) archivo.readObject();
            archivo.close();
            System.out.println("Siguiente tetrominoe cargado correctamente.");
		}  catch (Exception e) {
            System.out.println( e.getMessage() );
        }
        this.guardarPieza();
	}
}
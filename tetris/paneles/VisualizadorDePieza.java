package tetris.paneles;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import tetris.piezas.*;

public final class VisualizadorDePieza extends Cuadrícula {
	
	private Forma piezaActual;
	private ArrayList<Forma> piezas;

	public VisualizadorDePieza() {
		super(6, 6, 25, Color.LIGHT_GRAY);
		this.setBounds(386, 37, this.ALTO, this.ANCHO);
		this.cargarPiezaGuardada();
        this.inicializarPiezas();
	}

    public void crearConfiguracionInicial() {
        this.piezaActual = new FormaT();
        this.guardarPieza();
    }

    /*
    public static void main(String[] args) {
        VisualizadorDePieza v = new VisualizadorDePieza();
        v.crearConfiguracionInicial();
    }
    */

    public Forma getPieza() {
        Forma piezaAnterior = this.piezaActual;
        this.elegirNuevaPiezaAlAzar();
        this.guardarPieza();
        this.repaint();
        return piezaAnterior;
    }

	private void elegirNuevaPiezaAlAzar() {
		Random r = new Random();
		int numeroAlAzar = Math.abs(r.nextInt()) % this.piezas.size();
		try {
            this.piezaActual = (Forma) this.piezas.get(numeroAlAzar).clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
	}

	private void inicializarPiezas() {
		this.piezas = new ArrayList<Forma>();
		this.piezas.add(new FormaI());
		this.piezas.add(new FormaJ());
		this.piezas.add(new FormaL());
		this.piezas.add(new FormaO());
		this.piezas.add(new FormaS());
		this.piezas.add(new FormaT());
		this.piezas.add(new FormaZ());
	}

	@Override
    protected void paintComponent(Graphics pintor) {
        super.paintComponent(pintor);
		this.mostrarSiguientePieza(pintor);
    }

	private void mostrarSiguientePieza(Graphics pintor){
        this.piezaActual.setX(1);
        this.piezaActual.setY(1);
        pintor.setColor(this.piezaActual.getColor());
		for (int fila = 0; fila < this.piezaActual.getFilas(); fila++) {
            for (int columna = 0; columna < this.piezaActual.getColumnas(); columna++) {
                if (!this.piezaActual.tieneLaCasillaVacía(fila, columna)) {
                    pintor.fillRect(
						(columna + this.piezaActual.getX()) * this.TAMAÑO_BLOQUE,
						(fila + this.piezaActual.getY()) * this.TAMAÑO_BLOQUE,
						this.TAMAÑO_BLOQUE, 
						this.TAMAÑO_BLOQUE
					);
                }
            }
        }
	}

	private void guardarPieza(){
        try {
            ObjectOutputStream archivo = new ObjectOutputStream(
                new FileOutputStream("tetris/configuración/SiguientePieza.dat"));
            archivo.writeObject(this.piezaActual);
            archivo.close();
        } catch (Exception e) {
            System.out.println( e.getMessage() );
        }
	}

	private void cargarPiezaGuardada(){
        try {
            ObjectInputStream archivo = new ObjectInputStream(
                new FileInputStream("tetris/configuración/SiguientePieza.dat"));
			this.piezaActual = (Forma) archivo.readObject();
            archivo.close();
		}  catch (Exception e) {
            System.out.println( e.getMessage() );
        }
	}
}
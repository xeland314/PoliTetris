package tetris;

import java.io.Serializable;
import tetris.dificultades.*;

public class Puntuación implements Serializable {

	private static final long serialVersionUID = 1L;
	private int filasDestruídas, piezasUsadas, nivel, puntuacion;
    private Dificultad dificultad;

    public Puntuación(Dificultad dificultad) {
        this.filasDestruídas = 0;
        this.nivel = 1;
        this.puntuacion = 0;
        this.piezasUsadas = 0;
        this.dificultad = dificultad;
    }

    public void aumentarFilasDestruidas() {
        this.filasDestruídas++;
        this.puntuacion += dificultad.getGetValorDeFilaDestruída();
    }

    public void aumentarPiezasUsadas() {
        this.piezasUsadas++;
        if(this.piezasUsadas / 20 > this.nivel) {
            this.nivel++;
            this.dificultad.aumentarTiempoDeCaída();
        }
    }

    public void setDificultad(Dificultad dificultad) {
        this.dificultad = dificultad;
    }

    public Dificultad getDificultad() {
        return this.dificultad;
    }

    public int getFilasDestruídas() {
        return this.filasDestruídas;
    }

    public int getNivelActual() {
        return this.nivel;
    }

    public int getPuntaje() {
        return this.puntuacion;
    }
    
    @Override
    public String toString() {
    	return String.format(
    		"%d,%d,%d,%s",
    		this.puntuacion,
    		this.filasDestruídas,
    		this.nivel,
    		this.dificultad.toString()
    	);
    }
}
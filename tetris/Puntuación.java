package tetris;

import java.io.Serializable;

import tetris.dificultades.*;

public class Puntuación implements Serializable {

    private int filasDestruídas, piezasUsadas, nivel, puntuacion;
    private Dificultad dificultad;

    public Puntuación(Dificultad dificultad) {
        this.filasDestruídas = 0;
        this.nivel = 1;
        this.puntuacion = 0;
        this.piezasUsadas = 0;
        this.dificultad = dificultad;
    }

    public void aumentarFilasDestruidas(){
        this.filasDestruídas += 1;
        this.puntuacion += dificultad.getGetValorDeFilaDestruída();
    }

    public void aumentarPiezasUsadas(){
        this.piezasUsadas += 1;
        this.nivel += this.piezasUsadas / 15;
    }

    public void setDificultad(Dificultad dificultad) {
        this.dificultad = dificultad;
    }

    public Dificultad getDificultad() {
        return this.dificultad;
    }

    public int getFilasDestruídas(){
        return this.filasDestruídas;
    }

    public int getNivelActual(){
        return this.nivel;
    }

    public int getPuntaje(){
        return this.puntuacion;
    }
}
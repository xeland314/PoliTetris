package tetris.dificultades;

import java.io.Serializable;

public abstract class Dificultad implements Serializable{
    
    private int tiempoDeCaída;
    private int valorDeFilaDestruída;

    public Dificultad(int tiempoDeCaída, int valorDeFilaDestruída) {
        this.tiempoDeCaída = tiempoDeCaída;
        this.valorDeFilaDestruída = valorDeFilaDestruída;
    }

    public Dificultad(){

    }

    public int getTiempoDeCaída(){
        return this.tiempoDeCaída;
    }

    public int getGetValorDeFilaDestruída(){
        return this.valorDeFilaDestruída;
    }

}
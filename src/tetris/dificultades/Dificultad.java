package tetris.dificultades;

import java.io.Serializable;

public abstract class Dificultad implements Serializable {

	private static final long serialVersionUID = 1L;
	private int tiempoDeCaída;
    private int valorDeFilaDestruída;

    public Dificultad(int tiempoDeCaída, int valorDeFilaDestruída) {
        this.tiempoDeCaída = tiempoDeCaída;
        this.valorDeFilaDestruída = valorDeFilaDestruída;
    }

    public Dificultad(){

    }

    public void aumentarTiempoDeCaída() {
    	if(this.tiempoDeCaída > 200) {
        	this.tiempoDeCaída -= 5;
    	}
    }
    
    public int getTiempoDeCaída(){
        return this.tiempoDeCaída;
    }

    public int getGetValorDeFilaDestruída(){
        return this.valorDeFilaDestruída;
    }
    
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Dificultad) {
			return obj.toString().compareTo(this.toString()) == 0;
		}
		return false;
	}
    
}
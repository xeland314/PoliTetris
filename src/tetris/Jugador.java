package tetris;

public class Jugador {
    
    private String nombre; 
    private Puntuación puntuación;

    public Jugador(String nombre, Puntuación puntuación){
        this.nombre = nombre;
        this.puntuación = puntuación; 
    }

    public Puntuación getPuntuación(){
        return this.puntuación;
    }

    public String getNombre(){
        return this.nombre;
    }

    public String toString() {
        return String.format(
        	"%s,%s\n",
        	this.nombre,
        	this.puntuación.toString()
        );
    }
}
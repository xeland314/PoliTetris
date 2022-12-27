package tetris.dificultades;

public class Fácil extends Dificultad {
	
	private static final long serialVersionUID = 1L;
	
    public Fácil() {
        super(1300, 10);
    }

    @Override
    public String toString(){
        return "Fácil";
    }
}
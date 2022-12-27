package tetris.dificultades;

public class Normal extends Dificultad {
	
	private static final long serialVersionUID = 1L;
	
    public Normal() {
        super(800, 50);
    }
    
    @Override
    public String toString(){
        return "Normal";
    }
}
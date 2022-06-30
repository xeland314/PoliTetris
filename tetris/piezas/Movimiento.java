package tetris.piezas;

public interface Movimiento {
    abstract void rotar();
    void moverAbajo();
    void moverArriba();
    void moverIzquierda();
    void moverDerecha();    
}

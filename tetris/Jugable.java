package tetris;

public interface Jugable {
    void iniciarJuego();
    void pausarJuego();
    void abrirPanelDeConfiguración();
    void abrirTablaDePuntuaciones();
    void setDificultadFácil();
    void setDificultadNormal();
    void setDificultadDifícil();
}

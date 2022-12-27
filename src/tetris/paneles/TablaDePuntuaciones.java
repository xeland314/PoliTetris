package tetris.paneles;

import tetris.Jugador;

import java.awt.CardLayout;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

public class TablaDePuntuaciones extends JFrame{

	private static final long serialVersionUID = 1L;
	private DefaultTableModel dtm = new DefaultTableModel();

	public TablaDePuntuaciones() {
		this.loadUI();
	    this.mostrarPuntuaciones();
	}

	public void setConfiguraciónInicial() {
		String resultado = "xeland314,1300,26,5,Normal";
        try {
            ObjectOutputStream archivo = new ObjectOutputStream(
                new FileOutputStream("src/tetris/saved/Puntuaciones.dat")
            );
            archivo.reset();
            archivo.writeObject(resultado);
            archivo.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
		System.out.println("Tabla de puntuaciones reiniciada.");
	}
	
	private void mostrarPuntuaciones(){
		String datosAlmacenados = this.cargarPuntuaciones();
		String[] puntuaciones = datosAlmacenados.split("[\n]");
		String[] puntuación;
		for (String dato : puntuaciones) {
			if(dato.length() == 0){
				continue; 
			}
			puntuación = dato.split("[,]");
			this.dtm.addRow(puntuación);
		}
	}

	public void agregarNuevaPuntuación(Jugador jugador) {
		String datos = this.cargarPuntuaciones() + jugador.toString();
        try {
            ObjectOutputStream archivo = new ObjectOutputStream(
                new FileOutputStream("src/tetris/saved/Puntuaciones.dat")
            );
            archivo.writeObject(datos);
            archivo.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
	}

    private String cargarPuntuaciones() {
		String texto = "";
		try {
            ObjectInputStream archivo = new ObjectInputStream(
                new FileInputStream("src/tetris/saved/Puntuaciones.dat")
            );
			texto = (String) archivo.readObject();
            archivo.close();
		}  catch (Exception e) {
            System.out.println( e.getMessage() );
        }
        return texto;
	}

	private void loadUI() {
		this.setResizable(false);
		this.setTitle("Tetris - Puntuaciones");
		this.setBounds(100, 100, 310, 415);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(new CardLayout(0, 0));

		JDesktopPane desktopPane = new JDesktopPane();
		this.getContentPane().add(desktopPane, "name_7489119990859");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 300, 380);
		desktopPane.add(scrollPane);
		
		JTable puntuaciones = new JTable();
		String[] titulos = {"Nombre","Puntuación","Líneas","Nivel", "Dificultad"};
		dtm.setColumnIdentifiers(titulos);
		puntuaciones.setModel(dtm);
		puntuaciones.setRowSelectionAllowed(true);
		puntuaciones.setAutoscrolls(true);
		puntuaciones.setEnabled(false);
		scrollPane.setViewportView(puntuaciones);
	}
}
package tetris;

import tetris.paneles.TablaDePuntuaciones;
import tetris.paneles.Tablero;
import tetris.paneles.VisualizadorDeTetrominoes;
import tetris.dificultades.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class Tetris extends JFrame implements Jugable, ActionListener {

	private static final long serialVersionUID = 1L;
	
	private Tablero tablero;
	private VisualizadorDeTetrominoes visualizador;
	private TablaDePuntuaciones tablaDePuntuaciones;

	private JLabel cantidadDeLineas;
	private JLabel nivel;
	private JLabel puntuacion;

	private Dificultad dificultad;
	private boolean hayUnJuegoGuardado = true;
	public Timer relojInterno;

	public Tetris() {
		this.crearInterfazGráfica();
		this.tablaDePuntuaciones = new TablaDePuntuaciones();
		this.dificultad = new Normal();
		this.relojInterno = new Timer(dificultad.getTiempoDeCaída(), this);
		this.addKeyListener(new Controles(this.tablero, this));
		this.relojInterno.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(this.hayUnJuegoGuardado) {
			System.out.println("¡Juego guardado cargado correctamente!");
			this.hayUnJuegoGuardado = false;
			this.actualizarPuntuación();
			this.pausarJuego();
			return;
		}
		if(!this.tablero.puedeMoverLaPiezaAbajo()) {
			this.cambiarPieza();
		}
		if(!this.tablero.puedeAgregarOtroTetrominoe()) {
			System.out.println("\t¡Tetrominoe fuera del límite!");
			this.finalizarJuego();
		}
		this.tablero.moverAbajoTetrominoe();
	}
	
	@Override
	public void reiniciarJuego() {
		if(this.relojInterno.isRunning()){
			this.relojInterno.stop();
		}
		this.tablero.limpiar();
		this.tablero.setPieza(this.visualizador.getPieza());
		this.tablero.resetPuntuación(this.dificultad);
		this.actualizarPuntuación();
		this.relojInterno.start();
		System.out.println("Nuevo juego iniciado.");
	}

	private void cambiarPieza(){
		this.relojInterno.stop();
		this.tablero.guardarPieza();
		this.tablero.removerFilas();
		this.tablero.setPieza(this.visualizador.getPieza());
		this.actualizarPuntuación();
		this.relojInterno.start();
	}

	private void finalizarJuego(){
		this.relojInterno.stop();
		JOptionPane.showMessageDialog(null, "GAME OVER");
		Puntuación puntuación = this.tablero.getPuntuación();
		if (puntuación.getPuntaje() == 0) {
			// No almacena puntajes iguales a 0
			return;
		}
		String nombre = "";
		while(nombre.isEmpty() || nombre.isBlank()) {
			// Ingresar un nombre debe ser obligatorio
			nombre = this.ingresarNombreDelJugador();
		}
		this.tablaDePuntuaciones.agregarNuevaPuntuación(
            new Jugador(nombre, this.tablero.getPuntuación())
        );
		this.tablero.resetPuntuación(this.dificultad);
		this.actualizarPuntuación();
		this.reiniciarJuego();
		System.out.println("Juego finalizado.");
	}

	private void actualizarPuntuación() {
		this.cantidadDeLineas.setText(String.valueOf(tablero.getPuntuación().getFilasDestruídas()));
		this.nivel.setText(String.valueOf(tablero.getPuntuación().getNivelActual()));
		this.puntuacion.setText(String.valueOf(tablero.getPuntuación().getPuntaje()));
	}

	@Override
	public void pausarJuego() {
		tablero.pausar();
		this.relojInterno.stop();
		if (tablero.estáPausado()) {
			JOptionPane.showMessageDialog(
				null,
				"Juego Pausado",
				"Tetris",
				JOptionPane.INFORMATION_MESSAGE
			);
			System.out.println("\t\tJuego en pausa.");
		} else {
			this.relojInterno.start();
			System.out.println("\t\tJuego en curso.");
		}
	}

	private String ingresarNombreDelJugador() {
		String nombreEntrada;
		String solicitud = "\t\tSu puntuación es de: " + this.tablero.getPuntuación().getPuntaje() + ".\n";
		solicitud += "\t\tIngrese su nombre, por favor: \n";
		nombreEntrada = JOptionPane.showInputDialog(
			null,
			solicitud,
			"Nueva Puntuación",
			JOptionPane.QUESTION_MESSAGE
		);
		return nombreEntrada;
	}

	@Override
	public void setDificultadFácil() {
		if(this.dificultad.equals(new Fácil())) {
			System.out.println("\tLa dificultad es fácil.");
			return;
		}
		System.out.println("\tCambiando dificultad a fácil...");
		this.relojInterno.stop();
		this.dificultad = new Fácil();
        this.tablero.setDificultad(this.dificultad);
		this.relojInterno = new Timer(dificultad.getTiempoDeCaída(), this);
		this.relojInterno.start();
	}

	@Override
	public void setDificultadNormal() {
		if(this.dificultad.equals(new Normal())) {
			System.out.println("\tLa dificultad es normal.");
			return;
		}
		System.out.println("\tCambiando dificultad a normal...");
		this.relojInterno.stop();
		this.dificultad = new Normal();
        this.tablero.setDificultad(this.dificultad);
		this.relojInterno = new Timer(dificultad.getTiempoDeCaída(), this);
		this.relojInterno.start();
	}

	@Override
	public void setDificultadDifícil() {
		if(this.dificultad.equals(new Díficil())) {
			System.out.println("\tLa dificultad es dificil.");
			return;
		}
		System.out.println("\tCambiando dificultad a difícil...");
		this.relojInterno.stop();
		this.dificultad = new Díficil();
        this.tablero.setDificultad(this.dificultad);
		this.relojInterno = new Timer(dificultad.getTiempoDeCaída(), this);
		this.relojInterno.start();
	}

	@Override
	public void abrirTablaDePuntuaciones() {
		this.pausarJuego();
		this.tablaDePuntuaciones.setVisible(true);
	}

	private void crearInterfazGráfica() {
		this.setResizable(false);
		this.setTitle("Poli - Tetris");
		this.setBounds(100, 100, 600, 590);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new CardLayout(0, 0));
		JDesktopPane desktopPane = new JDesktopPane();
		this.getContentPane().add(desktopPane, "Pantalla del Tetris");
		
		this.tablero = new Tablero();
		this.visualizador = new VisualizadorDeTetrominoes();
		desktopPane.add(tablero);
		desktopPane.add(visualizador);

		JLabel lblSiguientePieza = new JLabel("Siguiente pieza:");
		lblSiguientePieza.setHorizontalAlignment(SwingConstants.CENTER);
		lblSiguientePieza.setBounds(386, 12, 150, 20);
		desktopPane.add(lblSiguientePieza);
				
		// Labeles :
		JLabel lblLineas_1 = new JLabel("Líneas:");
		lblLineas_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblLineas_1.setBounds(386, 200, 150, 20);
		desktopPane.add(lblLineas_1);

		cantidadDeLineas = new JLabel("0");
		cantidadDeLineas.setHorizontalAlignment(SwingConstants.CENTER);
		cantidadDeLineas.setBounds(386, 215, 150, 20);
		desktopPane.add(cantidadDeLineas);

		JLabel lblNivel_1 = new JLabel("Nivel:");
		lblNivel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNivel_1.setBounds(386, 240, 150, 20);
		desktopPane.add(lblNivel_1);

		nivel = new JLabel("0");
		nivel.setHorizontalAlignment(SwingConstants.CENTER);
		nivel.setBounds(386, 255, 150, 20);
		desktopPane.add(nivel);

		JLabel lblPuntaje_1 = new JLabel("Puntaje:");
		lblPuntaje_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblPuntaje_1.setBounds(386, 280, 150, 20);
		desktopPane.add(lblPuntaje_1);

		puntuacion = new JLabel("0");
		puntuacion.setHorizontalAlignment(SwingConstants.CENTER);
		puntuacion.setBounds(386, 295, 150, 20);
		desktopPane.add(puntuacion);
		
    	String ToStringTemplate = "";
    	ToStringTemplate += "Controles:\n";
    	ToStringTemplate += "  ↑: Rotar 45° pieza\n";
    	ToStringTemplate += "  ↓: Mover pieza hacia abajo\n";
    	ToStringTemplate += "  ←: Mover pieza hacia la izquierda\n";
    	ToStringTemplate += "  →: Mover pieza hacia la derecha\n";
    	ToStringTemplate += "Espacio: Mover la pieza al fondo\n";
    	ToStringTemplate += "Dificultadades:\n";
    	ToStringTemplate += "   1: Fácil\n";
    	ToStringTemplate += "   2: Normal\n";
    	ToStringTemplate += "   3: Difícil\n";
    	ToStringTemplate += "Tetris:\n";
    	ToStringTemplate += "   P: Pausar juego\n";
    	ToStringTemplate += "   R: Iniciar un nuevo juego\n";
    	ToStringTemplate += "   T: Tabla de puntuaciones\n";
    	
    	JTextArea controlesDeJuego = new JTextArea(ToStringTemplate);
    	controlesDeJuego.setAlignmentX(LEFT_ALIGNMENT);
		controlesDeJuego.setBounds(348, 325, 250, 250);
		controlesDeJuego.setEditable(false);
		controlesDeJuego.setEnabled(false);
		controlesDeJuego.setDisabledTextColor(Color.BLACK);
		desktopPane.add(controlesDeJuego);	
		
		// Mostrar la interfaz gráfica:
		this.setVisible(true);
	}
}
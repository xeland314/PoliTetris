package tetris;

import tetris.paneles.Configuración;
import tetris.paneles.TablaDePuntuaciones;
import tetris.paneles.Tablero;
import tetris.paneles.VisualizadorDePieza;
import tetris.dificultades.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class Tetris extends JFrame implements Jugable, ActionListener{

	private Tablero tablero;
	private VisualizadorDePieza visualizador;
	private TablaDePuntuaciones tablaDePuntuaciones;
	private Configuración configuración;

	private JLabel cantidadDeLineas;
	private JLabel nivel;
	private JLabel puntuacion;

	private Dificultad dificultad;

	Timer relojInterno;

	public Tetris() {
		this.tablero = new Tablero();
		this.visualizador = new VisualizadorDePieza();
		this.configuración = new Configuración(this);
		this.tablaDePuntuaciones = new TablaDePuntuaciones();
		this.crearInterfazGráfica();
		this.dificultad = new Normal();
		this.relojInterno = new Timer(dificultad.getTiempoDeCaída(), this);
		this.relojInterno.start();
		this.addKeyListener(new Controles(this.tablero, this));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!this.tablero.puedeMoverLaPiezaAbajo()) {
			this.cambiarPieza();
			if (this.tablero.estáLaPiezaFueraDelLímite()) {
				this.finalizarJuego();
			}
		} else {
			this.tablero.moverAbajoPieza();
		}
	}

	@Override
	public void iniciarJuego() {
		if(this.relojInterno.isRunning()){
			this.relojInterno.stop();
		}
		this.tablero.limpiar();
		this.tablero.setPieza(this.visualizador.getPieza());
		this.tablero.resetPuntuación(this.dificultad);
		this.actualizarPuntuación();
		this.relojInterno.start();
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
		String nombre = ingresarNombreDelJugador();
		this.tablaDePuntuaciones.agregarNuevaPuntuación(
            new Jugador(nombre + "", this.tablero.getPuntuación()));
		this.tablero.resetPuntuación(this.dificultad);
	}

	private void actualizarPuntuación() {
		this.cantidadDeLineas.setText(String.valueOf(tablero.getPuntuación().getFilasDestruídas()));
		this.nivel.setText(String.valueOf(tablero.getPuntuación().getNivelActual()));
		this.puntuacion.setText(String.valueOf(tablero.getPuntuación().getPuntaje()));
	}


	@Override
	public void pausarJuego() {
		tablero.pausar();
		if (tablero.estáPausado()) {
			this.relojInterno.stop();
			JOptionPane.showMessageDialog(null, "Juego Pausado", "Tetris", JOptionPane.INFORMATION_MESSAGE);
		} else {
			this.relojInterno.start();
		}
		tablero.repaint();
	}

	private String ingresarNombreDelJugador() {
		String nombreEntrada;
		String solicitud = "\t\tSu puntuación es de: " + this.tablero.getPuntuación().getPuntaje() + ".\n";
		solicitud += "\t\tIngrese su nombre, por favor: \n";
		nombreEntrada = JOptionPane.showInputDialog(null, solicitud, "Nueva Puntuación", JOptionPane.QUESTION_MESSAGE);
		return nombreEntrada;
	}

	@Override
	public void setDificultadFácil() {
		this.relojInterno.stop();
		this.dificultad = new Fácil();
        this.tablero.setDificultad(this.dificultad);
		this.relojInterno = new Timer(dificultad.getTiempoDeCaída(), this);
		this.relojInterno.start();
	}

	@Override
	public void setDificultadNormal() {
		this.relojInterno.stop();
		this.dificultad = new Normal();
        this.tablero.setDificultad(this.dificultad);
		this.relojInterno = new Timer(dificultad.getTiempoDeCaída(), this);
		this.relojInterno.start();
	}

	@Override
	public void setDificultadDifícil() {
		this.relojInterno.stop();
		this.dificultad = new Díficil();
        this.tablero.setDificultad(this.dificultad);
		this.relojInterno = new Timer(dificultad.getTiempoDeCaída(), this);
		this.relojInterno.start();
	}

	@Override
	public void abrirPanelDeConfiguración() {
		this.relojInterno.stop();
		this.configuración.setVisible(true);
        this.relojInterno.start();
	}

	@Override
	public void abrirTablaDePuntuaciones() {
		this.relojInterno.stop();
		this.tablaDePuntuaciones.setVisible(true);
        this.relojInterno.start();
	}

	private void crearInterfazGráfica() {
		this.setResizable(false);
		this.setTitle("PoliTetris");
		this.setBounds(100, 100, 600, 590);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new CardLayout(0, 0));
		JDesktopPane desktopPane = new JDesktopPane();
		this.getContentPane().add(desktopPane, "Pantalla del Tetris");

		desktopPane.add(tablero);
		desktopPane.add(visualizador);

		JLabel lblSiguientePieza = new JLabel("Siguiente pieza:");
		lblSiguientePieza.setHorizontalAlignment(SwingConstants.CENTER);
		lblSiguientePieza.setBounds(386, 12, 150, 20);
		desktopPane.add(lblSiguientePieza);

		// Botón para iniciar juego
		JButton btnIniciarJuego = new JButton("Iniciar Juego");
		btnIniciarJuego.setBackground(new Color(0, 102, 204));
		btnIniciarJuego.setBounds(386, 408, 150, 40);
        btnIniciarJuego.addActionListener(e -> {
            this.iniciarJuego();
        });
		desktopPane.add(btnIniciarJuego);

		// Botón para abrir configuración
		JButton btnConfiguracin = new JButton("Configuración");
		btnConfiguracin.setBackground(new Color(0, 153, 204));
		btnConfiguracin.setBounds(386, 445, 150, 40);
        btnConfiguracin.addActionListener(e -> {
            this.abrirPanelDeConfiguración();
        });
		desktopPane.add(btnConfiguracin);

		// Botón para abrir las puntuaciones guardadas
        JButton btnPuntuaciones = new JButton("Puntuaciones");
		btnPuntuaciones.setBackground(new Color(0, 102, 204));
		btnPuntuaciones.setBounds(386, 484, 150, 40);
        btnPuntuaciones.addActionListener(e -> {
            this.abrirTablaDePuntuaciones();
        });
		desktopPane.add(btnPuntuaciones);

		// Labeles :
		JLabel lblLineas_1 = new JLabel("Líneas:");
		lblLineas_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblLineas_1.setBounds(386, 220, 150, 20);
		desktopPane.add(lblLineas_1);

		cantidadDeLineas = new JLabel("0");
		cantidadDeLineas.setHorizontalAlignment(SwingConstants.CENTER);
		cantidadDeLineas.setBounds(386, 240, 150, 20);
		desktopPane.add(cantidadDeLineas);

		JLabel lblNivel_1 = new JLabel("Nivel:");
		lblNivel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNivel_1.setBounds(386, 280, 150, 20);
		desktopPane.add(lblNivel_1);

		nivel = new JLabel("0");
		nivel.setHorizontalAlignment(SwingConstants.CENTER);
		nivel.setBounds(386, 300, 150, 20);
		desktopPane.add(nivel);

		JLabel lblPuntaje_1 = new JLabel("Puntaje:");
		lblPuntaje_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblPuntaje_1.setBounds(386, 340, 150, 20);
		desktopPane.add(lblPuntaje_1);

		puntuacion = new JLabel("0");
		puntuacion.setHorizontalAlignment(SwingConstants.CENTER);
		puntuacion.setBounds(386, 360, 150, 20);
		desktopPane.add(puntuacion);
		// Mostrar la interfaz gráfica:
		this.setVisible(true);
	}
}
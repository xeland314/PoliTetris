package tetris.paneles;

import tetris.Tetris;
import tetris.Jugable;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JButton;

public final class Configuración extends JFrame {

    private Jugable tetris;
    private JLabel lblDificultadSeleccionada = new JLabel("Normal");

    public Configuración(Tetris tetris) {
        this.tetris = tetris;
        this.loadUI();
    }

    private void setDificultadFácil() {
        this.tetris.setDificultadFácil();
        this.lblDificultadSeleccionada.setText("Fácil");
    }
    private void setDificultadNormal() {
        this.tetris.setDificultadNormal();
        this.lblDificultadSeleccionada.setText("Normal");
    }

    private void setDificultadDifícil() {
        this.tetris.setDificultadDifícil();
        this.lblDificultadSeleccionada.setText("Difícil");
    }

    private void loadUI() {
        this.setResizable(false);
        this.setTitle("Tetris - Configuración");
        this.setBounds(100, 100, 400, 400);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.getContentPane().setLayout(new CardLayout(0, 0));

        JDesktopPane desktopPane = new JDesktopPane();
        getContentPane().add(desktopPane, "name_45508400561190");

        JLabel lblDificultad = new JLabel("Dificultad");
        lblDificultad.setBounds(30, 10, 100, 15);
        desktopPane.add(lblDificultad);

        lblDificultadSeleccionada.setBounds(30, 30, 100, 15);
        desktopPane.add(lblDificultadSeleccionada);

        JButton btnFcil = new JButton("Fácil");
        btnFcil.setBounds(30, 70, 120, 25);
        btnFcil.addActionListener(e -> {
            this.setDificultadFácil();
        });
        desktopPane.add(btnFcil);

        JButton btnNormal = new JButton("Normal");
        btnNormal.setBounds(30, 100, 120, 25);
        btnNormal.addActionListener(e -> { 
            this.setDificultadNormal();
        });
        desktopPane.add(btnNormal);

        JButton btnDifcil = new JButton("Difícil");
        btnDifcil.setBounds(30, 130, 120, 25);
        btnDifcil.addActionListener(e -> {
            this.setDificultadDifícil();
        });
        desktopPane.add(btnDifcil);

    }
}

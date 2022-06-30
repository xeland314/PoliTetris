import java.awt.EventQueue;

import tetris.Tetris;

public class Main {
    
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Tetris();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}


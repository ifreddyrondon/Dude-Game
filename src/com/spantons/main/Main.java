package com.spantons.main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// Contenedor del juego
		JFrame window = new JFrame("Dude");
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
	
}

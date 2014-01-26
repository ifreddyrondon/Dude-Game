package com.spantons.main;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// Contenedor del juego
		JFrame window = new JFrame();
		window.setContentPane(new GamePanel());
		
//		window.setUndecorated(true);
//		GraphicsEnvironment ge = GraphicsEnvironment
//				.getLocalGraphicsEnvironment();
//		GraphicsDevice gs = ge.getDefaultScreenDevice();
//		gs.setFullScreenWindow(window);
//		window.validate();
			
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
	}

}

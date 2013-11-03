package com.spantons.main;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// Contenedor del juego
		JFrame window = new JFrame();
		window.setUndecorated(true);
		window.setContentPane(new GamePanel());
		
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice gs = ge.getDefaultScreenDevice();
		gs.setFullScreenWindow(window);
		window.validate();

	}

}

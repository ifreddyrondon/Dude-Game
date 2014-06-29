package com.spantons.main;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

public class Main {
	
	static GraphicsDevice device = GraphicsEnvironment
		        .getLocalGraphicsEnvironment().getScreenDevices()[0];

	public static void main(String[] args) {
		JFrame window = new JFrame();
		window.setContentPane(new GamePanel());
		
		window.setUndecorated(true);
		device.setFullScreenWindow(window);

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

}

package com.spantons.main;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;

import javax.swing.JPanel;

import com.spantons.gameStages.GameStagesManager;

/**
 * @author spantons
 * @clase GamePanel
 * @descripcion encargada de configurar los parametros de la ventana del juego y
 *              el Game Loop, tambien implementa un KeyListener para manejar la
 *              entrada del usuario y Runnable para manejar el thread del juego
 */

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener {

	/**
	 * Ancho de la ventana de la aplicacion
	 */
	
	public static int RESOLUTION_WIDTH = (int) Toolkit
			.getDefaultToolkit().getScreenSize().getWidth();

//	public static int RESOLUTION_WIDTH = 1024;
	/**
	 * Alto de la ventana de la aplicacion
	 */
	
	public static int RESOLUTION_HEIGHT = (int) Toolkit
			.getDefaultToolkit().getScreenSize().getHeight();

//	public static int RESOLUTION_HEIGHT = 768;
	
	/**
	 * Thread del juego
	 */
	private Thread thread;
	/**
	 * Comprueba si el juego esta corriendo
	 */
	private boolean running;
	/**
	 * Frames por segundo que corre el juego
	 */
	private int FPS = 60;
	/**
	 * Tiempo en millisegundos que tiene que esperar para correr a la cantidad
	 * FPS
	 */
	private long targetTime = 1000 / FPS;

	/**
	 * Buffer de imagenes a dibujar en pantalla
	 */
	private BufferedImage image;
	/**
	 * Dibujar en pantalla
	 */
	private Graphics2D g;

	/**
	 * Manejaador de los estados del juego
	 */
	private GameStagesManager gsm;

	/****************************************************************************************/
	public GamePanel() {
		super();
		setPreferredSize(new Dimension(RESOLUTION_WIDTH, RESOLUTION_HEIGHT));
		setFocusable(true);
		requestFocus();
		setFocusTraversalKeysEnabled(false);
		hidePointer(0);
	}
	
	/****************************************************************************************/
	/**
	 * Espera a que Jpanel se adherido a JFrame o JApplet antes de comenzar,
	 * inicializa el thread y asigna el encargado del KeyListener
	 */
	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}
	
	/****************************************************************************************/
	@Override
	public void run() {
		// variables para calcular los tiempos de espera
		long startTime;
		long elapsedTime;
		long waitTime;

		init();
		while (running) {
			startTime = System.nanoTime();
			update();
			draw();
			drawToScreen();

			// tiempos
			elapsedTime = System.nanoTime() - startTime;
			waitTime = targetTime - elapsedTime / 1000000;

			if (waitTime < 0)
				waitTime = 1;
			try {
				Thread.sleep(waitTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/****************************************************************************************/
	private void init() {
		image = new BufferedImage(RESOLUTION_WIDTH, RESOLUTION_HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.createGraphics();
		running = true;
		gsm = new GameStagesManager();
	}
	
	/****************************************************************************************/
	private void update() {
		gsm.update();
	}
	
	/****************************************************************************************/
	private void draw() {
		gsm.draw(g);
	}
	
	/****************************************************************************************/
	private void drawToScreen() {
		Graphics2D g2 = (Graphics2D) getGraphics();
		g2.drawImage(image, 0, 0, RESOLUTION_WIDTH, RESOLUTION_HEIGHT, null);
		g2.dispose();
	}
	
	/****************************************************************************************/
	@Override
	public void keyPressed(KeyEvent e) {
		gsm.keyPressed(e.getKeyCode());
	}
	
	/****************************************************************************************/
	@Override
	public void keyReleased(KeyEvent e) {
		gsm.keyReleased(e.getKeyCode());
	}
	
	/****************************************************************************************/
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	
	/****************************************************************************************/
	// Utilitarias
	private void hidePointer(int n){
		switch (n) {
		case 0:
			int[] pixeles = new int[16*16];
			
			Image img= Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(16, 16, pixeles, 0, 16));
			Cursor transparentCursor = Toolkit.getDefaultToolkit().createCustomCursor(img, new Point(0, 0), "invisibleCursor");
			this.setCursor(transparentCursor);
			
			break;
		case 1:
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			break;
		}
	}
		
}
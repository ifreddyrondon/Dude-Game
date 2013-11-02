package com.spantons.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.spantons.gameState.GameStagesManager;

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
	public static final int WIDTH = 320;
	/**
	 * Alto de la ventana de la aplicacion
	 */
	public static final int HEIGHT = 240;
	/**
	 * Escala de la ventana de la aplicacion
	 */
	public static final int SCALE = 3;

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

	// imagen y dibujo
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

	/**
	 * Constructor por defecto y unico constructor, se encarga de ajustar las
	 * dimensiones de la ventana
	 */
	public GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH * SCALE,
				HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
	}

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

	/**
	 * Metodo principal del thread del juego, encargador del Game Loop
	 */
	@Override
	public void run() {
		// variables para calcular los tiempos de espera
		long inicio;
		long transcurrido;
		long espera;

		init();
		// loop del juego
		while (running) {
			inicio = System.nanoTime();
			update();
			draw();
			drawToScreen();

			// tiempos
			transcurrido = System.nanoTime() - inicio;
			espera = targetTime - transcurrido / 1000000;

			if (espera < 0)
				espera = 1;
			try {
				Thread.sleep(espera);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Metodo encargado de inicializar las variables image (BufferedImage), g
	 * (Graphics2D), running (boolean) y gsm (GameStateManager)
	 */
	private void init() {
		image = new BufferedImage(WIDTH, HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.createGraphics();
		running = true;
		gsm = new GameStagesManager();
	}

	/**
	 * Metodo encargado de actualizar todos los objetos, sprites, map etc del
	 * juego
	 */
	private void update() {
		gsm.update();
	}

	/**
	 * Metodo encargado dibujar
	 */
	private void draw() {
		gsm.draw(g);
	}

	/**
	 * Metodo encargado de dibujar en pantalla
	 */
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH * SCALE,
				HEIGHT * SCALE, null);
		g2.dispose();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		gsm.keyPressed(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		gsm.keyReleased(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
}
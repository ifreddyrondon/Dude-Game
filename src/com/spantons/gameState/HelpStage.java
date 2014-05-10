package com.spantons.gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

import com.spantons.main.GamePanel;
import com.spantons.tileMap.Background;

public class HelpStage extends Stage {

	private Background bg;
	private BufferedImage helpCharacter;
	private BufferedImage arrowRed;

	private int currentChoice = 0;
	private String[] descripcion = { "Arriba", "Abajo", "Izquierda",
			"Derecha", "Tomar algun objeto", "Abrir puerta", "Golpear",
			"Menu" };

	private String[] tecla = { "↑","↓", "←","→","ENTER","O","BARRA ESPACIADORA","ESC" };

	private String footer = "Copyright © 2013 Wasting Time For Game C.A Todos los derechos reservados.";

	private Font warningFont;
	private Color warningColor;
	private String warningString = "SI NO TOMAS ESTA AYUDA PODRIAS TERMINAR ASI";
	private Font titleFont;
	private Color titleColor;
	private String titleString = "(ENTER) para regresar";
	private Font helpFont;
	private Color helpColor;
	private Font footerFont;
	private FontMetrics fm;
	private Rectangle2D r;

	/****************************************************************************************/
	public HelpStage(GameStagesManager _gsm) {
		gsm = _gsm;

		try {
			bg = new Background("/backgrounds/menubg2.png", 0, true);
			bg.setVector(-0.1, 0);
			helpCharacter = ImageIO.read(getClass().getResourceAsStream(
					"/backgrounds/helpCharacter.png"));
			arrowRed = ImageIO.read(getClass().getResourceAsStream(
					"/backgrounds/arrowRed.png"));
			arrowRed = Scalr.resize(arrowRed, 250);

			warningFont = new Font("Helvetica", 8, 22);
			warningColor = new Color(227, 23, 23);
			titleFont = new Font("Century Gothic", Font.TRUETYPE_FONT, 30);
			titleColor = new Color(128, 0, 0);
			helpFont = new Font("Century Gothic", Font.TRUETYPE_FONT, 22);
			helpColor = Color.BLACK;
			footerFont = new Font("Helvetica", 8, 12);
			
			// player = new AudioPlayer("/music/ghosttown.wav");
			// player.loop();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/****************************************************************************************/
	@Override
	public void init() {
		// TODO Auto-generated method stub
	}

	/****************************************************************************************/
	@Override
	public void endStage() {
		// player.close();
		gsm.setStage(GameStagesManager.LEVEL_1_STAGE);
	}

	/****************************************************************************************/
	@Override
	public void update() {
		bg.update();
	}

	/****************************************************************************************/
	@Override
	public void draw(Graphics2D g) {
		bg.draw(g);
		g.drawImage(helpCharacter, 100, 100, null);

		g.setColor(warningColor);
		g.setFont(warningFont);
		fm = g.getFontMetrics();
		r = fm.getStringBounds(warningString, g);
		int x = (GamePanel.RESOLUTION_WIDTH - (int) r.getWidth()) / 2;
		int y = 100;
		g.drawString(warningString, x, y);

		g.drawImage(arrowRed, x + 20, 140, null);

		g.setColor(helpColor);
		g.setFont(helpFont);
		y = y + 240;
		for (int i = 0; i < descripcion.length; i++) {
			fm = g.getFontMetrics();
			String aux = descripcion[i] + " | " + tecla[i];
			r = fm.getStringBounds(aux, g);
			x = (GamePanel.RESOLUTION_WIDTH - (int) r.getWidth()) / 2
				+ fm.getDescent();
			g.drawString(aux, x, y + i * 30);
		}

		g.setColor(titleColor);
		g.setFont(titleFont);
		fm = g.getFontMetrics();
		r = fm.getStringBounds(titleString, g);
		x = (GamePanel.RESOLUTION_WIDTH - (int) r.getWidth()) / 2;
		g.drawString(titleString, x, 650);

		g.setFont(footerFont);
		g.setColor(Color.BLACK);
		g.drawString(footer, GamePanel.RESOLUTION_WIDTH - 438,
				GamePanel.RESOLUTION_HEIGHT - 20);
	}

	/****************************************************************************************/
	private void select() {
		if (currentChoice == 0)
			gsm.setStage(GameStagesManager.MENU_STAGE);

	}

	/****************************************************************************************/
	@Override
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_ENTER)
			select();

	}

	/****************************************************************************************/
	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void helpStage() {
		// TODO Auto-generated method stub

	}

}
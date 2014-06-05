package com.spantons.gameStagesMenus;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.imgscalr.Scalr;

import com.spantons.gameStages.GameStagesManager;
import com.spantons.gameStages.Stage;
import com.spantons.magicNumbers.FontPath;
import com.spantons.magicNumbers.ImagePath;
import com.spantons.magicNumbers.SoundPath;
import com.spantons.main.GamePanel;
import com.spantons.singleton.FontCache;
import com.spantons.singleton.ImageCache;
import com.spantons.singleton.SoundCache;
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
			bg = new Background(ImagePath.BACKGROUND_MENU, 0, true);
			bg.setVector(-0.1, 0);
			helpCharacter = ImageCache.getInstance().getImage(ImagePath.BACKGROUND_HELP_CHARACTER);
			arrowRed = ImageCache.getInstance().getImage(ImagePath.BACKGROUND_ARROW_RED);
			arrowRed = Scalr.resize(arrowRed, 250);

			warningFont = FontCache.getInstance().getFont(FontPath.FONT_HORRENDO);
			warningFont = warningFont.deriveFont(Font.PLAIN, 30);
			warningColor = Color.BLACK;
			titleFont = FontCache.getInstance().getFont(FontPath.FONT_SIXTY);
			titleFont = titleFont.deriveFont(Font.PLAIN, 40);
			titleColor = new Color(128, 0, 0);
			helpFont = new Font("Century Gothic", Font.TRUETYPE_FONT, 22);
			helpColor = Color.BLACK;
			footerFont = new Font("Arial", 8, 12);
			
			SoundCache.getInstance().getSound(SoundPath.MUSIC_HORROR_MOVIE_AMBIANCE).loop();
			
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
		SoundCache.getInstance().stopAllSound();
		gsm.setStage(GameStagesManager.MENU_STAGE);
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
		g.setColor(Color.LIGHT_GRAY);
		g.drawString(footer, GamePanel.RESOLUTION_WIDTH - 438,
				GamePanel.RESOLUTION_HEIGHT - 20);
	}

	/****************************************************************************************/
	private void select() {
		if (currentChoice == 0)
			endStage();
	}

	/****************************************************************************************/
	@Override	
	public void keyPressed(int k) {
		SoundCache.getInstance().getSound(SoundPath.SFX_SCRATCH).play();
		if (k == KeyEvent.VK_ENTER)
			select();
	}

	/****************************************************************************************/
	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub

	}

}
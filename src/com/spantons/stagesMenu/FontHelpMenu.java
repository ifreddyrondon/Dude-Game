package com.spantons.stagesMenu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.spantons.magicNumbers.FontPath;
import com.spantons.main.GamePanel;
import com.spantons.singleton.FontCache;
import com.spantons.stages.IFontStage;

public class FontHelpMenu implements IFontStage {

	private StagesMenu stage;
	private Font warningFont;
	private Color warningColor;
	private String warningString = "SI NO TOMAS ESTA AYUDA PODRIAS TERMINAR ASI";
	private String[] descripcion = { 
			"Arriba", "Abajo", "Izquierda",
			"Derecha", "Tomar algun objeto", 
			"Abrir puerta", "Golpear","Menu" };
	private String[] tecla = { "↑","↓", "←","→","ENTER","O","BARRA ESPACIADORA","ESC" };
	
	/****************************************************************************************/
	public FontHelpMenu(StagesMenu _stage) {
		stage = _stage;
	}

	/****************************************************************************************/	
	@Override
	public void setFont() {
		warningFont = FontCache.getInstance().getFont(FontPath.FONT_HORRENDO).deriveFont(Font.PLAIN, 30);
		warningColor = Color.BLACK;
		stage.titleFont = FontCache.getInstance().getFont(FontPath.FONT_SIXTY).deriveFont(Font.PLAIN, 40);
		stage.titleColor = new Color(128, 0, 0);
		stage.choicesFont = new Font("Century Gothic", Font.TRUETYPE_FONT, 22);
		stage.choicesColor = Color.BLACK;
		stage.footerFont = new Font("Arial", 8, 12);
	}

	/****************************************************************************************/
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	/****************************************************************************************/
	@Override
	public void draw(Graphics2D g) {
		g.setColor(warningColor);
		g.setFont(warningFont);
		stage.fm = g.getFontMetrics();
		stage.r = stage.fm.getStringBounds(warningString, g);
		int x = (GamePanel.RESOLUTION_WIDTH - (int) stage.r.getWidth()) / 2;
		int y = 100;
		g.drawString(warningString, x, y);

		g.setColor(stage.choicesColor);
		g.setFont(stage.choicesFont);
		y = y + 240;
		for (int i = 0; i < descripcion.length; i++) {
			stage.fm = g.getFontMetrics();
			String aux = descripcion[i] + " | " + tecla[i];
			stage.r = stage.fm.getStringBounds(aux, g);
			x = (GamePanel.RESOLUTION_WIDTH - (int) stage.r.getWidth()) / 2
				+ stage.fm.getDescent();
			g.drawString(aux, x, y + i * 30);
		}

		g.setColor(stage.titleColor);
		g.setFont(stage.titleFont);
		stage.fm = g.getFontMetrics();
		stage.r = stage.fm.getStringBounds(stage.title, g);
		x = (GamePanel.RESOLUTION_WIDTH - (int) stage.r.getWidth()) / 2;
		g.drawString(stage.title, x, 650);

		g.setFont(stage.footerFont);
		g.setColor(Color.BLACK);
		g.drawString(stage.footer, GamePanel.RESOLUTION_WIDTH - 438,
				GamePanel.RESOLUTION_HEIGHT - 20);
	}

}

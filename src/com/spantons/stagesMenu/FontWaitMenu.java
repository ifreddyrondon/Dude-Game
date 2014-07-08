package com.spantons.stagesMenu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import com.spantons.magicNumbers.FontPath;
import com.spantons.main.GamePanel;
import com.spantons.singleton.FontCache;
import com.spantons.stages.IFontStage;
import com.spantons.utilities.ArraysUtil;

public class FontWaitMenu implements IFontStage {

	private StagesMenu stage;
	private Font warningFont;
	private Color warningColor;
	private String[] descripcion = { 
			"Mover el personaje ","Cambiar de personaje con la tecla ", "Abrir puerta con la tecla ", 
			"Atacar con la tecla ","Recoge objetos con la tecla " };
	private String[] tecla = { "↑ ↓ ← →","TAB","O","BARRA ESPACIADORA","ENTER" };
	
	private Timer timerLoader;
	private boolean loader;
	private Font fontLoadState;
	private Color colorLoadState;
	private String loadStateString = "Cargando...";
	
	/****************************************************************************************/
	public FontWaitMenu(StagesMenu _stage) {
		stage = _stage;
		stage.currentChoice = 1;
	}

	/****************************************************************************************/
	@Override
	public void setFont() {
		stage.titleFont = FontCache.getInstance().getFont(FontPath.FONT_SIXTY).deriveFont(Font.PLAIN, 90);
		stage.titleColor = Color.BLACK;
		warningFont = FontCache.getInstance().getFont(FontPath.FONT_YOUMURDERERBB_REG).deriveFont(Font.PLAIN, 42);
		warningColor = Color.BLACK;
		stage.choicesFont = new Font("Century Gothic", Font.TRUETYPE_FONT, 22);
		stage.choicesColor = Color.BLACK;
		stage.footerFont = new Font("Arial", 8, 12);
		fontLoadState = FontCache.getInstance().getFont(FontPath.FONT_HORRENDO).deriveFont(Font.PLAIN, 40);
		colorLoadState = Color.GRAY;
		
		timerLoader = new Timer(800,
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ae) {
						if (loader)
							loader = false;
						else
							loader = true;
					}
				});
		timerLoader.start();
	}

	/****************************************************************************************/
	@Override
	public void update() {
		if (stage.change) {
			colorLoadState = Color.RED;
			loadStateString = "Presione ENTER para jugar";
			stage.currentChoice = 0;
			timerLoader.stop();
			loader = true;
		}	
	}
	
	/****************************************************************************************/
	@Override
	public void draw(Graphics2D g) {
		g.setColor(stage.titleColor);
		g.setFont(stage.titleFont);
		stage.fm = g.getFontMetrics();

		stage.r = stage.fm.getStringBounds(stage.title, g);
		int x = (GamePanel.RESOLUTION_WIDTH - (int) stage.r.getWidth()) / 2;
		int y = (GamePanel.RESOLUTION_HEIGHT - (int) stage.r.getHeight()) / 2 - 200;
		g.drawString(stage.title, x, y);
		
		g.setColor(warningColor);
		g.setFont(warningFont);
		ArrayList<String> split = ArraysUtil.getPartsOfStringByPartitionSizeWithoutCutPhrase(stage.someOtherText, 65);
		
		y = y + 30;
		
		for (int i = 0; i < split.size(); i++) {
			stage.fm = g.getFontMetrics();
			stage.r = stage.fm.getStringBounds(split.get(i), g);
			x = (GamePanel.RESOLUTION_WIDTH - (int) stage.r.getWidth()) / 2;
			y = y + 50;
			g.drawString(split.get(i), x, y);
		}
		
		g.setColor(stage.choicesColor);
		g.setFont(stage.choicesFont);
		y = y + 70;
		for (int i = 0; i < descripcion.length; i++) {
			stage.fm = g.getFontMetrics();
			String aux = descripcion[i] + tecla[i];
			stage.r = stage.fm.getStringBounds(aux, g);
			x = (GamePanel.RESOLUTION_WIDTH - (int) stage.r.getWidth()) / 2
				+ stage.fm.getDescent();
			g.drawString(aux, x, y + i * 30);
		}
		
		if (loader) {
			g.setColor(colorLoadState);
			g.setFont(fontLoadState);
			stage.fm = g.getFontMetrics();
			stage.r = stage.fm.getStringBounds(loadStateString, g);
			x = (GamePanel.RESOLUTION_WIDTH - (int) stage.r.getWidth()) / 2;
			y = y + 200;
			g.drawString(loadStateString, x, y);
		}
		
		g.setFont(stage.footerFont);
		g.setColor(Color.BLACK);
		g.drawString(stage.footer, GamePanel.RESOLUTION_WIDTH - 438,
				GamePanel.RESOLUTION_HEIGHT - 20);
	}

}

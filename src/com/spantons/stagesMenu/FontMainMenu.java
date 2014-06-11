package com.spantons.stagesMenu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.spantons.magicNumbers.FontPath;
import com.spantons.main.GamePanel;
import com.spantons.singleton.FontCache;
import com.spantons.stages.IFontStage;

public class FontMainMenu implements IFontStage{
	
	private StagesMenu stage;
	
	/****************************************************************************************/
	public FontMainMenu(StagesMenu _stage) {
		stage = _stage;
	}

	/****************************************************************************************/
	@Override
	public void setFont() {
		stage.titleFont = FontCache.getInstance().getFont(FontPath.FONT_SIXTY).deriveFont(Font.PLAIN, 90);
		stage.titleColor = Color.BLACK;
		stage.choicesFont = FontCache.getInstance().getFont(FontPath.FONT_HORRENDO).deriveFont(Font.PLAIN, 30);
		stage.footerFont = new Font("Arial", 8, 12);
	}

	/****************************************************************************************/
	@Override
	public void draw(Graphics2D g) {
		g.setColor(stage.titleColor);
		g.setFont(stage.titleFont);
		stage.fm = g.getFontMetrics();

		stage.r = stage.fm.getStringBounds(stage.title, g);
		int x = (GamePanel.RESOLUTION_WIDTH - (int) stage.r.getWidth()) / 2;
		int y = (GamePanel.RESOLUTION_HEIGHT - (int) stage.r.getHeight()) / 2 - 160;
		g.drawString(stage.title, x, y);

		// Dibujar menu de opciones
		y = y + 110;
		g.setFont(stage.choicesFont);
		for (int i = 0; i < stage.choices.size(); i++) {
			stage.fm = g.getFontMetrics();
			stage.r = stage.fm.getStringBounds(stage.choices.get(i), g);
			x = (GamePanel.RESOLUTION_WIDTH 
					- (int) stage.r.getWidth()) / 2 + stage.fm.getDescent();
			
			if (i == stage.currentChoice)
				g.setColor(Color.BLACK);
			else
				g.setColor(Color.LIGHT_GRAY);

			g.drawString(stage.choices.get(i), x, y + i * 40);
		}
		
		g.setFont(stage.footerFont);
		g.setColor(Color.LIGHT_GRAY);
		g.drawString(stage.footer, 
				GamePanel.RESOLUTION_WIDTH - 438, 
				GamePanel.RESOLUTION_HEIGHT - 20);
	}

}

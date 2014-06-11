package com.spantons.stagesMenu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.spantons.main.GamePanel;
import com.spantons.stages.IFontStage;

public class FontGameOverMenu implements IFontStage {

	private StagesMenu stage;
	private Color currentChoiceColor;
	
	/****************************************************************************************/
	public FontGameOverMenu(StagesMenu _stage) {
		stage = _stage;
	}

	/****************************************************************************************/
	@Override
	public void setFont() {
		stage.choicesFont = new Font("Helvetica", 8, 22);
		stage.choicesColor = Color.WHITE;
		currentChoiceColor = new Color(227, 23, 23);
		stage.footerFont = new Font("Arial", 8, 12);
	}

	/****************************************************************************************/
	@Override
	public void draw(Graphics2D g) {
		g.setFont(stage.choicesFont);
		g.setFont(stage.choicesFont);
		stage.fm = g.getFontMetrics();
		int y = GamePanel.RESOLUTION_HEIGHT / 2 - 100;
		
		for (int i = 0; i < stage.choices.size(); i++) {
			
			stage.r = stage.fm.getStringBounds(stage.choices.get(i), g);
			int x = (GamePanel.RESOLUTION_WIDTH - (int) stage.r.getWidth()) / 2;
			
			if (i == stage.currentChoice)
				g.setColor(currentChoiceColor);
			else
				g.setColor(stage.choicesColor);

			g.drawString(stage.choices.get(i), x, y + i * 26);
		}
	}

}

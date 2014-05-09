package com.spantons.gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import com.spantons.main.GamePanel;
import com.spantons.tileMap.Background;

public class HelpStage extends Stage {
	
	private Background bg;
	
	private int currentChoice = 0;
	private String[] choices = {  "Menu principal" };
	
	private int x;
	private int y;
	private Color titleColor;
	private Font titleFont;
	
	private Font choicesFont;
	private Color currentChoiceColor;
	private Color choicesColor;
	private FontMetrics fm;
	private Rectangle2D r;

	
	/****************************************************************************************/
	public HelpStage(GameStagesManager _gsm) {
		gsm = _gsm;
		
		choicesFont = new Font("Helvetica", 8, 22);
		currentChoiceColor = new Color(227, 23, 23);
		choicesColor = Color.WHITE;
		
		String imagesPath[] = {"/backgrounds/menubg.gif "};
		Random randomGenerator;
		randomGenerator = new Random();
		bg = new Background(imagesPath[randomGenerator.nextInt(2)], 0, false);
	}
	
	/****************************************************************************************/
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	/****************************************************************************************/
	@Override
	public void endStage() {
//		player.close();
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
		
		// Dibujar titulo
				g.setColor(titleColor);
				g.setFont(titleFont);
				fm = g.getFontMetrics();

				
				
				String stringTime = "LA SIGUIENTE AYUDA PODRIA SERVIRLE";
				r = fm.getStringBounds(stringTime, g);
				int x = (GamePanel.RESOLUTION_WIDTH - (int) r.getWidth()) / 2;
				int y = (GamePanel.RESOLUTION_HEIGHT - (int) r.getHeight()) / 2 - 150;
				g.drawString(stringTime, x, y);
		
		g.setFont(choicesFont);
		g.setFont(choicesFont);
		fm = g.getFontMetrics();
		y = GamePanel.RESOLUTION_HEIGHT / 2 - 100;
		
		for (int i = 0; i < choices.length; i++) {
			
			r = fm.getStringBounds(choices[i], g);
			x = (GamePanel.RESOLUTION_WIDTH - (int) r.getWidth()) / 2;
			
			if (i == currentChoice)
				g.setColor(currentChoiceColor);
			else
				g.setColor(choicesColor);

			g.drawString(choices[i], x, y + i * 26);
		}

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
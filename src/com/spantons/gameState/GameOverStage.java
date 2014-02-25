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

public class GameOverStage extends Stage {
	
	private Background bg;
	
	private int currentChoice = 0;
	private String[] choices = { "Jugar otra vez", "Menu principal", "Salir" };
	
	private int x;
	private int y;
	
	private Font choicesFont;
	private Color currentChoiceColor;
	private Color choicesColor;
	private FontMetrics fm;
	private Rectangle2D r;

	
	/****************************************************************************************/
	public GameOverStage(GameStagesManager _gsm) {
		gsm = _gsm;
		
		choicesFont = new Font("Helvetica", 8, 22);
		currentChoiceColor = new Color(227, 23, 23);
		choicesColor = Color.WHITE;
		
		String imagesPath[] = {"/backgrounds/game_over.png", "/backgrounds/game_over2.png"};
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
			endStage();
		else if (currentChoice == 1) {
//			player.close();
			gsm.setStage(GameStagesManager.MENU_STAGE);
		}
		else if (currentChoice == 2) 
			System.exit(0);
	}
	/****************************************************************************************/
	@Override
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_ENTER) 
			select();
		else if (k == KeyEvent.VK_UP) {
			currentChoice--;
			if (currentChoice == -1) {
				currentChoice = choices.length - 1;
			}
		} else if (k == KeyEvent.VK_DOWN) {
			currentChoice++;
			if (currentChoice == choices.length) {
				currentChoice = 0;
			}
		}
	}
	/****************************************************************************************/
	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		
	}

}

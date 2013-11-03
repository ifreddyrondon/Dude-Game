package com.spantons.gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

import com.spantons.main.GamePanel;
import com.spantons.tileMap.Background;

public class MenuStage extends Stage {

	private Background bg;

	private int currentChoice = 0;
	private String[] choices = { "Jugar", "Ayuda", "Salir" };

	private Color titleColor;
	private Font titleFont;
	private Font choicesFont;
	// Centrar titulo y menu en la pantalla
	private FontMetrics fm;
	private Rectangle2D r;

	public MenuStage(GameStagesManager gsm) {
		this.gsm = gsm;

		try {
			bg = new Background("/backgrounds/menubg.gif", 0, true);
			bg.setVector(-0.1, 0);

			titleColor = new Color(128, 0, 0);
			titleFont = new Font("Century Gothic", Font.PLAIN, 40);

			choicesFont = new Font("Helvetica", 8, 22);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
	}

	@Override
	public void update() {
		bg.update();
	}

	@Override
	public void draw(Graphics2D g) {
		// dibujar background
		bg.draw(g);
		
		// dibujar titulo
		g.setColor(titleColor);
		g.setFont(titleFont);
		fm = g.getFontMetrics();
		
		String stringTime = "Dude City";	
		r = fm.getStringBounds(stringTime, g);
		int x = (GamePanel.RESOLUTION_WIDTH - (int) r.getWidth()) / 2;
		int y = (GamePanel.RESOLUTION_HEIGHT- (int) r.getHeight()) / 2 - 150;
		g.drawString(stringTime, x, y);

		// dibujar menu de opciones
		y = y + 70;
		r = fm.getStringBounds(choices[0], g);
		x = (GamePanel.RESOLUTION_WIDTH - (int) r.getWidth()) / 2 + fm.getDescent();
		
		g.setFont(choicesFont);
		for (int i = 0; i < choices.length; i++) {
			if (i == currentChoice) 
				g.setColor(Color.DARK_GRAY);
			else 
				g.setColor(Color.LIGHT_GRAY);
			
			g.drawString(choices[i], x, y + i * 26);
		}
	}

	private void select() {
		if (currentChoice == 0) {
			gsm.setStage(GameStagesManager.LEVEL_1_STAGE);
		} else if (currentChoice == 1) {
			// ayuda
		} else if (currentChoice == 2) {
			System.exit(0);
		}
	}

	@Override
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_ENTER) {
			select();
		} else if (k == KeyEvent.VK_UP) {
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

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
	}

}

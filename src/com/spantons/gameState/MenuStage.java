package com.spantons.gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import com.spantons.entity.Entity;
import com.spantons.entity.character.SteveJobs;
import com.spantons.main.GamePanel;
import com.spantons.tileMap.Background;

import com.spantons.audio.*;

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

	private ArrayList<Entity> characters;
	private int currentCharacter;
	
	private AudioPlayer player;
	/****************************************************************************************/
	public MenuStage(GameStagesManager _gsm) {
		gsm = _gsm;

		try {
			bg = new Background("/backgrounds/menubg.gif", 0, true);
			bg.setVector(-0.1, 0);

			titleColor = new Color(128, 0, 0);
			titleFont = new Font("Century Gothic", Font.PLAIN, 40);

			choicesFont = new Font("Helvetica", 8, 22);

			// Personajes
			characters = new ArrayList<Entity>();
			SteveJobs sj = new SteveJobs(null, null, 0, 0, 0.5);
			sj.setPosition(180, 250);
			characters.add(sj);
			
//			player = new AudioPlayer("/music/ghosttown.wav");
//			player.loop();

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
//		player.close();
		gsm.setStage(GameStagesManager.LEVEL_1_STAGE);
	}
	/****************************************************************************************/
	@Override
	public void update() {
		bg.update();

		// Actualizar personajes
		for (int i = 0; i < characters.size(); i++)
			characters.get(i).update();
	}
	/****************************************************************************************/
	@Override
	public void draw(Graphics2D g) {
		// Dibujar background
		bg.draw(g);

		// Dibujar titulo
		g.setColor(titleColor);
		g.setFont(titleFont);
		fm = g.getFontMetrics();

		String stringTime = "Dude City";
		r = fm.getStringBounds(stringTime, g);
		int x = (GamePanel.RESOLUTION_WIDTH - (int) r.getWidth()) / 2;
		int y = (GamePanel.RESOLUTION_HEIGHT - (int) r.getHeight()) / 2 - 150;
		g.drawString(stringTime, x, y);

		// Dibujar menu de opciones
		y = y + 70;
		r = fm.getStringBounds(choices[0], g);
		x = (GamePanel.RESOLUTION_WIDTH - (int) r.getWidth()) / 2
				+ fm.getDescent();

		g.setFont(choicesFont);
		for (int i = 0; i < choices.length; i++) {
			if (i == currentChoice)
				g.setColor(Color.DARK_GRAY);
			else
				g.setColor(Color.LIGHT_GRAY);

			g.drawString(choices[i], x, y + i * 26);
		}

		// Dibujar personajes
		for (int i = 0; i < characters.size(); i++)
			characters.get(i).draw(g);

	}
	/****************************************************************************************/
	private void select() {
		if (currentChoice == 0) 
			endStage();
		else if (currentChoice == 1) 
			System.out.println("MENU AYUDA");
		else if (currentChoice == 2) 
			System.exit(0);
	}

	/****************************************************************************************/@Override
	public void keyPressed(int k) {
		// Mover en el menu
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
		// Mover personajes
		if (k == KeyEvent.VK_A)
			characters.get(currentCharacter).setMovLeft(true);
		if (k == KeyEvent.VK_D)
			characters.get(currentCharacter).setMovRight(true);
		if (k == KeyEvent.VK_W)
			characters.get(currentCharacter).setMovUp(true);
		if (k == KeyEvent.VK_S)
			characters.get(currentCharacter).setMovDown(true);
		if (k == KeyEvent.VK_SPACE)
			characters.get(currentCharacter).setMovJumping(true);
	}
	/****************************************************************************************/
	@Override
	public void keyReleased(int k) {
		// Mover personajes
		if (k == KeyEvent.VK_A)
			characters.get(currentCharacter).setMovLeft(false);
		if (k == KeyEvent.VK_D)
			characters.get(currentCharacter).setMovRight(false);
		if (k == KeyEvent.VK_W)
			characters.get(currentCharacter).setMovUp(false);
		if (k == KeyEvent.VK_S)
			characters.get(currentCharacter).setMovDown(false);
		if (k == KeyEvent.VK_SPACE)
			characters.get(currentCharacter).setMovJumping(false);
	}

}

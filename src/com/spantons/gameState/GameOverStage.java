package com.spantons.gameState;

import java.awt.Graphics2D;

import com.spantons.tileMap.Background;

public class GameOverStage extends Stage {
	
	private Background bg;

	//private int currentChoice = 0;
	//private String[] choices = { "Jugar de nuevo", "Salir" };
	/****************************************************************************************/
	public GameOverStage(GameStagesManager _gsm) {
		gsm = _gsm;
		bg = new Background("/backgrounds/game_over.png", 0, true);
		bg.setVector(-0.1, 0);
	}
	/****************************************************************************************/
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	/****************************************************************************************/
	@Override
	public void endStage() {
		// TODO Auto-generated method stub
		
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
	}
	/****************************************************************************************/
	@Override
	public void keyPressed(int k) {
		// TODO Auto-generated method stub
		
	}
	/****************************************************************************************/
	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		
	}


}

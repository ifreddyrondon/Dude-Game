package com.spantons.gameState;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.spantons.entity.Entity;

public abstract class Stage {

	protected GameStagesManager gsm;
	protected ArrayList<Entity> characters;
	protected ArrayList<Entity> jasons;
	protected Entity currentCharacter;
	protected boolean secondaryMenu;
	
	
	public abstract void init();
	public abstract void update();
	public abstract void draw(Graphics2D g);
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);
	
	/****************************************************************************************/
	public Entity getCurrentCharacter() {
		return currentCharacter;
	}
	public void setCurrentCharacter(Entity i) {
		currentCharacter = i;
	}
	public ArrayList<Entity> getCharacters() {
		return characters;
	}
	public ArrayList<Entity> getJasons() {
		return jasons;
	}
	public boolean isSecondaryMenu() {
		return secondaryMenu;
	}
}

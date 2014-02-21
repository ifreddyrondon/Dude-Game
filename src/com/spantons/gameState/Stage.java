package com.spantons.gameState;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.spantons.entity.Entity;
import com.spantons.object.Object;

public abstract class Stage {

	protected GameStagesManager gsm;
	protected ArrayList<Entity> characters;
	protected ArrayList<Entity> jasons;
	protected ArrayList<Object> objects;
	protected Entity currentCharacter;
	protected boolean secondaryMenu;
	
	public abstract void init();
	public abstract void endStage();
	public abstract void update();
	public abstract void draw(Graphics2D g);
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);
	
	/****************************************************************************************/
	public void selectNextCurrentCharacter(){
		if (characters.isEmpty() && currentCharacter.isDead())
			endStage();
		
		if (characters.isEmpty()) 
			return;
		
		currentCharacter.setAllMov(false);
		characters.add(currentCharacter);
		currentCharacter = characters.get(0);
		currentCharacter.setVisible(true);
		characters.remove(0);
	}
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
	public ArrayList<Object> getObjects() {
		return objects;
	}
	public boolean isSecondaryMenu() {
		return secondaryMenu;
	}
}

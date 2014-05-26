package com.spantons.gameState;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import com.spantons.dialogue.DialogueManager;
import com.spantons.entity.Entity;
import com.spantons.generalInterfaces.Drawable;
import com.spantons.generalInterfaces.Keyable;
import com.spantons.generalInterfaces.Updateable;
import com.spantons.object.Door;
import com.spantons.object.Object;
import com.spantons.tileMap.TileMap;

public abstract class Stage implements Drawable, Updateable, Keyable{

	protected TileMap tileMap;
	protected GameStagesManager gsm;
	protected ArrayList<Entity> characters;
	protected ArrayList<Entity> jasons;
	protected ArrayList<Entity> dead;
	protected ArrayList<Object> objects;
	protected HashMap<String, Door> doors;
	protected Entity currentCharacter;
	protected DialogueManager dialogues;
	protected boolean secondaryMenu;
	
	public abstract void init();
	public abstract void endStage();
	
	private Point saveNextCurrentCharMapPosition;
	
	/****************************************************************************************/
	public void selectNextCurrentCharacter(){
		if (characters.isEmpty() && currentCharacter.isDead())
			endStage();
		
		if (characters.isEmpty()) {
			dialogues.alone();
			return;
		}
		
		characters.add(currentCharacter);
		currentCharacter.setAllMov(false);
		currentCharacter = characters.get(0);
		
		saveNextCurrentCharMapPosition = currentCharacter.getMapPositionOfCharacter();
		tileMap.setPositionByCharacter(currentCharacter);
		currentCharacter.setMapPosition(
				saveNextCurrentCharMapPosition.x, 
				saveNextCurrentCharMapPosition.y);
		
		characters.remove(0);
		currentCharacter.setVisible(true);
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
	public ArrayList<Entity> getDead() {
		return dead;
	}
	public ArrayList<Object> getObjects() {
		return objects;
	}
	public HashMap<String, Door> getDoors() {
		return doors;
	}
	public boolean isSecondaryMenu() {
		return secondaryMenu;
	}
	public void helpStage() {
		// TODO Auto-generated method stub
	}
	public DialogueManager getDialogues() {
		return dialogues;
	}

}

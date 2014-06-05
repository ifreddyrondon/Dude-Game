package com.spantons.gameStages;

import java.util.ArrayList;
import java.util.HashMap;

import com.spantons.Interfaces.IDrawable;
import com.spantons.Interfaces.IKeyable;
import com.spantons.Interfaces.IUpdateable;
import com.spantons.dialogue.DialogueManager;
import com.spantons.entity.Entity;
import com.spantons.object.Door;
import com.spantons.object.Object;
import com.spantons.tileMap.TileMap;

public abstract class Stage implements IDrawable, IUpdateable, IKeyable{

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

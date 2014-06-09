package com.spantons.gameStages;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Timer;

import com.spantons.dialogue.DialogueManager;
import com.spantons.entity.Entity;
import com.spantons.entity.Hud;
import com.spantons.gameStagesLevels.DrawLevel;
import com.spantons.gameStagesLevels.SelectCurrentCharacterLevel;
import com.spantons.gameState.interfaces.IStage;
import com.spantons.magicNumbers.FontPath;
import com.spantons.object.HandleObjects;
import com.spantons.object.Object;
import com.spantons.objects.Door;
import com.spantons.singleton.FontCache;
import com.spantons.tileMap.TileMap;

public abstract class StagesLevels implements IStage {

	protected TileMap tileMap;
	protected GameStagesManager gsm;
	protected Hud hud;
	protected int countdown;
	protected Timer timer;
	
	protected ArrayList<Entity> characters;
	protected ArrayList<Entity> enemies;
	protected ArrayList<Entity> dead;
	protected ArrayList<Object> objects;
	protected HashMap<String, Door> doors;
	protected Entity currentCharacter;
	protected HandleObjects handleObject;
	protected DrawLevel drawLevel;
	protected SelectCurrentCharacterLevel nextCharacter;
	
	protected DialogueManager dialogues;
	protected boolean secondaryMenu;
	public static Font fontDialogues = FontCache.getInstance().getFont(FontPath.FONT_DARK_IS_THE_NIGTH).deriveFont(Font.PLAIN, 20);
	public static Color colorDialogues = Color.BLACK;
	protected int countdownStartDialogues;
	protected Timer startDialogues;
	
	/****************************************************************************************/
	public Entity getCurrentCharacter() {
		return currentCharacter;
	}
	public ArrayList<Entity> getCharacters() {
		return characters;
	}
	public ArrayList<Entity> getJasons() {
		return enemies;
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
	public DialogueManager getDialogues() {
		return dialogues;
	}
	public TileMap getTileMap() {
		return tileMap;
	}
	
}

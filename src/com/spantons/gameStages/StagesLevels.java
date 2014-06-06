package com.spantons.gameStages;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Timer;

import com.spantons.dialogue.DialogueManager;
import com.spantons.entity.Entity;
import com.spantons.entity.Hud;
import com.spantons.gameState.interfaces.Stage;
import com.spantons.magicNumbers.FontPath;
import com.spantons.object.Object;
import com.spantons.objects.Door;
import com.spantons.singleton.FontCache;
import com.spantons.tileMap.TileMap;

public abstract class StagesLevels implements Stage {

	protected TileMap tileMap;
	protected GameStagesManager gsm;
	protected Hud hud;
	protected int countdown;
	protected Timer timer;
	
	protected ArrayList<Entity> characters;
	protected ArrayList<Entity> jasons;
	protected ArrayList<Entity> dead;
	protected ArrayList<Object> objects;
	protected HashMap<String, Door> doors;
	protected Entity currentCharacter;
	
	protected DialogueManager dialogues;
	protected boolean secondaryMenu;
	public static Font fontDialogues = FontCache.getInstance().getFont(FontPath.FONT_DARK_IS_THE_NIGTH).deriveFont(Font.PLAIN, 20);
	public static Color colorDialogues = Color.BLACK;
	protected int countdownStartDialogues;
	protected Timer startDialogues;
	
	public abstract void draw(Graphics2D g);
	public abstract void update();
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);
	
	/****************************************************************************************/
	public Entity getCurrentCharacter() {
		return currentCharacter;
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
	public DialogueManager getDialogues() {
		return dialogues;
	}
	
}

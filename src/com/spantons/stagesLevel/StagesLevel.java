package com.spantons.stagesLevel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Timer;

import com.spantons.Interfaces.IDrawable;
import com.spantons.Interfaces.IUpdateable;
import com.spantons.dialogue.DialogueManager;
import com.spantons.entity.Entity;
import com.spantons.entity.EntityUtils;
import com.spantons.entity.Hud;
import com.spantons.magicNumbers.FontPath;
import com.spantons.object.HandleObjects;
import com.spantons.object.IHandleObjects;
import com.spantons.object.Object;
import com.spantons.singleton.FontCache;
import com.spantons.singleton.SoundCache;
import com.spantons.stages.GameStagesManager;
import com.spantons.stages.IStage;
import com.spantons.tileMap.TileMap;
import com.spantons.utilities.ToHours;

public class StagesLevel implements IStage {

	protected TileMap tileMap;
	protected GameStagesManager gsm;
	protected Hud hud;
	protected int countdown;
	protected Timer timer;
	
	protected ArrayList<Entity> characters;
	protected ArrayList<Entity> enemies;
	protected ArrayList<Entity> dead;
	protected ArrayList<Object> objects;
	protected ArrayList<Door> doors;
	protected Point exitPoint;
	protected ArrayList<Point> saveZone;
	protected Entity currentCharacter;
	
	protected IHandleObjects handleObject;
	protected IDrawable drawLevel;
	protected INextCharacter nextCharacter;
	protected ILevelGoals goals;
	protected ICheckTransparentWalls checkTransparentWalls;
	protected ITransformTransparentWalls transformTransparentWalls;
	protected IUpdateable update;
	protected ITimeOut timeOut;
	
	protected HashMap<String, ArrayList<String>> stringDialogues;
	protected DialogueManager dialogues;
	protected boolean secondaryMenu;
	public static Font fontDialogues = FontCache.getInstance().getFont(FontPath.FONT_DARK_IS_THE_NIGTH).deriveFont(Font.PLAIN, 20);
	public static Color colorDialogues = Color.BLACK;
	protected Timer timerAwakeningDialogues;
	
	protected Timer timerLightsOn;
	protected Timer timerLightsOff;
	
	/****************************************************************************************/
	public StagesLevel(GameStagesManager _gsm) {
		gsm = _gsm;
		hud = new Hud(this);
		secondaryMenu = false;
		
		characters = new ArrayList<Entity>();
		enemies = new ArrayList<Entity>();
		dead = new ArrayList<Entity>();
		objects = new ArrayList<Object>();
		doors = new ArrayList<Door>();
		
		handleObject = new HandleObjects();
		stringDialogues = new HashMap<String, ArrayList<String>>();
		stringDialogues.put("AWAKENING", new ArrayList<String>());
		stringDialogues.put("RAMDON", new ArrayList<String>());
		stringDialogues.put("STORY", new ArrayList<String>());
		
		timer = new Timer(1000, new ActionListener() { 
			@Override 
			public void actionPerformed(ActionEvent ae) { 
				countdown--; 
				hud.update(ToHours.SecondsToHours(countdown));
				if (countdown == 0) {
					timer.stop();
					timeOut.timeOut();
				}
			} 
		}); 
	}
	
	/****************************************************************************************/
	protected void startLevel() {
		if (timer != null) 
			timer.start();
		
		if (timerAwakeningDialogues != null)
			timerAwakeningDialogues.start();
		
		if (timerLightsOn != null && timerLightsOff != null) 
			timerLightsOn.start();
		
		hud.update(ToHours.SecondsToHours(countdown));
	}
	
	/****************************************************************************************/
	public void update() {
		update.update();
	}
	
	/****************************************************************************************/
	@Override
	public void draw(Graphics2D g) {
		drawLevel.draw(g);
	}
	
	/****************************************************************************************/
	public void keyPressed(int k) {
		
		if (k == KeyEvent.VK_LEFT)
			currentCharacter.setMovLeft(true);
		if (k == KeyEvent.VK_RIGHT)
			currentCharacter.setMovRight(true);
		if (k == KeyEvent.VK_UP)
			currentCharacter.setMovUp(true);
		if (k == KeyEvent.VK_DOWN)
			currentCharacter.setMovDown(true);
		if (k == KeyEvent.VK_TAB) {
			Entity oldCurrentCharacter = currentCharacter;
			currentCharacter = nextCharacter.selectNextCharacter();
			if (currentCharacter == null) {
				currentCharacter = oldCurrentCharacter;
				dialogues.alone();
			}
		}
		if (k == KeyEvent.VK_SPACE)
			currentCharacter.setAttack(true);
		if (k == KeyEvent.VK_ENTER)
			handleObject.takeOrLeaveObject(currentCharacter);
		if (k == KeyEvent.VK_O)
			EntityUtils.openDoor(currentCharacter, this);
		if(k == KeyEvent.VK_ESCAPE)
			secondaryMenu = !secondaryMenu;
		if(k == KeyEvent.VK_R && secondaryMenu)
			secondaryMenu = false;
		if(k == KeyEvent.VK_Q && secondaryMenu){
			SoundCache.getInstance().closeAllSound();
			System.exit(0);
		}
		if(k == KeyEvent.VK_M && secondaryMenu){
			SoundCache.getInstance().stopAllSound();
			gsm.setStage(GameStagesManager.MENU_STAGE);
		}
	}
	
	/****************************************************************************************/
	public void keyReleased(int k) {
		if (k == KeyEvent.VK_LEFT)
			currentCharacter.setMovLeft(false);
		if (k == KeyEvent.VK_RIGHT)
			currentCharacter.setMovRight(false);
		if (k == KeyEvent.VK_UP)
			currentCharacter.setMovUp(false);
		if (k == KeyEvent.VK_DOWN)
			currentCharacter.setMovDown(false);
		if (k == KeyEvent.VK_SPACE)
			currentCharacter.setAttack(false);	
	}

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
	public ArrayList<Door> getDoors() {
		return doors;
	}
	public boolean isSecondaryMenu() {
		return secondaryMenu;
	}
	public DialogueManager getDialogues() {
		return dialogues;
	}
	public HashMap<String, ArrayList<String>> getStringDialogues() {
		return stringDialogues;
	}
	public TileMap getTileMap() {
		return tileMap;
	}
	
}

package com.spantons.gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Timer;

import com.spantons.dialogue.Dialogue;
import com.spantons.dialogue.DialogueStage1;
import com.spantons.entity.Entity;
import com.spantons.entity.EntityChecks;
import com.spantons.entity.Hud;
import com.spantons.entity.character.Jason;
import com.spantons.magicNumbers.FontPath;
import com.spantons.magicNumbers.ImagePath;
import com.spantons.magicNumbers.SoundPath;
import com.spantons.object.Door;
import com.spantons.object.Hammer;
import com.spantons.object.Object;
import com.spantons.singleton.FontCache;
import com.spantons.singleton.SoundCache;
import com.spantons.tileMap.TileMap;
import com.spantons.utilities.RandomItemArrayList;
import com.spantons.utilities.ToHours;

public class Level_1_2_Stage extends Stage{

	private Hud hud;
	
	private int countdown = 120; 
	private Timer timer;
	private Timer lightsOn;
	private int timeLightsOn = 8000;
	private Timer lightsOff;
	private int timeLightsOff = 1100;
	private int countdownStartDialogues = 1300;
	private Timer startDialogues;
	public static Font fontDialogues;
	public static Color colorDialogues;

	/****************************************************************************************/
	public Level_1_2_Stage(GameStagesManager _gsm) {
		gsm = _gsm;
		init();
	}

	/****************************************************************************************/
	@Override
	public void init() {
		hud = new Hud(this);
		secondaryMenu = false;
		tileMap = new TileMap("/maps/map_1_2.txt");
		tileMap.setPosition(0, 0);
		
		jasons = new ArrayList<Entity>();
		dead = new ArrayList<Entity>();
		objects = new ArrayList<Object>();
		doors = new HashMap<String, Door>();
		
		currentCharacter = gsm.getCurrentCharacter();
		currentCharacter.respawn(tileMap, this, 28, 34);
		
		characters = gsm.getCharacters();
		characters.remove(
				RandomItemArrayList.getRandomItemFromArrayList(characters)
		);
		int i = 26;
		for (Entity entity : characters) {
			entity.respawn(tileMap, this, i, 34);
			i = i - 2;
		}
		
		jasons.add(new Jason(tileMap, this, 18, 24, 0.10));
		
//		objects.add(new TriggerPoint(tileMap, 10, 24));
		objects.add(new Hammer(tileMap, 27, 18, 0.15));
//		objects.add(new Hammer(tileMap, 18, 8, 0.15));
//		objects.add(new Hammer(tileMap, 19, 20, 0.15));
//		objects.add(new Alcohol(tileMap, 27, 16));
//		objects.add(new Alcohol(tileMap, 17, 11));
//		objects.add(new Alcohol(tileMap, 21, 25));
//		objects.add(new Beers(tileMap, 28, 10));
//		objects.add(new Beers(tileMap, 21, 17));
//		objects.add(new Beers(tileMap, 24, 16));
//		objects.add(new Pipe(tileMap, 27,19));
//		objects.add(new Pipe(tileMap, 17,27));
//		objects.add(new Pipe(tileMap, 27,13));
//		objects.add(new PieceOfPizza(tileMap, 21,18));
//		objects.add(new PieceOfPizza(tileMap, 10,29));
//		objects.add(new Pizza(tileMap, 28, 13));
//		objects.add(new Pizza(tileMap, 32, 8));
//		objects.add(new Food(tileMap, 11,11));
		
		SoundCache.getInstance().getSound(SoundPath.MUSIC_HORROR_AMBIANCE).loop();
		
		fontDialogues = FontCache.getInstance().getFont(FontPath.FONT_DARK_IS_THE_NIGTH); 
		fontDialogues = fontDialogues.deriveFont(Font.PLAIN, 20);
		colorDialogues = Color.BLACK;
		
		dialogues = new DialogueStage1(this);
		startDialogues =  new Timer(countdownStartDialogues, new ActionListener() { 
			@Override 
			public void actionPerformed(ActionEvent ae) { 
				for (String txt : getDialogues().getStrings().get("STORY_ROOM_1")) {
					getDialogues().addDialogue(
						new Dialogue(
							txt,fontDialogues, colorDialogues, 2500, 
							ImagePath.DIALOGUE_SPEECH_BALLON_MEDIUM,
							Dialogue.RANDOM, Dialogue.MEDIUM_PRIORITY
					));
				}
				startDialogues.stop();
			} 
		}); 
		startDialogues.start();
		
		// Temporizador
		timer = new Timer(1000, new ActionListener() { 
			@Override 
			public void actionPerformed(ActionEvent ae) { 
				countdown--; 
				if (countdown == 0) 
					timer.stop();
			} 
		}); 
		timer.start();
		
		lightsOn = new Timer(timeLightsOn, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tileMap.turnLights();
				lightsOn.stop();
				lightsOff.start();
				SoundCache.getInstance().getSound(SoundPath.SFX_ELECTRIC_CURRENT).play();
			}
		});
		
		lightsOff = new Timer(timeLightsOff, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				tileMap.turnLights();
				lightsOn.start();
				lightsOff.stop();
			}
		});
		
		lightsOn.start();
	}

	/****************************************************************************************/
	@Override
	public void endStage() {
		SoundCache.getInstance().stopAllSound();
		gsm.setStage(GameStagesManager.GAME_OVER_STAGE);
	}

	/****************************************************************************************/
	@Override
	public void update() {
		currentCharacter.update();
		
		if (dialogues != null) 
			dialogues.update();
		
		if (characters.size() > 0) {
			for (Entity character : characters)
				character.updateOtherCharacters();
		}
		
		if (jasons.size() > 0) {
			for (Entity jason : jasons) 
				jason.update();
		}
		
		if (objects.size() > 0) {
			for (Object object : objects) 
				object.update();
		}
		
		if (doors.size() > 0) {
		      for(String key : doors.keySet()) {
		      	doors.get(key).update();
		      	
		      	if (	doors.get(key).isDoorToNextLvl() 
		      		&& doors.get(key).isTryToOpen()) {
					
		      		for (String txt : getDialogues().getStrings().get("STORY_DOOR")) {
						getDialogues().addDialogue(
							new Dialogue(
								txt,fontDialogues, colorDialogues, 1600, 
								ImagePath.DIALOGUE_SPEECH_BALLON_HIGH,
								Dialogue.CURRENT, Dialogue.HIGH_PRIORITY
						));
					}
		      		doors.get(key).setTryToOpen(false);
				}
		      }
		}
		
		if (dead.size() > 0) {
			for (Entity _dead : dead)
				_dead.updateDead();
		}
	}

	/****************************************************************************************/
	@Override
	public void draw(Graphics2D g) {
		tileMap.draw(g);
		hud.Draw(g, ToHours.SecondsToHours(countdown));
		dialogues.draw(g);
	}

	/****************************************************************************************/
	@Override
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_LEFT)
			currentCharacter.setMovLeft(true);
		if (k == KeyEvent.VK_RIGHT)
			currentCharacter.setMovRight(true);
		if (k == KeyEvent.VK_UP)
			currentCharacter.setMovUp(true);
		if (k == KeyEvent.VK_DOWN)
			currentCharacter.setMovDown(true);
		if (k == KeyEvent.VK_TAB)
			selectNextCurrentCharacter();
		if (k == KeyEvent.VK_SPACE)
			currentCharacter.setAttack(true);
		if (k == KeyEvent.VK_ENTER)
			currentCharacter.takeOrLeaveObject();
		if (k == KeyEvent.VK_O)
			EntityChecks.checkIfDoorOpenWithKey(currentCharacter, this);
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
	@Override
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
	
}

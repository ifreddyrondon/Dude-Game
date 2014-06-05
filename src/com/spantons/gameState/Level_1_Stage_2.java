package com.spantons.gameState;

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

import com.spantons.dialogue.Dialogue;
import com.spantons.dialogue.DialogueStage1;
import com.spantons.entity.Entity;
import com.spantons.entity.EntityChecks;
import com.spantons.entity.Hud;
import com.spantons.entity.character.Jason;
import com.spantons.magicNumbers.FontPath;
import com.spantons.magicNumbers.ImagePath;
import com.spantons.magicNumbers.SoundPath;
import com.spantons.object.Alcohol;
import com.spantons.object.Beers;
import com.spantons.object.Door;
import com.spantons.object.Food;
import com.spantons.object.Hammer;
import com.spantons.object.Object;
import com.spantons.object.PieceOfPizza;
import com.spantons.object.Pipe;
import com.spantons.object.Pizza;
import com.spantons.object.TriggerPoint;
import com.spantons.singleton.FontCache;
import com.spantons.singleton.SoundCache;
import com.spantons.tileMap.TileMap;
import com.spantons.utilities.RandomItemArrayList;

public class Level_1_Stage_2 extends Stage{

	private Hud hud;
	
	private DrawLevel drawLevel;
	private SelectCurrentCharacterLevel nextCharacter;
	private LoseLevel loseLevel;
	
	private int countdown = 100; 
	private Timer timer;
	private Timer lightsDeploy;
	private Timer lightsOn;
	private int timeLightsOn = 8000;
	private Timer lightsOff;
	private int timeLightsOff = 1100;
	private int countdownStartDialogues = 1300;
	private Timer startDialogues;
	public static Font fontDialogues;
	public static Color colorDialogues;
	boolean allTriggerPointActivated;
	Point[] exitPoint = {new Point(28,3),new Point(29,3),new Point(30,3)};

	/****************************************************************************************/
	public Level_1_Stage_2(GameStagesManager _gsm) {
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
		if (characters.size() > 0){
			characters.remove(
					RandomItemArrayList.getRandomItemFromArrayList(characters)
			);
			int i = 26;
			for (Entity entity : characters) {
				entity.respawn(tileMap, this, i, 34);
				i = i - 2;
			}
		}
		
		jasons.add(new Jason(tileMap, this, 18, 24, 0.10));
		
		doors.put("hall", new Door(
				tileMap, 33,38, 
				Door.ANIMATION_CLOSE_A, 
				Door.CLOSE, 
				Door.LOCK,"hall",
				false));
		
		doors.put("exit", new Door(
				tileMap, 29,4, 
				Door.ANIMATION_CLOSE_B, 
				Door.CLOSE, 
				Door.LOCK,"exit",
				true));
		
		objects.add(new TriggerPoint(tileMap, this, 11, 23));
		objects.add(new TriggerPoint(tileMap, this, 6, 6));
		objects.add(new Hammer(tileMap, 19, 9, 0.15));
		objects.add(new Hammer(tileMap, 7, 16, 0.15));
		objects.add(new Alcohol(tileMap, 27, 18));
		objects.add(new Alcohol(tileMap, 17, 26));
		objects.add(new Alcohol(tileMap, 11, 33));
		objects.add(new Beers(tileMap, 24, 19));
		objects.add(new Beers(tileMap, 31, 8));
		objects.add(new Beers(tileMap, 18, 13));
		objects.add(new Pipe(tileMap, 33,8));
		objects.add(new Pipe(tileMap, 25,16));
		objects.add(new Pipe(tileMap, 8,10));
		objects.add(new PieceOfPizza(tileMap, 6,35));
		objects.add(new PieceOfPizza(tileMap, 25,22));
		objects.add(new PieceOfPizza(tileMap, 26,6));
		objects.add(new Pizza(tileMap, 10, 31));
		objects.add(new Pizza(tileMap,22, 28));
		objects.add(new Food(tileMap, 35,5));
		
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
				drawLevel.setCountdown(countdown);
				if (countdown == 0) {
					timer.stop();
					deployJason();
				}
					
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
		
		drawLevel = new DrawLevel(tileMap, hud, dialogues);
		nextCharacter = new SelectCurrentCharacterLevel(characters, currentCharacter, tileMap);
		loseLevel = new LoseLevel(gsm);
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
		
		if (characters.isEmpty() && currentCharacter.isDead()) 
			loseLevel.endStage();
		
		if (currentCharacter.isDead()) 
			currentCharacter = nextCharacter.selectNextCharacter();
		
		currentCharacter.update();
		
		for (Point exit : exitPoint) {
			if (currentCharacter.getMapPositionOfCharacter().equals(exit)) {
				SoundCache.getInstance().stopAllSound();
	      		gsm.setCurrentCharacter(currentCharacter);
	      		ArrayList<Entity> charactersNotBusy = new ArrayList<Entity>();
	      		for (Entity entity : characters) {
					if (!entity.isBusy()) 
						charactersNotBusy.add(entity);
				}
	      		gsm.setCharacters(charactersNotBusy);
	      		currentCharacter.setAllMov(false);
	      		gsm.setStage(GameStagesManager.LEVEL_1_STAGE_3);
			}
		}
		
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
			allTriggerPointActivated = true;
			for (Object object : objects) {
				object.update();
				
				if (	object.getDescription().equals("Punto de activacion") 
					&& allTriggerPointActivated) {
					TriggerPoint aux = (TriggerPoint) object;
					if (!aux.isActivated()) 
						allTriggerPointActivated = false;
				}
			}
		}
		
		if (doors.size() > 0) {
		      for(String key : doors.keySet()) {
		      	doors.get(key).update();
		      	
		      	if (doors.get(key).isDoorToNextLvl()) {
		      	
		      		if (allTriggerPointActivated) {
		      			doors.get(key).setStatusBlock(Door.UNLOCK);
		      			doors.get(key).setStatusOpen(Door.OPEN);
			      		return;
			      	
		      		} else {
		      			doors.get(key).setStatusBlock(Door.LOCK);
		      			doors.get(key).setStatusOpen(Door.CLOSE);
		      		}
		      		
		      		if(doors.get(key).isTryToOpen()) {
		      		
			      		for (String txt : getDialogues().getStrings().get("STORY_DOOR_ROOM_1")) {
			      			
							getDialogues().addDialogue(
								new Dialogue(
									txt,fontDialogues, colorDialogues, 1800, 
									ImagePath.DIALOGUE_SPEECH_BALLON_HIGH,
									Dialogue.CURRENT, Dialogue.HIGH_PRIORITY
							));
						}
			      		doors.get(key).setTryToOpen(false);
					}
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
		drawLevel.draw(g);
	}

	/****************************************************************************************/
	private void deployJason(){
		tileMap.turnLights();
		lightsDeploy = new Timer(1200, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tileMap.turnLights();
				lightsDeploy.stop();
			}
		});
		lightsDeploy.start();
		
		SoundCache.getInstance().getSound(SoundPath.SFX_ZOMBIE_COME_HERE).play();
		currentCharacter.setFlinchingIncreaseDeltaTimePerversity(250);
		for (Entity character : characters) 
			character.setFlinchingIncreaseDeltaTimePerversity(250);
		
		ArrayList<Entity> aux = new ArrayList<Entity>();
		
		for (Entity jason : jasons) 
			aux.add(new Jason(tileMap, this, jason.getXMap(), jason.getYMap(), 0.10));
		
		jasons.addAll(aux);
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

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
import com.spantons.entity.character.DanaScullyXFiles;
import com.spantons.entity.character.GordonFreeman;
import com.spantons.entity.character.Jason;
import com.spantons.entity.character.LeonTheProfessional;
import com.spantons.entity.character.LizSherman;
import com.spantons.entity.character.Preso;
import com.spantons.magicNumbers.FontPath;
import com.spantons.magicNumbers.ImagePath;
import com.spantons.magicNumbers.SoundPath;
import com.spantons.object.Alcohol;
import com.spantons.object.Beers;
import com.spantons.object.Crowbar;
import com.spantons.object.Door;
import com.spantons.object.Food;
import com.spantons.object.Hammer;
import com.spantons.object.Object;
import com.spantons.object.PieceOfPizza;
import com.spantons.object.Pipe;
import com.spantons.object.Pizza;
import com.spantons.singleton.FontCache;
import com.spantons.singleton.SoundCache;
import com.spantons.tileMap.TileMap;

public class Level_1_Stage_1 extends Stage {

	private Hud hud;
	
	private CheckTransparentWallsLvl1Stage1 checkTransparentWalls;
	private TransformTransparentWallsLv1Stage1 transformTransparentWalls;
	private DrawLevel drawLevel;
	private SelectCurrentCharacterLevel nextCharacter;
	private LoseLevel loseLevel;
	
	private int countdown = 120; 
	private Timer timer;
	private Timer lightsDeploy;
	private int countdownStartDialogues = 2000;
	private Timer startDialogues;
	public static Font fontDialogues;
	public static Color colorDialogues;

	public static int TRANSPARENT = 65;
	public static Point[] A = {
		new Point(10, 16),new Point(11, 16),new Point(12, 16),
		new Point(13, 16),new Point(14, 16),new Point(15, 16),
		new Point(16, 16),new Point(17, 16),new Point(18, 16),
		new Point(19, 16),new Point(20, 16),new Point(22, 16),
		new Point(23, 16),new Point(19,17), new Point(23, 7),
		new Point(23, 8),new Point(23, 9),new Point(23, 10),
		new Point(23, 11),new Point(23, 12),new Point(23, 13),
		new Point(23, 14),new Point(23, 15)};
	
	/****************************************************************************************/
	public Level_1_Stage_1(GameStagesManager _gsm) {
		gsm = _gsm;
		init();
	}
	
	/****************************************************************************************/
	@Override
	public void init() {
		hud = new Hud(this);
		secondaryMenu = false;
		tileMap = new TileMap("/maps/map_1_1.txt");
		tileMap.setPosition(0, 0);
		
		characters = new ArrayList<Entity>();
		jasons = new ArrayList<Entity>();
		dead = new ArrayList<Entity>();
		objects = new ArrayList<Object>();
		doors = new HashMap<String, Door>();
		
		currentCharacter = new LeonTheProfessional(tileMap, this, 16, 19, 0.10);

		characters.add(new GordonFreeman(tileMap, this, 9, 22, 0.10));
		characters.add(new LizSherman(tileMap, this, 13, 17, 0.10));
		characters.add(new DanaScullyXFiles(tileMap, this, 13, 22, 0.10));
		characters.add(new Preso(tileMap, this, 7, 17, 0.10));
//		characters.add(new Preso(tileMap, this, 8, 20, 0.10));
//		characters.add(new Preso(tileMap, this, 13, 17, 0.10));
//		characters.add(new Preso(tileMap, this, 13, 21, 0.10));
		
		Jason jason = new Jason(tileMap, this, 23, 18, 0.10);
		jason.update();
		jason.setDead(true);
		jasons.add(jason);
		jasons.add(new Jason(tileMap, this, 17, 12, 0.10));
		jasons.add(new Jason(tileMap, this, 26, 23, 0.10));
		
		doors.put("panicroom", new Door(
				tileMap, 19,18, 
				Door.ANIMATION_OPEN_A, 
				Door.OPEN, 
				Door.UNLOCK,"panicroom",
				false));
		
		doors.put("exit", new Door(
				tileMap, 21,5, 
				Door.ANIMATION_CLOSE_A, 
				Door.CLOSE, 
				Door.LOCK,"exit",
				true));
		
		doors.put("bathroom", new Door(
				tileMap, 21,16, 
				Door.ANIMATION_OPEN_B, 
				Door.OPEN, 
				Door.UNLOCK,"bathroom",
				false));
		
		doors.put("main", new Door(
				tileMap, 33,27, 
				Door.ANIMATION_CLOSE_B, 
				Door.CLOSE, 
				Door.LOCK,"main",
				false));
		
		objects.add(new Crowbar(tileMap, 10, 12, 0.23, "exit"));
		objects.add(new Hammer(tileMap, 12, 13, 0.15));
		objects.add(new Hammer(tileMap, 14, 18, 0.15));
		objects.add(new Hammer(tileMap, 25, 32, 0.15));
		objects.add(new Alcohol(tileMap, 27, 16));
		objects.add(new Alcohol(tileMap, 17, 11));
		objects.add(new Alcohol(tileMap, 20, 28));
		objects.add(new Beers(tileMap, 28, 10));
		objects.add(new Beers(tileMap, 21, 17));
		objects.add(new Beers(tileMap, 25, 17));
		objects.add(new Pipe(tileMap, 25,21));
		objects.add(new Pipe(tileMap, 17,27));
		objects.add(new Pipe(tileMap, 27,13));
		objects.add(new PieceOfPizza(tileMap, 21,18));
		objects.add(new PieceOfPizza(tileMap, 10,29));
		objects.add(new Pizza(tileMap, 28, 13));
		objects.add(new Pizza(tileMap, 32, 8));
		objects.add(new Food(tileMap, 11,11));
		
		SoundCache.getInstance().getSound(SoundPath.MUSIC_HORROR_AMBIANCE).loop();
		
		// Dialogos
		fontDialogues = FontCache.getInstance().getFont(FontPath.FONT_DARK_IS_THE_NIGTH); 
		fontDialogues = fontDialogues.deriveFont(Font.PLAIN, 20);
		colorDialogues = Color.BLACK;
		
		dialogues = new DialogueStage1(this);
		
		startDialogues =  new Timer(countdownStartDialogues, new ActionListener() { 
			@Override 
			public void actionPerformed(ActionEvent ae) { 
				for (String txt : getDialogues().getStrings().get("THOUGHTS_AWAKENING_1")) {
					getDialogues().addDialogue(
						new Dialogue(
							txt,fontDialogues, colorDialogues, 2500, 
							ImagePath.DIALOGUE_SPEECH_BALLON_NORMAL,
							Dialogue.RANDOM, Dialogue.NORMAL_PRIORITY
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
		
		
		Point[] enable = {new Point(20, 15),new Point(21, 15),new Point(22, 15)};
		Point[] disable = {new Point(21, 16)};
		
		checkTransparentWalls = new CheckTransparentWallsLvl1Stage1(enable, disable);
		transformTransparentWalls = new TransformTransparentWallsLv1Stage1(tileMap, doors.get("bathroom"));
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
		
		if (checkTransparentWalls.checkTransparent(currentCharacter)) 
			transformTransparentWalls.transformToTransparentWalls();
		else
			transformTransparentWalls.transformToOriginalWalls();
		
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
				
		      	} else if (	doors.get(key).isDoorToNextLvl() 
		      				&& doors.get(key).getStatusOpen() == Door.OPEN) {
		      		
		      		SoundCache.getInstance().stopAllSound();
		      		gsm.setCurrentCharacter(currentCharacter);
		      		gsm.setCharacters(characters);
		      		gsm.setStage(GameStagesManager.LEVEL_1_STAGE_2);
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

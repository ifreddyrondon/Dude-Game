package com.spantons.stagesLevel;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import com.spantons.dialogue.Dialogue;
import com.spantons.dialogue.DialogueStage1;
import com.spantons.entity.Entity;
import com.spantons.entity.ParseXMLEntity;
import com.spantons.magicNumbers.ImagePath;
import com.spantons.magicNumbers.SoundPath;
import com.spantons.magicNumbers.XMLPath;
import com.spantons.object.Object;
import com.spantons.objects.Alcohol;
import com.spantons.objects.Beers;
import com.spantons.objects.Crowbar;
import com.spantons.objects.Door;
import com.spantons.objects.Food;
import com.spantons.objects.Hammer;
import com.spantons.objects.PieceOfPizza;
import com.spantons.objects.Pipe;
import com.spantons.objects.Pizza;
import com.spantons.singleton.SoundCache;
import com.spantons.stages.GameStagesManager;
import com.spantons.tileMap.TileMap;

public class Level_1_Stage_1 extends StagesLevel {
 
	private Timer lightsDeploy;

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
		super(_gsm);
		
		tileMap = new TileMap("/maps/map_1_1.txt");
		countdown = 120;
		countdownStartDialogues = 2000;
		
		characters = new ArrayList<Entity>();
		enemies = new ArrayList<Entity>();
		dead = new ArrayList<Entity>();
		objects = new ArrayList<Object>();
		doors = new ArrayList<Door>();
		exitPoint = new Point(21,5);
		
		currentCharacter = ParseXMLEntity.getEntityFromXML(XMLPath.XML_CHARACTER_LEON_THE_PROFESSIONAL, this, 16, 19);

		characters.add(ParseXMLEntity.getEntityFromXML(XMLPath.XML_CHARACTER_GORDON_FREEMAN, this, 9, 22));
		characters.add(ParseXMLEntity.getEntityFromXML(XMLPath.XML_CHARACTER_LIZ_SHERMAN, this, 13, 17));
		characters.add(ParseXMLEntity.getEntityFromXML(XMLPath.XML_CHARACTER_DANA_SCULLY, this, 13, 22));
		characters.add(ParseXMLEntity.getEntityFromXML(XMLPath.XML_CHARACTER_PRESO, this, 7, 17));
		
		enemies.add(ParseXMLEntity.getEntityFromXML(XMLPath.XML_CHARACTER_JASON, this, 17, 12));
		enemies.add(ParseXMLEntity.getEntityFromXML(XMLPath.XML_CHARACTER_JASON, this, 26, 23));
		
		Object crowbar = new Crowbar(tileMap, 10, 12, 0.23);
		Door exit = new Door(this, 21,5, Door.ANIMATION_CLOSE_A, false, false);
		exit.setKey(crowbar);
		
		doors.add(
			new Door(this, 19,18, Door.ANIMATION_OPEN_A, true, true));
		
		doors.add(exit);
		
		doors.add(
			new Door(this, 21,16, Door.ANIMATION_OPEN_B, true, true));
		
		doors.add(
			new Door(this, 33,27, Door.ANIMATION_CLOSE_B, false, false));
		
		objects.add(crowbar);
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
		
		dialogues = new DialogueStage1(this);
		
		stringDialogues.get("AWAKENING").add("Hey qué hago aquí");
		stringDialogues.get("AWAKENING").add("Quienes son ustedes");
		stringDialogues.get("AWAKENING").add("Qué sucede");
		stringDialogues.get("AWAKENING").add("???");
		
		stringDialogues.get("STORY").add("Parece que hay algo \ndetrás que no deja abrirla");
		stringDialogues.get("STORY").add("Necesitamos una palanca!!");
		
		startDialogues =  new Timer(countdownStartDialogues, new ActionListener() { 
			@Override 
			public void actionPerformed(ActionEvent ae) { 
				for (String txt : stringDialogues.get("AWAKENING")) {
					dialogues.addDialogue(
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
		transformTransparentWalls = new TransformTransparentWallsLv1Stage1(tileMap);
		drawLevel = new DrawLevel(tileMap, hud, dialogues);
		nextCharacter = new SelectCurrentCharacterLevel(characters, currentCharacter, tileMap);
		drawLevel.setCountdown(countdown);
		goals =  new GoalsLevel_1_Stage_1(this);
		
	}
	
	/****************************************************************************************/
	@Override
	public void update() {
		
		if (goals != null) 
			goals.checkGoals();
		
		if (characters.isEmpty() && currentCharacter.isDead()) 
			gsm.setStage(GameStagesManager.GAME_OVER_STAGE);

		if (currentCharacter.isDead()) 
			currentCharacter = nextCharacter.selectNextCharacter();
		
		currentCharacter.update();
		
		if (checkTransparentWalls != null) {
			if (checkTransparentWalls.checkTransparent(currentCharacter)) 
				transformTransparentWalls.transformToTransparentWalls();
			else
				transformTransparentWalls.transformToOriginalWalls();
		}
		
		if (dialogues != null) 
			dialogues.update();
		
		if (characters.size() > 0) {
			for (Entity character : characters)
				character.update();
		}
		
		if (enemies.size() > 0) {
			for (Entity jason : enemies) 
				jason.update();
		}
		
		if (objects.size() > 0) {
			for (Object object : objects) 
				object.update();
		}
		
		if (doors.size() > 0) {
		      for(Door door : doors) 
		      	door.update();
		}
		
		if (dead.size() > 0) {
			for (Entity _dead : dead)
				_dead.update();
		}
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
		
		for (Entity jason : enemies) 
			aux.add(ParseXMLEntity.getEntityFromXML(XMLPath.XML_CHARACTER_JASON, this, jason.getXMap(), jason.getYMap()));
		
		enemies.addAll(aux);
	}
	
		
}

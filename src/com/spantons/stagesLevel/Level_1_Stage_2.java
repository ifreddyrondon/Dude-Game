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
import com.spantons.object.ParseXMLObject;
import com.spantons.singleton.SoundCache;
import com.spantons.stages.GameStagesManager;
import com.spantons.tileMap.TileMap;
import com.spantons.utilities.RandomItemArrayList;

public class Level_1_Stage_2 extends StagesLevel{

	private Timer lightsDeploy;
	private Timer lightsOn;
	private int timeLightsOn = 8000;
	private Timer lightsOff;
	private int timeLightsOff = 1100;
	boolean allTriggerPointActivated;

	/****************************************************************************************/
	public Level_1_Stage_2(GameStagesManager _gsm) {
		super(_gsm);
		
		tileMap = new TileMap("/maps/map_1_2.txt");
		tileMap.setPosition(0, 0);
		countdown = 100;
		countdownStartDialogues = 1300;
		exitPoint = new Point(29,4);
		
		enemies = new ArrayList<Entity>();
		dead = new ArrayList<Entity>();
		objects = new ArrayList<Object>();
		doors = new ArrayList<Door>();
		
		currentCharacter = gsm.getCurrentCharacter();
		currentCharacter.respawn(this, 28, 34);
		
		characters = gsm.getCharacters();
		if (characters.size() > 0){
			characters.remove(
					RandomItemArrayList.getRandomItemFromArrayList(characters)
			);
			int i = 26;
			for (Entity entity : characters) {
				entity.respawn(this, i, 34);
				i = i - 2;
			}
		}
		
		enemies.add(ParseXMLEntity.getEntityFromXML(XMLPath.XML_CHARACTER_JASON, this, 18, 24));
		
		doors.add( new Door(
				this, 33,38, Door.ANIMATION_CLOSE_A, false, false));
		
		Door exitDoor = new Door(this, 29,4, Door.ANIMATION_CLOSE_B, false, false);
		Object trigger1 = ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_TRIGGER_POINT, this, 11, 23);
		exitDoor.setKey(trigger1);
		doors.add(exitDoor);
		
		objects.add(trigger1);
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_TRIGGER_POINT, this, 6, 6));
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_HAMMER, this, 19, 9));
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_HAMMER, this, 7, 16));
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_ALCOHOL, this, 27, 18));
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_ALCOHOL, this, 17, 26));
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_ALCOHOL, this, 11, 33));
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_BEERS, this, 24, 19));
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_BEERS, this, 31, 8));
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_BEERS, this, 18, 13));
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_PIPE, this, 33, 8));
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_PIPE, this, 25, 16));
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_PIPE, this, 8, 10));
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_PIECE_OF_PIZZA, this, 6, 35));
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_PIECE_OF_PIZZA, this, 25, 22));
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_PIECE_OF_PIZZA, this, 26, 6));
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_PIZZA, this, 10, 31));
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_PIZZA, this, 22, 28));
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_FOOD, this, 35,5));
		
		dialogues = new DialogueStage1(this);
		
		stringDialogues.get("AWAKENING").add("Falta alguien");
		stringDialogues.get("AWAKENING").add("Seguro se quedó para \nusar el baño");
		stringDialogues.get("AWAKENING").add("Qué asqueroso");
		stringDialogues.get("AWAKENING").add("Tal vez encontró una \nsalida");
		
		stringDialogues.get("STORY").add("Que puerta para particular");
		stringDialogues.get("STORY").add("Creo que se abre con\n botones");
		stringDialogues.get("STORY").add("Parece que tenemos que \n presionar algo");
				
		startDialogues =  new Timer(countdownStartDialogues, new ActionListener() { 
			@Override 
			public void actionPerformed(ActionEvent ae) { 
				for (String txt : stringDialogues.get("AWAKENING")) {
					dialogues.addDialogue(
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
		drawLevel.setCountdown(countdown);
		goals =  new GoalsLevel_1_Stage_2(this);
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

package com.spantons.stagesLevel;

import java.awt.Point;
import java.util.ArrayList;

import com.spantons.dialogue.DialogueStage1;
import com.spantons.entity.Entity;
import com.spantons.entity.ParseXMLEntity;
import com.spantons.magicNumbers.XMLPath;
import com.spantons.object.Object;
import com.spantons.object.ParseXMLObject;
import com.spantons.stages.GameStagesManager;
import com.spantons.tileMap.TileMap;
import com.spantons.utilities.RandomItemArrayList;

public class Level_1_Stage_2 extends StagesLevel{

	/****************************************************************************************/
	public Level_1_Stage_2(GameStagesManager _gsm) {
		super(_gsm);
		
		tileMap = new TileMap("/maps/map_1_2.txt");
		tileMap.setPosition(0, 0);
		countdown = 100;
		timerAwakeningDialogues = StagesLevelUtils.setTimerAwakeningDialogues(1300, this);
		timerLightsOn = StagesLevelUtils.setTimerLightsOn(8000, this);
		timerLightsOff = StagesLevelUtils.setTimerLightsOff(1100, this);
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
		
		update = new IUpdateStagesLevel(this);
		drawLevel = new DrawLevel(tileMap, hud, dialogues);
		nextCharacter = new SelectCurrentCharacterLevel(characters, currentCharacter, tileMap);
		drawLevel.setCountdown(countdown);
		goals =  new GoalsLevel_1_Stage_2(this);
		timeOut = new ReleaseEnemiesLevels(this);
		startLevel();
	}
	
}

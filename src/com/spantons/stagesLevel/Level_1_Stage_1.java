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

public class Level_1_Stage_1 extends StagesLevel {

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
		timerAwakeningDialogues = StagesLevelUtils.setTimerAwakeningDialogues(2000, this);
		
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
		
		Object crowbar = ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_CROWBAR, this, 10, 12);
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
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_HAMMER, this, 12, 13));
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_HAMMER, this, 14, 18));
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_HAMMER, this, 25, 32));
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_ALCOHOL, this, 27, 16));
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_ALCOHOL, this, 17, 11));
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_ALCOHOL, this, 20, 28));
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_BEERS, this, 28, 10));
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_BEERS, this, 21, 17));
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_BEERS, this, 25, 17));
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_PIPE, this, 25, 21));
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_PIPE, this, 17, 27));
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_PIPE, this, 27, 13));
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_PIECE_OF_PIZZA, this, 21, 18));
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_PIECE_OF_PIZZA, this, 10, 29));
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_PIZZA, this, 28, 13));
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_PIZZA, this, 32, 8));
		objects.add(ParseXMLObject.getObjectFromXML(XMLPath.XML_OBJECT_FOOD, this, 11,11));
		
		dialogues = new DialogueStage1(this);
		
		stringDialogues.get("AWAKENING").add("Hey qué hago aquí");
		stringDialogues.get("AWAKENING").add("Quienes son ustedes");
		stringDialogues.get("AWAKENING").add("Qué sucede");
		stringDialogues.get("AWAKENING").add("???");
		
		stringDialogues.get("STORY").add("Parece que hay algo \ndetrás que no deja abrirla");
		stringDialogues.get("STORY").add("Necesitamos una palanca!!");
		
		Point[] enable = {new Point(20, 15),new Point(21, 15),new Point(22, 15)};
		Point[] disable = {new Point(21, 16)};
		
		update = new IUpdateStagesLevel(this);
		checkTransparentWalls = new CheckTransparentWallsLvl1Stage1(enable, disable);
		transformTransparentWalls = new TransformTransparentWallsLv1Stage1(tileMap);
		drawLevel = new DrawLevel(tileMap, hud, dialogues);
		nextCharacter = new SelectCurrentCharacterLevel(characters, currentCharacter, tileMap);
		drawLevel.setCountdown(countdown);
		goals =  new GoalsLevel_1_Stage_1(this);
		timeOut = new ReleaseEnemiesLevels(this);
		startLevel();
	}
			
		
}

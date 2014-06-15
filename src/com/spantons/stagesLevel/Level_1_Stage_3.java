package com.spantons.stagesLevel;

import java.util.ArrayList;

import com.spantons.dialogue.DialogueStage1;
import com.spantons.entity.Entity;
import com.spantons.entity.ParseXMLEntity;
import com.spantons.magicNumbers.XMLPath;
import com.spantons.object.Object;
import com.spantons.stages.GameStagesManager;
import com.spantons.tileMap.TileMap;

public class Level_1_Stage_3 extends StagesLevel {

	public Level_1_Stage_3(GameStagesManager _gsm) {
		super(_gsm);
		
		tileMap = new TileMap("/maps/map_1_3.txt");
		countdown = 90;

		characters = new ArrayList<Entity>();
		enemies = new ArrayList<Entity>();
		dead = new ArrayList<Entity>();
		objects = new ArrayList<Object>();
		doors = new ArrayList<Door>();

		currentCharacter = gsm.getCurrentCharacter();
		currentCharacter.respawn(this, 5, 22);
		
		characters = gsm.getCharacters();
		int i = 21;
		for (Entity entity : characters) {
			entity.respawn(this, 5, i);
			i--;
		}
		
		enemies.add(ParseXMLEntity.getEntityFromXML(XMLPath.XML_CHARACTER_JASON, this, 15, 5));
		enemies.add(ParseXMLEntity.getEntityFromXML(XMLPath.XML_CHARACTER_JASON, this, 20, 19));
		enemies.add(ParseXMLEntity.getEntityFromXML(XMLPath.XML_CHARACTER_JASON, this, 32, 24));
		enemies.add(ParseXMLEntity.getEntityFromXML(XMLPath.XML_CHARACTER_JASON, this, 37, 11));
		
		
		dialogues = new DialogueStage1(this);
				
		update = new IUpdateStagesLevel(this);
		drawLevel = new DrawLevel(tileMap, hud, dialogues);
		nextCharacter = new SelectCurrentCharacterLevel(characters, currentCharacter, tileMap);
		drawLevel.setCountdown(countdown);
		timeOut = new ReleaseEnemiesLevels(this);
		startLevel();
	}
	
}

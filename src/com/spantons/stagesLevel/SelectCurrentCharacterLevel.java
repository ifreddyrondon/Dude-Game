package com.spantons.stagesLevel;

import java.awt.Point;
import java.util.ArrayList;

import com.spantons.entity.Entity;
import com.spantons.tileMap.TileMap;

public class SelectCurrentCharacterLevel implements INextCharacter{

	private ArrayList<Entity> characters;
	private Entity currentCharacter;
	private TileMap tileMap;
	private Point saveNextCurrentCharMapPosition;
	
	/****************************************************************************************/
	public SelectCurrentCharacterLevel(ArrayList<Entity> _characters, Entity _currentCharacter, TileMap _tileMap) {
		characters = _characters;
		currentCharacter = _currentCharacter;
		tileMap = _tileMap;
	}
	
	/****************************************************************************************/
	@Override
	public Entity selectNextCharacter() {
		
		if (characters.isEmpty()) 
			return null;
		
		characters.add(currentCharacter);
		currentCharacter.setAllMov(false);
		currentCharacter = characters.get(0);
		
		saveNextCurrentCharMapPosition = currentCharacter.getMapPositionOfCharacter();
		tileMap.setPositionByCharacter(currentCharacter);
		currentCharacter.setMapPosition(
				saveNextCurrentCharMapPosition.x, 
				saveNextCurrentCharMapPosition.y);
		
		characters.remove(0);
		currentCharacter.setVisible(true);
		
		return currentCharacter;
	}

}

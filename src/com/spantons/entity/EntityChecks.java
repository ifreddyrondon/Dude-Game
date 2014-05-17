package com.spantons.entity;

import java.awt.Point;

import com.spantons.dialogue.Dialogue;
import com.spantons.gameState.Level_1_1_Stage;
import com.spantons.gameState.Stage;
import com.spantons.magicNumbers.ImagePath;
import com.spantons.object.Door;
import com.spantons.object.Object;
import com.spantons.tileMap.TileMap;
import com.spantons.utilities.TileWalk;

public class EntityChecks {

	/****************************************************************************************/
	public static Entity checkIsCloseToAnotherEntity(Entity _entity,
			Stage _stage) {

		Point position = _entity.getMapPositionOfCharacter();
		Point north = TileWalk.walkTo("N", position, 1);
		Point south = TileWalk.walkTo("S", position, 1);
		Point west = TileWalk.walkTo("W", position, 1);
		Point east = TileWalk.walkTo("E", position, 1);
		Point northWest = TileWalk.walkTo("NW", position, 1);
		Point northEast = TileWalk.walkTo("NE", position, 1);
		Point southWest = TileWalk.walkTo("SW", position, 1);
		Point southEast = TileWalk.walkTo("SE", position, 1);
		Point currentCharacterPosition = null;

		if (_stage.getCharacters().size() > 0) {
			for (Entity character : _stage.getCharacters()) {
				currentCharacterPosition = character
						.getMapPositionOfCharacter();
				if (currentCharacterPosition.equals(north)) {
					_entity.setCharacterCloseDirection("N");
					return character;
				} else if (currentCharacterPosition.equals(south)) {
					_entity.setCharacterCloseDirection("S");
					return character;
				} else if (currentCharacterPosition.equals(west)) {
					_entity.setCharacterCloseDirection("W");
					return character;
				} else if (currentCharacterPosition.equals(east)) {
					_entity.setCharacterCloseDirection("E");
					return character;
				} else if (currentCharacterPosition.equals(northWest)) {
					_entity.setCharacterCloseDirection("NW");
					return character;
				} else if (currentCharacterPosition.equals(northEast)) {
					_entity.setCharacterCloseDirection("NE");
					return character;
				} else if (currentCharacterPosition.equals(southWest)) {
					_entity.setCharacterCloseDirection("SW");
					return character;
				} else if (currentCharacterPosition.equals(southEast)) {
					_entity.setCharacterCloseDirection("SE");
					return character;
				}
			}
		}

		if (_stage.getJasons().size() > 0) {
			for (Entity jason : _stage.getJasons()) {
				currentCharacterPosition = jason
						.getMapPositionOfCharacter();
				if (currentCharacterPosition.equals(north)) {
					_entity.setCharacterCloseDirection("N");
					return jason;
				} else if (currentCharacterPosition.equals(south)) {
					_entity.setCharacterCloseDirection("S");
					return jason;
				} else if (currentCharacterPosition.equals(west)) {
					_entity.setCharacterCloseDirection("W");
					return jason;
				} else if (currentCharacterPosition.equals(east)) {
					_entity.setCharacterCloseDirection("E");
					return jason;
				} else if (currentCharacterPosition.equals(northWest)) {
					_entity.setCharacterCloseDirection("NW");
					return jason;
				} else if (currentCharacterPosition.equals(northEast)) {
					_entity.setCharacterCloseDirection("NE");
					return jason;
				} else if (currentCharacterPosition.equals(southWest)) {
					_entity.setCharacterCloseDirection("SW");
					return jason;
				} else if (currentCharacterPosition.equals(southEast)) {
					_entity.setCharacterCloseDirection("SE");
					return jason;
				}
			}
		}

		return null;
	}

	/****************************************************************************************/
	public static boolean checkCharactersCollision(Entity _entity,
			Stage _stage) {

		if (_stage.getCharacters().size() > 0) {
			for (Entity character : _stage.getCharacters()) {
				if (character.getMapPositionOfCharacter().equals(
						_entity.getNextPositionInMap()))
					return false;
			}
		}

		if (_stage.getJasons().size() > 0) {
			for (Entity jason : _stage.getJasons()) {
				if (jason.getMapPositionOfCharacter().equals(
						_entity.getNextPositionInMap()))
					return false;
			}
		}

		return true;
	}

	/****************************************************************************************/
	public static boolean checkTileCollision(Entity _entity, TileMap _tileMap) {

		if (	_entity.getNextPositionInMap().x >= 0
			&& _entity.getNextPositionInMap().y >= 0
			&& _entity.getNextPositionInMap().x < 
				_tileMap.getNumColMap()
			&& _entity.getNextPositionInMap().y < 
				_tileMap.getNumRowsMap()
			) {
			if (	_tileMap.
					getWallPosition(
					_entity.getNextPositionInMap().x,
					_entity.getNextPositionInMap().y) == 0
					) {
				if (	_tileMap.getObjectsPosition(
						_entity.getNextPositionInMap().x,
						_entity.getNextPositionInMap().y) == 0
					)
					return true;
			}
		}
		return false;
	}

	/****************************************************************************************/
	public static void checkTransparentWalls(Entity _entity, Stage _stage,
			TileMap _tileMap) {
		// bathroom walls
		if (_entity.getNextPositionInMap().equals(new Point(20, 15))
				|| _entity.getNextPositionInMap().equals(
						new Point(21, 15))) {

			_tileMap.setTransparentWalls("bathroom");
			_stage.getDoors().get("bathroom").setStatusOpen(Door.OPEN);
		} else if (_entity.getNextPositionInMap().equals(new Point(20, 16))
				|| _entity.getNextPositionInMap().equals(
						new Point(21, 16))) {

			_tileMap.setTransparentWalls("");
			_stage.getDoors().get("bathroom").setStatusOpen(Door.CLOSE);
		}
	}

	/****************************************************************************************/
	public static boolean checkDoors(Entity _entity, Stage _stage) {
		if (_stage.getDoors().size() > 0) {
			for (String key : _stage.getDoors().keySet()) {
				if (_entity.getNextPositionInMap().equals(
						_stage.getDoors().get(key)
								.getPositionInMap())) {
					if (_stage.getDoors().get(key).getStatusBlock() == Door.LOCK) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/****************************************************************************************/
	public static void checkIfDoorOpenWithKey(Entity _entity, Stage _stage) {
		
		Point position = _entity.getMapPositionOfCharacter();
		Point north = TileWalk.walkTo("N", position, 1);
		Point south = TileWalk.walkTo("S", position, 1);
		Point west = TileWalk.walkTo("W", position, 1);
		Point east = TileWalk.walkTo("E", position, 1);
		Point northWest = TileWalk.walkTo("NW", position, 1);
		Point northEast = TileWalk.walkTo("NE", position, 1);
		Point southWest = TileWalk.walkTo("SW", position, 1);
		Point southEast = TileWalk.walkTo("SE", position, 1);
		Door door = null;

		if (_stage.getDoors().size() > 0) {
			for (String key : _stage.getDoors().keySet()) {
				Point doorPosition = 
					_stage.getDoors().get(key).getPositionInMap();
				if (doorPosition.equals(north)
						|| doorPosition.equals(south)
						|| doorPosition.equals(west)
						|| doorPosition.equals(east)
						|| doorPosition.equals(northWest)
						|| doorPosition.equals(northEast)
						|| doorPosition.equals(southWest)
						|| doorPosition.equals(southEast))
					door = _stage.getDoors().get(key);
			}
			
			if (door != null) {
				if (door.getStatusBlock() == 1){
					if (_entity.getObject() != null) {
						if(_entity.getObject().getIdAssociated() != null) {
							if (_entity.getObject().getIdAssociated().equals(door.getId())) {
								door.setStatusBlock(Door.UNLOCK);
								door.setStatusOpen(Door.OPEN);
							} else 
								_stage.getDialogues().addDialogue(
										new Dialogue(
												"Este objeto no puede \nabrir esta puerta",
												Level_1_1_Stage.fontDialogues, 
												Level_1_1_Stage.colorDialogues, 
												1500, 
												ImagePath.DIALOGUE_SPEECH_BALLON_MEDIUM,
												Dialogue.CURRENT,
												Dialogue.MEDIUM_PRIORITY
										)
								);
						} else {
							_stage.getDialogues().addDialogue(
									new Dialogue(
											"Este objeto no sirve \npara abrir puertas",
											Level_1_1_Stage.fontDialogues, 
											Level_1_1_Stage.colorDialogues, 
											1500, 
											ImagePath.DIALOGUE_SPEECH_BALLON_MEDIUM,
											Dialogue.CURRENT,
											Dialogue.MEDIUM_PRIORITY
									)
							);
							door.setTryToOpen(true);
						}
					} else {
						_stage.getDialogues().addDialogue(
								new Dialogue(
										"Debes conseguir algo \npara abrir la puerta",
										Level_1_1_Stage.fontDialogues, 
										Level_1_1_Stage.colorDialogues, 
										1500, 
										ImagePath.DIALOGUE_SPEECH_BALLON_MEDIUM,
										Dialogue.CURRENT,
										Dialogue.MEDIUM_PRIORITY
								)
						);
						door.setTryToOpen(true);
					}
				} else 
					_stage.getDialogues().addDialogue(
							new Dialogue(
									"Puerta abierta",
									Level_1_1_Stage.fontDialogues, 
									Level_1_1_Stage.colorDialogues, 
									1500, 
									ImagePath.DIALOGUE_SPEECH_BALLON_MEDIUM,
									Dialogue.CURRENT,
									Dialogue.MEDIUM_PRIORITY
							)
					);
			} else 
				_stage.getDialogues().addDialogue(
						new Dialogue(
								"Debes estar cerca de \nla puerta",
								Level_1_1_Stage.fontDialogues, 
								Level_1_1_Stage.colorDialogues, 
								1500, 
								ImagePath.DIALOGUE_SPEECH_BALLON_MEDIUM,
								Dialogue.CURRENT,
								Dialogue.MEDIUM_PRIORITY
						)
				);
		}
	}

	/****************************************************************************************/
	public static void checkIsVisible(Entity _entity, TileMap _tileMap) {
		if (_entity.getX() >= 0
				&& _entity.getX() <= _tileMap.RESOLUTION_WIDTH_FIX
				&& _entity.getY() >= 0
				&& _entity.getY() <= _tileMap.RESOLUTION_HEIGHT_FIX)

			_entity.setVisible(true);
		else
			_entity.setVisible(false);
	}

	/****************************************************************************************/
	public static Object checkIsOverObject(Entity _entity, Stage _stage) {
		if (_stage.getObjects().size() > 0) {
			for (Object object : _stage.getObjects()) {
				if (_entity.getXMap() == object.getxMap()
						&& _entity.getYMap() == object.getyMap()
						&& !object.equals(_entity.getObject()))

					return object;
			}
		}
		return null;
	}

	/****************************************************************************************/
}

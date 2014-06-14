package com.spantons.entity;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.imgscalr.Scalr;

import com.spantons.dialogue.Dialogue;
import com.spantons.magicNumbers.ImagePath;
import com.spantons.object.Object;
import com.spantons.objects.Door;
import com.spantons.singleton.ImageCache;
import com.spantons.stagesLevel.StagesLevel;
import com.spantons.tileMap.TileMap;
import com.spantons.utilities.TileWalk;

public class EntityUtils {

	/****************************************************************************************/
	public static Entity checkIsCloseToAnotherEntity(Entity _entity,
			StagesLevel _stage) {

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
				currentCharacterPosition = character.getMapPositionOfCharacter();
				if (currentCharacterPosition.equals(north)) {
					_entity.characterCloseDirection = "N";
					return character;
				} else if (currentCharacterPosition.equals(south)) {
					_entity.characterCloseDirection = "S";
					return character;
				} else if (currentCharacterPosition.equals(west)) {
					_entity.characterCloseDirection = "W";
					return character;
				} else if (currentCharacterPosition.equals(east)) {
					_entity.characterCloseDirection = "E";
					return character;
				} else if (currentCharacterPosition.equals(northWest)) {
					_entity.characterCloseDirection = "NW";
					return character;
				} else if (currentCharacterPosition.equals(northEast)) {
					_entity.characterCloseDirection = "NE";
					return character;
				} else if (currentCharacterPosition.equals(southWest)) {
					_entity.characterCloseDirection = "SW";
					return character;
				} else if (currentCharacterPosition.equals(southEast)) {
					_entity.characterCloseDirection = "SE";
					return character;
				}
			}
		}

		if (_stage.getJasons().size() > 0) {
			for (Entity jason : _stage.getJasons()) {
				currentCharacterPosition = jason.getMapPositionOfCharacter();
				if (currentCharacterPosition.equals(north)) {
					_entity.characterCloseDirection = "N";
					return jason;
				} else if (currentCharacterPosition.equals(south)) {
					_entity.characterCloseDirection = "S";
					return jason;
				} else if (currentCharacterPosition.equals(west)) {
					_entity.characterCloseDirection = "W";
					return jason;
				} else if (currentCharacterPosition.equals(east)) {
					_entity.characterCloseDirection = "E";
					return jason;
				} else if (currentCharacterPosition.equals(northWest)) {
					_entity.characterCloseDirection = "NW";
					return jason;
				} else if (currentCharacterPosition.equals(northEast)) {
					_entity.characterCloseDirection = "NE";
					return jason;
				} else if (currentCharacterPosition.equals(southWest)) {
					_entity.characterCloseDirection = "SW";
					return jason;
				} else if (currentCharacterPosition.equals(southEast)) {
					_entity.characterCloseDirection = "SE";
					return jason;
				}
			}
		}

		return null;
	}

	/****************************************************************************************/
	public static boolean checkCharactersCollision(Entity _entity,
			StagesLevel _stage) {

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
	public static boolean checkDoors(Entity _entity, StagesLevel _stage) {
		if (_stage.getDoors().size() > 0) {
			for(Door door : _stage.getDoors()) {
				if (_entity.getNextPositionInMap().equals(
						door.getPositionInMap())) {
					if (!door.isOpen()) 
						return false;
				}
			}
		}
		return true;
	}

	/****************************************************************************************/
	public static void openDoor(Entity _entity, StagesLevel _stage) {
		
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
			for (Door doorAux : _stage.getDoors()) {
				Point doorPosition = doorAux.getPositionInMap();
				if (doorPosition.equals(north)
						|| doorPosition.equals(south)
						|| doorPosition.equals(west)
						|| doorPosition.equals(east)
						|| doorPosition.equals(northWest)
						|| doorPosition.equals(northEast)
						|| doorPosition.equals(southWest)
						|| doorPosition.equals(southEast))
					door = doorAux;
			}
			
			if (door != null) 
				door.openDoor(_entity);
			
			else 
				_stage.getDialogues().addDialogue(
						new Dialogue(
								"Debes estar cerca de \nla puerta",
								StagesLevel.fontDialogues, 
								StagesLevel.colorDialogues, 
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
	public static Object checkIsOverObject(Entity _entity, StagesLevel _stage) {
		if (_stage.getObjects().size() > 0) {
			for (Object object : _stage.getObjects()) {
				if (	_entity.getXMap() == ((Object) object).getXMap()
					&& _entity.getYMap() == object.getYMap()
					&& !object.equals(_entity.object))

					return object;
			}
		}
		return null;
	}
	
	/****************************************************************************************/
	public static void checkIsRecoveringFromAttack(Entity _entity) {
		if (_entity.recoveringFromAttack) {
			long elapsedTime = (System.nanoTime() - _entity.flinchingTimeRecoveringFromAttack) / 1000000;
			if (elapsedTime > 1000)
				_entity.recoveringFromAttack = false;
		}
	}
	
	/****************************************************************************************/
	public static void movFace(Entity _entity, String _direction) {
		if (_direction.equals("N")) 
			_entity.animation.setFrames(_entity.sprites.get(Entity.WALKING_BACK));
		
		else if (_direction.equals("S")) 
			_entity.animation.setFrames(_entity.sprites.get(Entity.WALKING_FRONT));
		
		else if (_direction.equals("W")) {
			_entity.facingRight = false;
			_entity.animation.setFrames(_entity.sprites.get(Entity.WALKING_SIDE));
			
		} else if (_direction.equals("E")){
			_entity.facingRight = true;
			_entity.animation.setFrames(_entity.sprites.get(Entity.WALKING_SIDE));
			
		} else if (_direction.equals("NW")){
			_entity.facingRight = false;
			_entity.animation.setFrames(_entity.sprites.get(Entity.WALKING_PERSPECTIVE_BACK));
			
		} else if (_direction.equals("NE")){
			_entity.facingRight = true;
			_entity.animation.setFrames(_entity.sprites.get(Entity.WALKING_PERSPECTIVE_BACK));
			
		} else if (_direction.equals("SW")){
			_entity.facingRight = false;
			_entity.animation.setFrames(_entity.sprites.get(Entity.WALKING_PERSPECTIVE_FRONT));
		
		} else if (_direction.equals("SE")){
			_entity.facingRight = true;
			_entity.animation.setFrames(_entity.sprites.get(Entity.WALKING_PERSPECTIVE_FRONT));
		}
	}

	/****************************************************************************************/
	public static Entity loadSprite(Entity _entity, String _pathFace, String pathSprite) {
		try {
			_entity.face = ImageCache.getInstance().getImage(_pathFace);

			BufferedImage spriteSheet = ImageCache.getInstance().getImage(pathSprite);

			_entity.spriteWidth = ((int) (spriteSheet.getWidth() / 3 * _entity.scale));
			_entity.spriteHeight = ((int) (spriteSheet.getHeight() / 2 * _entity.scale));

			spriteSheet = Scalr.resize(spriteSheet,
					(int) (spriteSheet.getWidth() * _entity.scale));

			_entity.sprites = new ArrayList<BufferedImage[]>();

			// WALKING_FRONT
			BufferedImage[] bi = new BufferedImage[1];
			bi[0] = spriteSheet.getSubimage(
					0, 
					0,
					_entity.spriteWidth,
					_entity.spriteHeight);
			_entity.sprites.add(bi);

			// WALKING_BACK
			bi = new BufferedImage[1];
			bi[0] = spriteSheet.getSubimage(
					_entity.spriteWidth, 
					0, 
					_entity.spriteWidth,
					_entity.spriteHeight);
			_entity.sprites.add(bi);

			// WALKING_SIDE
			bi = new BufferedImage[1];
			bi[0] = spriteSheet.getSubimage(
					_entity.spriteWidth * 2,
					0,
					_entity.spriteWidth,
					_entity.spriteHeight);
			_entity.sprites.add(bi);

			// WALKING_PERSPECTIVE_FRONT
			bi = new BufferedImage[1];
			bi[0] = spriteSheet.getSubimage(
					0,
					_entity.spriteHeight,
					_entity.spriteWidth,
					_entity.spriteHeight);
			_entity.sprites.add(bi);

			// WALKING_PERSPECTIVE_BACK
			bi = new BufferedImage[1];
			bi[0] = spriteSheet.getSubimage(
					_entity.spriteWidth, 
					_entity.spriteHeight,
					_entity.spriteWidth, 
					_entity.spriteHeight);
			_entity.sprites.add(bi);

			// DEAD
			bi = new BufferedImage[1];
			bi[0] = spriteSheet.getSubimage(
					_entity.spriteWidth * 2,
					_entity.spriteHeight, 
					_entity.spriteWidth, 
					_entity.spriteHeight);
			_entity.sprites.add(bi);
			
			return _entity;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
}

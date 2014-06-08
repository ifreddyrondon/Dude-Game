package com.spantons.entity;

import java.awt.Point;

import com.spantons.Interfaces.IUpdateable;
import com.spantons.utilities.TileWalk;

public class UpdateEnemy implements IUpdateable {

	private Entity entity;
	private boolean flinchingEnemyMov;
	private long flinchingTimeEnemyMov;
	private String nextDirectionEnemy;
	private EntityAttack attack;
	
	/****************************************************************************************/
	public UpdateEnemy(Entity _entity) {
		entity = _entity;
		nextDirectionEnemy = TileWalk.randomMov();
		entity.movFace(nextDirectionEnemy);
		attack = new EntityAttack(entity);
	}
	
	/****************************************************************************************/
	@Override
	public void update() {
		EntityUtils.checkIsVisible(entity, entity.tileMap);
		if (entity.visible) {
			entity.checkIsRecoveringFromAttack();
			entity.updateAnimation.update();
		}
		entity.characterClose = checkIsCloseToAnotherCharacter();
		if (entity.characterClose != null) 
			attack.attack();
		else
			movEnemy();

		entity.setMapPosition(entity.nextPositionInMap.x, entity.nextPositionInMap.y);
	}

	/****************************************************************************************/
	private void movEnemy() {
		if (flinchingEnemyMov) {
			long elapsedTime = (System.nanoTime() - flinchingTimeEnemyMov) / 1000000;
			if (elapsedTime > entity.getMoveSpeed())
				flinchingEnemyMov = false;
		} else {
			entity.oldPositionInMap = entity.nextPositionInMap;
			entity.nextPositionInMap = 
					TileWalk.walkTo(
							nextDirectionEnemy,
							entity.nextPositionInMap, 1);

			if (EntityUtils.checkTileCollision(entity, entity.tileMap)) {
				entity.entitysToDraw[entity.xMap][entity.yMap] = null;
				entity.xMap = entity.nextPositionInMap.x;
				entity.yMap = entity.nextPositionInMap.y;
				entity.entitysToDraw[entity.xMap][entity.yMap] = entity;
			} else {
				nextDirectionEnemy = TileWalk.randomMov();
				entity.nextPositionInMap = entity.oldPositionInMap;
			}
			entity.movFace(nextDirectionEnemy);
			flinchingEnemyMov = true;
			flinchingTimeEnemyMov = System.nanoTime();
		}
	}
	
	/****************************************************************************************/
	private Entity checkIsCloseToAnotherCharacter() {

		Point position = new Point(entity.xMap, entity.yMap);
		Point north = TileWalk.walkTo("N", position, 1);
		Point south = TileWalk.walkTo("S", position, 1);
		Point west = TileWalk.walkTo("W", position, 1);
		Point east = TileWalk.walkTo("E", position, 1);
		Point northWest = TileWalk.walkTo("NW",position, 1);
		Point northEast = TileWalk.walkTo("NE",position, 1);
		Point southWest = TileWalk.walkTo("SW",position, 1);
		Point southEast = TileWalk.walkTo("SE",position, 1);
		Point currentCharacterPosition = null;
		
		for (Entity character : entity.stage.getCharacters()) {
			currentCharacterPosition = character.getMapPositionOfCharacter();

			if (currentCharacterPosition.equals(north)) {
				entity.characterCloseDirection = "N";
				return character;
			} else if (currentCharacterPosition.equals(south)) {
				entity.characterCloseDirection = "S";
				return character;
			} else if (currentCharacterPosition.equals(west)) {
				entity.characterCloseDirection = "W";
				return character;
			} else if (currentCharacterPosition.equals(east)) {
				entity.characterCloseDirection = "E";
				return character;
			} else if (currentCharacterPosition.equals(northWest)) {
				entity.characterCloseDirection = "NW";
				return character;
			} else if (currentCharacterPosition.equals(northEast)) {
				entity.characterCloseDirection = "NE";
				return character;
			} else if (currentCharacterPosition.equals(southWest)) {
				entity.characterCloseDirection = "SW";
				return character;
			} else if (currentCharacterPosition.equals(southEast)) {
				entity.characterCloseDirection = "SE";
				return character;
			}
		}
		
		currentCharacterPosition = entity.stage.getCurrentCharacter().getMapPositionOfCharacter();
		
		if (currentCharacterPosition.equals(north)) {
			entity.characterCloseDirection = "N";
			return entity.stage.getCurrentCharacter();
		} else if (currentCharacterPosition.equals(south)) {
			entity.characterCloseDirection = "S";
			return entity.stage.getCurrentCharacter();
		} else if (currentCharacterPosition.equals(west)) {
			entity.characterCloseDirection = "W";
			return entity.stage.getCurrentCharacter();
		} else if (currentCharacterPosition.equals(east)) {
			entity.characterCloseDirection = "E";
			return entity.stage.getCurrentCharacter();
		} else if (currentCharacterPosition.equals(northWest)) {
			entity.characterCloseDirection = "NW";
			return entity.stage.getCurrentCharacter();
		} else if (currentCharacterPosition.equals(northEast)) {
			entity.characterCloseDirection = "NE";
			return entity.stage.getCurrentCharacter();
		} else if (currentCharacterPosition.equals(southWest)) {
			entity.characterCloseDirection = "SW";
			return entity.stage.getCurrentCharacter();
		} else if (currentCharacterPosition.equals(southEast)) {
			entity.characterCloseDirection = "SE";
			return entity.stage.getCurrentCharacter();
		}

		return null;
	}
	
}

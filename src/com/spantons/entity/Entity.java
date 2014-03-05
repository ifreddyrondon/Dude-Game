package com.spantons.entity;

import java.awt.Graphics2D;
import java.awt.Point;

import utilities.Multiple;
import utilities.TileWalk;

import com.spantons.gameState.Stage;
import com.spantons.tileMap.TileMap;

public class Entity extends EntityLogic{

	protected int x;
	protected int y;
	private Point nextPositionInMap;
	private Point nextPositionInAbsolute;
	private Point nextMapPosition;
	protected boolean visible;

	protected boolean flinching;
	protected long flinchingTime;
	protected boolean flinchingRandom;
	protected long flinchingTimeRandom;

	/****************************************************************************************/
	public Entity(TileMap _tm, Stage _stage, int _xMap, int _yMap) {
		if (_tm != null) {
			tileMap = _tm;
			stage = _stage;
			xMap = _xMap;
			yMap = _yMap;
			setMapPosition(xMap, yMap);
			getNextPosition();
			entitysToDraw = tileMap.getEntitysToDraw();
			entitysDeadToDraw = tileMap.getEntitysDeadToDraw();
			objectsToDraw = tileMap.getObjectsToDraw();
			entitysToDraw[xMap][yMap] = this;
			flinchingIncreasePerversity = true;
			object = null;
		}
	}
	/****************************************************************************************/
	public Point getMapPositionOfCharacter() {
		int _x = x + tileMap.getX();
		int _y = y + tileMap.getY();
		return tileMap.absoluteToMap(_x, _y);
	}
	/****************************************************************************************/
	public Point getMapPosition(int _x, int _y) {
		_x = _x + tileMap.getX();
		_y = _y + tileMap.getY();
		return tileMap.absoluteToMap(_x, _y);
	}
	/****************************************************************************************/
	public void setMapPosition(int _x, int _y) {
		Point absolutePosition = tileMap.mapToAbsolute(_x, _y);
		setPosition(absolutePosition.x - tileMap.getX(), absolutePosition.y
				- tileMap.getY());
	}
	/****************************************************************************************/
	public Point getAbsolutePosition(int _x, int _y) {
		Point absolutePosition = tileMap.mapToAbsolute(_x, _y);
		absolutePosition.x = absolutePosition.x - tileMap.getX();
		absolutePosition.y = absolutePosition.y - tileMap.getY();
		return absolutePosition;
	}
	/****************************************************************************************/
	protected void getNextPosition() {
		nextPositionInMap = getMapPositionOfCharacter();

		if (movUp)
			nextPositionInMap = TileWalk.walkTo("N", nextPositionInMap,
					moveSpeed);

		if (movDown)
			nextPositionInMap = TileWalk.walkTo("S", nextPositionInMap,
					moveSpeed);

		if (movLeft)
			nextPositionInMap = TileWalk.walkTo("W", nextPositionInMap,
					moveSpeed);

		if (movRight)
			nextPositionInMap = TileWalk.walkTo("E", nextPositionInMap,
					moveSpeed);

		nextPositionInAbsolute = getAbsolutePosition(nextPositionInMap.x,
				nextPositionInMap.y);

		nextMapPosition = new Point(
				tileMap.getX()
						+ (nextPositionInAbsolute.x - tileMap.RESOLUTION_WIDTH_FIX / 2),
				tileMap.getY()
						+ (nextPositionInAbsolute.y - tileMap.RESOLUTION_HEIGHT_FIX / 2));

		// System.out.println(nextMapPosition);
		// Point a = Multiple.findPointCloserTo(nextMapPosition,
		// tileMap.tileSize);
		// System.out.println(a);

		// if ( nextMapPosition.x % tileMap.tileSize.x != 0
		// || nextMapPosition.y % tileMap.tileSize.y != 0) {
		//
		// nextMapPosition = Multiple.findPointCloserTo(
		// nextMapPosition, tileMap.tileSize);
		// }
		//
		// System.out.println(nextMapPosition);
		// System.out.println();
	}
	/****************************************************************************************/
	protected Entity checkIsCloseToAnotherCharacter() {

		Point north = TileWalk.walkTo("N", getMapPositionOfCharacter(), 1);
		Point south = TileWalk.walkTo("S", getMapPositionOfCharacter(), 1);
		Point west = TileWalk.walkTo("W", getMapPositionOfCharacter(), 1);
		Point east = TileWalk.walkTo("E", getMapPositionOfCharacter(), 1);

		if (stage.getCharacters().size() > 0) {
			for (Entity character : stage.getCharacters()) {
				if (character.getMapPositionOfCharacter().equals(north)) {
					characterCloseDirection = "north";
					return character;
				} else if (character.getMapPositionOfCharacter()
						.equals(south)) {
					characterCloseDirection = "south";
					return character;
				} else if (character.getMapPositionOfCharacter()
						.equals(west)) {
					characterCloseDirection = "west";
					return character;
				} else if (character.getMapPositionOfCharacter()
						.equals(east)) {
					characterCloseDirection = "east";
					return character;
				}
			}
		}

		if (stage.getJasons().size() > 0) {
			for (Entity jason : stage.getJasons()) {
				if (jason.getMapPositionOfCharacter().equals(north)) {
					characterCloseDirection = "north";
					return jason;
				} else if (jason.getMapPositionOfCharacter().equals(
						south)) {
					characterCloseDirection = "south";
					return jason;
				} else if (jason.getMapPositionOfCharacter().equals(
						west)) {
					characterCloseDirection = "west";
					return jason;
				} else if (jason.getMapPositionOfCharacter().equals(
						east)) {
					characterCloseDirection = "east";
					return jason;
				}
			}
		}

		if (!this.equals(stage.getCurrentCharacter())) {
			if (stage.getCurrentCharacter().getMapPositionOfCharacter()
					.equals(north)) {
				characterCloseDirection = "north";
				return stage.getCurrentCharacter();
			} else if (stage.getCurrentCharacter()
					.getMapPositionOfCharacter().equals(south)) {
				characterCloseDirection = "south";
				return stage.getCurrentCharacter();
			} else if (stage.getCurrentCharacter()
					.getMapPositionOfCharacter().equals(west)) {
				characterCloseDirection = "west";
				return stage.getCurrentCharacter();
			} else if (stage.getCurrentCharacter()
					.getMapPositionOfCharacter().equals(east)) {
				characterCloseDirection = "east";
				return stage.getCurrentCharacter();
			}
		}
		
		return null;
	}
	/****************************************************************************************/
	public boolean checkCharactersCollision() {
		if (stage.getCharacters().size() > 0) {
			for (Entity character : stage.getCharacters()) {
				if (character.getMapPositionOfCharacter().equals(
						nextPositionInMap))
					return false;
			}
		}

		if (stage.getJasons().size() > 0) {
			for (Entity jason : stage.getJasons()) {
				if (jason.getMapPositionOfCharacter().equals(
						nextPositionInMap))
					return false;
			}
		}
		return true;
	}
	/****************************************************************************************/
	public boolean checkTileCollision() {

		if (	nextPositionInMap.x >= 0
			&& nextPositionInMap.y >= 0
			&& nextPositionInMap.x < tileMap.getNumColMap() 
			&& nextPositionInMap.y < tileMap.getNumRowsMap()){
			
			if(tileMap.getWallPosition(nextPositionInMap.x, nextPositionInMap.y) == 0){
				if(tileMap.getObjectsPosition(nextPositionInMap.x, nextPositionInMap.y) == 0)
					return true;
			}
		}
		
		return false;
	}
	/****************************************************************************************/
	public void update() {

		if (dead) {
			dead = true;
			stage.selectNextCurrentCharacter();
		}

		updateAnimation();
		decreasePerversity();
		characterClose = checkIsCloseToAnotherCharacter();
		checkIsRecoveringFromAttack();

		if (attack)
			attack();

		if (flinching) {
			long elapsedTime = (System.nanoTime() - flinchingTime) / 1000000;
			if (elapsedTime > 70)
				flinching = false;

		} else {
			getNextPosition();

			if (checkTileCollision()) {
				if (checkCharactersCollision())
					magicWalk();
			}

			entitysToDraw[xMap][yMap] = null;
			xMap = getMapPositionOfCharacter().x;
			yMap = getMapPositionOfCharacter().y;
			entitysToDraw[xMap][yMap] = this;

			flinching = true;
			flinchingTime = System.nanoTime();
		}
	}
	/****************************************************************************************/
	public void updateOtherCharacters() {
		checkIsVisible();
		if (visible) {
			checkOtherCharacterIsDead();
			checkIsRecoveringFromAttack();
			updateAnimation();
		}
		setMapPosition(xMap, yMap);
		increasePerversity();
	}
	/****************************************************************************************/
	public void updateJason() {
		checkIsVisible();
		if (visible) {
			checkOtherCharacterIsDead();
			checkIsRecoveringFromAttack();
			updateAnimation();
		}
		setMapPosition(xMap, yMap);
		characterClose = checkIsCloseToAnotherCharacter();
		if (characterClose != null)
			attack();
		else 
			animation.setFrames(sprites.get(IDLE));
	}
	/****************************************************************************************/
	public void updateDead() {
		setMapPosition(xMap, yMap);
	}
	/****************************************************************************************/
	private void magicWalk() {

		if (tileMap.getX() <= tileMap.getXMin()
				|| tileMap.getX() >= tileMap.getXMax()
				|| tileMap.getY() <= tileMap.getYMin()
				|| tileMap.getY() >= tileMap.getYMax()) {

			if ((tileMap.getX() == tileMap.getXMin() && x > tileMap.RESOLUTION_WIDTH_FIX / 2)
					|| (tileMap.getX() == tileMap.getXMax() && x < tileMap.RESOLUTION_WIDTH_FIX / 2)
					|| (tileMap.getY() == tileMap.getYMin() && y > tileMap.RESOLUTION_HEIGHT_FIX / 2)
					|| (tileMap.getY() == tileMap.getYMax() && y < tileMap.RESOLUTION_HEIGHT_FIX / 2)) {
				setPosition(tileMap.RESOLUTION_WIDTH_FIX / 2,
						tileMap.RESOLUTION_HEIGHT_FIX / 2);
				tileMap.setPosition(nextMapPosition.x,
						nextMapPosition.y);
			} else {
				if (x < tileMap.tileSize.x
						|| x > tileMap.RESOLUTION_WIDTH_FIX
								- tileMap.tileSize.x) {

					setPosition(tileMap.RESOLUTION_WIDTH_FIX / 2, y);
					tileMap.setPosition(nextMapPosition.x,
							nextMapPosition.y);
				} else if (y < tileMap.tileSize.y
						|| y > tileMap.RESOLUTION_HEIGHT_FIX
								- tileMap.tileSize.y * 2) {

					setPosition(x, tileMap.RESOLUTION_HEIGHT_FIX / 2);
					tileMap.setPosition(nextMapPosition.x,
							nextMapPosition.y);
				} else
					setMapPosition(nextPositionInMap.x,
							nextPositionInMap.y);
			}
		} else {
			setPosition(tileMap.RESOLUTION_WIDTH_FIX / 2,
					tileMap.RESOLUTION_HEIGHT_FIX / 2);
			tileMap.setPosition(nextMapPosition.x, nextMapPosition.y);
		}
	}
	/****************************************************************************************/
	public void draw(Graphics2D g) {
		if (visible) {
			if (recoveringFromAttack) {
				long elapsedTime = (System.nanoTime() - flinchingTimeRecoveringFromAttack) / 1000000;
				if (elapsedTime / 100 % 2 == 0)
					return;
			}
			if (facingRight)
				g.drawImage(animation.getCurrentImageFrame(), x
						- spriteWidth / 2, y - spriteHeight, null);

			else
				g.drawImage(animation.getCurrentImageFrame(), x
						+ spriteWidth - spriteWidth / 2, y
						- spriteHeight, -spriteWidth, spriteHeight,
						null);

			if (object != null)
				object.draw(g);
		}
	}
	/****************************************************************************************/
	private void checkIsVisible() {
		if (x >= 0 && x <= tileMap.RESOLUTION_WIDTH_FIX && y >= 0
				&& y <= tileMap.RESOLUTION_HEIGHT_FIX)

			visible = true;
		else
			visible = false;
	}
	/****************************************************************************************/
	// Setter and Getter
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setPosition(int _x, int _y) {
		if (tileMap == null
				|| (_x % tileMap.tileSize.x == 0 && _y
						% tileMap.tileSize.y == 0)) {

			x = _x;
			y = _y;
		} else {
			Point multiple = Multiple.findPointCloserTo(
					new Point(_x, _y), tileMap.tileSize);
			x = multiple.x;
			y = multiple.y;
		}
	}

	public void setAllMov(boolean b) {
		movDown = b;
		movLeft = b;
		movRight = b;
		movUp = b;
		attack = b;
	}

	public boolean isMovLeft() {
		return movLeft;
	}

	public void setMovLeft(boolean b) {
		movLeft = b;
	}

	public boolean isMovRight() {
		return movRight;
	}

	public void setMovRight(boolean b) {
		movRight = b;
	}

	public boolean isMovUp() {
		return movUp;
	}

	public void setMovUp(boolean b) {
		movUp = b;
	}

	public void setMovDown(boolean b) {
		movDown = b;
	}

	public void setMovJumping(boolean b) {
		movJumping = b;
	}

	public int getSpriteWidth() {
		return spriteWidth;
	}

	public int getSpriteHeight() {
		return spriteHeight;
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float d) {
		this.health = d;
	}

	public float getMaxHealth() {
		return maxHealth;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public String getDescription() {
		return description;
	}

	public int getPerversity() {
		return perversity;
	}

	public int getMaxPerversity() {
		return maxPerversity;
	}

	public void setFlinchingIncreaseDeltaTimePerversity(int i) {
		flinchingIncreaseDeltaTimePerversity = i;
	}

	public Entity getCharacterClose() {
		return characterClose;
	}

	public boolean isAttack() {
		return attack;
	}

	public void setAttack(boolean b) {
		attack = b;
	}

	public double getDamage() {
		return damage;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean isFacingRight() {
		return facingRight;
	}	
}
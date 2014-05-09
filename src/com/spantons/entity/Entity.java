package com.spantons.entity;

import java.awt.Graphics2D;
import java.awt.Point;

import utilities.TileWalk;

import com.spantons.gameState.Stage;
import com.spantons.object.Object;
import com.spantons.tileMap.TileMap;

public class Entity extends EntityLogic {

	protected int x;
	protected int y;
	protected Point nextPositionInMap;
	protected Point oldPositionInMap;
	private Point nextPositionInAbsolute;
	private Point nextMapPosition;
	protected boolean visible;

	protected boolean flinching;
	protected long flinchingTime;
	protected boolean flinchingJasonMov;
	protected long flinchingTimeJasonMov;

	/****************************************************************************************/
	public Entity(TileMap _tm, Stage _stage, int _xMap, int _yMap) {
		if (_tm != null) {
			tileMap = _tm;
			stage = _stage;
			xMap = _xMap;
			yMap = _yMap;
			setMapPosition(xMap, yMap);
			getNextPosition();
			oldPositionInMap = nextPositionInMap; 
			entitysToDraw = tileMap.getEntitysToDraw();
			entitysDeadToDraw = tileMap.getEntitysDeadToDraw();
			objectsToDraw = tileMap.getObjectsToDraw();
			entitysToDraw[xMap][yMap] = this;
			flinchingIncreasePerversity = true;
			object = null;
			tileMap.setTransparentWalls("");
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

		if (movUp && movLeft)
			nextPositionInMap = TileWalk.walkTo("NW", nextPositionInMap,1);

		else if (movUp && movRight)
			nextPositionInMap = TileWalk.walkTo("NE", nextPositionInMap,1);

		else if (movDown && movLeft)
			nextPositionInMap = TileWalk.walkTo("SW", nextPositionInMap,1);

		else if (movDown && movRight)
			nextPositionInMap = TileWalk.walkTo("SE", nextPositionInMap,1);

		else {
			if (movUp)
				nextPositionInMap = TileWalk.walkTo("N",nextPositionInMap, 1);

			else if (movDown)
				nextPositionInMap = TileWalk.walkTo("S",nextPositionInMap, 1);

			else if (movLeft)
				nextPositionInMap = TileWalk.walkTo("W",nextPositionInMap, 1);

			else if (movRight)
				nextPositionInMap = TileWalk.walkTo("E",nextPositionInMap, 1);
		}

		nextPositionInAbsolute = getAbsolutePosition(nextPositionInMap.x,
				nextPositionInMap.y);

		nextMapPosition = new Point(
				tileMap.getX()
						+ (nextPositionInAbsolute.x - tileMap.RESOLUTION_WIDTH_FIX / 2),
				tileMap.getY()
						+ (nextPositionInAbsolute.y - tileMap.RESOLUTION_HEIGHT_FIX / 2));
	}

	/****************************************************************************************/
	public void update() {

		if (dead) {
			stage.selectNextCurrentCharacter();
			return;
		}
			
		updateAnimation();
		decreasePerversity();
		characterClose = EntityChecks.checkIsCloseToAnotherEntity(this, stage);
		checkIsRecoveringFromAttack();

		if (attack)
			attack();

		if (flinching) {
			long elapsedTime = (System.nanoTime() - flinchingTime) / 1000000;
			if (elapsedTime > moveSpeed)
				flinching = false;

		} else {
			getNextPosition();
			if (nextPositionInMap != oldPositionInMap) {
				if (EntityChecks.checkTileCollision(this, tileMap)) {
					if (EntityChecks.checkCharactersCollision(this, stage)) {
						if(EntityChecks.checkDoors(this, stage)){
							EntityChecks.checkTransparentWalls(this, stage, tileMap);
							magicWalk();
							entitysToDraw[xMap][yMap] = null;
							xMap = getMapPositionOfCharacter().x;
							yMap = getMapPositionOfCharacter().y;
							entitysToDraw[xMap][yMap] = this;
							oldPositionInMap = nextPositionInMap;
						}
					}
				}
			}
			
			flinching = true;
			flinchingTime = System.nanoTime();
		}
	}
	
	/****************************************************************************************/
	public void updateOtherCharacters() {
		EntityChecks.checkIsVisible(this, tileMap);
		if (visible) {
			checkCharacterIsDead();
			checkIsRecoveringFromAttack();
			updateAnimation();
		}
		setMapPosition(xMap, yMap);
		increasePerversity();
	}

	/****************************************************************************************/
	public void updateDead() {
		setMapPosition(xMap, yMap);
	}

	/****************************************************************************************/
	private void magicWalk() {

		if (	tileMap.getX() <= tileMap.getXMin()
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
		} 
		else {
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
	// Setter and Getter
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getXMap() {
		return xMap;
	}

	public int getYMap() {
		return yMap;
	}
	
	public Point getNextPositionInMap(){
		return nextPositionInMap;
	}

	public void setPosition(int _x, int _y) {
		x = _x;
		y = _y;
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
	
	public void setDamage(float a) {
		damage = a;
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
	
	public void setCharacterCloseDirection(String a){
		characterCloseDirection = a;
	}

	public  Object getObject(){
		return object;
	}

}
package com.spantons.entity;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import utilities.Multiple;
import utilities.TileWalk;

import com.spantons.entity.character.Jason;
import com.spantons.gameState.Stage;
import com.spantons.object.Object;
import com.spantons.tileMap.ElementsToDraw;
import com.spantons.tileMap.TileMap;

public class Entity  {
	
	protected Stage stage;
	protected float health;
	protected float maxHealth;
	protected boolean dead;
	protected String description;
	protected int perversity;
	protected int maxPerversity;
	
	private Entity characterClose;
	private Object object;
	
	protected int flinchingIncreaseDeltaTimePerversity;
	protected long flinchingIncreaseTimePerversity;
	protected boolean flinchingIncreasePerversity;
	protected int flinchingDecreaseDeltaTimePerversity;
	protected long flinchingDecreaseTimePerversity;
	protected boolean flinchingDecreasePerversity;
	protected int deltaForReduceFlinchingIncreaseDeltaTimePerversity;
	
	// Animacion
	protected Animation animation;
	protected int currentAnimation;
	protected int lastAnimation;
	protected boolean facingRight;
	
	// Hud
	protected BufferedImage face;
	
	// TileMap
	protected TileMap tileMap;
	protected int xMap;
	protected int yMap;
	private ElementsToDraw[][] elements;

	// Posicion
	protected int x;
	protected int y;
	protected double xDest;
	protected double yDest;
	protected double xTemp;
	protected double yTemp;
	protected boolean visible;
	
	// Proxima posicion en el mapa
	private Point nextPositionMap;
	protected int xDestMap;
	protected int yDestMap;
	
	// Reposicion
	protected boolean flinching;
	protected long flinchingTime;
	protected boolean flinchingRandom;
	protected long flinchingTimeRandom;
	
	// Dimensiones
	protected int spriteWidth;
	protected int spriteHeight;
	protected double scale;

	// Caja de colision
	protected int collisionBoxWidth;
	protected int collisionBoxHeight;
		
	// movimientos
	protected boolean movLeft;
	protected boolean movRight;
	protected boolean movUp;
	protected boolean movDown;
	protected boolean movJumping;
	protected boolean movFalling;
	
	// Ataque
	protected boolean attack;
	protected boolean recoveringFromAttack;
	protected long flinchingTimeRecoveringFromAttack;
	protected float damage;
	
	// atributos de movimientos
	protected int moveSpeed;
	protected double fallSpeed;
	protected double maxFallSpeed;
	protected double jumpStart;
	protected double reducerJumpSpeed;
	
	
	/****************************************************************************************/
	public Entity(TileMap _tm, Stage _stage, int _xMap, int _yMap) {
		if (_tm != null){
			tileMap = _tm;
			stage = _stage;
			flinchingIncreasePerversity = true;
			object = null;
			xMap = _xMap;
			yMap = _yMap;
			elements = tileMap.getElements();
			elements[xMap][yMap] = new ElementsToDraw(this, null);
		}
	}
	/****************************************************************************************/
	public void initChief(){
		calculateMapPositionInAbsolute(xMap,yMap);		
		magicWalk();
	}
	/****************************************************************************************/
	public Rectangle getRectangle() {
		return new Rectangle(
				(int) x - collisionBoxWidth, 
				(int) y - collisionBoxHeight, 
				collisionBoxWidth,
				collisionBoxHeight);
	}
	/****************************************************************************************/
	public boolean intersection(Entity e) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = e.getRectangle();
		return r1.intersects(r2);
	}
	/****************************************************************************************/
	public Point getMapPosition() {
		double x = this.x + tileMap.getX();
		double y = this.y + tileMap.getY();
		return tileMap.absoluteToMap(x, y);
	}
	/****************************************************************************************/
	public void calculateMapPositionInAbsolute(double _x, double _y) {
		Point absolutePosition = tileMap.mapToAbsolute(_x, _y);
		xDest = absolutePosition.x - tileMap.getX();
		yDest = absolutePosition.y - tileMap.getY();
	}
	/****************************************************************************************/
	public void setMapPosition(double _x, double _y) {

		Point absolutePosition = tileMap.mapToAbsolute(_x, _y);
		setPosition((int) absolutePosition.x - tileMap.getX(),
					(int) absolutePosition.y - tileMap.getY());
	}
	/****************************************************************************************/
	protected void getNextPosition() {
		
		nextPositionMap = getMapPosition();
		
		if (movUp)
			nextPositionMap = TileWalk.walkTo("N", nextPositionMap,moveSpeed);
		
		if (movDown) 
			nextPositionMap = TileWalk.walkTo("S", nextPositionMap,moveSpeed);
			
		if (movLeft) 
			nextPositionMap = TileWalk.walkTo("W", nextPositionMap,moveSpeed);
				
		if (movRight)
			nextPositionMap = TileWalk.walkTo("E", nextPositionMap,moveSpeed);
		
	}	
	/****************************************************************************************/
	public boolean checkCharactersCollision() {
		
		if (stage.getCharacters().size() > 0) {
			for (int i = 0; i < stage.getCharacters().size(); i++){
				if (stage.getCharacters().get(i).getMapPosition().equals(nextPositionMap)) 
					return false;
			}
		}
		
		if (stage.getJasons().size() > 0) {
			for (int i = 0; i < stage.getJasons().size(); i++){
				if (stage.getJasons().get(i).getMapPosition().equals(nextPositionMap)) 
					return false;
			}
		}
		
		return true;
	}
	/****************************************************************************************/
	public boolean checkTileCollision() {

		if ((nextPositionMap.x > 0 && nextPositionMap.y > 0
				&& nextPositionMap.x < tileMap.getNumColMap()
				&& nextPositionMap.y < tileMap.getNumRowsMap())
//			&& tileMap.getUnlockedTiles().contains(map[(int)nextPositionMap.x][(int)nextPositionMap.y])
			) 
			return true;
		
		return false;	
	}
	/****************************************************************************************/
	public void updateAnimation() {}
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
				if (checkCharactersCollision()) {
					calculateMapPositionInAbsolute(nextPositionMap.x, nextPositionMap.y);
					magicWalk();
				}
			}
			
			Object aux = elements[xMap][yMap].object;
			elements[xMap][yMap] = null;
			xMap = getMapPosition().x;
			yMap = getMapPosition().y;
			elements[xMap][yMap] = new ElementsToDraw(this, aux);
			
			flinching = true;
			flinchingTime = System.nanoTime();
		}
	}
	/****************************************************************************************/
	public void updateOtherCharacters(){
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
		attack();
	}
	/****************************************************************************************/
	public void updateDead(){
		setMapPosition(xMap, yMap);
	}	
	/****************************************************************************************/
	private void magicWalk() {
		
		xDestMap = (int) (tileMap.getX() + 
				(xDest - tileMap.RESOLUTION_WIDTH_FIX /2));
		yDestMap = (int) (tileMap.getY() + 
				(yDest - tileMap.RESOLUTION_HEIGHT_FIX /2));
		
		if (	tileMap.getX() <= tileMap.getXMin() ||
			tileMap.getX() >= tileMap.getXMax() ||
			tileMap.getY() <= tileMap.getYMin() ||
			tileMap.getY() >= tileMap.getYMax()	){		
			
			if ((tileMap.getX() == tileMap.getXMin() && x > tileMap.RESOLUTION_WIDTH_FIX / 2)
				|| (tileMap.getX() == tileMap.getXMax() && x < tileMap.RESOLUTION_WIDTH_FIX / 2) 
				|| (tileMap.getY() == tileMap.getYMin() && y > tileMap.RESOLUTION_HEIGHT_FIX / 2) 
				|| (tileMap.getY() == tileMap.getYMax() && y < tileMap.RESOLUTION_HEIGHT_FIX / 2) 
				) {
				setPosition(
						(int) tileMap.RESOLUTION_WIDTH_FIX / 2, 
						(int) tileMap.RESOLUTION_HEIGHT_FIX / 2);
				tileMap.setPosition(xDestMap,yDestMap);
			}
			else {
				if (	x < tileMap.tileWidthSize 
					|| x > tileMap.RESOLUTION_WIDTH_FIX - tileMap.tileWidthSize){
					
					setPosition(
							(int) tileMap.RESOLUTION_WIDTH_FIX / 2, 
							y);
					tileMap.setPosition(xDestMap,yDestMap);
				}
				else if(	y < tileMap.tileHeightSize
						|| y > tileMap.RESOLUTION_HEIGHT_FIX - tileMap.tileHeightSize * 2){
					
					setPosition(x, (int) tileMap.RESOLUTION_HEIGHT_FIX / 2);
					tileMap.setPosition(xDestMap,yDestMap);
				}
				else
					setPosition((int) xDest, (int) yDest);
			}
		}
		else {
			
			setPosition(
					(int) tileMap.RESOLUTION_WIDTH_FIX / 2, 
					(int) tileMap.RESOLUTION_HEIGHT_FIX / 2);
			tileMap.setPosition(xDestMap,yDestMap);
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
				g.drawImage(animation.getCurrentImageFrame(),
						x, y - spriteHeight + 12, null);
			
			else 
				g.drawImage(animation.getCurrentImageFrame(), 
						x + spriteWidth, y - spriteHeight + 12,
						-spriteWidth, spriteHeight, null);
			
			if (object != null) 
				object.draw(g);
		}
	}

	/****************************************************************************************/
	/****************************************************************************************/
	private void checkIsVisible(){
		
		if (	x >= 0 && x <= tileMap.RESOLUTION_WIDTH_FIX
			&& y>= 0 && y<= tileMap.RESOLUTION_HEIGHT_FIX) 
			
			visible = true;
		else
			visible = false;
	}
	/****************************************************************************************/
	protected void increasePerversity(){
		
		if (flinchingIncreasePerversity) {
			long elapsedTime = (System.nanoTime() - flinchingIncreaseTimePerversity) / 1000000;
			if (elapsedTime > flinchingIncreaseDeltaTimePerversity) 
				flinchingIncreasePerversity = false;
		
		} else {
			if (getPerversity() >= getMaxPerversity()) {
				perversity = maxPerversity;
				jasonTransform();
			}
			else
				perversity = perversity + 1;
			
			flinchingIncreasePerversity = true;
			flinchingIncreaseTimePerversity = System.nanoTime();
		}
	}
	/****************************************************************************************/
	protected void jasonTransform() {
		stage.getCharacters().remove(this);
		if (object != null) 
			object.setCarrier(null);
		
		stage.getJasons().add(new Jason(tileMap, stage, xMap, yMap, 0.10));
	}
	/****************************************************************************************/
	protected void decreasePerversity(){
		
		if (flinchingDecreasePerversity) {
			long elapsedTime = (System.nanoTime() - flinchingDecreaseTimePerversity) / 1000000;
			if (elapsedTime > flinchingDecreaseDeltaTimePerversity) 
				flinchingDecreasePerversity = false;
		
		} else {
			if (getPerversity() <= 0) 
				perversity = 0;
			else
				perversity = perversity - 1;
			
			flinchingDecreasePerversity = true;
			flinchingDecreaseTimePerversity = System.nanoTime();
		}
	}
	/****************************************************************************************/
	protected Entity checkIsCloseToAnotherCharacter() {
		
		Point north = TileWalk.walkTo("N", getMapPosition(),1);
		Point south = TileWalk.walkTo("S", getMapPosition(),1);
		Point west = TileWalk.walkTo("W", getMapPosition(),1);
		Point east = TileWalk.walkTo("E", getMapPosition(),1);
				
		if (stage.getCharacters().size() > 0) {
			for (Entity character : stage.getCharacters()) {
				if (
					character.getMapPosition().equals(north)
					|| character.getMapPosition().equals(south)
					|| character.getMapPosition().equals(west)
					|| character.getMapPosition().equals(east)) 
					return character;
			}
		}
		
		if (stage.getJasons().size() > 0) {
			for (Entity jason : stage.getJasons()) {
				if (
					jason.getMapPosition().equals(north)
					|| jason.getMapPosition().equals(south)
					|| jason.getMapPosition().equals(west)
					|| jason.getMapPosition().equals(east)) 
					return jason;
			}
		}
		
		if (!this.equals(stage.getCurrentCharacter())) {
			if (	stage.getCurrentCharacter().getMapPosition().equals(north)
				|| stage.getCurrentCharacter().getMapPosition().equals(south)
				|| stage.getCurrentCharacter().getMapPosition().equals(west)
				|| stage.getCurrentCharacter().getMapPosition().equals(east)) 
				
				return stage.getCurrentCharacter();
		}

		return null;
	}
	/****************************************************************************************/
	private Object checkIsOverObject() {
		if (stage.getObjects().size() > 0) {
			for (Object object : stage.getObjects()) {
				if (	xMap == object.getxMap() 
					&& yMap == object.getyMap())
					return object;
			}
		}
		
		return null;
	}
	/****************************************************************************************/
	public void takeOrLeaveObject(){
		if (object != null){
			object.setCarrier(null);
			damage = damage - object.getDamage();
			object = null;
		}
		else {
			object = checkIsOverObject();
			if (object != null){
				object.setCarrier(this);
				damage = damage + object.getDamage();	
			}
		}
	}
	/****************************************************************************************/
	private void attack() {
		
		if (characterClose != null){
			
			if (characterClose.recoveringFromAttack)
				return;
			
			characterClose.setHealth(characterClose.getHealth() - damage);
			
			if (this == stage.getCurrentCharacter()) 
				flinchingIncreaseDeltaTimePerversity -= deltaForReduceFlinchingIncreaseDeltaTimePerversity;
			
			if (characterClose.getHealth() <= 0){
				characterClose.setHealth(0);
				characterClose.setDead(true);
			}
			
			characterClose.recoveringFromAttack = true;
			characterClose.flinchingTimeRecoveringFromAttack = System.nanoTime();
		}
	}
	/****************************************************************************************/
	private void checkIsRecoveringFromAttack(){
		if (recoveringFromAttack) {
			long elapsedTime = (System.nanoTime() - flinchingTimeRecoveringFromAttack) / 1000000;
			if (elapsedTime > 1000) 
				recoveringFromAttack = false;
		}
	}
	/****************************************************************************************/
	private void checkOtherCharacterIsDead() {
		if (dead){
			if (description == "Jason") 
				stage.getJasons().remove(this);
			else if (description != "Jason") 
				stage.getCharacters().remove(this);
			
			if (object != null) 
				object.setCarrier(null);
			recoveringFromAttack = false;
			stage.getDead().add(this);
		}
	}
	/****************************************************************************************/

	// Setter and Getter
	public int getX() {
		return (int) x;
	}
	public int getY() {
		return (int) y;
	}
	public void setPosition(int x, int y) {
		
		if (tileMap == null 
			|| (x % tileMap.tileWidthSize == 0 
			&& y % tileMap.tileHeightSize == 0)) {
			
			this.x = x;
			this.y = y;
		
		} else {
			
			Point multiple = 
					Multiple.findPointCloserTo(
							new Point(x,y), 
							new Point(
									tileMap.tileWidthSize,
									tileMap.tileHeightSize));
			this.x = (int) multiple.x;
			this.y = (int) multiple.y;
		}
	}

	public void setAllMov(boolean b){
		movDown = b;
		movLeft = b;
		movRight = b;
		movUp = b;
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
	public boolean isMovUp(){
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
	public boolean isAttack(){
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

package com.spantons.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import utilities.Multiple;
import utilities.TileWalk;

import com.spantons.tileMap.TileMap;

public class Entity {

	// Animacion
	protected Animation animation;
	protected int currentAnimation;
	protected int lastAnimation;
	protected boolean facingRight;
	
	// Hud
	protected BufferedImage face;
	
	// TileMap
	protected TileMap tileMap;
	int[][] map;
	private int xMap;
	private int yMap;
	private boolean inBounds;

	// Posicion
	protected int x;
	protected int y;
	protected double xDest;
	protected double yDest;
	protected double xTemp;
	protected double yTemp;
	
	// Proxima posicion en el mapa
	protected Point2D.Double nextPositionMap;
	protected int xDestMap;
	protected int yDestMap;
	
	// Reposicion
	protected boolean flinching;
	protected long flinchingTime;
	
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

	// atributos de movimientos
	protected double moveSpeed;
	protected double fallSpeed;
	protected double maxFallSpeed;
	protected double jumpStart;
	protected double reducerJumpSpeed;
	
	// personaje
	private int health;
	private int maxHealth;
	private int perversity;
	private int maxPerversity;
	private boolean dead;
	private String description;

	/****************************************************************************************/
	public Entity(TileMap tm) {
		if (tm != null){
			tileMap = tm;
			map = tileMap.getMap();
			xMap = (int) getMapPosition().x;
			yMap = (int) getMapPosition().y;
		}
	}
	/****************************************************************************************/
	public void initChief(int _xMap, int _yMap){
		calculateMapPositionInAbsolute(_xMap,_yMap);		
		magicWalk();
	}
	/****************************************************************************************/
	public void initOtherCharacters(int _xMap, int _yMap){
		xMap = _xMap;
		yMap = _yMap;
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
	public Point2D.Double getMapPosition() {
		double x = this.x + tileMap.getX();
		double y = this.y + tileMap.getY();

		return tileMap.absoluteToMap(x, y);
	}

	/****************************************************************************************/
	public void calculateMapPositionInAbsolute(double _x, double _y) {

		Point2D.Double absolutePosition = tileMap.mapToAbsolute(_x, _y);

		xDest = absolutePosition.x - tileMap.getX();
		yDest = absolutePosition.y - tileMap.getY();

	}
	public void setMapPosition(double _x, double _y) {

		Point2D.Double absolutePosition = tileMap.mapToAbsolute(_x, _y);
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
	public boolean checkCharactersCollision(ArrayList<Entity> characters, int currentCharacter) {
		
		if(characters.size() == 1)
			return true;
		
		for (int i = 0; i < characters.size(); i++){
			if (currentCharacter != i){
				if (characters.get(i).getMapPosition().equals(nextPositionMap)) 
					return false;
			}
		}
		
		return true;
	}
	/****************************************************************************************/
	public boolean checkTileCollision() {

		if ((nextPositionMap.x >= 0 && nextPositionMap.y >= 0
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
	public void update(ArrayList<Entity> characters, int currentCharacter) {
		
		updateAnimation();
		
		if (flinching) {
			long elapsedTime = (System.nanoTime() - flinchingTime) / 1000000;
			if (elapsedTime > 70) 
				flinching = false;
		
		} else {
			getNextPosition();
			
//			if (nextPositionMap.x != xMap
//				&& nextPositionMap.y != yMap) {
				if (checkTileCollision()) {
					if (checkCharactersCollision(characters, currentCharacter)) {
						
						calculateMapPositionInAbsolute(nextPositionMap.x, nextPositionMap.y);
						magicWalk();
					}
				}
//			}
			
			xMap = (int) getMapPosition().x;
			yMap = (int) getMapPosition().y;
			
			flinching = true;
			flinchingTime = System.nanoTime();
		}
	}
	/****************************************************************************************/
	public void updateOtherCharacters(){
		updateAnimation();
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
			
			inBounds = true;
			
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
			
			inBounds = false;
			setPosition(
					(int) tileMap.RESOLUTION_WIDTH_FIX / 2, 
					(int) tileMap.RESOLUTION_HEIGHT_FIX / 2);
			tileMap.setPosition(xDestMap,yDestMap);
		}
	}
	/****************************************************************************************/
	public void draw(Graphics2D g) {

		if (facingRight) {
			g.drawImage(animation.getCurrentImageFrame(),
					(int) (x - spriteWidth / 2), 
					(int) (y - spriteHeight / 2), null);
		} else {
			g.drawImage(animation.getCurrentImageFrame(), 
					(int) (x - spriteWidth / 2 + spriteWidth),
					(int) (y  - spriteHeight / 2),
					-spriteWidth, spriteHeight, null);
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

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public void setPosition(int x, int y) {
		
		if (tileMap == null || (x % tileMap.tileWidthSize == 0 
			&& y % tileMap.tileHeightSize == 0)) {
			
			this.x = x;
			this.y = y;
		
		} else {
			
			Point2D.Double multiple = 
					Multiple.findPointCloserTo(
							new Point2D.Double(x,y), 
							new Point2D.Double(
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
	
	public void setMovLeft(boolean b) {
		movLeft = b;
	}

	public void setMovRight(boolean b) {
		movRight = b;
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
	
	public int getXMap() {
		return xMap;
	}

	public void setXMap(int xMap) {
		this.xMap = xMap;
	}

	public int getYMap() {
		return yMap;
	}

	public void setYMap(int yMap) {
		this.yMap = yMap;
	}
	
	public boolean isInBounds() {
		return inBounds;
	}
	
	public int getSpriteWidth() {
		return spriteWidth;
	}
	
	public int getSpriteHeight() {
		return spriteHeight;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public int getMaxHealth() {
		return maxHealth;
	}
	public int setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
		return maxHealth;
	}
	public int getPerversity() {
		return perversity;
	}
	public void setPerversity(int perversity) {
		this.perversity = perversity;
	}
	public int getMaxPerversity() {
		return maxPerversity;
	}
	public void setMaxPerversity(int maxPerversity) {
		this.maxPerversity = maxPerversity;
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
	public void setDescription(String description) {
		this.description = description;
	}
	
}

package com.spantons.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import utilities.TileWalk;

import com.spantons.tileMap.TileMap;

public class Entity {

	// Animacion
	protected Animation animation;
	protected int currentAnimation;
	protected int lastAnimation;
	protected boolean facingRight;

	// Tile
	protected TileMap tileMap;
	int[][] map;
	private int xMap;
	private int yMap;

	// Posicion
	protected int x;
	protected int y;
	
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

	// colisiones
	protected int currentRow;
	protected int currentCol;
	protected double xDest;
	protected double yDest;
	protected double xTemp;
	protected double yTemp;
	protected boolean cornerTopLeft;
	protected boolean cornerTopRight;
	protected boolean cornerBottomLeft;
	protected boolean cornerBottomRight;

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

	/****************************************************************************************/
	public Entity(TileMap tm) {
		if (tm != null){
			tileMap = tm;
			map = tileMap.getMap();
			xDestMap = tileMap.getX();
			yDestMap = tileMap.getY();
			xMap = (int) getMapPosition().x;
			yMap = (int) getMapPosition().y;
		}
	}

	/****************************************************************************************/
	public Rectangle getRectangle() {
		return new Rectangle((int) x - collisionBoxWidth, (int) y
				- collisionBoxHeight, collisionBoxWidth,
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
	public void setMapPosition(double _x, double _y) {

		Point2D.Double absolutePosition = tileMap.mapToAbsolute(_x, _y);

		xDest = absolutePosition.x - tileMap.getX();
		yDest = absolutePosition.y - tileMap.getY();

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
	public void checkTileMapCollision() {

		if ((nextPositionMap.x >= 0 && nextPositionMap.y >= 0
				&& nextPositionMap.x < tileMap.getNumColMap()
				&& nextPositionMap.y < tileMap.getNumRowsMap())
//			&& tileMap.getUnlockedTiles().contains(map[(int)nextPositionMap.x][(int)nextPositionMap.y])
			) 
			
			setMapPosition(nextPositionMap.x, nextPositionMap.y);
		
		else {
			xDest = this.x;
			yDest = this.y;
		}
	}
	/****************************************************************************************/
	public void updateAnimation() {}
	/****************************************************************************************/
	public void update() {
		
		updateAnimation();
		
		if (flinching) {
			long elapsedTime = (System.nanoTime() - flinchingTime) / 1000000;
			if (elapsedTime > 70) 
				flinching = false;
		
		} else {
			getNextPosition();
			checkTileMapCollision();		
			magicWalk();
			
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
		setPosition((int) xDest, (int) yDest);
	}
	/****************************************************************************************/
	private void magicWalk() {
		
		xDestMap = (int) (tileMap.getX() + 
				(xDest - tileMap.RESOLUTION_WIDTH_FIX /2));
		yDestMap = (int) (tileMap.getY() + 
				(yDest - tileMap.RESOLUTION_HEIGHT_FIX /2));
					
		if (	tileMap.getX() == tileMap.getXMin() ||
			tileMap.getX() == tileMap.getXMax() ||
			tileMap.getY() == tileMap.getYMin() ||
			tileMap.getY() == tileMap.getYMax()	){
		
			if ( 	(tileMap.getX() == tileMap.getXMin() && x > tileMap.RESOLUTION_WIDTH_FIX / 2)
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

	public int getSpriteWidth() {
		return spriteWidth;
	}

	public int getSpriteHeight() {
		return spriteHeight;
	}

	public int getCollisionBoxWidth() {
		return collisionBoxWidth;
	}

	public int getCollisionBoxHeight() {
		return collisionBoxHeight;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
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

}

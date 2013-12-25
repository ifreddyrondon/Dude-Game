package com.spantons.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import utilities.TileWalk;

import com.spantons.main.GamePanel;
import com.spantons.tileMap.TileMap;

public class Entity {

	// Animacion
	protected Animation animation;
	protected int currentAnimation;
	protected int lastAnimation;
	protected boolean facingRight;

	// Tile
	protected TileMap tileMap;
	protected int xMap;
	protected int yMap;

	// Posicion
	protected int x;
	protected int y;
	
	// Posicion Actual y Proxima en el mapa
	protected Point2D.Double currentPositionMap;
	protected Point2D.Double nextPositionMap;
	
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
		if (tm != null)
			tileMap = tm;
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
		
		currentPositionMap = getMapPosition();
		
		if (movUp) 
			nextPositionMap = TileWalk.walkTo("N", currentPositionMap,moveSpeed); 
			
		if (movDown) 
			nextPositionMap = TileWalk.walkTo("S", currentPositionMap,moveSpeed);
			
		if (movLeft) 
			nextPositionMap = TileWalk.walkTo("W", currentPositionMap,moveSpeed);
				
		if (movRight)
			nextPositionMap = TileWalk.walkTo("E", currentPositionMap,moveSpeed);
		
		if (movUp && movLeft)
			nextPositionMap = TileWalk.walkTo("NW", currentPositionMap,moveSpeed + 1);
			
		if (movUp && movRight)
			nextPositionMap = TileWalk.walkTo("NE", currentPositionMap,moveSpeed + 1);
		
		if (movDown && movLeft)
			nextPositionMap = TileWalk.walkTo("SW", currentPositionMap,moveSpeed + 1);
	
		if (movDown && movRight) 
			nextPositionMap = TileWalk.walkTo("SE", currentPositionMap,moveSpeed + 1);
		
		if (!movUp && !movDown && !movLeft && !movRight) 
			nextPositionMap = TileWalk.walkTo("non", currentPositionMap,moveSpeed);
		
	}	
	/****************************************************************************************/
	public void checkTileMapCollision() {

		if (nextPositionMap.x >= 0 && nextPositionMap.y >= 0
				&& nextPositionMap.x < tileMap.getNumColMap()
				&& nextPositionMap.y < tileMap.getNumRowsMap()) {
		
			setMapPosition(nextPositionMap.x, nextPositionMap.y);
		}
		else {
			xDest = this.x;
			yDest = this.y;
		}
		
	}
	/****************************************************************************************/
	public void update() {
		
		if (flinching) {
			long elapsedTime = (System.nanoTime() - flinchingTime) / 1000000;
			if (elapsedTime > 70) 
				flinching = false;
		
		} else {
			getNextPosition();
			checkTileMapCollision();		
			
			int a = (int) (tileMap.getX() + (xDest - GamePanel.RESOLUTION_WIDTH /2));
			int b = (int) (tileMap.getY() + (yDest - GamePanel.RESOLUTION_HEIGHT /2));
			
//			if (	tileMap.getX() == tileMap.getXMin() ||
//				tileMap.getX() == tileMap.getXMax() ||
//				tileMap.getY() == tileMap.getYMin() ||
//				tileMap.getY() == tileMap.getYMax()	){
//			
//				if ( 	(tileMap.getX() == tileMap.getXMin() && x > GamePanel.RESOLUTION_WIDTH / 2) //||
//					//(tileMap.getX() == tileMap.getXMax() && x < GamePanel.RESOLUTION_WIDTH / 2) //||
//					//(tileMap.getY() == tileMap.getYMin() && x >= GamePanel.RESOLUTION_HEIGHT / 2) ||
//					//(tileMap.getY() == tileMap.getYMax() && x <= GamePanel.RESOLUTION_HEIGHT / 2) 
//					) {
//					
//					
//					tileMap.setPosition(a,b);
//				}
//				else
//					setPosition((int) xDest,(int) yDest);
//
//			}
//			else {
				
				
				tileMap.setPosition(a,b);
//			}
			
			flinching = true;
			flinchingTime = System.nanoTime();
		}
	}

	/****************************************************************************************/
	public void draw(Graphics2D g) {

		if (facingRight) {
			g.drawImage(animation.getCurrentImageFrame(),
					(int) (x + xMap - spriteWidth / 2), (int) (y
							+ yMap - spriteHeight / 2), null);
		} else {
			g.drawImage(animation.getCurrentImageFrame(), (int) (x + xMap
					- spriteWidth / 2 + spriteWidth),
					(int) (y + yMap - spriteHeight / 2),
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

}

package com.spantons.entity;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

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

	// Posicion y vector
	protected int x;
	protected int y;
	protected double dx;
	protected double dy;

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
	protected double maxMoveSpeed;
	protected double recuderMoveSpeed;
	protected double fallSpeed;
	protected double maxFallSpeed;
	protected double jumpStart;
	protected double reducerJumpSpeed;
	
	protected boolean flinching;
	protected long flinchingTime;
		
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
	public Point getMapPosition(){
		double x = this.x + tileMap.getX();
		double y = this.y + tileMap.getY();
		
		return tileMap.absoluteToMap(x, y);
	}
	/****************************************************************************************/
	public void setMapPosition(double _x, double _y){
		
		Point nextAbsolutePosition = 
				tileMap.mapToAbsolute(_x, _y);
		
		xDest = nextAbsolutePosition.x - tileMap.getX();
		yDest = nextAbsolutePosition.y - tileMap.getY();
		
		setPosition((int)_x, (int)_y);
	}
	/****************************************************************************************/
	public static Point tileWalk(String direction, Point coor, int steps){
		
		if (direction.equals("ninguna"))
			return new Point(coor.x ,coor.y);
		
		else if (direction.equals("norte"))
			return new Point(coor.x - steps,coor.y - steps);
		
		else if (direction.equals("norte este")) 
			return new Point(coor.x,coor.y - steps);
			
		else if (direction.equals("este")) 
			return new Point(coor.x + steps,coor.y - steps);
		
		else if (direction.equals("sur este"))
			return new Point(coor.x + steps,coor.y);
		
		else if (direction.equals("sur"))
			return new Point(coor.x + steps,coor.y + steps);
		
		else if (direction.equals("sur oeste")) 
			return new Point(coor.x,coor.y + steps);
			
		else if (direction.equals("oeste")) 
			return new Point(coor.x - steps,coor.y + steps);
		
		else if (direction.equals("norte oeste")) 
			return new Point(coor.x - steps,coor.y);
				
		return null;
	}
	/****************************************************************************************/
	public Point updateCoord(int _x, int _y){
		Point currentCoord = new Point(_x,_y);
		
		Point nextPosition = null;
		
		if (dx != 0 && dy == 0) {
			if (dx < 0)  
				nextPosition = tileWalk("oeste", currentCoord,(int) Math.abs(dx));
			if (dx > 0) 
				nextPosition = tileWalk("este", currentCoord,(int) Math.abs(dx));
		} else if (dx == 0 && dy != 0) {
			if (dy < 0) 
				nextPosition = tileWalk("norte", currentCoord,(int) Math.abs(dy));
			if (dy > 0) 
				nextPosition = tileWalk("sur", currentCoord,(int) Math.abs(dy));
			
		} else if (dx != 0 && dy != 0) {
			if (dx < 0 && dy < 0) 
				nextPosition = tileWalk("norte oeste", currentCoord,(int) Math.abs(dx));
			if (dx > 0 && dy < 0) 
				nextPosition = tileWalk("norte este", currentCoord,(int) Math.abs(dx));
			if (dx < 0 && dy > 0) 
				nextPosition = tileWalk("sur oeste", currentCoord,(int) Math.abs(dx));
			if (dx > 0 && dy > 0) 
				nextPosition = tileWalk("sur este", currentCoord,(int) Math.abs(dx));
		}
		return nextPosition;
	}
	/****************************************************************************************/
	public void checkTileMapCollision(){
		
		if (flinching) {
			long elapsedTime = (System.nanoTime() - flinchingTime) / 1000000;
			// si tiempo transcurrido es mayor que un segundo
			if (elapsedTime > 100) 
				flinching = false;
		
		} else {
		
			Point currentPosition = getMapPosition();
			Point nextPosition = updateCoord(currentPosition.x, currentPosition.y);
					
			if (nextPosition.x >= 0 && nextPosition.y >= 0
					&& nextPosition.x < tileMap.getNumColMap() + 1
					&& nextPosition.y < tileMap.getNumRowsMap()) {
				
				setMapPosition(nextPosition.x, nextPosition.y);
			
			} else {
				xDest = this.x;
				yDest = this.y;
			}
			
			flinching = true;
			flinchingTime = System.nanoTime();
		}
	}
	/****************************************************************************************/
	public void update() {
		
	}
	/****************************************************************************************/
	public void draw(Graphics2D g) {

		if (facingRight) {
			g.drawImage(animation.getCurrentImageFrame(),
					(int) (x + xMap - spriteWidth / 2), 
					(int) (y + yMap - spriteHeight / 2), 
					null);
		} else {
			g.drawImage(animation.getCurrentImageFrame(), 
					(int) (x + xMap- spriteWidth / 2 + spriteWidth),
					(int) (y + yMap - spriteHeight / 2),
					-spriteWidth, 
					spriteHeight, 
					null);
		}
	}
	/****************************************************************************************/
	// Movimientos
	public void movEntityLeft(){
		dx -= moveSpeed;
		if (dx < -maxMoveSpeed)	 
			dx = -maxMoveSpeed;
	}
	
	public void movEntityRight(){
		dx += moveSpeed;
		if (dx > maxMoveSpeed) 
			dx = maxMoveSpeed;
	}
	
	public void movEntityUp(){
		dy -= moveSpeed;
		if (dy < -maxMoveSpeed) 
			dy = -maxMoveSpeed;
	}
	
	public void movEntityDown(){
		dy += moveSpeed;
		if (dy > maxMoveSpeed) 
			dy = maxMoveSpeed;
	}
	
	public void movEntityStop(){
		if (dy < 0) {
			dy += recuderMoveSpeed;
			if (dy > 0) 
				dy = 0;

		} else if (dy > 0) {
			dy -= recuderMoveSpeed;
			if (dy < 0) 
				dy = 0;
				
		} else if (dx > 0) {
			dx -= recuderMoveSpeed;
			if (dx < 0)
				dx = 0;

		} else if (dx < 0) {
			dx += recuderMoveSpeed;
			if (dx > 0)
				dx = 0;
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

	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public void setPositionInMap() {
		xMap = tileMap.getX();
		yMap = tileMap.getY();
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

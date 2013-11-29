package com.spantons.entity;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

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
	/**
	 * Interseccion entre 2 rectangulos
	 * 
	 * @param e
	 * @return
	 */
	public boolean intersection(Entity e) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = e.getRectangle();
		return r1.intersects(r2);
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
	public void checkTileMapCollisionAndUpdateCoord(){
		
		if (flinching) {
			long elapsedTime = (System.nanoTime() - flinchingTime) / 1000000;
			// si tiempo transcurrido es mayor que un segundo
			if (elapsedTime > 200) flinching = false;
		}
		else {
			//System.out.println("x: " + x + " ; " + "y: " + y);
			Point currentPosition = TileMap.absoluteToMap(x, y);
			Point2D.Double nextPosition = null;
			
			if (currentPosition.x >= 0 && currentPosition.y >= 0
					&& currentPosition.x < tileMap.getNumColMap()
					&& currentPosition.y < tileMap.getNumRowsMap()) {
				
				if (dx != 0 && dy == 0) {
					if (dx < 0) currentPosition = tileWalk("norte oeste", currentPosition, 1);
					if (dx > 0) currentPosition = tileWalk("sur este", currentPosition, 1);
				} else if (dx == 0 && dy != 0) {
					if (dy < 0) currentPosition = tileWalk("norte este", currentPosition, 1);
					if (dy > 0) currentPosition = tileWalk("sur oeste", currentPosition, 1);
				} else {
					System.err.println("a");
				}
				
				nextPosition = TileMap.mapToAbsolute(currentPosition.x, currentPosition.y);
				xDest = nextPosition.x;
				yDest = nextPosition.y;
				
				flinching = true;
				flinchingTime = System.nanoTime();
				
					//System.out.println("mapX: " + currentPosition.x + "' ; " + "mapY: " + currentPosition.y);
					//System.out.println("NextX: " + nextPosition.x + "' ; " + "NextY: " + nextPosition.y);
			
			}
		}
		
		
		/*
		Point currentPosition = TileMap.absoluteToMap(x, y);
		Point destination = null;
	
		
		
		
		System.out.println("x: " + x + "' ; " + "y: " + y);
		System.out.println("mapX: " + currentPosition.x + "' ; " + "mapY: " + currentPosition.y);
		System.out.println("DestinationMapX: " + destination.x + "' ; " + "DestinationMapY: " + destination.y);
		
		//destination = TileMap.mapToAbsolute(destination.x, destination.y);
		
		System.out.println("newX: " + destination.x + "' ; " + "newY: " + destination.y);
		System.out.println();
		System.out.println();
		
		xDest = destination.x;
		yDest = destination.y;
		*/
	
	}
	/****************************************************************************************/
	public void update() {}

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

	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public void setPositionInMap() {
		// xMap = tileMapa.getX();
		// yMap = tileMapa.getY();
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

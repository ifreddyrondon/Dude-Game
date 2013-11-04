package com.spantons.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Entity {

	// Animacion
	protected Animation animation;
	protected int currentAnimation;
	protected int lastAnimation;
	protected boolean facingRight;

	// Tile
	protected int tileSize;
	protected double xMap;
	protected double yMap;

	// Posicion y vector
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;

	// Dimensiones
	protected int spriteWidth;
	protected int spriteHeight;

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

	public Rectangle getRectangle() {
		return new Rectangle((int) x - collisionBoxWidth, (int) y
				- collisionBoxHeight, collisionBoxWidth,
				collisionBoxHeight);
	}

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

	public void setPosition(double x, double y) {
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

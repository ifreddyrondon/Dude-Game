package com.spantons.object;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import com.spantons.entity.Animation;
import com.spantons.entity.Entity;
import com.spantons.tileMap.TileMap;

public class Object {

	protected int xMap;
	protected int yMap;
	protected int x;
	protected int y;
	
	protected TileMap tileMap;
	protected Entity carrier;
	
	protected String description;
	protected float damage;
	
	protected Animation animation;
	protected int currentAnimation;
	protected int spriteWidth;
	protected int spriteHeight;
	protected double scale;
	
	public static int WEAPON_TYPE = 0;
	/****************************************************************************************/
	public Object() {
		
	}
	/****************************************************************************************/
	private void calculatePositionToDraw() {
		Point2D.Double absolutePosition = tileMap.mapToAbsolute(xMap, yMap);
		x = (int) (absolutePosition.x - tileMap.getX());
		y = (int) (absolutePosition.y - tileMap.getY());
	}
	/****************************************************************************************/
	public void update() {
		if (carrier != null) {
			Point2D.Double aux = carrier.getMapPosition();
			xMap = (int) aux.x;
			yMap = (int) aux.y;
		}	
		calculatePositionToDraw();
	}
	/****************************************************************************************/
	public void draw(Graphics2D g) {
		if (carrier != null) {
			if (carrier.isMovUp() && !carrier.isMovLeft() && !carrier.isMovRight()) 
				return;
			
			if (carrier.isFacingRight()) 
				g.drawImage(animation.getCurrentImageFrame(),
					x + carrier.getSpriteWidth() / 2, 
					y + carrier.getSpriteHeight() / 3, null);
			
			else 
				g.drawImage(animation.getCurrentImageFrame(), 
					x + carrier.getSpriteWidth() / 2, 
					y + carrier.getSpriteHeight() / 3, -spriteWidth, spriteHeight, null);
		}			
		else
			g.drawImage(animation.getCurrentImageFrame(),
				x + spriteWidth / 2, 
				y + spriteHeight, null);
	}
	/****************************************************************************************/
	public int getxMap() {
		return xMap;
	}
	public void setxMap(int xMap) {
		this.xMap = xMap;
	}
	public int getyMap() {
		return yMap;
	}
	public void setyMap(int yMap) {
		this.yMap = yMap;
	}
	public String getDescription() {
		return description;
	}
	public Entity getCarrier() {
		return carrier;
	}
	public void setCarrier(Entity carrier) {
		this.carrier = carrier;
	}
	public float getDamage() {
		return damage;
	}
	
}

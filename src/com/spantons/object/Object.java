package com.spantons.object;

import java.awt.Graphics2D;
import java.awt.Point;

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
	protected boolean showObject;
	private Object[][] objectsToDraw;
	
	protected String description;
	protected float damage;
	
	protected Animation animation;
	protected int currentAnimation;
	protected int spriteWidth;
	protected int spriteHeight;
	protected int offSetXLoading;
	protected int offSetYLoading;	
	protected double scale;
	
	protected int type;
	public static int BLOCKED = 0;
	public static int NON_BLOCKED = 1;
	
	/****************************************************************************************/
	public Object(TileMap _tileMap, int _xMap, int _yMap) {
		tileMap = _tileMap;
		xMap = _xMap;
		yMap = _yMap;
		objectsToDraw = tileMap.getObjectsToDraw();
		objectsToDraw[xMap][yMap] = this;
		showObject = true;
	}
	/****************************************************************************************/
	private void calculatePositionToDraw() {
		Point absolutePosition = tileMap.mapToAbsolute(xMap, yMap);
		x = (int) (absolutePosition.x - tileMap.getX());
		y = (int) (absolutePosition.y - tileMap.getY());
	}
	/****************************************************************************************/
	public void update() {
		if (carrier != null && type == NON_BLOCKED) {
			Point aux = carrier.getMapPositionOfCharacter();
			xMap = (int) aux.x;
			yMap = (int) aux.y;
		}	
		calculatePositionToDraw();
	}
	/****************************************************************************************/
	public void draw(Graphics2D g) {
		
		if (showObject == false) 
			return;
		
		if (carrier != null && type == NON_BLOCKED) {
			if (carrier.isMovUp() && !carrier.isMovLeft() && !carrier.isMovRight()) 
				return;
			
			if (carrier.isFacingRight()) 
				g.drawImage(animation.getCurrentImageFrame(),
					x + carrier.getSpriteWidth() / 2 - spriteWidth + offSetXLoading, 
					y - spriteHeight - offSetYLoading, null);
			
			else 
				g.drawImage(animation.getCurrentImageFrame(), 
					x + carrier.getSpriteWidth() / 2 - spriteWidth + offSetXLoading,
					y - spriteHeight - offSetYLoading, -spriteWidth, spriteHeight, null);
		}			
		else
			g.drawImage(animation.getCurrentImageFrame(),
				x - spriteWidth / 2, 
				y - spriteHeight, null);
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
	public int getType(){
		return type;
	}
	
}

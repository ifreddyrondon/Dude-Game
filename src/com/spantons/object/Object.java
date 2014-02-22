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
	
	protected boolean loadObject;
	
	public static int WEAPON_TYPE = 0;
	/****************************************************************************************/
	public Object() {
		loadObject = false;
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
		g.drawImage(animation.getCurrentImageFrame(),x, y, null);
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

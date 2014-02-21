package com.spantons.object;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import com.spantons.entity.Animation;
import com.spantons.tileMap.TileMap;

public class Object {

	protected int xMap;
	protected int yMap;
	protected int x;
	protected int y;
	
	protected TileMap tileMap;
	
	protected Animation animation;
	protected int currentAnimation;
	protected int spriteWidth;
	protected int spriteHeight;
	protected double scale;
	
	protected boolean loadObject;
	
	public static int WEAPON_TYPE = 0;
	
	public Object() {
		loadObject = false;
	}
	
	private void calculatePositionToDraw() {
		Point2D.Double absolutePosition = tileMap.mapToAbsolute(xMap, yMap);
		x = (int) (absolutePosition.x - tileMap.getX());
		y = (int) (absolutePosition.y - tileMap.getY());
	}
	
	public void update() {
		calculatePositionToDraw();
	}
	
	public void draw(Graphics2D g) {
		if (!loadObject) 
			g.drawImage(animation.getCurrentImageFrame(),x, y, null);
	}
	
}

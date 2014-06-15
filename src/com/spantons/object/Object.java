package com.spantons.object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.spantons.Interfaces.IDrawable;
import com.spantons.Interfaces.ILoadSprite;
import com.spantons.Interfaces.IUpdateable;
import com.spantons.entity.Animation;
import com.spantons.entity.Entity;
import com.spantons.tileMap.TileMap;

public class Object implements IDrawable {
	
	public static int NON_CONSUMABLE = 0;
	public static int CONSUMABLE = 1;
	protected int type;
	protected int timeToConsumable;
	
	protected Entity carrier;
	protected String description;
	protected boolean activated;
	
	protected int xMap;
	protected int yMap;
	protected int x;
	protected int y;
	protected int spriteWidth;
	protected int spriteHeight;
	protected double scale;
	protected ArrayList<BufferedImage[]> sprites;
	
	protected Animation animation;
	protected int currentAnimation;
	protected int offSetXLoading;
	protected int offSetYLoading;
	
	protected IUpdateable update;
	protected IDrawable draw;
	protected IObjectAttribute attribute;
	protected ILoadSprite loadSprite;
	
	/****************************************************************************************/
	public Object(TileMap _tileMap, int _xMap, int _yMap) {
		xMap = _xMap;
		yMap = _yMap;
		_tileMap.setObjectToDraw(xMap, yMap, this);
	}
	
	/****************************************************************************************/
	public void update() {
		update.update();
	}
	
	/****************************************************************************************/
	public void draw(Graphics2D g) {
		draw.draw(g);
	}

	/****************************************************************************************/
	public void actionLoad() {
		attribute.loadAttribute(carrier);
	}

	/****************************************************************************************/
	public void actionUnload() {
		attribute.unloadAttribute(carrier);
	}
	
	/****************************************************************************************/
	public void setCarrier(Entity _entity) {
		carrier = _entity;
	}
	
	public int getXMap() {
		return xMap;
	}
	
	public int getYMap() {
		return yMap;
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getType() {
		return type;
	}
	
	public boolean isActivated(){
		return activated;
	}
}

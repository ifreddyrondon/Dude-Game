package com.spantons.object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.spantons.Interfaces.IDrawable;
import com.spantons.Interfaces.IUpdateable;
import com.spantons.entity.Animation;
import com.spantons.entity.Entity;
import com.spantons.tileMap.TileMap;

public abstract class Object implements IDrawable, IUpdateable {
	
	public static int NON_CONSUMABLE = 0;
	public static int CONSUMABLE = 1;
	protected int timeToConsumable;
	
	protected Entity carrier;
	protected TileMap tileMap;
	protected String description;
	protected int type;
	protected String idAssociated;
	
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
	
	public abstract void actionLoad();
	public abstract void actionUnload();
	public abstract void update();
	public abstract void draw(Graphics2D g);
	
	/****************************************************************************************/
	public Object(TileMap _tileMap, int _xMap, int _yMap) {
		tileMap = _tileMap;
		xMap = _xMap;
		yMap = _yMap;
		tileMap.setObjectToDraw(xMap, yMap, this);
	}
	
	/****************************************************************************************/
	public void setCarrier(Entity _entity) {
		carrier = _entity;
	}
	
	/****************************************************************************************/
	public int getXMap() {
		return xMap;
	}
	
	/****************************************************************************************/
	public int getYMap() {
		return yMap;
	}
	
	/****************************************************************************************/
	public String getDescription() {
		return description;
	}
	
	/****************************************************************************************/
	public int getType() {
		return type;
	}
	
	/****************************************************************************************/
	public String getIdAssociated(){
		return idAssociated;
	}
	
}

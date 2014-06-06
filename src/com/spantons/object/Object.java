package com.spantons.object;

import java.awt.Graphics2D;

import com.spantons.entity.Animation;
import com.spantons.entity.Entity;
import com.spantons.tileMap.TileMap;

public abstract class Object {

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
	protected int timeOfDrunk;
	protected float health;
	protected int moveSpeed;
	protected int timeOfHigh;
	protected String idAssociated;
	
	protected Animation animation;
	protected int currentAnimation;
	protected int spriteWidth;
	protected int spriteHeight;
	protected int offSetXLoading;
	protected int offSetYLoading;	
	protected double scale;
	
	public abstract void update();
	public abstract void draw(Graphics2D g);
	public abstract void load(Entity _entity);
	public abstract void unload(Entity _entity);
	
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
	public float getHealth() {
		return health;
	}
	public int getTimeOfDrunk() {
		return timeOfDrunk;
	}
	public int getMoveSpeed() {
		return moveSpeed;
	}
	public int getTimeOfHigh() {
		return timeOfHigh;
	}
	public String getIdAssociated(){
		return idAssociated;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
		
}

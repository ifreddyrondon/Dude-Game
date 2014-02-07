package com.spantons.entity;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import utilities.TileWalk;

public class EntityGameFuntions {

	private int health;
	private int maxHealth;
	private boolean dead;
	
	private String description;
	
	private boolean jason;
	private int perversity;
	private int maxPerversity;
	
	protected boolean closeJason;
	protected boolean closeOtherCharacter;
	
	protected int flinchingIncreaseDeltaTimePerversity;
	protected long flinchingIncreaseTimePerversity;
	protected boolean flinchingIncreasePerversity;
	protected int flinchingDecreaseDeltaTimePerversity;
	protected long flinchingDecreaseTimePerversity;
	protected boolean flinchingDecreasePerversity;
	
	/****************************************************************************************/
	protected void increasePerversity(){
		
		if (flinchingIncreasePerversity) {
			long elapsedTime = (System.nanoTime() - flinchingIncreaseTimePerversity) / 1000000;
			if (elapsedTime > flinchingIncreaseDeltaTimePerversity) 
				flinchingIncreasePerversity = false;
		
		} else {
			if (perversity >= maxPerversity) {
				perversity = maxPerversity;
				jasonTransform();
			}
			else
				perversity++;
			
			flinchingIncreasePerversity = true;
			flinchingIncreaseTimePerversity = System.nanoTime();
		}
	}
	/****************************************************************************************/
	protected void jasonTransform() {
		jason = true;
		
	}
	/****************************************************************************************/
	protected void decreasePerversity(){
		
		if (flinchingDecreasePerversity) {
			long elapsedTime = (System.nanoTime() - flinchingDecreaseTimePerversity) / 1000000;
			if (elapsedTime > flinchingDecreaseDeltaTimePerversity) 
				flinchingDecreasePerversity = false;
		
		} else {
			if (perversity <= 0) 
				perversity = 0;
			else
				perversity--;
			
			flinchingDecreasePerversity = true;
			flinchingDecreaseTimePerversity = System.nanoTime();
		}
	}
	/****************************************************************************************/
	protected boolean checkIsCloseToAnotherCharacter(ArrayList<Entity> _characters, ArrayList<Entity> _jasons, int _currentCharacter) {
		
		Point2D.Double north = TileWalk.walkTo("N", _characters.get(_currentCharacter).getMapPosition(),1);
		Point2D.Double south = TileWalk.walkTo("S", _characters.get(_currentCharacter).getMapPosition(),1);
		Point2D.Double west = TileWalk.walkTo("W", _characters.get(_currentCharacter).getMapPosition(),1);
		Point2D.Double east = TileWalk.walkTo("E", _characters.get(_currentCharacter).getMapPosition(),1);
				
		if (_characters.size() > 0) {
			for (int i = 0; i < _characters.size(); i++){
				if (_currentCharacter != i){
					if (
						_characters.get(i).getMapPosition().equals(north)
						|| _characters.get(i).getMapPosition().equals(south)
						|| _characters.get(i).getMapPosition().equals(west)
						|| _characters.get(i).getMapPosition().equals(east)
							) {
						closeOtherCharacter = true;
						return true;
					}
				}
			}
		}
		
		if (_jasons.size() > 0) {
			for (int i = 0; i < _jasons.size(); i++){
				if (
					_jasons.get(i).getMapPosition().equals(north)
					|| _jasons.get(i).getMapPosition().equals(south)
					|| _jasons.get(i).getMapPosition().equals(west)
					|| _jasons.get(i).getMapPosition().equals(east)
					) {
					closeJason = true;
					return true;
				}
					
				}
		}
		
		closeJason = false;
		closeOtherCharacter = false;
		return false;
	}
	/****************************************************************************************/
	public String whoIsClose(){
		if (closeJason) 
			return "jason";
		else if(closeOtherCharacter)
			return "other";
		
		return null;
	}
	/****************************************************************************************/
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public int getMaxHealth() {
		return maxHealth;
	}
	public int setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
		return maxHealth;
	}
	public int getPerversity() {
		return perversity;
	}
	public void setPerversity(int perversity) {
		this.perversity = perversity;
	}
	public int getMaxPerversity() {
		return maxPerversity;
	}
	public void setMaxPerversity(int maxPerversity) {
		this.maxPerversity = maxPerversity;
	}
	public boolean isDead() {
		return dead;
	}
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isJason() {
		return jason;
	}
	public void setJason(boolean a){
		jason = a;
	}
	
}

package com.spantons.entity;

public class EntityGameFuntions {

	private int health;
	private int maxHealth;
	private boolean dead;
	
	private String description;
	
	private boolean jason;
	private int perversity;
	private int maxPerversity;
	
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

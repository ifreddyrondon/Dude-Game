package com.spantons.entity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.Timer;

import utilities.SoundCache;

import com.spantons.entity.character.Jason;
import com.spantons.gameState.Stage;
import com.spantons.object.Object;
import com.spantons.path.SoundPath;
import com.spantons.tileMap.TileMap;

public class EntityLogic {

	protected Stage stage;
	
	protected TileMap tileMap;
	protected int xMap;
	protected int yMap;
	
	protected BufferedImage face;
	protected int spriteWidth;
	protected int spriteHeight;
	protected double scale;
	protected Animation animation;
	protected int currentAnimation;
	protected int lastAnimation;
	protected boolean facingRight;
	protected ArrayList<BufferedImage[]> sprites;
	protected static final int WALKING_FRONT = 0;
	protected static final int WALKING_BACK = 1;
	protected static final int WALKING_SIDE = 2;
	protected static final int WALKING_PERSPECTIVE_FRONT = 3;
	protected static final int WALKING_PERSPECTIVE_BACK = 4;
	protected static final int IDLE = 3;
	protected static final int DEAD = 5;
	
	protected boolean movLeft;
	protected boolean movRight;
	protected boolean movUp;
	protected boolean movDown;
	protected boolean movJumping;
	protected boolean movFalling;

	protected float health;
	protected float maxHealth;
	protected boolean dead;
	protected String description;
	protected int perversity;
	protected int maxPerversity;
	protected float damage;
	protected int moveSpeed;
	
	protected boolean attack;
	protected boolean recoveringFromAttack;
	protected long flinchingTimeRecoveringFromAttack;
	
	protected int flinchingIncreaseDeltaTimePerversity;
	protected long flinchingIncreaseTimePerversity;
	protected boolean flinchingIncreasePerversity;
	protected int flinchingDecreaseDeltaTimePerversity;
	protected long flinchingDecreaseTimePerversity;
	protected boolean flinchingDecreasePerversity;
	protected int deltaForReduceFlinchingIncreaseDeltaTimePerversity;
	
	protected Entity characterClose;
	protected String characterCloseDirection;
	protected Object object;
	protected Entity[][] entitysToDraw;
	protected Entity[][] entitysDeadToDraw;
	protected Object[][] objectsToDraw;
		
	/****************************************************************************************/
	protected void updateAnimation() {
		if (dead) {
			if (currentAnimation != DEAD) {
				currentAnimation = DEAD;
				animation.setFrames(sprites.get(DEAD));
				animation.setDelayTime(150);
			}
		} else {
			if ((movLeft && movDown) || (movRight && movDown)) {
				if (currentAnimation != WALKING_PERSPECTIVE_FRONT) {
					currentAnimation = WALKING_PERSPECTIVE_FRONT;
					animation.setFrames(sprites
							.get(WALKING_PERSPECTIVE_FRONT));
					animation.setDelayTime(150);
				}
			} else if ((movLeft && movUp) || (movRight && movUp)) {
				if (currentAnimation != WALKING_PERSPECTIVE_BACK) {
					currentAnimation = WALKING_PERSPECTIVE_BACK;
					animation.setFrames(sprites
							.get(WALKING_PERSPECTIVE_BACK));
					animation.setDelayTime(150);
				}
			} else if (movDown) {
				if (currentAnimation != WALKING_FRONT) {
					currentAnimation = WALKING_FRONT;
					animation.setFrames(sprites.get(WALKING_FRONT));
					animation.setDelayTime(100);
				}
			} else if (movUp) {
				if (currentAnimation != WALKING_BACK) {
					currentAnimation = WALKING_BACK;
					animation.setFrames(sprites.get(WALKING_BACK));
					animation.setDelayTime(40);
				}
			} else if (movLeft || movRight) {
				if (currentAnimation != WALKING_SIDE) {
					currentAnimation = WALKING_SIDE;
					animation.setFrames(sprites.get(WALKING_SIDE));
					animation.setDelayTime(150);
				}
			} else {
				if (currentAnimation != IDLE) {
					currentAnimation = IDLE;
					animation.setFrames(sprites.get(IDLE));
					animation.setDelayTime(1000);
				}
			}
			if (movRight)
				facingRight = true;
			if (movLeft)
				facingRight = false;

			animation.update();
		}
	}
	
	/****************************************************************************************/
	protected void increasePerversity() {
		if (flinchingIncreasePerversity) {
			long elapsedTime = (System.nanoTime() - flinchingIncreaseTimePerversity) / 1000000;
			if (elapsedTime > flinchingIncreaseDeltaTimePerversity)
				flinchingIncreasePerversity = false;

		} else {
			if (perversity >= maxPerversity) {
				perversity = maxPerversity;
				jasonTransform();
			} else
				perversity = perversity + 1;

			flinchingIncreasePerversity = true;
			flinchingIncreaseTimePerversity = System.nanoTime();
		}
	}
	/****************************************************************************************/
	protected void decreasePerversity() {
		if (flinchingDecreasePerversity) {
			long elapsedTime = (System.nanoTime() - flinchingDecreaseTimePerversity) / 1000000;
			if (elapsedTime > flinchingDecreaseDeltaTimePerversity)
				flinchingDecreasePerversity = false;

		} else {
			if (perversity <= 0)
				perversity = 0;
			else
				perversity = perversity - 1;

			flinchingDecreasePerversity = true;
			flinchingDecreaseTimePerversity = System.nanoTime();
		}
	}
	
	/****************************************************************************************/
	private void jasonTransform() {
		if (object != null){
			object.setCarrier(null);
			objectsToDraw[xMap][yMap] = object;
		}
		stage.getCharacters().remove(this);
		stage.getJasons().add(new Jason(tileMap, stage, xMap, yMap, 0.10));
	}
	
	/****************************************************************************************/
	public void takeOrLeaveObject() {
		if (object != null) {
			Object newObject = EntityChecks.checkIsOverObject((Entity) this, stage);
			if (newObject != null) {
				
				newObject.setCarrier((Entity) this);
				object.setCarrier(null);
				object.unload((Entity) this);
				objectsToDraw[xMap][yMap] = object;
				object = newObject;
				object.load((Entity) this);
			}
			else {
				object.setCarrier(null);
				object.unload((Entity) this);
				objectsToDraw[xMap][yMap] = object;
				object = null;
			}
		} else {
			object = EntityChecks.checkIsOverObject((Entity) this, stage);
			if (object != null) {
				object.setCarrier((Entity) this);
				object.load((Entity) this);
				objectsToDraw[xMap][yMap] = null;
			}
		}
	}
	
	/****************************************************************************************/
	public void getDrunk(Object _object){
		SoundCache.getInstance().getSound(SoundPath.SFX_BURP).play();
		object = null;
		final float damageObject = _object.getDamage();	
		damage = damage + damageObject;
		if (damage <= 0) 
			damage = 0;		
		stage.getObjects().remove(_object);
		
		Timer timer = new Timer(_object.getTimeOfDrunk(), new ActionListener() { 
			@Override 
			public void actionPerformed(ActionEvent ae) { 
				damage = damage - damageObject;
			} 
		}); 
		timer.setRepeats(false);
		timer.start();
	}
	
	/****************************************************************************************/
	public void getHigh(Object _object){
		SoundCache.getInstance().getSound(SoundPath.SFX_SMOKE).play();
		object = null;
		final int moveSpeedObject = _object.getMoveSpeed();		
		moveSpeed = moveSpeed - moveSpeedObject;
		stage.getObjects().remove(_object);
		
		Timer timer = new Timer(_object.getTimeOfHigh(), new ActionListener() { 
			@Override 
			public void actionPerformed(ActionEvent ae) { 
				moveSpeed = moveSpeed + moveSpeedObject;
			} 
		}); 
		timer.setRepeats(false);
		timer.start();
	}
	
	/****************************************************************************************/
	public void getHealth(Object _object) {
		SoundCache.getInstance().getSound(SoundPath.SFX_EAT).play();
		float healthObject = _object.getHealth();
		stage.getObjects().remove(_object);
		if (health == maxHealth) 
			return;
		else {
			if (health + healthObject > maxHealth)
				health = maxHealth;
			else
				health = health + healthObject;
		}
	}
	
	/****************************************************************************************/
	protected void attack() {
		
		if (characterClose != null) {
			if (characterClose.recoveringFromAttack)
				return;
			
			movFace(characterCloseDirection);
			characterClose.setHealth(characterClose.getHealth() - damage);
			
			if (characterClose.getHealth() <= 0) {
				characterClose.setDead(true);
				characterClose.setHealth(0);
				return;
			}
			
			if (this == stage.getCurrentCharacter())
				flinchingIncreaseDeltaTimePerversity -= deltaForReduceFlinchingIncreaseDeltaTimePerversity;

			characterClose.recoveringFromAttack = true;
			characterClose.flinchingTimeRecoveringFromAttack = System.nanoTime();
		}
	}
	
	/****************************************************************************************/
	protected void movFace(String _direction) {
		if (_direction.equals("N")) 
			animation.setFrames(sprites.get(WALKING_BACK));
		
		else if (_direction.equals("S")) 
			animation.setFrames(sprites.get(WALKING_FRONT));
		
		else if (_direction.equals("W")) {
			facingRight = false;
			animation.setFrames(sprites.get(WALKING_SIDE));
			
		} else if (_direction.equals("E")){
			facingRight = true;
			animation.setFrames(sprites.get(WALKING_SIDE));
			
		} else if (_direction.equals("NW")){
			facingRight = false;
			animation.setFrames(sprites.get(WALKING_PERSPECTIVE_BACK));
			
		} else if (_direction.equals("NE")){
			facingRight = true;
			animation.setFrames(sprites.get(WALKING_PERSPECTIVE_BACK));
			
		} else if (_direction.equals("SW")){
			facingRight = false;
			animation.setFrames(sprites.get(WALKING_PERSPECTIVE_FRONT));
		
		} else if (_direction.equals("SE")){
			facingRight = true;
			animation.setFrames(sprites.get(WALKING_PERSPECTIVE_FRONT));
		}
	}
	/****************************************************************************************/
	protected void checkIsRecoveringFromAttack() {
		if (recoveringFromAttack) {
			long elapsedTime = (System.nanoTime() - flinchingTimeRecoveringFromAttack) / 1000000;
			if (elapsedTime > 1000)
				recoveringFromAttack = false;
		}
	}
	/****************************************************************************************/
	public void checkCharacterIsDead() {
		if (dead) {
			SoundCache.getInstance().getSound(SoundPath.SFX_DYING).play();
			if (object != null)
				object.setCarrier(null);

			entitysToDraw[xMap][yMap] = null;
			entitysDeadToDraw[xMap][yMap] = (Entity) this;
			recoveringFromAttack = false;
			stage.getDead().add((Entity) this);
			
			if (description == "Jason")
				stage.getJasons().remove(this);
			else if (description != "Jason")
				stage.getCharacters().remove(this);
		}
	}
	/****************************************************************************************/
}
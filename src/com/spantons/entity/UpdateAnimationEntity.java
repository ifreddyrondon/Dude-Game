package com.spantons.entity;

import com.spantons.Interfaces.IUpdateable;

public class UpdateAnimationEntity implements IUpdateable {

	private Entity entity;
	
	/****************************************************************************************/
	public UpdateAnimationEntity(Entity _entity) {
		entity = _entity;
	}

	/****************************************************************************************/
	@Override
	public void update() {
		if (entity.dead) {
			if (entity.currentAnimation != Entity.DEAD) {
				entity.currentAnimation = Entity.DEAD;
				entity.animation.setFrames(entity.sprites.get(Entity.DEAD));
				entity.animation.setDelayTime(150);
			}
		} else {
			if (entity.movLeft && entity.movUp) {
				if (entity.currentAnimation != Entity.WALKING_BACK) {
					entity.currentAnimation = Entity.WALKING_BACK;
					entity.animation.setFrames(entity.sprites.get(Entity.WALKING_BACK));
					entity.animation.setDelayTime(150);
				}
			
			} else if (entity.movRight && entity.movUp) {
				if (entity.currentAnimation != Entity.WALKING_SIDE) {
					entity.currentAnimation = Entity.WALKING_SIDE;
					entity.animation.setFrames(entity.sprites.get(Entity.WALKING_SIDE));
					entity.animation.setDelayTime(150);
					entity.facingRight = true;
				}
			
			} else if (entity.movLeft && entity.movDown) {
				if (entity.currentAnimation != Entity.WALKING_SIDE) {
					entity.currentAnimation = Entity.WALKING_SIDE;
					entity.animation.setFrames(entity.sprites.get(Entity.WALKING_SIDE));
					entity.animation.setDelayTime(150);
					entity.facingRight = false;
				}
			
			} else if (entity.movRight && entity.movDown) {
				if (entity.currentAnimation != Entity.WALKING_FRONT) {
					entity.currentAnimation = Entity.WALKING_FRONT;
					entity.animation.setFrames(entity.sprites.get(Entity.WALKING_FRONT));
					entity.animation.setDelayTime(150);
				}
			
			} else if (entity.movUp) {
				entity.facingRight = true;
				if (entity.currentAnimation != Entity.WALKING_PERSPECTIVE_BACK) {
					entity.currentAnimation = Entity.WALKING_PERSPECTIVE_BACK;
					entity.animation.setFrames(entity.sprites
							.get(Entity.WALKING_PERSPECTIVE_BACK));
					entity.animation.setDelayTime(40);
				}
				
			} else if (entity.movDown) {
				entity.facingRight = false;
				if (entity.currentAnimation != Entity.WALKING_PERSPECTIVE_FRONT) {
					entity.currentAnimation = Entity.WALKING_PERSPECTIVE_FRONT;
					entity.animation.setFrames(entity.sprites
							.get(Entity.WALKING_PERSPECTIVE_FRONT));
					entity.animation.setDelayTime(100);
				}
			
			} else if (entity.movLeft) {
				entity.facingRight = false;
				if (entity.currentAnimation != Entity.WALKING_PERSPECTIVE_BACK) {
					entity.currentAnimation = Entity.WALKING_PERSPECTIVE_BACK;
					entity.animation.setFrames(entity.sprites
							.get(Entity.WALKING_PERSPECTIVE_BACK));
					entity.animation.setDelayTime(150);
				}
				
			} else if (entity.movRight) {
				entity.facingRight = true;
				if (entity.currentAnimation != Entity.WALKING_PERSPECTIVE_FRONT) {
					entity.currentAnimation = Entity.WALKING_PERSPECTIVE_FRONT;
					entity.animation.setFrames(entity.sprites
							.get(Entity.WALKING_PERSPECTIVE_FRONT));
					entity.animation.setDelayTime(150);
				}
				
			} else {
				if (entity.currentAnimation != Entity.IDLE) {
					entity.currentAnimation = Entity.IDLE;
					entity.animation.setFrames(entity.sprites
							.get(Entity.IDLE));
					entity.animation.setDelayTime(1000);
				}
			}
			
			entity.animation.update();
		}
	}

}

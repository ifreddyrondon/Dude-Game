package com.spantons.object;

import com.spantons.Interfaces.IUpdateable;
import com.spantons.tileMap.TileMap;

public class UpdateObjectWeapon implements IUpdateable {

	private static final int IDLE = 0;
	private static final int LOADING = 1;
	private static final int ATTACKING = 2;

	private Object object;
	private UpdateObjectMobile updateMobile;
	
	/****************************************************************************************/
	public UpdateObjectWeapon(TileMap _tileMap, Object _object) {
		object = _object;
		updateMobile = new UpdateObjectMobile(_tileMap, object);
	}
	
	/****************************************************************************************/
	@Override
	public void update() {
		
		if(object.carrier != null){
			if (object.carrier.isAttack()) {
				if (object.currentAnimation != ATTACKING) {
					object.currentAnimation = ATTACKING;
					object.animation.setFrames(object.sprites.get(ATTACKING));
					object.animation.setDelayTime(50);
				}
			} else {
				if (object.currentAnimation != LOADING) {
					object.currentAnimation = LOADING;
					object.animation.setFrames(object.sprites.get(LOADING));
					object.animation.setDelayTime(1000);
				}
			}
		}
		else {
			if (object.currentAnimation != IDLE) {
				object.currentAnimation = IDLE;
				object.animation.setFrames(object.sprites.get(IDLE));
				object.animation.setDelayTime(1000);
			}
		}
	
		updateMobile.update();
		object.animation.update();
	}

}

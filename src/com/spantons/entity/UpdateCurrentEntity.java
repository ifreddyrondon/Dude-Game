package com.spantons.entity;

import com.spantons.Interfaces.IUpdateable;
import com.spantons.magicNumbers.SoundPath;
import com.spantons.singleton.SoundCache;

public class UpdateCurrentEntity implements IUpdateable {

	private Entity entity;
	private boolean flinching;
	private long flinchingTime;
	
	/****************************************************************************************/
	public UpdateCurrentEntity(Entity _entity) {
		entity = _entity;
	}
	
	/****************************************************************************************/
	@Override
	public void update() {
		entity.updateAnimation.update();
		entity.decreasePerversity();
		entity.characterClose = EntityUtils.checkIsCloseToAnotherEntity(entity, entity.stage);
		entity.checkIsRecoveringFromAttack();
		
		if (entity.attack) {
			if (entity.object == null)
				SoundCache.getInstance().getSound(SoundPath.SFX_PUNCH).play();
			else
				SoundCache.getInstance().getSound(SoundPath.SFX_PUNCH_WITH_OBJECT).play();
			entity.attack();
		} 

		if (flinching) {
			long elapsedTime = (System.nanoTime() - flinchingTime) / 1000000;
			if (elapsedTime > entity.moveSpeed)
				flinching = false;

		} else {
			entity.getNextPosition();
			if (entity.nextPositionInMap != entity.oldPositionInMap) {
				if (EntityUtils.checkTileCollision(entity, entity.tileMap)) {
					if (EntityUtils.checkCharactersCollision(entity, entity.stage)) {
						if(EntityUtils.checkDoors(entity, entity.stage)){
							magicWalk();
							entity.entitysToDraw[entity.xMap][entity.yMap] = null;
							entity.xMap = entity.getMapPositionOfCharacter().x;
							entity.yMap = entity.getMapPositionOfCharacter().y;
							entity.entitysToDraw[entity.xMap][entity.yMap] = entity;
							entity.oldPositionInMap = entity.nextPositionInMap;
						}
					}
				}
			}
			
			flinching = true;
			flinchingTime = System.nanoTime();
		}
		
	}
	
	/****************************************************************************************/
	private void magicWalk() {

		if (	entity.tileMap.getX() <= entity.tileMap.getXMin()
			|| entity.tileMap.getX() >= entity.tileMap.getXMax()
			|| entity.tileMap.getY() <= entity.tileMap.getYMin()
			|| entity.tileMap.getY() >= entity.tileMap.getYMax()) {

			if ((entity.tileMap.getX() == entity.tileMap.getXMin() && entity.x > entity.tileMap.RESOLUTION_WIDTH_FIX / 2)
					|| (entity.tileMap.getX() == entity.tileMap.getXMax() && entity.x < entity.tileMap.RESOLUTION_WIDTH_FIX / 2)
					|| (entity.tileMap.getY() == entity.tileMap.getYMin() && entity.y > entity.tileMap.RESOLUTION_HEIGHT_FIX / 2)
					|| (entity.tileMap.getY() == entity.tileMap.getYMax() && entity.y < entity.tileMap.RESOLUTION_HEIGHT_FIX / 2)) {
				
				entity.setPosition(entity.tileMap.RESOLUTION_WIDTH_FIX / 2,
						entity.tileMap.RESOLUTION_HEIGHT_FIX / 2);
				entity.tileMap.setPosition(entity.nextMapPosition.x,
						entity.nextMapPosition.y);
			} else {
				if (entity.x < entity.tileMap.tileSize.x
						|| entity.x > entity.tileMap.RESOLUTION_WIDTH_FIX
								- entity.tileMap.tileSize.x) {

					entity.setPosition(entity.tileMap.RESOLUTION_WIDTH_FIX / 2, entity.y);
					entity.tileMap.setPosition(entity.nextMapPosition.x,
							entity.nextMapPosition.y);
				} else if (entity.y < entity.tileMap.tileSize.y
						|| entity.y > entity.tileMap.RESOLUTION_HEIGHT_FIX
								- entity.tileMap.tileSize.y * 2) {

					entity.setPosition(entity.x, entity.tileMap.RESOLUTION_HEIGHT_FIX / 2);
					entity.tileMap.setPosition(entity.nextMapPosition.x,
							entity.nextMapPosition.y);
				} else
					entity.setMapPosition(entity.nextPositionInMap.x,
							entity.nextPositionInMap.y);
			}
		} 
		else {
			entity.setPosition(entity.tileMap.RESOLUTION_WIDTH_FIX / 2,
					entity.tileMap.RESOLUTION_HEIGHT_FIX / 2);
			entity.tileMap.setPosition(entity.nextMapPosition.x, entity.nextMapPosition.y);
		}
	}
	
}

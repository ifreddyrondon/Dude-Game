package com.spantons.entity;

import com.spantons.Interfaces.IUpdateable;
import com.spantons.magicNumbers.XMLPath;

public class UpdateIdleEntity implements IUpdateable {
	
	private Entity entity;
	
	/****************************************************************************************/
	public UpdateIdleEntity(Entity _entity) {
		entity = _entity;
	}

	/****************************************************************************************/	
	@Override
	public void update() {
		increasePerversity();
		EntityUtils.checkIsVisible(entity, entity.tileMap);
		if (entity.visible) 
			EntityUtils.checkIsRecoveringFromAttack(entity);
		entity.setMapPosition(entity.xMap, entity.yMap);
	}
	
	/****************************************************************************************/
	private void increasePerversity() {
		if (entity.flinchingIncreasePerversity) {
			long elapsedTime = (System.nanoTime() - entity.flinchingIncreaseTimePerversity) / 1000000;
			if (elapsedTime > entity.flinchingIncreaseDeltaTimePerversity)
				entity.flinchingIncreasePerversity = false;

		} else {
			if (entity.perversity >= entity.maxPerversity) {
				entity.perversity = entity.maxPerversity;
				jasonTransform();
			} else
				entity.perversity = entity.perversity + 1;

			entity.flinchingIncreasePerversity = true;
			entity.flinchingIncreaseTimePerversity = System.nanoTime();
		}
	}
	
	/****************************************************************************************/
	private void jasonTransform() {
		entity.stage.getCharacters().remove(entity);
		entity.stage.getJasons().add(ParseXMLEntity.getEntityFromXML(XMLPath.XML_CHARACTER_JASON, entity.stage, entity.xMap, entity.yMap));
	}

}

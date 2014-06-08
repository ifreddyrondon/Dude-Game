package com.spantons.entity;

import com.spantons.Interfaces.IUpdateable;

public class UpdateIdleEntity implements IUpdateable {
	
	private Entity entity;
	
	/****************************************************************************************/
	public UpdateIdleEntity(Entity _entity) {
		entity = _entity;
	}

	/****************************************************************************************/	
	@Override
	public void update() {
		entity.increasePerversity();
		EntityUtils.checkIsVisible(entity, entity.tileMap);
		if (entity.visible) 
			entity.checkIsRecoveringFromAttack();
		entity.setMapPosition(entity.xMap, entity.yMap);
	}

}

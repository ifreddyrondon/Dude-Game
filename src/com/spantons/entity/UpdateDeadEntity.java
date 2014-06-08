package com.spantons.entity;

import com.spantons.Interfaces.IUpdateable;

public class UpdateDeadEntity implements IUpdateable {

	private Entity entity;
	
	/****************************************************************************************/
	public UpdateDeadEntity(Entity _entity) {
		entity = _entity;
	}

	/****************************************************************************************/	
	@Override
	public void update() {
		entity.setMapPosition(entity.xMap, entity.yMap);
		
	}

}

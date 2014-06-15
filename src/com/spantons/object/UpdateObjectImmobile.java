package com.spantons.object;

import java.awt.Point;

import com.spantons.Interfaces.IUpdateable;
import com.spantons.stagesLevel.StagesLevel;
import com.spantons.utilities.PositionUtil;

public class UpdateObjectImmobile implements IUpdateable{

	private StagesLevel stage;
	private Object object;
	
	/****************************************************************************************/
	public UpdateObjectImmobile(StagesLevel _stage, Object _object) {
		stage = _stage;
		object = _object;
	}
	
	/****************************************************************************************/
	@Override
	public void update() {
		Point absolutePosition = PositionUtil.calculatePositionToDraw(stage.getTileMap(), object.xMap, object.yMap);
		object.x = absolutePosition.x;
		object.y = absolutePosition.y;
	}

}

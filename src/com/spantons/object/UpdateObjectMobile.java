package com.spantons.object;

import java.awt.Point;

import com.spantons.Interfaces.IUpdateable;
import com.spantons.stagesLevel.StagesLevel;
import com.spantons.utilities.PositionUtil;

public class UpdateObjectMobile implements IUpdateable{

	private StagesLevel stage;
	private Object object;
	
	/****************************************************************************************/
	public UpdateObjectMobile(StagesLevel _stage, Object _object) {
		stage = _stage;
		object = _object;
	}
	
	/****************************************************************************************/
	@Override
	public void update() {
		if (object.carrier != null) {
			Point aux = object.carrier.getMapPositionOfCharacter();
			object.xMap = aux.x;
			object.yMap = aux.y;
		}	
		
		Point absolutePosition = PositionUtil.calculatePositionToDraw(stage.getTileMap(), object.xMap, object.yMap);
		object.x = absolutePosition.x;
		object.y = absolutePosition.y;
	}

	
}

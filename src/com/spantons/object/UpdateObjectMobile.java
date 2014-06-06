package com.spantons.object;

import java.awt.Point;

import com.spantons.Interfaces.IUpdateable;
import com.spantons.tileMap.TileMap;
import com.spantons.utilities.PositionUtil;

public class UpdateObjectMobile implements IUpdateable{

	private TileMap tileMap;
	private Object object;
	
	/****************************************************************************************/
	public UpdateObjectMobile(TileMap _tileMap, Object _object) {
		tileMap = _tileMap;
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
		
		Point absolutePosition = PositionUtil.calculatePositionToDraw(tileMap, object.xMap, object.yMap);
		object.x = absolutePosition.x;
		object.y = absolutePosition.y;
	}

	
}

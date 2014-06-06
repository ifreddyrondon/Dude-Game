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
		if (object.getCarrier() != null) {
			Point aux = object.getCarrier().getMapPositionOfCharacter();
			object.setxMap(aux.x);
			object.setyMap(aux.y);
		}	
		
		Point absolutePosition = PositionUtil.calculatePositionToDraw(tileMap, object.getxMap(), object.getyMap());
		object.setX(absolutePosition.x);
		object.setY(absolutePosition.y);
	}

	
}

package com.spantons.object;

import java.awt.Point;

import com.spantons.Interfaces.IUpdateable;
import com.spantons.tileMap.TileMap;
import com.spantons.utilities.PositionUtil;

public class UpdateObjectImmobile implements IUpdateable{

	private TileMap tileMap;
	private Object object;
	
	/****************************************************************************************/
	public UpdateObjectImmobile(TileMap _tileMap, Object _object) {
		tileMap = _tileMap;
		object = _object;
	}
	
	/****************************************************************************************/
	@Override
	public void update() {
		Point absolutePosition = PositionUtil.calculatePositionToDraw(tileMap, object.xMap, object.yMap);
		object.x = absolutePosition.x;
		object.y = absolutePosition.y;
	}

}

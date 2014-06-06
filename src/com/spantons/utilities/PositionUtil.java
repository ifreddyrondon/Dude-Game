package com.spantons.utilities;

import java.awt.Point;

import com.spantons.tileMap.TileMap;

public class PositionUtil {

	public static Point calculatePositionToDraw(TileMap _tileMap, int _xMap, int _yMap) {
		Point absolutePosition = _tileMap.mapToAbsolute(_xMap, _yMap);
		return new Point(absolutePosition.x - _tileMap.getX(), absolutePosition.y - _tileMap.getY());
	}
	
}

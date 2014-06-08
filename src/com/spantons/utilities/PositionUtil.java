package com.spantons.utilities;

import java.awt.Point;

import com.spantons.tileMap.TileMap;

public class PositionUtil {

	public static Point calculatePositionToDraw(TileMap _tileMap, int _xMap, int _yMap) {
		Point absolutePosition = _tileMap.mapToAbsolute(_xMap, _yMap);
		return new Point(absolutePosition.x - _tileMap.getX(), absolutePosition.y - _tileMap.getY());
	}
	
	/****************************************************************************************/
	public static Point getMapPosition(int _x, int _y, TileMap _tileMap) {
		_x = _x + _tileMap.getX();
		_y = _y + _tileMap.getY();
		return _tileMap.absoluteToMap(_x, _y);
	}
	
	/****************************************************************************************/
	public static Point getAbsolutePosition(int _x, int _y, TileMap _tileMap) {
		Point absolutePosition = _tileMap.mapToAbsolute(_x, _y);
		absolutePosition.x = absolutePosition.x - _tileMap.getX();
		absolutePosition.y = absolutePosition.y - _tileMap.getY();
		return absolutePosition;
	}
	
}

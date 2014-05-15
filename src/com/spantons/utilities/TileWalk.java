package com.spantons.utilities;

import java.awt.Point;
import java.util.Random;

public class TileWalk {

	private static Random randomGenerator;
	
	public static Point walkTo(String direction, Point _coor, int _steps) {
		
		Point out = new Point();
		
		if (direction.equals("non") || _steps == 0)
			out.setLocation(_coor.x, _coor.y);
		
		else if (direction.equals("NW"))
			out.setLocation(_coor.x - _steps, _coor.y);
		
		else if (direction.equals("NE"))
			out.setLocation(_coor.x, _coor.y - _steps);
		
		else if (direction.equals("SE"))
			out.setLocation(_coor.x + _steps, _coor.y);
		
		else if (direction.equals("SW"))
			out.setLocation(_coor.x, _coor.y + _steps);
		
		else if (direction.equals("N"))
			out.setLocation(_coor.x - _steps, _coor.y - _steps);
		
		else if (direction.equals("E"))
			out.setLocation(_coor.x + _steps, _coor.y - _steps);
		
		else if (direction.equals("S"))
			out.setLocation(_coor.x + _steps, _coor.y + _steps);
		
		else if (direction.equals("W"))
			out.setLocation(_coor.x - _steps, _coor.y + _steps);
		
		else 
			out.setLocation(_coor.x, _coor.y);
		
		return out;		
	}
	
	public static String randomMov(){
		randomGenerator = new Random();
		switch (randomGenerator.nextInt((8 - 1) + 1) + 1) {
		case 1:
			return "NW";
						
		case 2:
			return "NE";
		
		case 3:
			return "SE";
		
		case 4:
			return "SW";
		
		case 5:
			return "N";
		
		case 6:
			return "E";
		
		case 7:
			return "S";
		
		case 8:
			return "W";

		default:
			return "non";
		}
	}
	
}

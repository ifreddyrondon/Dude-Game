package utilities;

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
	
	public static Point randomMov(Point _coor, int _steps){
		randomGenerator = new Random();
		Point mov = null;
		switch (randomGenerator.nextInt(8)) {
		case 1:
			mov = TileWalk.walkTo("NW", _coor,_steps);
			break;
			
		case 2:
			mov = TileWalk.walkTo("NE", _coor,_steps);
			break;
		
		case 3:
			mov = TileWalk.walkTo("SE", _coor,_steps);
			break;
		
		case 4:
			mov = TileWalk.walkTo("SW", _coor,_steps);
			break;
		
		case 5:
			mov = TileWalk.walkTo("N", _coor,_steps);
			break;
		
		case 6:
			mov = TileWalk.walkTo("E", _coor,_steps);
			break;
		
		case 7:
			mov = TileWalk.walkTo("S", _coor,_steps);
			break;
		
		case 8:
			mov = TileWalk.walkTo("W", _coor,_steps);
			break;

		default:
			mov = TileWalk.walkTo("non", _coor,_steps);
			break;
		}
		return mov;
	}
	
}

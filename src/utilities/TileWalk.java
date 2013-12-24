package utilities;

import java.awt.geom.Point2D;

public class TileWalk {

	public static Point2D.Double walkTo(String direction, Point2D.Double coor, double steps) {
		
		Point2D.Double out = new Point2D.Double();
		
		if (direction.equals("non") || steps == 0)
			out.setLocation(coor.x, coor.y);
		
		else if (direction.equals("NW"))
			out.setLocation(coor.x - steps, coor.y);
		
		else if (direction.equals("NE"))
			out.setLocation(coor.x, coor.y - steps);
		
		else if (direction.equals("SE"))
			out.setLocation(coor.x + steps, coor.y);
		
		else if (direction.equals("SW"))
			out.setLocation(coor.x, coor.y + steps);
		
		else if (direction.equals("N"))
			out.setLocation(coor.x - steps, coor.y - steps);
		
		else if (direction.equals("E"))
			out.setLocation(coor.x + steps, coor.y - steps);
		
		else if (direction.equals("S"))
			out.setLocation(coor.x + steps, coor.y + steps);
		
		else if (direction.equals("W"))
			out.setLocation(coor.x - steps, coor.y + steps);
		
		else 
			out.setLocation(coor.x, coor.y);
		
		//if (out.x < 0 || out.y < 0) 
			//out.setLocation(coor.x, coor.y);
		
		return out;		
	}

}

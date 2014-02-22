package utilities;

import java.awt.Point;

public class Multiple {

	public static int findNumberCloserTo(int number, int multipleOf) {
		
		if (multipleOf == 0)
			return 0;
		
		if (number % multipleOf == 0) 
			return number;
		else
			return ((int) (number / multipleOf)) * multipleOf;		
	}

	public static Point findPointCloserTo(Point point, Point pointMultiple) {

		int x = findNumberCloserTo((int) point.x, (int) pointMultiple.x);
		int y = findNumberCloserTo((int) point.y, (int) pointMultiple.y);
		
		return new Point(x,y);
	}

}

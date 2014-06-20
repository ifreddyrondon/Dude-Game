package com.spantons.stagesLevel;

import java.awt.Point;
import java.util.ArrayList;

import com.spantons.entity.Entity;

public class CheckTransparentWallsLvl1Stage1 implements ICheckTransparentWalls{

	private ArrayList<Point> pointsToEnableTransparentWalls;
	private ArrayList<Point> pointsToDisableTransparentWalls;
	private boolean flag;
	
	/****************************************************************************************/
	public CheckTransparentWallsLvl1Stage1(ArrayList<Point> _enable, ArrayList<Point> _disable) {
		pointsToEnableTransparentWalls = _enable;
		pointsToDisableTransparentWalls = _disable;
	}
	
	/****************************************************************************************/
	@Override
	public boolean checkTransparent(Entity _currentCharacter) {
		
		for (Point position : pointsToEnableTransparentWalls) {
			if (_currentCharacter.getNextPositionInMap().equals(position)) 
				flag = true;
		}
		
		for (Point position : pointsToDisableTransparentWalls) {
			if (_currentCharacter.getNextPositionInMap().equals(position)) 
				flag = false;
		}

		if (flag) 
			return true;
		else 
			return false;
	}

	
}

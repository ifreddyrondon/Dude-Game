package com.spantons.stagesLevel;

import java.awt.Point;

import com.spantons.entity.Entity;

public class CheckTransparentWallsLvl1Stage1 implements ICheckTransparentWalls{

	private Point[] pointsToEnableTransparentWalls;
	private Point[] pointsToDisableTransparentWalls;
	private boolean flag;
	
	/****************************************************************************************/
	public CheckTransparentWallsLvl1Stage1(Point[] _enable, Point[] _disable) {
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

package com.spantons.stagesLevel;

import java.awt.Point;

public class GoalsLevelWithAllCharacters implements ILevelGoals {

	private StagesLevel stage;
	private int nextLevel;

	/****************************************************************************************/
	public GoalsLevelWithAllCharacters(StagesLevel _stage, int _nextLevel) {
		stage = _stage;
		nextLevel = _nextLevel;
	}

	/****************************************************************************************/
	@Override
	public void checkGoals() {
		for (Point point : stage.exitPoints) {
			if (stage.currentCharacter.getMapPositionOfCharacter().equals(point)) 
				StagesLevelUtils.finishLevel(stage, StagesLevelUtils.END_LEVEL_WITH_ALL_CHARACTERS, nextLevel);
		}
	}

}

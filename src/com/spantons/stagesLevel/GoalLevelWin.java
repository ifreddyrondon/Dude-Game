package com.spantons.stagesLevel;


public class GoalLevelWin implements ILevelGoals {
	
	private StagesLevel stage;
	private int nextLevel;
	
	/****************************************************************************************/
	public GoalLevelWin(StagesLevel _stage, int _nextLevel) {
		stage = _stage;
		nextLevel = _nextLevel;
	}

	/****************************************************************************************/
	@Override
	public void checkGoals() {
		if (stage.enemies.size() < 1) 
			StagesLevelUtils.finishLevel(stage, StagesLevelUtils.END_LEVEL_WIN, nextLevel);
	}
	
}

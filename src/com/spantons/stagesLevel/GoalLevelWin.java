package com.spantons.stagesLevel;

import com.spantons.singleton.SoundCache;

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
		if (stage.enemies.size() < 1) {
			SoundCache.getInstance().stopAllSound();
			stage.currentCharacter.setAllMov(false);
      		stage.currentCharacter.setAllMov(false);
      		stage.gsm.setStage(nextLevel);
		}
	}
	
}

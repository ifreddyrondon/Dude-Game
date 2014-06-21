package com.spantons.stagesLevel;

import com.spantons.singleton.SoundCache;

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
		if (stage.currentCharacter.getMapPositionOfCharacter().equals(stage.exitPoint)) {
			SoundCache.getInstance().stopAllSound();
			stage.currentCharacter.setAllMov(false);
			stage.gsm.setCurrentCharacter(stage.currentCharacter);
			stage.gsm.setCharacters(stage.characters);
			stage.gsm.setStage(nextLevel);
		}
	}

}

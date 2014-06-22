package com.spantons.stagesLevel;

import java.awt.Point;

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
		for (Point point : stage.exitPoints) {
			if (stage.currentCharacter.getMapPositionOfCharacter().equals(point)) {
				SoundCache.getInstance().stopAllSound();
				stage.currentCharacter.setAllMov(false);
				stage.gsm.setCurrentCharacter(stage.currentCharacter);
				stage.gsm.setCharacters(stage.characters);
				stage.gsm.setStage(nextLevel);
			}
		}
	}

}

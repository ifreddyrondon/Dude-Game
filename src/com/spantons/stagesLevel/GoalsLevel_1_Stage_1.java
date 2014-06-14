package com.spantons.stagesLevel;

import com.spantons.singleton.SoundCache;
import com.spantons.stages.GameStagesManager;

public class GoalsLevel_1_Stage_1 implements ILevelGoals {

	private StagesLevel stage;

	/****************************************************************************************/
	public GoalsLevel_1_Stage_1(StagesLevel _stage) {
		stage = _stage;
	}

	/****************************************************************************************/
	@Override
	public void checkGoals() {
		if (stage.currentCharacter.getMapPositionOfCharacter().equals(stage.exitPoint)) {
			SoundCache.getInstance().stopAllSound();
			stage.currentCharacter.setAllMov(false);
			stage.gsm.setCurrentCharacter(stage.currentCharacter);
			stage.gsm.setCharacters(stage.characters);
			stage.gsm.setStage(GameStagesManager.LEVEL_1_STAGE_2);
		}
	}

}

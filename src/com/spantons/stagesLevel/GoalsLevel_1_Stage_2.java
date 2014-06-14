package com.spantons.stagesLevel;

import java.util.ArrayList;

import com.spantons.entity.Entity;
import com.spantons.singleton.SoundCache;
import com.spantons.stages.GameStagesManager;

public class GoalsLevel_1_Stage_2 implements ILevelGoals {

	private StagesLevel stage;

	/****************************************************************************************/
	public GoalsLevel_1_Stage_2(StagesLevel _stage) {
		stage = _stage;
	}

	/****************************************************************************************/
	@Override
	public void checkGoals() {
		if (stage.currentCharacter.getMapPositionOfCharacter().equals(stage.exitPoint)) {
			SoundCache.getInstance().stopAllSound();
			stage.currentCharacter.setAllMov(false);
			stage.gsm.setCurrentCharacter(stage.currentCharacter);
      		ArrayList<Entity> charactersNotBusy = new ArrayList<Entity>();
      		for (Entity entity : stage.characters) {
				if (!entity.isBusy()) 
					charactersNotBusy.add(entity);
			}
      		stage.gsm.setCharacters(charactersNotBusy);
      		stage.currentCharacter.setAllMov(false);
      		stage.gsm.setStage(GameStagesManager.LEVEL_1_STAGE_3);
		}
	}
	
}

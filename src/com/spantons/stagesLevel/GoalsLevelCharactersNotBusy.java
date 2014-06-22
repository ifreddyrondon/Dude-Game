package com.spantons.stagesLevel;

import java.awt.Point;
import java.util.ArrayList;

import com.spantons.entity.Entity;
import com.spantons.singleton.SoundCache;

public class GoalsLevelCharactersNotBusy implements ILevelGoals {

	private StagesLevel stage;
	private int nextLevel;
	
	/****************************************************************************************/
	public GoalsLevelCharactersNotBusy(StagesLevel _stage, int _nextLevel) {
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
	      		ArrayList<Entity> charactersNotBusy = new ArrayList<Entity>();
	      		for (Entity entity : stage.characters) {
					if (!entity.isBusy()) 
						charactersNotBusy.add(entity);
				}
	      		stage.gsm.setCharacters(charactersNotBusy);
	      		stage.currentCharacter.setAllMov(false);
	      		stage.gsm.setStage(nextLevel);
			}
		}
	}
	
}

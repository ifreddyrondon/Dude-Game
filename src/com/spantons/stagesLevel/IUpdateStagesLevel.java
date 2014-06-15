package com.spantons.stagesLevel;

import com.spantons.Interfaces.IUpdateable;
import com.spantons.entity.Entity;
import com.spantons.object.Object;
import com.spantons.stages.GameStagesManager;

public class IUpdateStagesLevel implements IUpdateable {

	private StagesLevel stage;
	
	/****************************************************************************************/
	public IUpdateStagesLevel(StagesLevel _stage) {
		stage = _stage;
	}

	/****************************************************************************************/
	@Override
	public void update() {
		if (stage.goals != null) 
			stage.goals.checkGoals();
		
		if (stage.characters.isEmpty() && stage.currentCharacter.isDead()) 
			stage.gsm.setStage(GameStagesManager.GAME_OVER_STAGE);

		if (stage.currentCharacter.isDead()) 
			stage.currentCharacter = stage.nextCharacter.selectNextCharacter();
		
		stage.currentCharacter.update();
		
		if (stage.checkTransparentWalls != null) {
			if (stage.checkTransparentWalls.checkTransparent(stage.currentCharacter)) 
				stage.transformTransparentWalls.transformToTransparentWalls();
			else
				stage.transformTransparentWalls.transformToOriginalWalls();
		}
		
		if (stage.dialogues != null) 
			stage.dialogues.update();
		
		if (stage.characters.size() > 0) {
			for (Entity character : stage.characters)
				character.update();
		}
		
		if (stage.enemies.size() > 0) {
			for (Entity jason : stage.enemies) 
				jason.update();
		}
		
		if (stage.objects.size() > 0) {
			for (Object object : stage.objects) 
				object.update();
		}
		
		if (stage.doors.size() > 0) {
		      for(Door door : stage.doors) 
		      	door.update();
		}
		
		if (stage.dead.size() > 0) {
			for (Entity _dead : stage.dead)
				_dead.update();
		}
	}
	
}

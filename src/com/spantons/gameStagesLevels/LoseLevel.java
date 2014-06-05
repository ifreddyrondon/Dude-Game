package com.spantons.gameStagesLevels;

import com.spantons.gameStages.GameStagesManager;
import com.spantons.gameState.interfaces.IEndStage;
import com.spantons.singleton.SoundCache;

public class LoseLevel implements IEndStage {

	private GameStagesManager gsm;
	
	/****************************************************************************************/
	public LoseLevel(GameStagesManager _gsm) {
		gsm = _gsm;
	}
	
	/****************************************************************************************/
	@Override
	public void endStage() {
		SoundCache.getInstance().stopAllSound();
		gsm.setStage(GameStagesManager.GAME_OVER_STAGE);
	}

}

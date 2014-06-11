package com.spantons.stagesMenu;

import com.spantons.singleton.SoundCache;
import com.spantons.stages.GameStagesManager;

public class SelectMainMenu implements ISelectAction{

	private GameStagesManager gsm;
	
	/****************************************************************************************/
	public SelectMainMenu(GameStagesManager _gsm) {
		gsm = _gsm;
	}
	
	/****************************************************************************************/
	@Override
	public void select(int _currentChoice) {
		if (_currentChoice == 0) 
			gsm.setStage(GameStagesManager.LEVEL_1_STAGE_1);
		else if (_currentChoice == 1) 
			gsm.setStage(GameStagesManager.HELP_STAGE);
		else if (_currentChoice == 2) {
			SoundCache.getInstance().closeAllSound();
			System.exit(0);
		}
	}

}

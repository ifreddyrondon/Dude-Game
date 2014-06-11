package com.spantons.stagesMenu;

import com.spantons.stages.GameStagesManager;

public class SelectHelpMenu implements ISelectAction {
	
	private GameStagesManager gsm;
	
	/****************************************************************************************/
	public SelectHelpMenu(GameStagesManager _gsm) {
		gsm = _gsm;
	}
	
	/****************************************************************************************/
	@Override
	public void select(int _currentChoice) {
		if (_currentChoice == 0)
			gsm.setStage(GameStagesManager.MENU_STAGE);
	}

}

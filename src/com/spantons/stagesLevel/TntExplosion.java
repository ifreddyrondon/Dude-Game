package com.spantons.stagesLevel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import com.spantons.magicNumbers.SoundPath;
import com.spantons.object.Object;
import com.spantons.singleton.SoundCache;

public class TntExplosion implements ITimeOut {

	private StagesLevel stage;
	private Timer lightsDeploy;
	
	/****************************************************************************************/
	public TntExplosion(StagesLevel _stage) {
		stage = _stage;
	}
	
	/****************************************************************************************/
	@Override
	public void timeOut() {
		stage.tileMap.turnLights();
		lightsDeploy = new Timer(2200, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				stage.tileMap.turnLights();
				lightsDeploy.stop();
			}
		});
		lightsDeploy.start();
		
		SoundCache.getInstance().getSound(SoundPath.SFX_TNT_EXPLOSION).play();
		
		// Si esta en la zona segura
		for (Object object : stage.objects) {
			if (object.getDescription().equals("Tnt")) {
				object.setCarrier(null);
				stage.getTileMap().setObjectToDraw(object.getXMap(), object.getYMap(), null);
			}
		}
		
	}

}

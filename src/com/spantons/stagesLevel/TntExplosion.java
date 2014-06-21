package com.spantons.stagesLevel;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import com.spantons.entity.Entity;
import com.spantons.entity.EntityUtils;
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
	@SuppressWarnings("unchecked")
	@Override
	public void timeOut() {
		SoundCache.getInstance().getSound(SoundPath.SFX_TNT_EXPLOSION).play();
		stage.tileMap.turnLights();
		lightsDeploy = new Timer(2200, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				stage.tileMap.turnLights();
				lightsDeploy.stop();
			}
		});
		lightsDeploy.start();
		
		for (Object object : stage.objects) {
			if (object.getDescription().equals("Tnt")) {
				object.setCarrier(null);
				stage.getTileMap().setObjectToDraw(object.getXMap(), object.getYMap(), null);
			}
		}
		
		if (stage.saveZone != null) {
			boolean alive = false;
			ArrayList<Entity> aux = null;
			
			if (stage.enemies.size() > 0) {
				aux = (ArrayList<Entity>) stage.enemies.clone();
				for (Entity enemy : aux) {
					alive = false;
					for (Point point : stage.saveZone) {
						if (enemy.getMapPositionOfCharacter().equals(point)) {
							alive = true;
							break;
						}
					}
					if (!alive)
						EntityUtils.killCharacter(enemy);
				}
			}
			
			if (stage.characters.size() > 0) {
				aux = (ArrayList<Entity>) stage.characters.clone();
				for (Entity character : aux) {
					alive = false;
					for (Point point : stage.saveZone) {
						if (character.getMapPositionOfCharacter().equals(point)) {
							alive = true;
							break;
						}
					}
					if (!alive) 
						EntityUtils.killCharacter(character);
				}
			}
			
			alive = false;
			for (Point point : stage.saveZone) {
				if (stage.currentCharacter.getMapPositionOfCharacter().equals(point)) {
					alive = true;
					break;
				}
			}
			if (!alive) 
				EntityUtils.killCharacter(stage.currentCharacter);
		}
		
		
	}

}

package com.spantons.stagesLevel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import com.spantons.entity.Entity;
import com.spantons.entity.ParseXMLEntity;
import com.spantons.magicNumbers.SoundPath;
import com.spantons.magicNumbers.XMLPath;
import com.spantons.singleton.SoundCache;

public class ReleaseEnemiesLevels implements ITimeOut {

	private StagesLevel stage;
	private Timer lightsDeploy;
	
	/****************************************************************************************/
	public ReleaseEnemiesLevels(StagesLevel _stage) {
		stage = _stage;
	}
	
	/****************************************************************************************/
	@Override
	public void timeOut() {
		stage.tileMap.turnLights();
		lightsDeploy = new Timer(1200, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				stage.tileMap.turnLights();
				lightsDeploy.stop();
			}
		});
		lightsDeploy.start();
		
		SoundCache.getInstance().getSound(SoundPath.SFX_ZOMBIE_COME_HERE).play();
		stage.currentCharacter.setFlinchingIncreaseDeltaTimePerversity(250);
		for (Entity character : stage.characters) 
			character.setFlinchingIncreaseDeltaTimePerversity(250);
		
		ArrayList<Entity> aux = new ArrayList<Entity>();
		
		for (Entity jason : stage.enemies) 
			aux.add(ParseXMLEntity.getEntityFromXML(XMLPath.XML_CHARACTER_JASON, stage, jason.getXMap(), jason.getYMap()));
		
		stage.enemies.addAll(aux);
		
	}

}

package com.spantons.stagesLevel;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import com.spantons.entity.Entity;
import com.spantons.entity.EntityUtils;
import com.spantons.entity.ParseXMLEntity;
import com.spantons.magicNumbers.SoundPath;
import com.spantons.magicNumbers.XMLPath;
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
		SoundCache.getInstance().getSound("/music/TickTock.mp3").stop();
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
		
		if (!stage.characters.isEmpty() || !stage.currentCharacter.isDead()) {
			for (Object object : stage.objects) {
				if (object.getDescription().equals("Tnt")) {
					boolean deleteWall = false;
					ArrayList<Point> placeToPutTnt = new ArrayList<Point>();
					placeToPutTnt.add(new Point(10,15));
					placeToPutTnt.add(new Point(10,16));
					placeToPutTnt.add(new Point(10,17));
					placeToPutTnt.add(new Point(10,18));
					placeToPutTnt.add(new Point(10,19));
					placeToPutTnt.add(new Point(10,20));
					Point objectPosition = new Point(object.getXMap(), object.getYMap());
					for (Point point : placeToPutTnt) {
						if (objectPosition.equals(point)) 
							deleteWall = true;
					}
					
					if (deleteWall) {
						for (Point point : stage.wallsToTransform) 
							stage.tileMap.setWallToDraw(point.x, point.y, 0);
						
						stage.tileMap.setWallToDraw(stage.wallsToTransform.get(0).x,stage.wallsToTransform.get(0).y,3);
						stage.tileMap.setWallToDraw(
								stage.wallsToTransform.get(stage.wallsToTransform.size() - 1).x,
								stage.wallsToTransform.get(stage.wallsToTransform.size() - 1).y,
								4);
					} else {
						for (int i = 0; i < 20; i++) 
							stage.enemies.add(ParseXMLEntity.getEntityFromXML(XMLPath.XML_CHARACTER_JASON, stage, 18, 18));
					}
				}
				if (object.getCarrier() == null) {
					object.setCarrier(null);
					stage.getTileMap().setObjectToDraw(object.getXMap(), object.getYMap(), null);
				}	
			}
		}
		
		
		
	}

}

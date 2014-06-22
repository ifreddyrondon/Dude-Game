package com.spantons.stagesLevel;

import java.awt.Point;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.spantons.Interfaces.IDrawable;
import com.spantons.Interfaces.IUpdateable;
import com.spantons.dialogue.DialogueManager;
import com.spantons.dialogue.DialogueStage1;
import com.spantons.entity.Entity;
import com.spantons.entity.Hud;
import com.spantons.entity.ParseXMLEntity;
import com.spantons.object.Object;
import com.spantons.object.ParseXMLObject;
import com.spantons.singleton.SoundCache;
import com.spantons.stages.GameStagesManager;
import com.spantons.stages.IStage;
import com.spantons.tileMap.TileMap;
import com.spantons.utilities.RandomItemArrayList;

public class ParseXMLStageLevel {

	public static IStage getStageFromXML(String _path, GameStagesManager _gsm){
		try {
			File file = new File(_path);
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document document = builder.parse(file);
			document.getDocumentElement().normalize();
		 
			if (document.hasChildNodes()) 
				return createStage(document.getChildNodes(),_gsm);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/****************************************************************************************/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static IStage createStage(NodeList childNodes, GameStagesManager _gsm) throws DOMException, ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
		 
		if(childNodes != null && childNodes.getLength() > 0) {
			for (int i = 0; i < childNodes.getLength(); i++) {
				 
				Node nNode = childNodes.item(i);
		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		 
					Element eElement = (Element) nNode;
					StagesLevel aux = new StagesLevel(_gsm);
					
					if(eElement.getElementsByTagName("TileMap").item(0) != null){
						aux.tileMap = new TileMap(eElement.getElementsByTagName("TileMap").item(0).getTextContent());
						if(eElement.getElementsByTagName("NumberTransparentTile").item(0) != null)
							aux.tileMap.setNumberTransparentTile(Integer.parseInt(eElement.getElementsByTagName("NumberTransparentTile").item(0).getTextContent()));
					}
					
					if(eElement.getElementsByTagName("WallPoint").item(0) != null){
						NodeList wallList = eElement.getElementsByTagName("WallPoint");
						if (wallList.getLength() > 0) {
							aux.wallsToTransform = new ArrayList<Point>();
							for (int i1 = 0; i1 < wallList.getLength(); i1++) {
								Node wallNode = wallList.item(i1);
								if (wallNode.getNodeType() == Node.ELEMENT_NODE) {
									Element wallElement = (Element) wallNode;
									String pointString[] = wallElement.getTextContent().split(",");
									aux.wallsToTransform.add(new Point(Integer.parseInt(pointString[0]),Integer.parseInt(pointString[1])));
								}
							}
						}
					}
					
					if(eElement.getElementsByTagName("Countdown").item(0) != null)
						aux.countdown = Integer.parseInt(eElement.getElementsByTagName("Countdown").item(0).getTextContent());
					
					if(eElement.getElementsByTagName("ExitPoint").item(0) != null) {
						String pointString[] = eElement.getElementsByTagName("ExitPoint").item(0).getTextContent().split(",");
						aux.exitPoint = new Point(Integer.parseInt(pointString[0]),Integer.parseInt(pointString[1]));
					}
						
					if(eElement.getElementsByTagName("CurrentCharacter").item(0) != null) {
						Node currentCharacterNode = eElement.getElementsByTagName("CurrentCharacter").item(0);
						if (currentCharacterNode.getNodeType() == Node.ELEMENT_NODE) {
							Element currentCharacterElement = (Element) currentCharacterNode;
							String pointCharacter[] = currentCharacterElement.getElementsByTagName("Point").item(0).getTextContent().split(",");
							if (currentCharacterElement.getElementsByTagName("Path").item(0).getTextContent().equals("GET")) {
								aux.currentCharacter = _gsm.getCurrentCharacter();
								aux.currentCharacter.respawn(aux, Integer.parseInt(pointCharacter[0]), Integer.parseInt(pointCharacter[1]));
								aux.characters = _gsm.getCharacters();
								if (aux.characters.size() > 0){
									if(currentCharacterElement.getElementsByTagName("DeleteRandomCharacter").item(0) != null) {
										if (Boolean.parseBoolean(currentCharacterElement.getElementsByTagName("DeleteRandomCharacter").item(0).getTextContent())) 
											aux.characters.remove(
													RandomItemArrayList.getRandomItemFromArrayList(aux.characters));
									}
									int x = aux.getCurrentCharacter().getXMap() - 1;
									int y = aux.getCurrentCharacter().getYMap();
									for (Entity entity : aux.characters) {
										entity.respawn(aux, x, y);
										x = x - 1;
									}
								}
							}
							else {
								aux.currentCharacter = 
										ParseXMLEntity.getEntityFromXML(
												currentCharacterElement.getElementsByTagName("Path").item(0).getTextContent(), 
												aux, 
												Integer.parseInt(pointCharacter[0]), 
												Integer.parseInt(pointCharacter[1]));
							}
						}
					}
					
					if (eElement.getElementsByTagName("Character").item(0) != null) {
						NodeList charactersList = eElement.getElementsByTagName("Character");
						if (charactersList.getLength() > 0) {
							for (int j = 0; j < charactersList.getLength(); j++) {
								Node characterNode = charactersList.item(j);
								if (characterNode.getNodeType() == Node.ELEMENT_NODE) {
									Element characterElement = (Element) characterNode;
									String pointCharacter[] = characterElement.getElementsByTagName("Point").item(0).getTextContent().split(",");
									aux.characters.add(
											ParseXMLEntity.getEntityFromXML(
													characterElement.getElementsByTagName("Path").item(0).getTextContent(), 
													aux, 
													Integer.parseInt(pointCharacter[0]), 
													Integer.parseInt(pointCharacter[1])));
								}
							}
						}
					}
					
					if (eElement.getElementsByTagName("Enemy").item(0) != null) {
						NodeList enemiesList = eElement.getElementsByTagName("Enemy");
						if (enemiesList.getLength() > 0) {
							for (int j = 0; j < enemiesList.getLength(); j++) {
								Node enemyNode = enemiesList.item(j);
								if (enemyNode.getNodeType() == Node.ELEMENT_NODE) {
									Element enemyElement = (Element) enemyNode;
									String pointCharacter[] = enemyElement.getElementsByTagName("Point").item(0).getTextContent().split(",");
									aux.enemies.add(
											ParseXMLEntity.getEntityFromXML(
													enemyElement.getElementsByTagName("Path").item(0).getTextContent(), 
													aux, 
													Integer.parseInt(pointCharacter[0]), 
													Integer.parseInt(pointCharacter[1])));
								}
							}
						}
					}
					
					if(eElement.getElementsByTagName("DoorsWithKey").item(0) != null) {
						Node doorWithKeyNode = eElement.getElementsByTagName("DoorsWithKey").item(0);
						if (doorWithKeyNode.getNodeType() == Node.ELEMENT_NODE) {
							Element doorWithKeyElement = (Element) doorWithKeyNode;
							String pointDoor[] = doorWithKeyElement.getElementsByTagName("Point").item(0).getTextContent().split(",");
							int animation = 0;
							if (doorWithKeyElement.getElementsByTagName("Animation").item(0).getTextContent().equals("ANIMATION_CLOSE_A")) 
								animation = Door.ANIMATION_CLOSE_A;
							else if (doorWithKeyElement.getElementsByTagName("Animation").item(0).getTextContent().equals("ANIMATION_CLOSE_B")) 
								animation = Door.ANIMATION_CLOSE_B;
							else if (doorWithKeyElement.getElementsByTagName("Animation").item(0).getTextContent().equals("ANIMATION_OPEN_A")) 
								animation = Door.ANIMATION_OPEN_A;
							else if (doorWithKeyElement.getElementsByTagName("Animation").item(0).getTextContent().equals("ANIMATION_OPEN_B")) 
								animation = Door.ANIMATION_OPEN_B;
							
							Door doorWithKey = 
								new Door(aux, 
										Integer.parseInt(pointDoor[0]),
										Integer.parseInt(pointDoor[1]), 
										animation, 
										Boolean.parseBoolean(doorWithKeyElement.getElementsByTagName("Open").item(0).getTextContent()), 
										Boolean.parseBoolean(doorWithKeyElement.getElementsByTagName("Unlock").item(0).getTextContent()));
							
							Node objectKeyNode = eElement.getElementsByTagName("ObjectKey").item(0);
							if (objectKeyNode.getNodeType() == Node.ELEMENT_NODE) {
								Element objectKeyElement = (Element) objectKeyNode;
								String pointObject[] = objectKeyElement.getElementsByTagName("Point").item(0).getTextContent().split(",");
								Object objectkey = ParseXMLObject.getObjectFromXML(
										objectKeyElement.getElementsByTagName("Path").item(0).getTextContent(),
										aux, 
										Integer.parseInt(pointObject[0]), 
										Integer.parseInt(pointObject[1]));
								
								doorWithKey.setKey(objectkey);
								aux.doors.add(doorWithKey);
								aux.objects.add(objectkey);
							}
						}
					}
					
					if (eElement.getElementsByTagName("Door").item(0) != null) {
						NodeList doorsList = eElement.getElementsByTagName("Door");
						if (doorsList.getLength() > 0) {
							for (int j = 0; j < doorsList.getLength(); j++) {
								Node doorNode = doorsList.item(j);
								if (doorNode.getNodeType() == Node.ELEMENT_NODE) {
									Element doorElement = (Element) doorNode;
									String pointDoor[] = doorElement.getElementsByTagName("Point").item(0).getTextContent().split(",");
									int animation = 0;
									if (doorElement.getElementsByTagName("Animation").item(0).getTextContent().equals("ANIMATION_CLOSE_A")) 
										animation = Door.ANIMATION_CLOSE_A;
									else if (doorElement.getElementsByTagName("Animation").item(0).getTextContent().equals("ANIMATION_CLOSE_B")) 
										animation = Door.ANIMATION_CLOSE_B;
									else if (doorElement.getElementsByTagName("Animation").item(0).getTextContent().equals("ANIMATION_OPEN_A")) 
										animation = Door.ANIMATION_OPEN_A;
									else if (doorElement.getElementsByTagName("Animation").item(0).getTextContent().equals("ANIMATION_OPEN_B")) 
										animation = Door.ANIMATION_OPEN_B;
									aux.doors.add(new Door(
											aux, 
											Integer.parseInt(pointDoor[0]), 
											Integer.parseInt(pointDoor[1]), 
											animation,
											Boolean.parseBoolean(doorElement.getElementsByTagName("Open").item(0).getTextContent()), 
											Boolean.parseBoolean(doorElement.getElementsByTagName("Unlock").item(0).getTextContent())));
								}
							}
						}
					}
					
					if (eElement.getElementsByTagName("Object").item(0) != null) {
						NodeList objectsList = eElement.getElementsByTagName("Object");
						if (objectsList.getLength() > 0) {
							for (int j = 0; j < objectsList.getLength(); j++) {
								Node objectNode = objectsList.item(j);
								if (objectNode.getNodeType() == Node.ELEMENT_NODE) {
									Element objectElement = (Element) objectNode;
									String pointObject[] = objectElement.getElementsByTagName("Point").item(0).getTextContent().split(",");
									aux.objects.add(
											ParseXMLObject.getObjectFromXML(
													objectElement.getElementsByTagName("Path").item(0).getTextContent(), 
													aux, 
													Integer.parseInt(pointObject[0]), 
													Integer.parseInt(pointObject[1])));
								}
							}
						}
					}
					
					if (eElement.getElementsByTagName("AWAKENING").item(0) != null) {
						Node dialogueNode = eElement.getElementsByTagName("AWAKENING").item(0);
						if (dialogueNode.getNodeType() == Node.ELEMENT_NODE) {
							Element dialogueElement = (Element) dialogueNode;
							if(dialogueElement.getElementsByTagName("String").item(0) != null){
								NodeList dialogueList = dialogueElement.getElementsByTagName("String");
								if (dialogueList.getLength() > 0) {
									for (int i1 = 0; i1 < dialogueList.getLength(); i1++) {
										Node stringNode = dialogueList.item(i1);
										if (stringNode.getNodeType() == Node.ELEMENT_NODE) {
											Element stringElement = (Element) stringNode;
											aux.stringDialogues.get("AWAKENING").add(stringElement.getTextContent());
										}
									}
								}
							}
						}
					}
					
					if (eElement.getElementsByTagName("STORY").item(0) != null) {
						Node dialogueNode = eElement.getElementsByTagName("STORY").item(0);
						if (dialogueNode.getNodeType() == Node.ELEMENT_NODE) {
							Element dialogueElement = (Element) dialogueNode;
							if(dialogueElement.getElementsByTagName("String").item(0) != null){
								NodeList dialogueList = dialogueElement.getElementsByTagName("String");
								if (dialogueList.getLength() > 0) {
									for (int i1 = 0; i1 < dialogueList.getLength(); i1++) {
										Node stringNode = dialogueList.item(i1);
										if (stringNode.getNodeType() == Node.ELEMENT_NODE) {
											Element stringElement = (Element) stringNode;
											aux.stringDialogues.get("STORY").add(stringElement.getTextContent());
										}
									}
								}
							}
						}
					}
					
					if (eElement.getElementsByTagName("RAMDON").item(0) != null) {
						Node dialogueNode = eElement.getElementsByTagName("RAMDON").item(0);
						if (dialogueNode.getNodeType() == Node.ELEMENT_NODE) {
							Element dialogueElement = (Element) dialogueNode;
							if(dialogueElement.getElementsByTagName("String").item(0) != null){
								NodeList dialogueList = dialogueElement.getElementsByTagName("String");
								if (dialogueList.getLength() > 0) {
									for (int i1 = 0; i1 < dialogueList.getLength(); i1++) {
										Node stringNode = dialogueList.item(i1);
										if (stringNode.getNodeType() == Node.ELEMENT_NODE) {
											Element stringElement = (Element) stringNode;
											aux.stringDialogues.get("RAMDON").add(stringElement.getTextContent());
											System.out.println(stringElement.getTextContent());
										}
									}
								}
							}
						}
					}
					
					aux.dialogues = new DialogueStage1(aux);
					
					if (eElement.getElementsByTagName("Update").item(0) != null) {
						Class classObject = Class.forName(
								"com.spantons.stagesLevel." + eElement.getElementsByTagName("Update").item(0).getTextContent());
						Class[] types = {StagesLevel.class};
						Constructor constructor = classObject.getConstructor(types);
						java.lang.Object[] parameters = {aux};
						java.lang.Object instance = constructor.newInstance(parameters);
						aux.update = (IUpdateable) instance;
					}
					
					if (eElement.getElementsByTagName("TransformTransparentWalls").item(0) != null) {
						Class classObject = Class.forName(
								"com.spantons.stagesLevel." + eElement.getElementsByTagName("TransformTransparentWalls").item(0).getTextContent());
						Class[] types = {StagesLevel.class};
						Constructor constructor = classObject.getConstructor(types);
						java.lang.Object[] parameters = {aux};
						java.lang.Object instance = constructor.newInstance(parameters);
						aux.transformTransparentWalls = (ITransformTransparentWalls) instance;
					}
					
					if (eElement.getElementsByTagName("DrawLevel").item(0) != null) {
						Class classObject = Class.forName(
								"com.spantons.stagesLevel." + eElement.getElementsByTagName("DrawLevel").item(0).getTextContent());
						Class[] types = {TileMap.class, Hud.class, DialogueManager.class};
						Constructor constructor = classObject.getConstructor(types);
						java.lang.Object[] parameters = {aux.tileMap, aux.hud, aux.dialogues};
						java.lang.Object instance = constructor.newInstance(parameters);
						aux.drawLevel = (IDrawable) instance;
					}
					
					if (eElement.getElementsByTagName("NextCharacter").item(0) != null) {
						Class classObject = Class.forName(
								"com.spantons.stagesLevel." + eElement.getElementsByTagName("NextCharacter").item(0).getTextContent());
						Class[] types = {ArrayList.class, Entity.class, TileMap.class};
						Constructor constructor = classObject.getConstructor(types);
						java.lang.Object[] parameters = {aux.characters, aux.currentCharacter, aux.tileMap};
						java.lang.Object instance = constructor.newInstance(parameters);
						aux.nextCharacter = (INextCharacter) instance;
					}
					
					if (eElement.getElementsByTagName("Goals").item(0) != null) {
						if (eElement.getElementsByTagName("NextLevel").item(0) != null) {
							int nextLevel = Integer.parseInt(eElement.getElementsByTagName("NextLevel").item(0).getTextContent());
							Class classObject = Class.forName(
									"com.spantons.stagesLevel." + eElement.getElementsByTagName("Goals").item(0).getTextContent());
							Class[] types = {StagesLevel.class, int.class};
							Constructor constructor = classObject.getConstructor(types);
							java.lang.Object[] parameters = {aux, nextLevel};
							java.lang.Object instance = constructor.newInstance(parameters);
							aux.goals = (ILevelGoals) instance;
						}
					}
					
					if (eElement.getElementsByTagName("TimeOut").item(0) != null) {
						Class classObject = Class.forName(
								"com.spantons.stagesLevel." + eElement.getElementsByTagName("TimeOut").item(0).getTextContent());
						Class[] types = {StagesLevel.class};
						Constructor constructor = classObject.getConstructor(types);
						java.lang.Object[] parameters = {aux};
						java.lang.Object instance = constructor.newInstance(parameters);
						aux.timeOut = (ITimeOut) instance;
					}
					
					if(eElement.getElementsByTagName("SaveZone").item(0) != null){
						Node saveZoneNode = eElement.getElementsByTagName("SaveZone").item(0);
						if (saveZoneNode.getNodeType() == Node.ELEMENT_NODE) {
							Element saveZoneElement = (Element) saveZoneNode;
							NodeList saveZonePointList = saveZoneElement.getElementsByTagName("Point");
							if (saveZonePointList.getLength() > 0) {
								aux.saveZone = new ArrayList<Point>();
								for (int i1 = 0; i1 < saveZonePointList.getLength(); i1++) {
									Node pointNode = saveZonePointList.item(i1);
									if (pointNode.getNodeType() == Node.ELEMENT_NODE) {
										Element pointElement = (Element) pointNode;
										String pointString[] = pointElement.getTextContent().split(",");
										aux.saveZone.add(new Point(Integer.parseInt(pointString[0]),Integer.parseInt(pointString[1])));
									}
								}
							}
						}
					}
					
					if(eElement.getElementsByTagName("CheckTransparentWalls").item(0) != null) {
						Node checkTransparentWallsNode = eElement.getElementsByTagName("CheckTransparentWalls").item(0);
						if (checkTransparentWallsNode.getNodeType() == Node.ELEMENT_NODE) {
							Element checkTransparentWallsElement = (Element) checkTransparentWallsNode;
							ArrayList<Point> pointsEnable = new ArrayList<Point>();
							ArrayList<Point> pointsDisable = new ArrayList<Point>();
							if(checkTransparentWallsElement.getElementsByTagName("PointEnable").item(0) != null){
								NodeList pointEnableList = checkTransparentWallsElement.getElementsByTagName("PointEnable");
								if (pointEnableList.getLength() > 0) {
									for (int i1 = 0; i1 < pointEnableList.getLength(); i1++) {
										Node pointNode = pointEnableList.item(i1);
										if (pointNode.getNodeType() == Node.ELEMENT_NODE) {
											Element pointElement = (Element) pointNode;
											String pointTransparent[] = pointElement.getTextContent().split(",");
											pointsEnable.add(new Point(Integer.parseInt(pointTransparent[0]),Integer.parseInt(pointTransparent[1])));
										}
									}
								}
							}
							if(checkTransparentWallsElement.getElementsByTagName("PointDisable").item(0) != null){
								NodeList pointEnableList = checkTransparentWallsElement.getElementsByTagName("PointDisable");
								if (pointEnableList.getLength() > 0) {
									for (int i1 = 0; i1 < pointEnableList.getLength(); i1++) {
										Node pointNode = pointEnableList.item(i1);
										if (pointNode.getNodeType() == Node.ELEMENT_NODE) {
											Element pointElement = (Element) pointNode;
											String pointTransparent[] = pointElement.getTextContent().split(",");
											pointsDisable.add(new Point(Integer.parseInt(pointTransparent[0]),Integer.parseInt(pointTransparent[1])));
										}
									}
								}
							}
							if(checkTransparentWallsElement.getElementsByTagName("Class").item(0) != null) {
								Class classObject = Class.forName(
										"com.spantons.stagesLevel." + eElement.getElementsByTagName("Class").item(0).getTextContent());
								Class[] types = {ArrayList.class,ArrayList.class};
								Constructor constructor = classObject.getConstructor(types);
								java.lang.Object[] parameters = {pointsEnable,pointsDisable};
								java.lang.Object instance = constructor.newInstance(parameters);
								aux.checkTransparentWalls = (ICheckTransparentWalls) instance;
							}
						}
					}
					
					if(eElement.getElementsByTagName("TimeToLightsOn").item(0) != null)
						aux.timerLightsOn = StagesLevelUtils.setTimerLightsOn(
								Integer.parseInt(eElement.getElementsByTagName("TimeToLightsOn").item(0).getTextContent()), 
								aux);
					
					if(eElement.getElementsByTagName("TimeToLightsOff").item(0) != null)
						aux.timerLightsOff = StagesLevelUtils.setTimerLightsOff(
								Integer.parseInt(eElement.getElementsByTagName("TimeToLightsOff").item(0).getTextContent()), 
								aux);
					
					if(eElement.getElementsByTagName("TimeToAwakeDialogues").item(0) != null)
						aux.timerAwakeningDialogues = StagesLevelUtils.setTimerAwakeningDialogues(
								Integer.parseInt(eElement.getElementsByTagName("TimeToAwakeDialogues").item(0).getTextContent()), 
								aux);
					
					aux.startLevel();
					
					if(eElement.getElementsByTagName("Music").item(0) != null)
						SoundCache.getInstance().getSound(eElement.getElementsByTagName("Music").item(0).getTextContent()).loop();
					
					return aux;
				}
			}
		}
		return null;
	}
	
}

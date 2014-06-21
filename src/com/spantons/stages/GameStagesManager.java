package com.spantons.stages;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.spantons.entity.Entity;
import com.spantons.magicNumbers.XMLPath;
import com.spantons.singleton.SoundCache;
import com.spantons.stagesLevel.ParseXMLStageLevel;
import com.spantons.stagesMenu.ParseXMLStageMenu;

public class GameStagesManager {

	private IStage[] gameStages;
	private int currentStage;
	
	private ArrayList<Entity> characters;
	private Entity currentCharacter;

	public static final int NUM_STAGES = 7;
	public static final int MENU_STAGE = 0;
	public static final int LEVEL_1_STAGE_1 = 1;
	public static final int LEVEL_1_STAGE_2 = 2;
	public static final int LEVEL_1_STAGE_3 = 3;
	public static final int LEVEL_1_STAGE_4 = 4;
	public static final int GAME_OVER_STAGE = 5;
	public static final int HELP_STAGE = 6;

	/****************************************************************************************/
	public GameStagesManager() {
		gameStages = new IStage[NUM_STAGES];
		currentStage = LEVEL_1_STAGE_1;
		loadStage(currentStage);
	}

	/****************************************************************************************/
	private void loadStage(int stage) {
		
		SoundCache.getInstance().stopAllSound();
		
		if (stage == MENU_STAGE)
			gameStages[MENU_STAGE] = ParseXMLStageMenu.getStageFromXML(XMLPath.XML_STAGE_MENU_MAIN, this);
		if (stage == LEVEL_1_STAGE_1)
			gameStages[LEVEL_1_STAGE_1] = ParseXMLStageLevel.getStageFromXML(XMLPath.XML_STAGE_LEVEL_1_STAGE_1, this);
		if (stage == LEVEL_1_STAGE_2)
			gameStages[LEVEL_1_STAGE_2] = ParseXMLStageLevel.getStageFromXML(XMLPath.XML_STAGE_LEVEL_1_STAGE_2, this);
		if (stage == LEVEL_1_STAGE_3)
			gameStages[LEVEL_1_STAGE_3] = ParseXMLStageLevel.getStageFromXML(XMLPath.XML_STAGE_LEVEL_1_STAGE_3, this);
		if (stage == LEVEL_1_STAGE_4)
			gameStages[LEVEL_1_STAGE_4] = ParseXMLStageLevel.getStageFromXML(XMLPath.XML_STAGE_LEVEL_1_STAGE_4, this);
		if (stage == GAME_OVER_STAGE)
			gameStages[GAME_OVER_STAGE] = ParseXMLStageMenu.getStageFromXML(XMLPath.XML_STAGE_MENU_GAME_OVER, this);
		if (stage == HELP_STAGE)
			gameStages[HELP_STAGE] = ParseXMLStageMenu.getStageFromXML(XMLPath.XML_STAGE_MENU_HELP, this);
	}

	/****************************************************************************************/
	private void unloadStage(int stage) {
		gameStages[stage] = null;
	}

	/****************************************************************************************/
	public void setStage(int stage) {
		unloadStage(currentStage);
		currentStage = stage;
		loadStage(currentStage);
	}

	/****************************************************************************************/
	// update() y draw() deben ir en un try catch porque tienen que esperar
	// que se termine de cargar el estado actual en el vector de lo contrario
	// estara vacio y dara un null, la carga del estado actual es llamada en
	// un thread diferente del de donde se llama a update y draw por eso no
	// los espera
	public void update() {
		try {
			gameStages[currentStage].update();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/****************************************************************************************/
	public void draw(Graphics2D g) {
		try {
			gameStages[currentStage].draw(g);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/****************************************************************************************/
	public void keyPressed(int k) {
		try {
			gameStages[currentStage].keyPressed(k);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/****************************************************************************************/
	public void keyReleased(int k) {
		try {
			gameStages[currentStage].keyReleased(k);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/****************************************************************************************/
	public ArrayList<Entity> getCharacters() {
		return characters;
	}

	public void setCharacters(ArrayList<Entity> characters) {
		this.characters = characters;
	}

	public Entity getCurrentCharacter() {
		return currentCharacter;
	}

	public void setCurrentCharacter(Entity currentCharacter) {
		this.currentCharacter = currentCharacter;
	}
}

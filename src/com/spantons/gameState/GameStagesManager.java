package com.spantons.gameState;

import java.awt.Graphics2D;

public class GameStagesManager {

	private Stage[] gameStages;
	private int currentStage;

	public static final int NUM_STAGES = 4;
	public static final int MENU_STAGE = 0;
	public static final int LEVEL_1_STAGE = 1;
	public static final int GAME_OVER_STAGE = 2;
	public static final int HELP_STAGE = 3;

	public GameStagesManager() {
		gameStages = new Stage[NUM_STAGES];
		currentStage = MENU_STAGE;
		loadStage(currentStage);
	}

	private void loadStage(int stage) {
		if (stage == MENU_STAGE)
			gameStages[MENU_STAGE] = new MenuStage(this);
		if (stage == LEVEL_1_STAGE)
			gameStages[LEVEL_1_STAGE] = new Level1Stage(this);
		if (stage == GAME_OVER_STAGE)
			gameStages[GAME_OVER_STAGE] = new GameOverStage(this);
		if (stage == HELP_STAGE)
			gameStages[HELP_STAGE] = new HelpStage(this);
	}

	private void unloadStage(int stage) {
		gameStages[stage] = null;
	}

	public void setStage(int stage) {
		unloadStage(currentStage);
		currentStage = stage;
		loadStage(currentStage);
	}

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

	public void draw(Graphics2D g) {
		try {
			gameStages[currentStage].draw(g);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void keyPressed(int k) {
		gameStages[currentStage].keyPressed(k);
	}

	public void keyReleased(int k) {
		gameStages[currentStage].keyReleased(k);
	}
}

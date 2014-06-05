package com.spantons.gameState;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Timer;

import com.spantons.dialogue.DialogueStage1;
import com.spantons.entity.Entity;
import com.spantons.entity.EntityChecks;
import com.spantons.entity.Hud;
import com.spantons.entity.character.Jason;
import com.spantons.object.Door;
import com.spantons.object.Object;
import com.spantons.singleton.SoundCache;
import com.spantons.tileMap.TileMap;

public class Level_1_Stage_3 extends Stage {

	private Hud hud;
	
	private DrawLevel drawLevel;
	private SelectCurrentCharacterLevel nextCharacter;
	private LoseLevel loseLevel;

	private int countdown = 120;
	private Timer timer;

	/****************************************************************************************/
	public Level_1_Stage_3(GameStagesManager _gsm) {
		gsm = _gsm;
		init();
	}

	/****************************************************************************************/
	@Override
	public void init() {
		hud = new Hud(this);
		secondaryMenu = false;
		tileMap = new TileMap("/maps/map_1_3.txt");
		tileMap.setPosition(0, 0);

		characters = new ArrayList<Entity>();
		jasons = new ArrayList<Entity>();
		dead = new ArrayList<Entity>();
		objects = new ArrayList<Object>();
		doors = new HashMap<String, Door>();

		currentCharacter = gsm.getCurrentCharacter();
		currentCharacter.respawn(tileMap, this, 5, 22);
		
		characters = gsm.getCharacters();
		int i = 21;
		for (Entity entity : characters) {
			entity.respawn(tileMap, this, 5, i);
			i--;
		}
		
		jasons.add(new Jason(tileMap, this, 15, 5, 0.10));
		jasons.add(new Jason(tileMap, this, 20, 19, 0.10));
		jasons.add(new Jason(tileMap, this, 32, 24, 0.10));
		jasons.add(new Jason(tileMap, this, 37, 11, 0.10));
		
		dialogues = new DialogueStage1(this);
		
		// Temporizador
		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				countdown--;
				drawLevel.setCountdown(countdown);
				if (countdown == 0) {
					timer.stop();

				}
			}
		});
		timer.start();
		
		
		drawLevel = new DrawLevel(tileMap, hud, dialogues);
		nextCharacter = new SelectCurrentCharacterLevel(characters, currentCharacter, tileMap);
		loseLevel = new LoseLevel(gsm);
	}
	
	/****************************************************************************************/
	@Override
	public void update() {
		
		if (characters.isEmpty() && currentCharacter.isDead()) 
			loseLevel.endStage();
		
		if (currentCharacter.isDead()) 
			currentCharacter = nextCharacter.selectNextCharacter();
		
		currentCharacter.update();
		
		if (dialogues != null) 
			dialogues.update();
		
		if (characters.size() > 0) {
			for (Entity character : characters)
				character.updateOtherCharacters();
		}
		
		if (jasons.size() > 0) {
			for (Entity jason : jasons) 
				jason.update();
		}
	}

	/****************************************************************************************/
	@Override
	public void draw(Graphics2D g) {
		drawLevel.draw(g);
	}

	/****************************************************************************************/
	@Override
	public void endStage() {
		SoundCache.getInstance().stopAllSound();
		gsm.setStage(GameStagesManager.GAME_OVER_STAGE);
	}

	/****************************************************************************************/
	@Override
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_LEFT)
			currentCharacter.setMovLeft(true);
		if (k == KeyEvent.VK_RIGHT)
			currentCharacter.setMovRight(true);
		if (k == KeyEvent.VK_UP)
			currentCharacter.setMovUp(true);
		if (k == KeyEvent.VK_DOWN)
			currentCharacter.setMovDown(true);
		if (k == KeyEvent.VK_TAB) {
			Entity oldCurrentCharacter = currentCharacter;
			currentCharacter = nextCharacter.selectNextCharacter();
			if (currentCharacter == null) {
				currentCharacter = oldCurrentCharacter;
				dialogues.alone();
			}
		}
		if (k == KeyEvent.VK_SPACE)
			currentCharacter.setAttack(true);
		if (k == KeyEvent.VK_ENTER)
			currentCharacter.takeOrLeaveObject();
		if (k == KeyEvent.VK_O)
			EntityChecks.checkIfDoorOpenWithKey(currentCharacter, this);
		if (k == KeyEvent.VK_ESCAPE)
			secondaryMenu = !secondaryMenu;
		if (k == KeyEvent.VK_R && secondaryMenu)
			secondaryMenu = false;
		if (k == KeyEvent.VK_Q && secondaryMenu) {
			SoundCache.getInstance().closeAllSound();
			System.exit(0);
		}
		if (k == KeyEvent.VK_M && secondaryMenu) {
			SoundCache.getInstance().stopAllSound();
			gsm.setStage(GameStagesManager.MENU_STAGE);
		}
	}

	/****************************************************************************************/
	@Override
	public void keyReleased(int k) {
		if (k == KeyEvent.VK_LEFT)
			currentCharacter.setMovLeft(false);
		if (k == KeyEvent.VK_RIGHT)
			currentCharacter.setMovRight(false);
		if (k == KeyEvent.VK_UP)
			currentCharacter.setMovUp(false);
		if (k == KeyEvent.VK_DOWN)
			currentCharacter.setMovDown(false);
		if (k == KeyEvent.VK_SPACE)
			currentCharacter.setAttack(false);
	}

}

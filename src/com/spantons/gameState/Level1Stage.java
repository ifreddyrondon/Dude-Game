package com.spantons.gameState;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.Timer;

import utilities.ToHours;

import com.spantons.audio.AudioPlayer;
import com.spantons.dialogue.DialogueStage1;
import com.spantons.entity.Entity;
import com.spantons.entity.Hud;
import com.spantons.entity.character.DanaScullyXFiles;
import com.spantons.entity.character.GordonFreeman;
import com.spantons.entity.character.LeonTheProfessional‎;
import com.spantons.entity.character.LizSherman;
import com.spantons.entity.character.Preso;
import com.spantons.tileMap.TileMap;
import com.spantons.tileMap.TileSet;

public class Level1Stage extends Stage {

	private TileMap tileMap;
	private Hud hud;
	private AudioPlayer player;
	private DialogueStage1 dialogues;
	
	private int countdown = 180; 
	private Timer timer;

	public Level1Stage(GameStagesManager _gsm) {
		gsm = _gsm;
		init();
	}
	/****************************************************************************************/
	@Override
	public void init() {
		TileSet tileSet = new 
				TileSet("/tilesets/isometric_grass_and_water.png",64, 64, 0, 0);
		hud = new Hud(this);
		secondaryMenu = false;
		tileMap = new TileMap(64, 32, tileSet);
		tileMap.loadMap("/maps/map.txt");
		tileMap.setPosition(0, 0);
		
		// Personajes
		characters = new ArrayList<Entity>();
		jasons = new ArrayList<Entity>();
		
		currentCharacter = new LeonTheProfessional‎(tileMap, this, 25, 25, 0.10);
		currentCharacter.initChief();
		
		characters.add(new Preso(tileMap, this, 15, 25, 0.10));
		characters.add(new GordonFreeman(tileMap, this, 4, 30, 0.10));
		characters.add(new LizSherman(tileMap, this, 30, 4, 0.10));
		characters.add(new DanaScullyXFiles(tileMap, this, 45, 30, 0.10));
		
		// Sonido del juego
		player = new AudioPlayer("/music/terror.wav");
		player.loop();
		
		// Dialogos
		dialogues = new DialogueStage1(this);

		// Temporizador
		timer = new Timer(1000, new ActionListener() { 
			@Override 
			public void actionPerformed(ActionEvent ae) { 
				countdown--; 
				if (countdown == 0) {
					timer.stop();
					deployJason();
				}
			} 
		}); 
		timer.start();
	}
	/****************************************************************************************/
	@Override
	public void endStage() {
		player.close();
		gsm.setStage(GameStagesManager.GAME_OVER_STAGE);
	}
	/****************************************************************************************/
	@Override
	public void update() {
		
		// Actualizar personajes actual
		currentCharacter.update();
		
		dialogues.update();
		
		if (characters.size() > 0) {
			for (int i = 0; i < characters.size(); i++)
				characters.get(i).updateOtherCharacters();
		}
		
		if (jasons.size() > 0) {
			for (int i = 0; i < jasons.size(); i++) 
				jasons.get(i).updateJason();
		}
	}
	/****************************************************************************************/
	@Override
	public void draw(Graphics2D g) {
	
		tileMap.draw(g);
		currentCharacter.draw(g);
		
		for (int i = 0; i < characters.size(); i++)
			characters.get(i).draw(g);
		
		for (int i = 0; i < jasons.size(); i++) 
			jasons.get(i).draw(g);
		
		hud.Draw(g, ToHours.SecondsToHours(countdown));
		
		dialogues.draw(g);
	}
	/****************************************************************************************/
	private void deployJason(){
		currentCharacter.setFlinchingIncreaseDeltaTimePerversity(250);
		
		for (int i = 0; i < characters.size(); i++) 
			characters.get(i).setFlinchingIncreaseDeltaTimePerversity(250);
	}
	/****************************************************************************************/
	@Override
	public void keyPressed(int k) {
		// Mover personajes
		if (k == KeyEvent.VK_LEFT)
			currentCharacter.setMovLeft(true);
		if (k == KeyEvent.VK_RIGHT)
			currentCharacter.setMovRight(true);
		if (k == KeyEvent.VK_UP)
			currentCharacter.setMovUp(true);
		if (k == KeyEvent.VK_DOWN)
			currentCharacter.setMovDown(true);
		if (k == KeyEvent.VK_TAB)
			selectNextCurrentCharacter();
		if (k == KeyEvent.VK_SPACE)
			currentCharacter.setAttack(true);
		if(k == KeyEvent.VK_ESCAPE)
			secondaryMenu = !secondaryMenu;
		if(k == KeyEvent.VK_R && secondaryMenu)
			secondaryMenu = false;
		if(k == KeyEvent.VK_Q && secondaryMenu){
			player.close();
			System.exit(0);
		}
		if(k == KeyEvent.VK_M && secondaryMenu){
			player.close();
			gsm.setStage(GameStagesManager.MENU_STAGE);
		}
	}
	/****************************************************************************************/
	@Override
	public void keyReleased(int k) {
		
		// Mover personajes
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

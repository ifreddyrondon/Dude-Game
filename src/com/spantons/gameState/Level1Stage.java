package com.spantons.gameState;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Timer;

import utilities.ToHours;

import com.spantons.audio.AudioPlayer;
import com.spantons.dialogue.DialogueStage1;
import com.spantons.entity.Entity;
import com.spantons.entity.EntityChecks;
import com.spantons.entity.Hud;
import com.spantons.entity.character.DanaScullyXFiles;
import com.spantons.entity.character.GordonFreeman;
import com.spantons.entity.character.Jason;
import com.spantons.entity.character.LeonTheProfessional;
import com.spantons.entity.character.LizSherman;
import com.spantons.entity.character.Preso;
import com.spantons.object.Alcohol;
import com.spantons.object.Beers;
import com.spantons.object.Door;
import com.spantons.object.Food;
import com.spantons.object.Hammer;
import com.spantons.object.Object;
import com.spantons.object.Crowbar;
import com.spantons.object.PieceOfPizza;
import com.spantons.object.Pipe;
import com.spantons.object.Pizza;
import com.spantons.tileMap.TileMap;

public class Level1Stage extends Stage {

	private Hud hud;
	
	private int countdown = 180; 
	private Timer timer;

	public static int TRANSPARENT_A = 71;
	public static int TRANSPARENT_B = 70;
	public static Point[] A = {new Point(10, 16),new Point(11, 16),new Point(12, 16),new Point(13, 16),new Point(14, 16),new Point(15, 16),new Point(16, 16),
		new Point(17, 16),new Point(18, 16),new Point(19, 16),new Point(20, 16),new Point(22, 16)};
	
	public static Point[] B = {new Point(22, 8),new Point(22, 9),new Point(22, 10),new Point(22, 11),new Point(22, 12),new Point(22, 13),new Point(22, 14),
		new Point(22, 15)};
	
	public Level1Stage(GameStagesManager _gsm) {
		gsm = _gsm;
		init();
	}
	/****************************************************************************************/
	@Override
	public void init() {
		hud = new Hud(this);
		secondaryMenu = false;
		tileMap = new TileMap("/maps/map.txt");
		tileMap.setPosition(0, 0);
		
		characters = new ArrayList<Entity>();
		jasons = new ArrayList<Entity>();
		dead = new ArrayList<Entity>();
		objects = new ArrayList<Object>();
		doors = new HashMap<String, Door>();
		
		currentCharacter = new LeonTheProfessional(tileMap, this, 16, 19, 0.10);
		
		characters.add(new Preso(tileMap, this, 7, 17, 0.10));
		characters.add(new GordonFreeman(tileMap, this, 8, 20, 0.10));
		characters.add(new LizSherman(tileMap, this, 13, 17, 0.10));
		characters.add(new DanaScullyXFiles(tileMap, this, 13, 21, 0.10));
		
		Jason characterDead = new Jason(tileMap, this, 26, 21, 0.10);
		characterDead.update();
		characterDead.setDead(true);
		
		jasons.add(characterDead);
		
		doors.put("panicroom", new Door(
				tileMap, 18,17, 
				Door.ANIMATION_OPEN_A, 
				Door.OPEN, 
				Door.UNLOCK,"panicroom"));
		
		doors.put("exit", new Door(
				tileMap, 21,6, 
				Door.ANIMATION_CLOSE_A, 
				Door.CLOSE, 
				Door.LOCK,"exit"));
		
		doors.put("bathroom", new Door(
				tileMap, 21,16, 
				Door.ANIMATION_OPEN_B, 
				Door.OPEN, 
				Door.UNLOCK,"bathroom"));
		
		doors.put("main", new Door(
				tileMap, 32,28, 
				Door.ANIMATION_CLOSE_B, 
				Door.CLOSE, 
				Door.LOCK,"main"));
		
		objects.add(new Crowbar(tileMap, 10, 12, 0.23, "exit"));
		objects.add(new Hammer(tileMap, 12, 13, 0.15));
		objects.add(new Hammer(tileMap, 14, 18, 0.15));
		objects.add(new Hammer(tileMap, 19, 20, 0.15));
		objects.add(new Alcohol(tileMap, 27, 16));
		objects.add(new Alcohol(tileMap, 17, 11));
		objects.add(new Alcohol(tileMap, 21, 25));
		objects.add(new Beers(tileMap, 28, 10));
		objects.add(new Beers(tileMap, 21, 17));
		objects.add(new Beers(tileMap, 24, 16));
		objects.add(new Pipe(tileMap, 27,19));
		objects.add(new Pipe(tileMap, 17,27));
		objects.add(new Pipe(tileMap, 27,13));
		objects.add(new PieceOfPizza(tileMap, 21,18));
		objects.add(new PieceOfPizza(tileMap, 10,29));
		objects.add(new Pizza(tileMap, 28, 13));
		objects.add(new Pizza(tileMap, 32, 8));
		objects.add(new Food(tileMap, 11,11));
		
		// Sonido del juego
		player = new AudioPlayer("/music/terror.wav");
		//player.loop();
		
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
		
		currentCharacter.update();
		dialogues.update();
		
		if (characters.size() > 0) {
			for (Entity character : characters)
				character.updateOtherCharacters();
		}
		
		if (jasons.size() > 0) {
			for (Entity jason : jasons) 
				jason.update();
		}
		
		if (objects.size() > 0) {
			for (Object object : objects) 
				object.update();
		}
		
		if (doors.size() > 0) {
		      for(String key : doors.keySet())
		      	doors.get(key).update();
		}
		
		if (dead.size() > 0) {
			for (Entity _dead : dead)
				_dead.updateDead();
		}
	}
	/****************************************************************************************/
	@Override
	public void draw(Graphics2D g) {
		tileMap.draw(g);
		hud.Draw(g, ToHours.SecondsToHours(countdown));
		dialogues.draw(g);
	}
	/****************************************************************************************/
	private void deployJason(){
		currentCharacter.setFlinchingIncreaseDeltaTimePerversity(250);
		for (Entity character : characters) 
			character.setFlinchingIncreaseDeltaTimePerversity(250);
		
		ArrayList<Entity> aux = new ArrayList<Entity>();
		
		for (Entity jason : jasons) 
			aux.add(new Jason(tileMap, this, jason.getXMap(), jason.getYMap(), 0.10));
		
		jasons.addAll(aux);
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
		if (k == KeyEvent.VK_ENTER)
			currentCharacter.takeOrLeaveObject();
		if (k == KeyEvent.VK_O)
			EntityChecks.checkIfDoorOpenWithKey(currentCharacter, this);
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

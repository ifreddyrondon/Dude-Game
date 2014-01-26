package com.spantons.gameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.spantons.audio.AudioPlayer;
import com.spantons.entity.Entity;
import com.spantons.entity.character.SteveJobs;
import com.spantons.main.GamePanel;
import com.spantons.tileMap.TileMap;
import com.spantons.tileMap.TileSet;

public class Level1Stage extends Stage {

	private TileMap tileMap;
	private ArrayList<Entity> characters;
	private int currentCharacter;
<<<<<<< HEAD
	private boolean secondaryMenu = false;
	private AudioPlayer player;
=======
	
	//boolean para abrir el menu secundario
		private boolean quit = false;
		//Audio del juego
		private AudioPlayer music1;
>>>>>>> 88e447555dd79227f26fc4022409c65f6c639550

	public Level1Stage(GameStagesManager gsm) {
		this.gsm = gsm;
		init();
	}
	/****************************************************************************************/
	@Override
	public void init() {
		TileSet tileSet = new 
				TileSet("/tilesets/isometric_grass_and_water.png",64, 64, 0, 0); 
		tileMap = new TileMap(64, 32, tileSet);
		tileMap.loadMap("/maps/map.txt");
		
		tileMap.setPosition(
				-GamePanel.RESOLUTION_WIDTH / 2, 
				GamePanel.RESOLUTION_HEIGHT / 2);
		
		
		// Personajes
		characters = new ArrayList<Entity>();
		SteveJobs sj = new SteveJobs(tileMap, 0.15);
		sj.setPosition(tileMap.RESOLUTION_WIDTH_FIX / 2, 
				tileMap.RESOLUTION_HEIGHT_FIX / 2);
		characters.add(sj);
		
		SteveJobs sj2 = new SteveJobs(tileMap, 0.15);
		sj2.setPosition(tileMap.RESOLUTION_WIDTH_FIX / 4, 
				tileMap.RESOLUTION_HEIGHT_FIX / 4);
		characters.add(sj2);
		
		// Personaje actual
		currentCharacter = 0;
		
		//sonido del juego
<<<<<<< HEAD
		player = new AudioPlayer("/music/terror.wav");
		player.loop();
=======
		music1 = new AudioPlayer("/music/terror.wav");
		music1.loop();
>>>>>>> 88e447555dd79227f26fc4022409c65f6c639550
		
	}
	/****************************************************************************************/
	@Override
	public void update() {
		
		// Actualizar personajes actual
		characters.get(currentCharacter).update(characters,currentCharacter);
		
		for (int i = 0; i < characters.size(); i++){
			if (currentCharacter != i)
				characters.get(i).updateOtherCharacters();
		}
	}
	/****************************************************************************************/
	@Override
	public void draw(Graphics2D g) {
		// Dibujar tilemap
		tileMap.draw(g);
		// Dibujar personajes
		for (int i = 0; i < characters.size(); i++)
			characters.get(i).draw(g);
		
<<<<<<< HEAD
		// Menu secundario
		if(secondaryMenu == true){
			g.setColor(Color.BLACK);
			g.drawString("Resume (R)", 
				tileMap.RESOLUTION_WIDTH_FIX / 2, 
				-50 + tileMap.RESOLUTION_HEIGHT_FIX / 2);
			g.drawString("Main Menu (M)", 
				tileMap.RESOLUTION_WIDTH_FIX / 2, 
				tileMap.RESOLUTION_HEIGHT_FIX / 2);
			g.drawString("Quit Game (Q)", 
				tileMap.RESOLUTION_WIDTH_FIX / 2 , 
				50 + tileMap.RESOLUTION_HEIGHT_FIX / 2);
			}
=======
		//Implementacion de Menu Secundario
				if(quit == true){
					g.setColor(Color.BLACK);
					g.drawString("Resume (R)", tileMap.RESOLUTION_WIDTH_FIX / 2, 
							-50 + tileMap.RESOLUTION_HEIGHT_FIX / 2);
					g.drawString("Main Menu (M)", tileMap.RESOLUTION_WIDTH_FIX / 2, 
							tileMap.RESOLUTION_HEIGHT_FIX / 2);
					g.drawString("Quit Game (Q)", tileMap.RESOLUTION_WIDTH_FIX / 2 , 
							50 + tileMap.RESOLUTION_HEIGHT_FIX / 2);
				}
					
>>>>>>> 88e447555dd79227f26fc4022409c65f6c639550
	}
	/****************************************************************************************/
	public void selectNextCurrentCharacter(){
		if (currentCharacter == characters.size() - 1) 
			currentCharacter = 0;
		else 
			currentCharacter++;		
	}
	/****************************************************************************************/
	@Override
	public void keyPressed(int k) {
		// Mover personajes
		if (k == KeyEvent.VK_LEFT)
			characters.get(currentCharacter).setMovLeft(true);
		if (k == KeyEvent.VK_RIGHT)
			characters.get(currentCharacter).setMovRight(true);
		if (k == KeyEvent.VK_UP)
			characters.get(currentCharacter).setMovUp(true);
		if (k == KeyEvent.VK_DOWN)
			characters.get(currentCharacter).setMovDown(true);
		if (k == KeyEvent.VK_SPACE)
			characters.get(currentCharacter).setMovJumping(true);
		if (k == KeyEvent.VK_TAB)
			selectNextCurrentCharacter();
		if(k == KeyEvent.VK_ESCAPE)
<<<<<<< HEAD
			secondaryMenu = true;
		if(k == KeyEvent.VK_R && secondaryMenu)
			secondaryMenu = false;
		if(k == KeyEvent.VK_Q && secondaryMenu)
			System.exit(0);
		if(k == KeyEvent.VK_M && secondaryMenu){
			player.close();
			gsm.setStage(GameStagesManager.MENU_STAGE);
=======
			quit = true;
		if(k == KeyEvent.VK_R)
			quit = false;
		if(k == KeyEvent.VK_Q)
			System.exit(0);
		if(k == KeyEvent.VK_M){
			gsm.setStage(GameStagesManager.MENU_STAGE);
			music1.close();
>>>>>>> 88e447555dd79227f26fc4022409c65f6c639550
		}
	}
	/****************************************************************************************/
	@Override
	public void keyReleased(int k) {
		
		// Mover personajes
		if (k == KeyEvent.VK_LEFT)
			characters.get(currentCharacter).setMovLeft(false);
		if (k == KeyEvent.VK_RIGHT)
			characters.get(currentCharacter).setMovRight(false);
		if (k == KeyEvent.VK_UP)
			characters.get(currentCharacter).setMovUp(false);
		if (k == KeyEvent.VK_DOWN)
			characters.get(currentCharacter).setMovDown(false);
		if (k == KeyEvent.VK_SPACE)
			characters.get(currentCharacter).setMovJumping(false);
	}

}

package com.spantons.gameState;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.spantons.entity.Entity;
import com.spantons.entity.character.SteveJobs;
import com.spantons.tileMap.TileMap;

public class Level1Stage extends Stage {

	private TileMap tileMap;

	// Personajes
	private ArrayList<Entity> characters;
	private int currentCharacter;

	public Level1Stage(GameStagesManager gsm) {
		this.gsm = gsm;
		init();
	}
	/****************************************************************************************/
	@Override
	public void init() {
		tileMap = new TileMap(64, 32);
		tileMap.loadMap("/maps/map.txt");
		
		//System.out.println(-tileMap.getMapHeight()/((Math.sqrt(2)/2)) / 2);
		//tileMap.setPosition(	TileMap.mapToAbsolute(0, 0).x   , 0);
		//tileMap.setPosition(	-tileMap.getMapHeight()/((Math.sqrt(2)/2))  , 0);
		// tileMap.setPosition(-GamePanel.RESOLUTION_WIDTH / 2 - 150, 0);
		// tileMap.setPosition(-GamePanel.RESOLUTION_WIDTH / 2, -100);
		// tileMap.setPosition(-GamePanel.RESOLUTION_WIDTH / 2, 0);
		// tileMap.setPosition(-672, 276);
		// tileMap.setPosition(64, 16);
		//tileMap.setPosition(-4850, 2700);
		
		 
		// Personajes
		characters = new ArrayList<Entity>();
		SteveJobs sj = new SteveJobs(tileMap, 0.2);
		sj.setPosition(170, 360);
		characters.add(sj);
		// Personaje actual
		currentCharacter = 0;
		
	}
	/****************************************************************************************/
	@Override
	public void update() {
		
		// Actualizar personajes
		for (int i = 0; i < characters.size(); i++)
			characters.get(i).update();

		// actualizar mapa
		/*tileMap.setPosition(
				GamePanel.RESOLUTION_WIDTH / 2
						- characters.get(currentCharacter).getX(),
				GamePanel.RESOLUTION_HEIGHT / 2
						- characters.get(currentCharacter).getY());
		*/
		tileMap.update();
	}
	/****************************************************************************************/
	@Override
	public void draw(Graphics2D g) {
		// Dibujar tilemap
		tileMap.draw(g);
		// Dibujar personajes
		for (int i = 0; i < characters.size(); i++)
			characters.get(i).draw(g);
	}
	/****************************************************************************************/
	@Override
	public void keyPressed(int k) {
		//tileMap.keyPressed(k);
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
	}
	/****************************************************************************************/
	@Override
	public void keyReleased(int k) {
		//tileMap.keyReleased(k);
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

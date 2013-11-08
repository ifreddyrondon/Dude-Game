package com.spantons.gameState;

import java.awt.Graphics2D;

import com.spantons.tileMap.TileMap;

public class Level1Stage extends Stage {

	private TileMap tileMap;

	public Level1Stage(GameStagesManager gsm) {
		this.gsm = gsm;
		init();
	}

	@Override
	public void init() {
		tileMap = new TileMap(30, 30);
		tileMap.loadTiles("/tilesets/grasstileset.gif");
		tileMap.setPosition(0, 0);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics2D g) {
		// Dibujar tilemap
		tileMap.draw(g);

	}

	@Override
	public void keyPressed(int k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub

	}

}

package com.spantons.object;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.spantons.tileMap.TileMap;

public class Hammer extends Object{

	private BufferedImage sprite;
	
	public Hammer(TileMap _tileMap, int _xMap, int _yMap) {
		
		tileMap = _tileMap;
		xMap = _xMap;
		yMap = _yMap;
		
		loadSprite();
	}

	private void loadSprite() {
		try {
			sprite = ImageIO.read(getClass()
					.getResourceAsStream("/objects/hammer.png"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

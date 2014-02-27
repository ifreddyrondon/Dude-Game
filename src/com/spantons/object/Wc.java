package com.spantons.object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

import com.spantons.entity.Animation;
import com.spantons.tileMap.TileMap;

public class Wc extends Object{

	private ArrayList<BufferedImage[]> sprites;
	private static final int IDLE = 0;
	
	public Wc(TileMap _tileMap, int _xMap, int _yMap, double _scale) {
		super(_tileMap, _xMap, _yMap);
		scale = _scale;
		
		description = "Poceta";
		type = BLOCKED;
		loadSprite();
		
		animation = new Animation();
		currentAnimation = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelayTime(1000);
	}
	/****************************************************************************************/
	private void loadSprite() {
		try {
			BufferedImage sprite = ImageIO.read(getClass()
					.getResourceAsStream("/objects_sprites/wc.png"));
			
			sprites = new ArrayList<BufferedImage[]>();
			
			spriteWidth = ((int) (sprite.getWidth() * scale));
			spriteHeight = ((int) (sprite.getHeight() * scale));
			
			sprite = Scalr.resize(sprite, (int)(sprite.getWidth() * scale));
			
			BufferedImage[] bi = new BufferedImage[1];
			bi[0] = sprite;
			sprites.add(bi);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/****************************************************************************************/
	public void update() {		
		super.update();
	}
	/****************************************************************************************/
	public void draw(Graphics2D g) {
		super.draw(g);
	}

}

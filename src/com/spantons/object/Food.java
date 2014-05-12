package com.spantons.object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import utilities.ImageCache;

import com.spantons.entity.Animation;
import com.spantons.entity.Entity;
import com.spantons.path.ImagePath;
import com.spantons.tileMap.TileMap;

public class Food extends Object {

	private static final int IDLE = 0;
	private ArrayList<BufferedImage[]> sprites;
	
	public Food(TileMap _tileMap, int _xMap, int _yMap) {
		super(_tileMap, _xMap, _yMap);
		
		description = "Comida";
		type = NON_BLOCKED;
		health = 1f;
		
		loadSprite();
		
		animation = new Animation();
		currentAnimation = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelayTime(1000);
	}
	/****************************************************************************************/
	private void loadSprite() {
		try {
			BufferedImage spriteSheet = ImageCache.getInstance().getImage(ImagePath.OBJECT_FOOD);
			
			spriteWidth = spriteSheet.getWidth();
			spriteHeight = spriteSheet.getHeight();
			
			sprites = new ArrayList<BufferedImage[]>();

			// IDLE
			BufferedImage[] bi = new BufferedImage[1];
			bi[0] = spriteSheet.getSubimage(0, 0, spriteWidth,
					spriteHeight);
			sprites.add(bi);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	/****************************************************************************************/
	@Override
	public void load(Entity _entity) {
		showObject = false;
		carrier.getHealth(this);
		carrier.takeOrLeaveObject();
	}
	/****************************************************************************************/
	@Override
	public void unload(Entity _entity) {
		// TODO Auto-generated method stub
		
	}
	/****************************************************************************************/
	public void update() {
		
		if(carrier == null){
			if (currentAnimation != IDLE) {
				currentAnimation = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelayTime(1000);
			}
		}
		
		super.update();
		animation.update();
	}
	/****************************************************************************************/
	public void draw(Graphics2D g) {
		super.draw(g);
	}

}

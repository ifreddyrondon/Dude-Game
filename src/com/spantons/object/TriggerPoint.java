package com.spantons.object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.spantons.entity.Animation;
import com.spantons.entity.Entity;
import com.spantons.magicNumbers.ImagePath;
import com.spantons.singleton.ImageCache;
import com.spantons.tileMap.TileMap;

public class TriggerPoint extends Object {

	private static final int IDLE = 0;
	private ArrayList<BufferedImage[]> sprites;
	
	/****************************************************************************************/
	public TriggerPoint(TileMap _tileMap, int _xMap, int _yMap) {
		super(_tileMap, _xMap, _yMap);
		
		description = "Punto de activacion";
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
			BufferedImage spriteSheet = ImageCache.getInstance().getImage(ImagePath.OBJECT_TRIGGER_POINT);
			
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
		// TODO Auto-generated method stub
		
	}

	/****************************************************************************************/
	@Override
	public void unload(Entity _entity) {
		// TODO Auto-generated method stub
		
	}
	
	/****************************************************************************************/
	public void update() {
		super.update();
		animation.update();
	}
	
	/****************************************************************************************/
	public void draw(Graphics2D g) {
		super.draw(g);
	}

}

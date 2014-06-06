package com.spantons.objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.spantons.entity.Animation;
import com.spantons.entity.Entity;
import com.spantons.magicNumbers.ImagePath;
import com.spantons.object.DrawObjectImmobile;
import com.spantons.object.Object;
import com.spantons.object.UpdateObjectImmobile;
import com.spantons.singleton.ImageCache;
import com.spantons.tileMap.TileMap;

public class Pipe extends Object {

	private static final int IDLE = 0;
	private ArrayList<BufferedImage[]> sprites;
	
	private UpdateObjectImmobile updateObject;
	private DrawObjectImmobile drawObject;
	
	/****************************************************************************************/
	public Pipe(TileMap _tileMap, int _xMap, int _yMap) {
		super(_tileMap, _xMap, _yMap);
		
		description = "Pipa";
		moveSpeed = -60;
		timeOfHigh = 30000;
		
		loadSprite();
		
		updateObject = new UpdateObjectImmobile(tileMap, this);
		drawObject = new DrawObjectImmobile(this);
		
		animation = new Animation();
		currentAnimation = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelayTime(1000);
	}
	
	/****************************************************************************************/
	private void loadSprite() {
		try {
			BufferedImage spriteSheet = ImageCache.getInstance().getImage(ImagePath.OBJECT_PIPE);
			
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
		carrier.getHigh(this);
		setCarrier(null);
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
		
		updateObject.update();
		animation.update();
	}
	
	/****************************************************************************************/
	public void draw(Graphics2D g) {
		drawObject.draw(g);
	}

}

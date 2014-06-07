package com.spantons.objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.spantons.entity.Animation;
import com.spantons.magicNumbers.ImagePath;
import com.spantons.object.DrawObjectImmobile;
import com.spantons.object.Object;
import com.spantons.object.ObjectAttributeGetHealth;
import com.spantons.object.UpdateObjectImmobile;
import com.spantons.singleton.ImageCache;
import com.spantons.tileMap.TileMap;

public class Pizza extends Object {
	
	private UpdateObjectImmobile updateObject;
	private DrawObjectImmobile drawObject;
	private ObjectAttributeGetHealth attribute;
	
	/****************************************************************************************/
	public Pizza(TileMap _tileMap, int _xMap, int _yMap) {
		super(_tileMap, _xMap, _yMap);
		
		updateObject = new UpdateObjectImmobile(tileMap, this);
		drawObject = new DrawObjectImmobile(this);
		attribute = new ObjectAttributeGetHealth(0.8);
		
		type = CONSUMABLE;
		timeToConsumable = 0;
		description = "Pizza";
		scale = 1;
		
		loadSprite();
		animation = new Animation();
		animation.setFrames(sprites.get(0));
		animation.setDelayTime(1000);	
	}
	
	/****************************************************************************************/
	private void loadSprite() {
		try {
			BufferedImage spriteSheet = ImageCache.getInstance().getImage(ImagePath.OBJECT_PIZZA);
			spriteWidth = spriteSheet.getWidth();
			spriteHeight = spriteSheet.getHeight();
			
			sprites = new ArrayList<BufferedImage[]>();

			BufferedImage[] bi = new BufferedImage[1];
			bi[0] = spriteSheet.getSubimage(
					0, 0, spriteWidth,spriteHeight);
			sprites.add(bi);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/****************************************************************************************/
	public void update() {
		updateObject.update();
	}
	
	/****************************************************************************************/
	public void draw(Graphics2D g) {
		drawObject.draw(g);
	}

	/****************************************************************************************/
	public void actionLoad() {
		attribute.loadAttribute(carrier);
	}

	/****************************************************************************************/
	@Override
	public void actionUnload() {
		attribute.unloadAttribute(carrier);
	}

}

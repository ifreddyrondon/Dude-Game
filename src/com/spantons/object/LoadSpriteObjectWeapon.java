package com.spantons.object;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.imgscalr.Scalr;

import com.spantons.Interfaces.ILoadSprite;
import com.spantons.entity.Animation;
import com.spantons.singleton.ImageCache;

public class LoadSpriteObjectWeapon implements ILoadSprite {
	
	private Object object;
	private static final int IDLE = 0;
	
	/****************************************************************************************/
	public LoadSpriteObjectWeapon(Object _object) {
		object = _object;
	}

	/****************************************************************************************/
	@Override
	public void loadSprite(String _path) {
		try {
			BufferedImage spriteSheet = ImageCache.getInstance().getImage(_path);
			
			object.spriteWidth = ((int) (spriteSheet.getWidth() / 3 * object.scale));
			object.spriteHeight = ((int) (spriteSheet.getHeight() * object.scale));
			
			spriteSheet = Scalr.resize(
					spriteSheet, 
					(int)(spriteSheet.getWidth() * object.scale));
			
			object.sprites = new ArrayList<BufferedImage[]>();

			// IDLE
			BufferedImage[] bi = new BufferedImage[1];
			bi[0] = spriteSheet.getSubimage(
					0, 0, object.spriteWidth,
					object.spriteHeight);
			object.sprites.add(bi);
			
			// LOADING
			bi = new BufferedImage[1];
			bi[0] = spriteSheet.getSubimage(
					object.spriteWidth, 
					0, 
					object.spriteWidth,
					object.spriteHeight);
			object.sprites.add(bi);
			
			// ATTACKING
			bi = new BufferedImage[3];
			bi[0] = spriteSheet.getSubimage(
					object.spriteWidth, 
					0, 
					object.spriteWidth,
					object.spriteHeight);
			bi[1] = spriteSheet.getSubimage(0, 0, 
					object.spriteWidth,
					object.spriteHeight);
			bi[2] = spriteSheet.getSubimage(
					object.spriteWidth * 2, 
					0, 
					object.spriteWidth,
					object.spriteHeight);
			object.sprites.add(bi);
			
			object.animation = new Animation();
			object.currentAnimation = IDLE;
			object.animation.setFrames(object.sprites.get(IDLE));
			object.animation.setDelayTime(1000);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

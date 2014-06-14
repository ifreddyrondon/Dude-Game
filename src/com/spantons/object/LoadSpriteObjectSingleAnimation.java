package com.spantons.object;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.spantons.Interfaces.ILoadSprite;
import com.spantons.entity.Animation;
import com.spantons.singleton.ImageCache;

public class LoadSpriteObjectSingleAnimation implements ILoadSprite {
	
	private Object object;
	
	/****************************************************************************************/
	public LoadSpriteObjectSingleAnimation(Object _object) {
		object = _object;
	}

	/****************************************************************************************/
	@Override
	public void loadSprite(String _path) {
		try {
			BufferedImage spriteSheet = ImageCache.getInstance().getImage(_path);
			object.spriteWidth = spriteSheet.getWidth();
			object.spriteHeight = spriteSheet.getHeight();
			
			object.sprites = new ArrayList<BufferedImage[]>();

			BufferedImage[] bi = new BufferedImage[1];
			bi[0] = spriteSheet.getSubimage(
					0, 
					0, 
					object.spriteWidth,
					object.spriteHeight);
			object.sprites.add(bi);
			
			object.animation = new Animation();
			object.animation.setFrames(object.sprites.get(0));
			object.animation.setDelayTime(1000);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

package com.spantons.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.imgscalr.Scalr;

import com.spantons.singleton.ImageCache;
import com.spantons.stagesLevel.StagesLevels;

public class Enemy extends Entity {
	
	private UpdateEnemy update;

	/****************************************************************************************/
	public Enemy(StagesLevels _stage, int _xMap, int _yMap) {

		super( _stage, _xMap, _yMap);	
	}

	/****************************************************************************************/
	public void loadSprite(String _pathFace, String pathSprite) {
		try {
			face = ImageCache.getInstance().getImage(_pathFace);

			BufferedImage spriteSheet = ImageCache.getInstance().getImage(pathSprite);

			spriteWidth = ((int) (spriteSheet.getWidth() / 3 * scale));
			spriteHeight = ((int) (spriteSheet.getHeight() / 2 * scale));

			spriteSheet = Scalr.resize(spriteSheet,
					(int) (spriteSheet.getWidth() * scale));

			sprites = new ArrayList<BufferedImage[]>();

			// WALKING_FRONT
			BufferedImage[] bi = new BufferedImage[1];
			bi[0] = spriteSheet.getSubimage(0, 0, spriteWidth,
					spriteHeight);
			sprites.add(bi);

			// WALKING_BACK
			bi = new BufferedImage[1];
			bi[0] = spriteSheet.getSubimage(spriteWidth, 0, spriteWidth,
					spriteHeight);
			sprites.add(bi);

			// WALKING_SIDE
			bi = new BufferedImage[1];
			bi[0] = spriteSheet.getSubimage(spriteWidth * 2, 0,
					spriteWidth, spriteHeight);
			sprites.add(bi);

			// WALKING_PERSPECTIVE_FRONT
			bi = new BufferedImage[1];
			bi[0] = spriteSheet.getSubimage(0, spriteHeight, spriteWidth,
					spriteHeight);
			sprites.add(bi);

			// WALKING_PERSPECTIVE_BACK
			bi = new BufferedImage[1];
			bi[0] = spriteSheet.getSubimage(spriteWidth, spriteHeight,
					spriteWidth, spriteHeight);
			sprites.add(bi);

			// DEAD
			bi = new BufferedImage[1];
			bi[0] = spriteSheet.getSubimage(spriteWidth * 2,
					spriteHeight, spriteWidth, spriteHeight);
			sprites.add(bi);
			
			animation = new Animation();
			draw = new DrawEntity(this);
			update = new UpdateEnemy(this);
			updateAnimation = new UpdateAnimationEntity(this);
			updateDead = new UpdateDeadEntity(this);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/****************************************************************************************/
	public void update() {
		if (dead) 
			updateDead.update();
		else
			update.update();
	}
	
	/****************************************************************************************/
	public void draw(Graphics2D g) {
		draw.draw(g);
	}

}

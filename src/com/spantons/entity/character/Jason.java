package com.spantons.entity.character;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.imgscalr.Scalr;

import com.spantons.entity.Animation;
import com.spantons.entity.DrawEntity;
import com.spantons.entity.Entity;
import com.spantons.entity.UpdateAnimationEntity;
import com.spantons.entity.UpdateDeadEntity;
import com.spantons.entity.UpdateEnemy;
import com.spantons.gameStages.StagesLevels;
import com.spantons.magicNumbers.ImagePath;
import com.spantons.singleton.ImageCache;
import com.spantons.tileMap.TileMap;

public class Jason extends Entity {
	
	private UpdateEnemy update;

	/****************************************************************************************/
	public Jason(TileMap _tm, StagesLevels _stage, int _xMap, int _yMap,
			double _scale) {

		super(_tm, _stage, _xMap, _yMap);
		
		scale = _scale;

		visible = true;
		description = "Jason";
		health = 4;
		maxHealth = 4;
		perversity = 0;
		maxPerversity = 100;
		damage = 1.5f;
		damageBackup = damage;
		flinchingIncreaseDeltaTimePerversity = 1000;
		flinchingDecreaseDeltaTimePerversity = 1000;
		deltaForReduceFlinchingIncreaseDeltaTimePerversity = 0;
		dead = false;
		moveSpeed = 120;
		facingRight = true;

		loadSprite();
		animation = new Animation();
		draw = new DrawEntity(this);
		update = new UpdateEnemy(this);
		updateAnimation = new UpdateAnimationEntity(this);
		updateDead = new UpdateDeadEntity(this);
	}

	/****************************************************************************************/
	private void loadSprite() {
		try {
			face = ImageCache.getInstance().getImage(ImagePath.HUD_CHARACTER_JASON);

			BufferedImage spriteSheet = ImageCache.getInstance().getImage(ImagePath.SPRITE_CHARACTER_JASON);

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

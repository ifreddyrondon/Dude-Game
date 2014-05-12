package com.spantons.entity.character;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.imgscalr.Scalr;

import utilities.ImageCache;

import com.spantons.entity.Animation;
import com.spantons.entity.Entity;
import com.spantons.gameState.Stage;
import com.spantons.path.ImagePath;
import com.spantons.tileMap.TileMap;

public class SteveJobs extends Entity {

	/****************************************************************************************/
	public SteveJobs(TileMap _tm, Stage _stage, int _xMap, int _yMap,
			double _scale) {

		super(_tm, _stage, _xMap, _yMap);
		scale = _scale;

		visible = true;
		description = "Steve Jobs";
		health = 5;
		maxHealth = 5;
		perversity = 0;
		maxPerversity = 100;
		damage = 1;
		flinchingIncreaseDeltaTimePerversity = 1000;
		flinchingDecreaseDeltaTimePerversity = 1000;
		deltaForReduceFlinchingIncreaseDeltaTimePerversity = 50;
		dead = false;
		moveSpeed = 110;
		facingRight = true;

		loadSprite();

		animation = new Animation();
		currentAnimation = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelayTime(400);
	}

	/****************************************************************************************/
	private void loadSprite() {
		try {

			face = ImageCache.getInstance().getImage(ImagePath.HUD_CHARACTER_STEVE_JOBS);

			BufferedImage spriteSheet = ImageCache.getInstance().getImage(ImagePath.SPRITE_CHARACTER_STEVE_JOBS);

			spriteWidth = ((int) (spriteSheet.getWidth() / 4 * scale));
			spriteHeight = ((int) (spriteSheet.getHeight() / 2 * scale));

			spriteSheet = Scalr.resize(spriteSheet,
					(int) (spriteSheet.getWidth() * scale),
					(int) (spriteSheet.getHeight() * scale));

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
			bi = new BufferedImage[3];
			bi[0] = spriteSheet.getSubimage(spriteWidth * 2, 0,
					spriteWidth, spriteHeight);
			bi[1] = spriteSheet.getSubimage(spriteWidth * 3, 0,
					spriteWidth, spriteHeight);
			bi[2] = spriteSheet.getSubimage(0, spriteHeight,
					spriteWidth, spriteHeight);
			sprites.add(bi);

			// WALKING_PERSPECTIVE_BACK
			bi = new BufferedImage[1];
			bi[0] = spriteSheet.getSubimage(spriteWidth, spriteHeight,
					spriteWidth, spriteHeight);
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

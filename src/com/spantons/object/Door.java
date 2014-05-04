package com.spantons.object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.spantons.entity.Animation;
import com.spantons.entity.Entity;
import com.spantons.tileMap.TileMap;

public class Door extends Object {

	public static final int CLOSE_A = 0;
	public static final int CLOSE_B = 1;
	public static final int OPEN_A = 2;
	public static final int OPEN_B = 3;
	private ArrayList<BufferedImage[]> sprites;

	public Door(TileMap _tileMap, int _xMap, int _yMap, int _animation) {
		super(_tileMap, _xMap, _yMap);

		description = "Puerta";
		type = BLOCKED;

		loadSprite();

		animation = new Animation();
		currentAnimation = _animation;
		animation.setFrames(sprites.get(_animation));
		animation.setDelayTime(1000);
	}

	/****************************************************************************************/
	private void loadSprite() {
		try {
			BufferedImage spriteSheet = ImageIO.read(getClass()
					.getResourceAsStream("/objects_sprites/Doors.png"));

			spriteWidth = ((int) (spriteSheet.getWidth()));
			spriteHeight = ((int) (spriteSheet.getHeight() / 8));

			sprites = new ArrayList<BufferedImage[]>();

			// CLOSE_A
			BufferedImage[] bi = new BufferedImage[2];
			bi[0] = spriteSheet.getSubimage(0, 0, spriteWidth,
					spriteHeight);
			bi[1] = spriteSheet.getSubimage(0, spriteHeight, spriteWidth,
					spriteHeight);
			sprites.add(bi);

			// CLOSE_B
			bi = new BufferedImage[2];
			bi[0] = spriteSheet.getSubimage(0, spriteHeight * 2,
					spriteWidth, spriteHeight);
			bi[1] = spriteSheet.getSubimage(0, spriteHeight * 3,
					spriteWidth, spriteHeight);
			sprites.add(bi);

			// OPEN_A
			bi = new BufferedImage[2];
			bi[0] = spriteSheet.getSubimage(0, spriteHeight * 4,
					spriteWidth, spriteHeight);
			bi[1] = spriteSheet.getSubimage(0, spriteHeight * 5,
					spriteWidth, spriteHeight);
			sprites.add(bi);

			// OPEN_B
			bi = new BufferedImage[2];
			bi[0] = spriteSheet.getSubimage(0, spriteHeight * 6,
					spriteWidth, spriteHeight);
			bi[1] = spriteSheet.getSubimage(0, spriteHeight * 7,
					spriteWidth, spriteHeight);
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
	}

	/****************************************************************************************/
	public void draw(Graphics2D g) {
		super.draw(g);
	}

}

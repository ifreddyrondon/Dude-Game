package com.spantons.object;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.spantons.entity.Animation;
import com.spantons.entity.Entity;
import com.spantons.magicNumbers.ImagePath;
import com.spantons.singleton.ImageCache;
import com.spantons.tileMap.TileMap;

public class Door extends Object {

	public static final int ANIMATION_CLOSE_A = 0;
	public static final int ANIMATION_OPEN_A = 1;
	public static final int ANIMATION_CLOSE_B = 2;
	public static final int ANIMATION_OPEN_B = 3;
	
	public static final int LOCK = 1;
	public static final int UNLOCK = 2;
	private int statusBlock;
	
	public static final int OPEN = 1;
	public static final int CLOSE = 2;
	private int statusOpen;
	private boolean tryToOpen = false;
	private boolean doorToNextLvl;
	
	private String id;
	
	private ArrayList<BufferedImage[]> sprites;

	public Door(
			TileMap _tileMap, 
			int _xMap, 
			int _yMap, 
			int _animation, 
			int _statusOpen, 
			int _statusBlock,
			String _id,
			boolean _doorToNextLvl) {
		
		super(_tileMap, _xMap, _yMap);

		description = "Puerta";
		id = _id;
		type = BLOCKED;
		statusOpen = _statusOpen;
		statusBlock = _statusBlock;
		doorToNextLvl = _doorToNextLvl;
		
		loadSprite();

		animation = new Animation();
		currentAnimation = _animation;
		animation.setFrames(sprites.get(_animation));
		animation.setDelayTime(1000);
	}

	/****************************************************************************************/
	private void loadSprite() {
		try {
			BufferedImage spriteSheet = ImageCache.getInstance().getImage(ImagePath.OBJECT_DOORS);

			spriteWidth = ((int) (spriteSheet.getWidth()));
			spriteHeight = ((int) (spriteSheet.getHeight() / 4));

			sprites = new ArrayList<BufferedImage[]>();

			// CLOSE_A
			BufferedImage[] bi = new BufferedImage[1];
			bi[0] = spriteSheet.getSubimage(0, 0, spriteWidth,
					spriteHeight);
			sprites.add(bi);

			// CLOSE_B
			bi = new BufferedImage[1];
			bi[0] = spriteSheet.getSubimage(0, spriteHeight,
					spriteWidth, spriteHeight);
			sprites.add(bi);

			// OPEN_A
			bi = new BufferedImage[1];
			bi[0] = spriteSheet.getSubimage(0, spriteHeight * 2,
					spriteWidth, spriteHeight);
			sprites.add(bi);

			// OPEN_B
			bi = new BufferedImage[1];
			bi[0] = spriteSheet.getSubimage(0, spriteHeight * 3,
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

		if (statusOpen == OPEN) {
			if (currentAnimation == ANIMATION_CLOSE_A) {
				currentAnimation = ANIMATION_OPEN_A;
				animation.setFrames(sprites.get(ANIMATION_OPEN_A));
			}
			else if (currentAnimation == ANIMATION_CLOSE_B) {
				currentAnimation = ANIMATION_OPEN_B;
				animation.setFrames(sprites.get(ANIMATION_OPEN_B));
			}
		}
		else if (statusOpen == CLOSE) {
			if (currentAnimation == ANIMATION_OPEN_A) {
				currentAnimation = ANIMATION_CLOSE_A;
				animation.setFrames(sprites.get(ANIMATION_CLOSE_A));
			}
			else if (currentAnimation == ANIMATION_OPEN_B) {
				currentAnimation = ANIMATION_CLOSE_B;
				animation.setFrames(sprites.get(ANIMATION_CLOSE_B));
			}
		}

		super.update();
	}

	/****************************************************************************************/
	public void draw(Graphics2D g) {
		super.draw(g);
	}
	/****************************************************************************************/

	public Point getPositionInMap() {
		return new Point(xMap, yMap);
	}
	
	public int getStatusBlock() {
		return statusBlock;
	}

	public void setStatusBlock(int statusBlock) {
		this.statusBlock = statusBlock;
	}

	public int getStatusOpen() {
		return statusOpen;
	}

	public void setStatusOpen(int statusOpen) {
		this.statusOpen = statusOpen;
	}
	
	public String getId(){
		return id;
	}

	public boolean isTryToOpen() {
		return tryToOpen;
	}

	public void setTryToOpen(boolean tryToOpen) {
		this.tryToOpen = tryToOpen;
	}

	public boolean isDoorToNextLvl() {
		return doorToNextLvl;
	}
	
}

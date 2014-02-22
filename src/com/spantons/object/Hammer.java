package com.spantons.object;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.spantons.entity.Animation;
import com.spantons.tileMap.TileMap;

public class Hammer extends Object{

	private static final int IDLE = 0;
	private static final int LOADING = 1;
	private static final int ATTACKING = 2;
	
	private ArrayList<BufferedImage[]> sprites;
	/****************************************************************************************/
	public Hammer(TileMap _tileMap, int _xMap, int _yMap, double _scale) {
		super();
		tileMap = _tileMap;
		setxMap(_xMap);
		setyMap(_yMap);
		scale = _scale;
		
		description = "Martillo";
		damage = 0.5f;
		
		loadSprite();
		
		animation = new Animation();
		currentAnimation = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelayTime(1000);
	}
	/****************************************************************************************/
	private void loadSprite() {
		try {
			BufferedImage spriteSheet = ImageIO.read(getClass()
					.getResourceAsStream("/objects_sprites/Hammer.png"));
			
			spriteWidth = ((int) (spriteSheet.getWidth() / 3 * scale));
			spriteHeight = ((int) (spriteSheet.getHeight() * scale));
			
			int newWidth = (int) (spriteSheet.getWidth() * scale);
			int newHeight = (int) (spriteSheet.getHeight() * scale);

			BufferedImage spriteSheetNew = new BufferedImage(newWidth,
					newHeight, spriteSheet.getType());
			Graphics2D g = spriteSheetNew.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(spriteSheet, 0, 0, newWidth, newHeight, 0, 0,
			spriteSheet.getWidth(),
			spriteSheet.getHeight(), null);

			sprites = new ArrayList<BufferedImage[]>();

			// IDLE
			BufferedImage[] bi = new BufferedImage[1];
			bi[0] = spriteSheetNew.getSubimage(0, 0, spriteWidth,
					spriteHeight);
			sprites.add(bi);
			
			// LOADING
			bi = new BufferedImage[1];
			bi[0] = spriteSheetNew.getSubimage(spriteWidth, 0, spriteWidth,
					spriteHeight);
			sprites.add(bi);
			
			// ATTACKING
			bi = new BufferedImage[3];
			bi[0] = spriteSheetNew.getSubimage(spriteWidth, 0, spriteWidth,
					spriteHeight);
			bi[1] = spriteSheetNew.getSubimage(0, 0, spriteWidth,
					spriteHeight);
			bi[2] = spriteSheetNew.getSubimage(spriteWidth * 2, 0, spriteWidth,
					spriteHeight);
			sprites.add(bi);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/****************************************************************************************/
	public void update() {
		
		if(carrier != null){
			if (carrier.isAttack()) {
				if (currentAnimation != ATTACKING) {
					currentAnimation = ATTACKING;
					animation.setFrames(sprites.get(ATTACKING));
					animation.setDelayTime(80);
				}
			} else {
				if (currentAnimation != LOADING) {
					currentAnimation = LOADING;
					animation.setFrames(sprites.get(LOADING));
					animation.setDelayTime(1000);
				}
			}
		}
		else {
			if (currentAnimation != IDLE) {
				currentAnimation = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelayTime(1000);
			}
		}
		
		super.update();
		animation.update();
	}
	/****************************************************************************************/
	public void draw(Graphics2D g) {
		super.draw(g);
	}

}

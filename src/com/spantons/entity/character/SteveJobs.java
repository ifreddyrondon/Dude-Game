package com.spantons.entity.character;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.spantons.entity.Animation;
import com.spantons.entity.Entity;
import com.spantons.gameState.Stage;
import com.spantons.tileMap.TileMap;

public class SteveJobs extends Entity {

	// Personaje
	/*
	private boolean recovery;
	private long recoveryTimer;
	*/

	// Animacion
	private ArrayList<BufferedImage[]> sprites;

	// Acciones de la animacion
	private static final int WALKING_BACK = 0;
	private static final int WALKING_FRONT = 1;
	private static final int IDLE = 2;
	private static final int WALKING = 3;
	//private static final int FALLING = 1;
	//private static final int JUMPING = 1;

	/****************************************************************************************/
	public SteveJobs(TileMap _tm, Stage _stage, int _xMap, int _yMap, double _scale) {
		
		super(_tm,_stage,_xMap,_yMap);
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
		moveSpeed = 1;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -4.8;
		reducerJumpSpeed = 0.3;
		facingRight = true;

		loadSprite();
		
		animation = new Animation();
		currentAnimation = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelayTime(1000);
	}
	/****************************************************************************************/
	private void loadSprite() {
		try {

			face = ImageIO.read(getClass().getResourceAsStream("/hud/SteveJobs.png"));
			
			BufferedImage spriteSheet2 = ImageIO.read(getClass()
					.getResourceAsStream("/characteres_sprites/SteveJobs.png"));

			spriteWidth = ((int) (spriteSheet2.getWidth() /4 * scale));
			spriteHeight = ((int) (spriteSheet2.getHeight() /2 * scale));
			
			// Redimencionar SpriteSheet
			int newWidth = (int) (spriteSheet2.getWidth() * scale);
			int newHeight = (int) (spriteSheet2.getHeight() * scale);

			BufferedImage spriteSheet = new BufferedImage(newWidth,
					newHeight, spriteSheet2.getType());
			Graphics2D g = spriteSheet.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(spriteSheet2, 0, 0, newWidth, newHeight, 0, 0,
					spriteSheet2.getWidth(),
					spriteSheet2.getHeight(), null);

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
			bi[0] = spriteSheet.getSubimage(spriteWidth * 2, 0, spriteWidth,
					spriteHeight);
			sprites.add(bi);
			
			// WALKING_PERSPECTIVE_FRONT
			bi = new BufferedImage[1];
			bi[0] = spriteSheet.getSubimage(0, spriteHeight, spriteWidth,
					spriteHeight);
			sprites.add(bi);
			
			// WALKING_PERSPECTIVE_BACK
			bi = new BufferedImage[1];
			bi[0] = spriteSheet.getSubimage(spriteWidth, spriteHeight, spriteWidth,
					spriteHeight);
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
	public void updateAnimation(){

		if (movDown) {
			if (currentAnimation != WALKING_FRONT) {
				currentAnimation = WALKING_FRONT;
				animation.setFrames(sprites.get(WALKING_FRONT));
				animation.setDelayTime(100);
			}
		} else if (movUp) {
			if (currentAnimation != WALKING_BACK) {
				currentAnimation = WALKING_BACK;
				animation.setFrames(sprites.get(WALKING_BACK));
				animation.setDelayTime(40);
			}
		} else if (movLeft || movRight) {
			if (currentAnimation != WALKING) {
				currentAnimation = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelayTime(150);
			}
		} else {
			if (currentAnimation != IDLE) {
				currentAnimation = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelayTime(1000);
			}
		}

		// direccion de la cara del jugador
		if (movRight)
			facingRight = true;
		if (movLeft)
			facingRight = false;

		animation.update();

	}
	/****************************************************************************************/
	public void draw(Graphics2D g) {
		super.draw(g);
	}

}

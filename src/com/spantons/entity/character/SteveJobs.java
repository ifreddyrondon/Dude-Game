package com.spantons.entity.character;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.spantons.entity.Animation;
import com.spantons.entity.Entity;
import com.spantons.tileMap.Background;

public class SteveJobs extends Entity {

	// Personaje
	private int health;
	private int maxHealth;
	private boolean dead;
	private boolean recovery;
	private long recoveryTimer;

	// Animacion
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFramesPerAction = { 3, 4, 1 };

	// Acciones de la animacion
	private static final int WALKING = 0;
	private static final int IDLE = 1;
	private static final int GIVING_BACK = 2;

	public SteveJobs(Background bg) {

		spriteWidth = 283;
		spriteHeight = 400;
		collisionBoxWidth = 290;
		collisionBoxHeight = 400;

		health = maxHealth = 5;
		dead = false;
		
		facingRight = true;

		// Cargar sprites
		try {

			BufferedImage spriteSheet = ImageIO.read(getClass()
					.getResourceAsStream("/sprites/steve_jobs.png"));

			sprites = new ArrayList<BufferedImage[]>();
			for (int i = 0; i < 3; i++) {

				BufferedImage[] bi = new BufferedImage[numFramesPerAction[i]];

				for (int j = 0; j < numFramesPerAction[i]; j++) {
					bi[j] = spriteSheet.getSubimage(j * spriteWidth,
							i * spriteHeight, spriteWidth,
							spriteHeight);
				}

				sprites.add(bi);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		animation = new Animation();
		currentAnimation = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelayTime(400);

	}
	
	public void update(){
		animation.update();
	}
	
	public void draw(Graphics2D g){
		super.draw(g);
	}

}

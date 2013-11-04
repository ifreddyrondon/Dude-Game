package com.spantons.entity.character;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
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

	// Acciones de la animacion
	private static final int GIVING_BACK = 0;
	private static final int IDLE = 1;
	private static final int WALKING = 2;

	public SteveJobs(Background bg) {

		scale = 0.5;

		// Tamano original de cada sprite 320x460, le sumo 10 a cada
		// proporcion por el inner padding de 10 a cada lado de los sprites
		// en el sprite sheep
		spriteWidth = (int) (330 * scale);
		spriteHeight = (int) (470 * scale);
		collisionBoxWidth = (int) (320 * scale);
		collisionBoxHeight = (int) (460 * scale);

		health = maxHealth = 5;
		dead = false;

		facingRight = true;

		// Cargar sprites
		try {

			BufferedImage spriteSheet2 = ImageIO.read(getClass()
					.getResourceAsStream("/sprites/steve_jobs.png"));
			
			// Redimencionar SpriteSheet
			int newWidth = new Double(spriteSheet2.getWidth() * scale).intValue();
			int newHeight = new Double(spriteSheet2.getHeight() * scale).intValue();
			
			BufferedImage spriteSheet = new BufferedImage(newWidth,newHeight, spriteSheet2.getType());
			Graphics2D g = spriteSheet.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(spriteSheet2, 0, 0, newWidth, newHeight, 0, 0,
					spriteSheet2.getWidth(), spriteSheet2.getHeight(), null);


			sprites = new ArrayList<BufferedImage[]>();

			BufferedImage[] bi = new BufferedImage[1];
			bi[0] = spriteSheet.getSubimage(0, 0, spriteWidth,
					spriteHeight);
			sprites.add(bi);

			bi = new BufferedImage[4];
			bi[0] = spriteSheet.getSubimage(spriteWidth, 0, spriteWidth,
					spriteHeight);
			bi[1] = spriteSheet.getSubimage(spriteWidth * 2, 0,
					spriteWidth, spriteHeight);
			bi[2] = spriteSheet.getSubimage(spriteWidth * 3, 0,
					spriteWidth, spriteHeight);
			bi[3] = spriteSheet.getSubimage(0, spriteHeight, spriteWidth,
					spriteHeight);
			sprites.add(bi);

			bi = new BufferedImage[3];
			bi[0] = spriteSheet.getSubimage(spriteWidth, spriteHeight,
					spriteWidth, spriteHeight);
			bi[1] = spriteSheet.getSubimage(spriteWidth * 2,
					spriteHeight, spriteWidth, spriteHeight);
			bi[2] = spriteSheet.getSubimage(spriteWidth * 3,
					spriteHeight, spriteWidth, spriteHeight);
			sprites.add(bi);

		} catch (Exception e) {
			e.printStackTrace();
		}

		animation = new Animation();
		currentAnimation = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelayTime(1000);

	}

	public void update() {

		// direccion de la cara del jugador
		if (movRight)
			facingRight = true;
		if (movLeft)
			facingRight = false;

		animation.update();
	}

	public void draw(Graphics2D g) {
		super.draw(g);
	}

}

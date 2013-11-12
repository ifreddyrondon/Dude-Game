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
	private static final int FALLING = 0;
	private static final int JUMPING = 0;

	
	
	public SteveJobs(Background bg) {

		scale = 0.5;

		// Tamano original de cada sprite 320x460, le sumo 2 a cada
		// proporcion por el inner padding de 10 a cada lado de los sprites
		// en el sprite sheep
		spriteWidth = (int) (322 * scale);
		spriteHeight = (int) (462 * scale);
		collisionBoxWidth = (int) (320 * scale);
		collisionBoxHeight = (int) (460 * scale);

		health = maxHealth = 5;
		dead = false;

		moveSpeed = 0.3;
		maxMoveSpeed = 1.6;
		recuderMoveSpeed = 0.4;
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

	private void loadSprite() {
		try {

			BufferedImage spriteSheet2 = ImageIO.read(getClass()
					.getResourceAsStream("/sprites/SteveJobs.png"));

			// Redimencionar SpriteSheet
			int newWidth = new Double(spriteSheet2.getWidth() * scale)
					.intValue();
			int newHeight = new Double(spriteSheet2.getHeight() * scale)
					.intValue();

			BufferedImage spriteSheet = new BufferedImage(newWidth,
					newHeight, spriteSheet2.getType());
			Graphics2D g = spriteSheet.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(spriteSheet2, 0, 0, newWidth, newHeight, 0, 0,
					spriteSheet2.getWidth(),
					spriteSheet2.getHeight(), null);

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
	}

	private void getNextPosition() {
		// Saltar
		if (movJumping && !movFalling) {
			dy = jumpStart;
			movFalling = true;
		}
		// Caer
		else if (movFalling) {
			// Mientras mas tiempo esta activo movSaltar mas aumenta la
			// velodidad de caida (gravedad)
			dy += fallSpeed;

			// Si dy es positivo quiere decir que ya va cayendo
			if (dy > 0)
				movJumping = false;
			// Si aun sigue subiendo pero ya no esta saltando entonces
			// vamos frenando el salto
			if (dy < 0 && !movJumping)
				dy += reducerJumpSpeed;
			// Si la velocidad de caida alcanza la velocidad maxima a la
			// que puede caer entonces deja de crecer
			if (dy > maxFallSpeed)
				dy = maxFallSpeed;
		}
		// Izquierda
		// Mientras mas tiempo esta activo movIzquiera o derecha mas aumenta
		// la velocidad sin embargo esta limitado por la maxima velocidad de
		// movimiento
		if (movLeft) {
			dx -= moveSpeed;
			if (dx < -maxMoveSpeed)
				dx = -maxMoveSpeed;

			// Derecha
		} else if (movRight) {
			dx += moveSpeed;
			if (dx > maxMoveSpeed)
				dx = maxMoveSpeed;

			// Si no esta oprimiendo ningun boton para alguna accion de
			// movimiento hacia los lados
		} else {
			// Si iba hacia la derecha
			if (dx > 0) {
				// Frenamos
				dx -= recuderMoveSpeed;
				if (dx < 0)
					dx = 0;
				// Si iba hacia la izquierda
			} else if (dx < 0) {
				// Frenamos
				dx += recuderMoveSpeed;
				if (dx > 0) {
					dx = 0;
				}
			}
		}

	}

	public void update() {

		getNextPosition();

		if (dy > 0) {
			if (currentAnimation != FALLING) {
				currentAnimation = FALLING;
				animation.setFrames(sprites.get(FALLING));
				animation.setDelayTime(100);
			}
		} else if (dy < 0) {
			if (currentAnimation != JUMPING) {
				currentAnimation = JUMPING;
				animation.setFrames(sprites.get(JUMPING));
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

	public void draw(Graphics2D g) {
		super.draw(g);
	}

}

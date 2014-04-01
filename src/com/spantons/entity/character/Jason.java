package com.spantons.entity.character;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

import utilities.TileWalk;

import com.spantons.entity.Animation;
import com.spantons.entity.Entity;
import com.spantons.gameState.Stage;
import com.spantons.tileMap.TileMap;

public class Jason extends Entity implements Runnable {

	private String nextDirectionJason;

	/****************************************************************************************/
	public Jason(TileMap _tm, Stage _stage, int _xMap, int _yMap,
			double _scale) {

		super(_tm, _stage, _xMap, _yMap);
		scale = _scale;

		visible = true;
		description = "Jason";
		health = 5;
		maxHealth = 5;
		perversity = 0;
		maxPerversity = 100;
		damage = 1.5f;
		flinchingIncreaseDeltaTimePerversity = 1000;
		flinchingDecreaseDeltaTimePerversity = 1000;
		deltaForReduceFlinchingIncreaseDeltaTimePerversity = 0;
		dead = false;
		moveSpeed = 1;
		facingRight = true;
		nextDirectionJason = TileWalk.randomMov();

		loadSprite();

		animation = new Animation();
		movFace(nextDirectionJason);
	}

	/****************************************************************************************/
	private void loadSprite() {
		try {

			face = ImageIO.read(getClass().getResourceAsStream(
					"/hud/Jason.png"));

			BufferedImage spriteSheet = ImageIO.read(getClass()
					.getResourceAsStream(
							"/characteres_sprites/Jason.png"));

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
	@Override
	public void run() {
		System.out.println("Thread start");

	}

	/****************************************************************************************/
	public void update() {
		checkIsVisible();
		if (visible) {
			checkCharacterIsDead();
			checkIsRecoveringFromAttack();
			updateAnimation();
		}
		characterClose = checkIsCloseToAnotherCharacter();
		if (characterClose != null) {
			attack();
		} else
			movJason();

		setMapPosition(nextPositionInMap.x, nextPositionInMap.y);
	}

	/****************************************************************************************/
	private void movJason() {
		if (flinchingJasonMov) {
			long elapsedTime = (System.nanoTime() - flinchingTimeJasonMov) / 1000000;
			if (elapsedTime > 120)
				flinchingJasonMov = false;
		} else {
			nextPositionInMap = TileWalk.walkTo(nextDirectionJason,
					nextPositionInMap, moveSpeed);

			if (checkTileCollision()) {
				entitysToDraw[xMap][yMap] = null;
				xMap = nextPositionInMap.x;
				yMap = nextPositionInMap.y;
				entitysToDraw[xMap][yMap] = this;
			} else {
				nextDirectionJason = TileWalk.randomMov();
				nextPositionInMap = getMapPositionOfCharacter();
			}
			movFace(nextDirectionJason);
			flinchingJasonMov = true;
			flinchingTimeJasonMov = System.nanoTime();
		}
	}

	/****************************************************************************************/
	private Entity checkIsCloseToAnotherCharacter() {

		Point north = TileWalk.walkTo("N", getMapPositionOfCharacter(), 1);
		Point south = TileWalk.walkTo("S", getMapPositionOfCharacter(), 1);
		Point west = TileWalk.walkTo("W", getMapPositionOfCharacter(), 1);
		Point east = TileWalk.walkTo("E", getMapPositionOfCharacter(), 1);
		Point northWest = TileWalk.walkTo("NW",
				getMapPositionOfCharacter(), 1);
		Point northEast = TileWalk.walkTo("NE",
				getMapPositionOfCharacter(), 1);
		Point southWest = TileWalk.walkTo("SW",
				getMapPositionOfCharacter(), 1);
		Point southEast = TileWalk.walkTo("SE",
				getMapPositionOfCharacter(), 1);

		if (stage.getCurrentCharacter().getMapPositionOfCharacter()
				.equals(north)) {
			characterCloseDirection = "N";
			return stage.getCurrentCharacter();
		} else if (stage.getCurrentCharacter().getMapPositionOfCharacter()
				.equals(south)) {
			characterCloseDirection = "S";
			return stage.getCurrentCharacter();
		} else if (stage.getCurrentCharacter().getMapPositionOfCharacter()
				.equals(west)) {
			characterCloseDirection = "W";
			return stage.getCurrentCharacter();
		} else if (stage.getCurrentCharacter().getMapPositionOfCharacter()
				.equals(east)) {
			characterCloseDirection = "E";
			return stage.getCurrentCharacter();
		} else if (stage.getCurrentCharacter().getMapPositionOfCharacter()
				.equals(northWest)) {
			characterCloseDirection = "NW";
			return stage.getCurrentCharacter();
		} else if (stage.getCurrentCharacter().getMapPositionOfCharacter()
				.equals(northEast)) {
			characterCloseDirection = "NE";
			return stage.getCurrentCharacter();
		} else if (stage.getCurrentCharacter().getMapPositionOfCharacter()
				.equals(southWest)) {
			characterCloseDirection = "SW";
			return stage.getCurrentCharacter();
		} else if (stage.getCurrentCharacter().getMapPositionOfCharacter()
				.equals(southEast)) {
			characterCloseDirection = "SE";
			return stage.getCurrentCharacter();
		}

		for (Entity character : stage.getCharacters()) {
			if (character.getMapPositionOfCharacter().equals(north)) {
				characterCloseDirection = "N";
				return character;
			} else if (character.getMapPositionOfCharacter()
					.equals(south)) {
				characterCloseDirection = "S";
				return character;
			} else if (character.getMapPositionOfCharacter().equals(west)) {
				characterCloseDirection = "W";
				return character;
			} else if (character.getMapPositionOfCharacter().equals(east)) {
				characterCloseDirection = "E";
				return character;
			} else if (character.getMapPositionOfCharacter().equals(
					northWest)) {
				characterCloseDirection = "NW";
				return character;
			} else if (character.getMapPositionOfCharacter().equals(
					northEast)) {
				characterCloseDirection = "NE";
				return character;
			} else if (character.getMapPositionOfCharacter().equals(
					southWest)) {
				characterCloseDirection = "SW";
				return character;
			} else if (character.getMapPositionOfCharacter().equals(
					southEast)) {
				characterCloseDirection = "SE";
				return character;
			}
		}

		return null;
	}

	/****************************************************************************************/
	public void draw(Graphics2D g) {
		super.draw(g);
	}

}

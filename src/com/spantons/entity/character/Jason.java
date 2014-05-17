package com.spantons.entity.character;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.imgscalr.Scalr;

import com.spantons.entity.Animation;
import com.spantons.entity.Entity;
import com.spantons.entity.EntityChecks;
import com.spantons.gameState.Stage;
import com.spantons.magicNumbers.ImagePath;
import com.spantons.singleton.ImageCache;
import com.spantons.tileMap.TileMap;
import com.spantons.utilities.TileWalk;

public class Jason extends Entity{

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
		damageBackup = damage;
		flinchingIncreaseDeltaTimePerversity = 1000;
		flinchingDecreaseDeltaTimePerversity = 1000;
		deltaForReduceFlinchingIncreaseDeltaTimePerversity = 0;
		dead = false;
		moveSpeed = 120;
		facingRight = true;
		nextDirectionJason = TileWalk.randomMov();

		loadSprite();

		animation = new Animation();
		movFace(nextDirectionJason);
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
		EntityChecks.checkIsVisible(this, tileMap);
		if (visible) {
			checkCharacterIsDead();
			checkIsRecoveringFromAttack();
			updateAnimation();
		}
		characterClose = checkIsCloseToAnotherCharacter();
		if (characterClose != null) 
			attack();
		else
			movJason();

		setMapPosition(nextPositionInMap.x, nextPositionInMap.y);
	}

	/****************************************************************************************/
	private void movJason() {
		if (flinchingJasonMov) {
			long elapsedTime = (System.nanoTime() - flinchingTimeJasonMov) / 1000000;
			if (elapsedTime > moveSpeed)
				flinchingJasonMov = false;
		} else {
			nextPositionInMap = TileWalk.walkTo(nextDirectionJason,
					nextPositionInMap, 1);

			if (EntityChecks.checkTileCollision(this, tileMap)) {
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

		Point position = new Point(xMap, yMap);
		Point north = TileWalk.walkTo("N", position, 1);
		Point south = TileWalk.walkTo("S", position, 1);
		Point west = TileWalk.walkTo("W", position, 1);
		Point east = TileWalk.walkTo("E", position, 1);
		Point northWest = TileWalk.walkTo("NW",position, 1);
		Point northEast = TileWalk.walkTo("NE",position, 1);
		Point southWest = TileWalk.walkTo("SW",position, 1);
		Point southEast = TileWalk.walkTo("SE",position, 1);
		Point currentCharacterPosition = null;
		
		for (Entity character : stage.getCharacters()) {
			currentCharacterPosition = character.getMapPositionOfCharacter();

			if (currentCharacterPosition.equals(north)) {
				characterCloseDirection = "N";
				return character;
			} else if (currentCharacterPosition.equals(south)) {
				characterCloseDirection = "S";
				return character;
			} else if (currentCharacterPosition.equals(west)) {
				characterCloseDirection = "W";
				return character;
			} else if (currentCharacterPosition.equals(east)) {
				characterCloseDirection = "E";
				return character;
			} else if (currentCharacterPosition.equals(northWest)) {
				characterCloseDirection = "NW";
				return character;
			} else if (currentCharacterPosition.equals(northEast)) {
				characterCloseDirection = "NE";
				return character;
			} else if (currentCharacterPosition.equals(southWest)) {
				characterCloseDirection = "SW";
				return character;
			} else if (currentCharacterPosition.equals(southEast)) {
				characterCloseDirection = "SE";
				return character;
			}
		}
		
		currentCharacterPosition = 
				stage.getCurrentCharacter().getMapPositionOfCharacter();
		
		if (currentCharacterPosition.equals(north)) {
			characterCloseDirection = "N";
			return stage.getCurrentCharacter();
		} else if (currentCharacterPosition.equals(south)) {
			characterCloseDirection = "S";
			return stage.getCurrentCharacter();
		} else if (currentCharacterPosition.equals(west)) {
			characterCloseDirection = "W";
			return stage.getCurrentCharacter();
		} else if (currentCharacterPosition.equals(east)) {
			characterCloseDirection = "E";
			return stage.getCurrentCharacter();
		} else if (currentCharacterPosition.equals(northWest)) {
			characterCloseDirection = "NW";
			return stage.getCurrentCharacter();
		} else if (currentCharacterPosition.equals(northEast)) {
			characterCloseDirection = "NE";
			return stage.getCurrentCharacter();
		} else if (currentCharacterPosition.equals(southWest)) {
			characterCloseDirection = "SW";
			return stage.getCurrentCharacter();
		} else if (currentCharacterPosition.equals(southEast)) {
			characterCloseDirection = "SE";
			return stage.getCurrentCharacter();
		}

		return null;
	}

	/****************************************************************************************/
	public void draw(Graphics2D g) {
		super.draw(g);
	}

}

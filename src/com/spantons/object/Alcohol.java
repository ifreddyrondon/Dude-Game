package com.spantons.object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.spantons.entity.Animation;
import com.spantons.tileMap.TileMap;

public class Alcohol extends Object{

	private static final int IDLE = 0;
	private ArrayList<BufferedImage[]> sprites;
	
	public Alcohol(TileMap _tileMap, int _xMap, int _yMap) {
		super(_tileMap, _xMap, _yMap);
		
		description = "Alcohol";
		type = NON_BLOCKED;
		damage = -0.3f;
		offSetXLoading = 20;
		offSetYLoading = 12;
		
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
					.getResourceAsStream("/objects_sprites/Alcohol.png"));
			
			spriteWidth = spriteSheet.getWidth();
			spriteHeight = spriteSheet.getHeight();
			
			sprites = new ArrayList<BufferedImage[]>();

			// IDLE
			BufferedImage[] bi = new BufferedImage[1];
			bi[0] = spriteSheet.getSubimage(0, 0, spriteWidth,
					spriteHeight);
			sprites.add(bi);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/****************************************************************************************/
	public void update() {
		
		if(carrier != null){
			showObject = false;
			carrier.getDrunk(this);
			carrier.takeOrLeaveObject();
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

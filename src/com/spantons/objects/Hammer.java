package com.spantons.objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.imgscalr.Scalr;

import com.spantons.entity.Animation;
import com.spantons.entity.Entity;
import com.spantons.magicNumbers.ImagePath;
import com.spantons.object.DrawObjectMobile;
import com.spantons.object.Object;
import com.spantons.object.UpdateObjectMobile;
import com.spantons.singleton.ImageCache;
import com.spantons.tileMap.TileMap;

public class Hammer extends Object{

	private static final int IDLE = 0;
	private static final int LOADING = 1;
	private static final int ATTACKING = 2;
	
	private ArrayList<BufferedImage[]> sprites;
	
	private UpdateObjectMobile updateObject;
	private DrawObjectMobile drawObject;
	
	/****************************************************************************************/
	public Hammer(TileMap _tileMap, int _xMap, int _yMap, double _scale) {
		super(_tileMap, _xMap, _yMap);
		scale = _scale;
		
		description = "Martillo";
		damage = 0.6f;
		offSetXLoading = 12;
		offSetYLoading = 12;
		
		updateObject = new UpdateObjectMobile(tileMap, this);
		drawObject = new DrawObjectMobile(this);
		
		loadSprite();
		
		animation = new Animation();
		currentAnimation = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelayTime(1000);
	}
	
	/****************************************************************************************/
	private void loadSprite() {
		try {
			BufferedImage spriteSheet = ImageCache.getInstance().getImage(ImagePath.OBJECT_HAMMER);
			
			spriteWidth = ((int) (spriteSheet.getWidth() / 3 * scale));
			spriteHeight = ((int) (spriteSheet.getHeight() * scale));
			
			spriteSheet = Scalr.resize(spriteSheet, (int)(spriteSheet.getWidth() * scale));
			
			sprites = new ArrayList<BufferedImage[]>();

			// IDLE
			BufferedImage[] bi = new BufferedImage[1];
			bi[0] = spriteSheet.getSubimage(0, 0, spriteWidth,
					spriteHeight);
			sprites.add(bi);
			
			// LOADING
			bi = new BufferedImage[1];
			bi[0] = spriteSheet.getSubimage(spriteWidth, 0, spriteWidth,
					spriteHeight);
			sprites.add(bi);
			
			// ATTACKING
			bi = new BufferedImage[3];
			bi[0] = spriteSheet.getSubimage(spriteWidth, 0, spriteWidth,
					spriteHeight);
			bi[1] = spriteSheet.getSubimage(0, 0, spriteWidth,
					spriteHeight);
			bi[2] = spriteSheet.getSubimage(spriteWidth * 2, 0, spriteWidth,
					spriteHeight);
			sprites.add(bi);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/****************************************************************************************/
	@Override
	public void load(Entity _entity) {
		_entity.setDamage((float) (_entity.getDamage() + damage));
		
	}
	
	/****************************************************************************************/
	@Override
	public void unload(Entity _entity) {
		_entity.setDamage((float) (_entity.getDamage() - damage));
		
	}
	
	/****************************************************************************************/
	public void update() {
		
		if(carrier != null){
			if (carrier.isAttack()) {
				if (currentAnimation != ATTACKING) {
					currentAnimation = ATTACKING;
					animation.setFrames(sprites.get(ATTACKING));
					animation.setDelayTime(50);
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
		
		updateObject.update();
		animation.update();
	}
	
	/****************************************************************************************/
	public void draw(Graphics2D g) {
		drawObject.draw(g);
	}

}

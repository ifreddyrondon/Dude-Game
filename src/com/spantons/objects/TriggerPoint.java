package com.spantons.objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.spantons.entity.Animation;
import com.spantons.entity.Entity;
import com.spantons.gameStages.StagesLevels;
import com.spantons.magicNumbers.ImagePath;
import com.spantons.magicNumbers.SoundPath;
import com.spantons.object.Object;
import com.spantons.singleton.ImageCache;
import com.spantons.singleton.SoundCache;
import com.spantons.tileMap.TileMap;

public class TriggerPoint extends Object {

	private StagesLevels stage;
	private boolean activated;
	private static final int IDLE = 0;
	private ArrayList<BufferedImage[]> sprites;
	private Entity characterInTrigger;
	
	private boolean soundPlay;
	
	/****************************************************************************************/
	public TriggerPoint(TileMap _tileMap, StagesLevels _stage, int _xMap, int _yMap) {
		super(_tileMap, _xMap, _yMap);
		
		stage = _stage;
		description = "Punto de activacion";
		type = BLOCKED;
		soundPlay = false;

		loadSprite();
		
		animation = new Animation();
		currentAnimation = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelayTime(1000);
	}

	/****************************************************************************************/
	private void loadSprite() {
		try {
			BufferedImage spriteSheet = ImageCache.getInstance().getImage(ImagePath.OBJECT_TRIGGER_POINT);
			
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
		super.update();
		animation.update();
		
		if (	stage.getCurrentCharacter().getMapPositionOfCharacter().x == xMap
			&& stage.getCurrentCharacter().getMapPositionOfCharacter().y == yMap) {
			
			characterInTrigger = stage.getCurrentCharacter();
			characterInTrigger.setBusy(true);
			
			if (!soundPlay) {
				SoundCache.getInstance().getSound(SoundPath.SFX_DRAG_DOOR).play();
				soundPlay = true;
				activated = true;
			}
			
		} else if (	characterInTrigger != null && !characterInTrigger.isDead()
					&& characterInTrigger.getMapPositionOfCharacter().x == xMap
					&& characterInTrigger.getMapPositionOfCharacter().y == yMap)
			
			activated = true;
		
		else {
			soundPlay = false;
			activated = false;
			if (characterInTrigger != null) 
				characterInTrigger.setBusy(false);
			characterInTrigger = null;
		}
	}
	
	/****************************************************************************************/
	public void draw(Graphics2D g) {
		super.draw(g);
	}

	public boolean isActivated() {
		return activated;
	}
	
}

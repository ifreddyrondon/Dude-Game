package com.spantons.objects;

import com.spantons.magicNumbers.ImagePath;
import com.spantons.object.DrawObjectImmobile;
import com.spantons.object.LoadSpriteObjectSingleAnimation;
import com.spantons.object.Object;
import com.spantons.object.ObjectAttributeGetDrunk;
import com.spantons.object.UpdateObjectImmobile;
import com.spantons.tileMap.TileMap;

public class Beers extends Object {
	
	/****************************************************************************************/
	public Beers(TileMap _tileMap, int _xMap, int _yMap) {
		super(_tileMap, _xMap, _yMap);
		
		update = new UpdateObjectImmobile(_tileMap, this);
		draw = new DrawObjectImmobile(this);
		attribute = new ObjectAttributeGetDrunk(-0.5,this);
		
		type = CONSUMABLE;
		timeToConsumable = 30000;
		description = "Cerveza";
		scale = 1;
		
		loadSprite = new LoadSpriteObjectSingleAnimation(this);
		loadSprite.loadSprite(ImagePath.OBJECT_BEERS);
	}
		
}

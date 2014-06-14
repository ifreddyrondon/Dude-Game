package com.spantons.objects;

import com.spantons.magicNumbers.ImagePath;
import com.spantons.object.DrawObjectImmobile;
import com.spantons.object.LoadSpriteObjectSingleAnimation;
import com.spantons.object.Object;
import com.spantons.object.ObjectAttributeGetHealth;
import com.spantons.object.UpdateObjectImmobile;
import com.spantons.tileMap.TileMap;

public class PieceOfPizza extends Object {

	/****************************************************************************************/
	public PieceOfPizza(TileMap _tileMap, int _xMap, int _yMap) {
		super(_tileMap, _xMap, _yMap);
		
		update = new UpdateObjectImmobile(_tileMap, this);
		draw = new DrawObjectImmobile(this);
		attribute = new ObjectAttributeGetHealth(0.3);
		
		type = CONSUMABLE;
		timeToConsumable = 0;
		description = "Pedazo de pizza";
		scale = 1;
		
		loadSprite = new LoadSpriteObjectSingleAnimation(this);
		loadSprite.loadSprite(ImagePath.OBJECT_PIECE_OF_PIZZA);
			
	}
	
}

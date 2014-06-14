package com.spantons.objects;

import com.spantons.magicNumbers.ImagePath;
import com.spantons.object.DrawObjectMobile;
import com.spantons.object.LoadSpriteObjectWeapon;
import com.spantons.object.Object;
import com.spantons.object.ObjectAttributeGetMoreDamage;
import com.spantons.object.UpdateObjectWeapon;
import com.spantons.tileMap.TileMap;

public class Crowbar extends Object {

	
	public Crowbar(TileMap _tileMap, int _xMap, int _yMap, double _scale) {
		super(_tileMap, _xMap, _yMap);
		
		update = new UpdateObjectWeapon(_tileMap, this);
		draw = new DrawObjectMobile(this);
		attribute = new ObjectAttributeGetMoreDamage(0.4);

		type = NON_CONSUMABLE;
		scale = _scale;
		description = "Palanca";
		
		offSetXLoading = 12;
		offSetYLoading = 12;
				
		loadSprite = new LoadSpriteObjectWeapon(this);
		loadSprite.loadSprite(ImagePath.OBJECT_CROWBAR);
	}
				
}

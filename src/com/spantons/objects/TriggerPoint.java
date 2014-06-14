package com.spantons.objects;

import com.spantons.magicNumbers.ImagePath;
import com.spantons.object.DrawObjectImmobile;
import com.spantons.object.LoadSpriteObjectSingleAnimation;
import com.spantons.object.Object;
import com.spantons.object.UpdateObjectTriggerPoint;
import com.spantons.stagesLevel.StagesLevel;

public class TriggerPoint extends Object {

	public TriggerPoint(StagesLevel _stage, int _xMap,int _yMap) { 
		super(_stage.getTileMap(), _xMap, _yMap);

		description = "Punto de activacion";

		loadSprite = new LoadSpriteObjectSingleAnimation(this);
		loadSprite.loadSprite(ImagePath.OBJECT_TRIGGER_POINT);
		
		update = new UpdateObjectTriggerPoint(_stage, this);
		draw = new DrawObjectImmobile(this);
	}
	
}

package com.spantons.object;

import java.awt.Graphics2D;

import com.spantons.Interfaces.IDrawable;

public class DrawObjectImmobile implements IDrawable {

	private Object object;

	/****************************************************************************************/
	public DrawObjectImmobile(Object _object) {
		object = _object;
	}

	/****************************************************************************************/
	@Override
	public void draw(Graphics2D g) {
		if (object.showObject == false)
			return;
		
		g.drawImage(
			object.animation.getCurrentImageFrame(), 
			object.x - object.spriteWidth / 2,
			object.y - object.spriteHeight, 
			null);
	}

}

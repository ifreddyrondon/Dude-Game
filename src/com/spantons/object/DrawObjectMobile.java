package com.spantons.object;

import java.awt.Graphics2D;

import com.spantons.Interfaces.IDrawable;

public class DrawObjectMobile implements IDrawable{

	private Object object;
	
	/****************************************************************************************/
	public DrawObjectMobile(Object _object) {
		object = _object;
	}
	
	/****************************************************************************************/
	@Override
	public void draw(Graphics2D g) {
		if (object.carrier != null) {
			if (object.carrier.isMovUp() && !object.carrier.isMovLeft() && !object.carrier.isMovRight()) 
				return;
			
			if (object.carrier.isFacingRight()) 
				g.drawImage(
					object.animation.getCurrentImageFrame(),
					object.x + object.carrier.getSpriteWidth() / 2 - object.spriteWidth + object.offSetXLoading, 
					object.y - object.spriteHeight - object.offSetYLoading, 
					null);
			
			else 
				g.drawImage(
					object.animation.getCurrentImageFrame(), 
					object.x + object.carrier.getSpriteWidth() / 2 - object.spriteWidth + object.offSetXLoading,
					object.y - object.spriteHeight - object.offSetYLoading, 
					-object.spriteWidth, 
					object.spriteHeight, 
					null);
		} else
			g.drawImage(
				object.animation.getCurrentImageFrame(),
				object.x - object.spriteWidth / 2, 
				object.y - object.spriteHeight, 
				null);
	}

}

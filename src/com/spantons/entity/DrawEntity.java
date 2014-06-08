package com.spantons.entity;

import java.awt.Graphics2D;

import com.spantons.Interfaces.IDrawable;

public class DrawEntity implements IDrawable {

	private Entity entity;
	
	/****************************************************************************************/
	public DrawEntity(Entity _entity) {
		entity = _entity;
	}
	
	/****************************************************************************************/
	@Override
	public void draw(Graphics2D g) {
		if (entity.visible) {
			if (entity.recoveringFromAttack && !entity.dead) {
				long elapsedTime = (System.nanoTime() - entity.flinchingTimeRecoveringFromAttack) / 1000000;
				if (elapsedTime / 100 % 2 == 0)
					return;
			}
			if (entity.facingRight)
				g.drawImage(
						entity.animation.getCurrentImageFrame(), 
						entity.x - entity.spriteWidth / 2,
						entity.y - entity.spriteHeight, 
						null);
			else
				g.drawImage(
						entity.animation.getCurrentImageFrame(), 
						entity.x + entity.spriteWidth - entity.spriteWidth / 2, 
						entity.y- entity.spriteHeight,
						-entity.spriteWidth, 
						entity.spriteHeight,
						null);

			if (entity.object != null)
				entity.object.draw(g);
		}
	}
	
}

package com.spantons.entity;

import com.spantons.entity.character.Jason;
import com.spantons.magicNumbers.SoundPath;
import com.spantons.singleton.SoundCache;

public class EntityAttack implements IEntityAttack {

	private Entity entity;
	
	/****************************************************************************************/
	public EntityAttack(Entity _entity) {
		entity = _entity;
	}
	
	/****************************************************************************************/
	@Override
	public void attack() {
		if (entity.characterClose != null) {
			if (entity.characterClose.recoveringFromAttack)
				return;
			
			EntityUtils.movFace(entity, entity.characterCloseDirection);
			entity.characterClose.health = entity.characterClose.health - entity.damage;
			
			if (entity.characterClose.health <= 0) {
				entity.characterClose.dead = true;
				entity.characterClose.health = 0;
				killCharacter(entity.characterClose);
				return;
			}
			
			if (entity == entity.stage.getCurrentCharacter())
				entity.flinchingIncreaseDeltaTimePerversity -= entity.deltaForReduceFlinchingIncreaseDeltaTimePerversity;

			entity.characterClose.recoveringFromAttack = true;
			entity.characterClose.flinchingTimeRecoveringFromAttack = System.nanoTime();
		}
	}
	
	/****************************************************************************************/
	public void killCharacter(Entity _entity) {
		SoundCache.getInstance().getSound(SoundPath.SFX_DYING).play();
		_entity.dead = true;
		
		if (_entity.object != null)
			_entity.object.setCarrier(null);

		_entity.getTileMap().setEntityToDraw(_entity.xMap, _entity.yMap, null);
		_entity.getTileMap().setEntityDeadToDraw(_entity.xMap, _entity.yMap, _entity);
		_entity.recoveringFromAttack = false;
		_entity.stage.getDead().add(_entity);
		_entity.updateAnimation.update();
		
		if (_entity.getClass().equals(Jason.class))
			_entity.stage.getJasons().remove(_entity);
		else
			_entity.stage.getCharacters().remove(_entity);
	}

}

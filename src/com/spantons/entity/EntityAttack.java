package com.spantons.entity;


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
				EntityUtils.killCharacter(entity.characterClose);
				return;
			}
			
			if (entity == entity.stage.getCurrentCharacter())
				entity.flinchingIncreaseDeltaTimePerversity -= entity.deltaForReduceFlinchingIncreaseDeltaTimePerversity;

			entity.characterClose.recoveringFromAttack = true;
			entity.characterClose.flinchingTimeRecoveringFromAttack = System.nanoTime();
		}
	}
	
}

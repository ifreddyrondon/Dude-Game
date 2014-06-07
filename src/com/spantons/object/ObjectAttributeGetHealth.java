package com.spantons.object;

import com.spantons.entity.Entity;
import com.spantons.magicNumbers.SoundPath;
import com.spantons.singleton.SoundCache;

public class ObjectAttributeGetHealth implements IObjectAttribute {

	private double health;
	
	/****************************************************************************************/
	public ObjectAttributeGetHealth(double _health) {
		health = _health;
	}
	
	/****************************************************************************************/
	@Override
	public void loadAttribute(Entity _entity) {
		SoundCache.getInstance().getSound(SoundPath.SFX_EAT).play();
		double newHealth = _entity.getHealth() + health;
		
		if (newHealth > _entity.getMaxHealth()) 
			_entity.setHealth(_entity.getMaxHealth());
		else
			_entity.setHealth(newHealth);
		
	}

	/****************************************************************************************/
	@Override
	public void unloadAttribute(Entity _entity) {
		// TODO Auto-generated method stub
		
	}

}

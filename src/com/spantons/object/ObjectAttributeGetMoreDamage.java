package com.spantons.object;

import com.spantons.entity.Entity;

public class ObjectAttributeGetMoreDamage implements IObjectAttribute {

	private double damage;
	
	/****************************************************************************************/
	public ObjectAttributeGetMoreDamage(double _damage) {
		damage = _damage;
	}

	/****************************************************************************************/
	@Override
	public void loadAttribute(Entity _entity) {
		_entity.setDamage(_entity.getDamage() + damage);
	}

	/****************************************************************************************/
	@Override
	public void unloadAttribute(Entity _entity) {
		_entity.setDamage(_entity.getDamage() - damage);	
	} 
	
}

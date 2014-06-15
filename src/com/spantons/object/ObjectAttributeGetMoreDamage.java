package com.spantons.object;

import com.spantons.entity.Entity;

public class ObjectAttributeGetMoreDamage implements IObjectAttribute {

	private double damage;
	@SuppressWarnings("unused")
	private Object object;
	
	/****************************************************************************************/
	public ObjectAttributeGetMoreDamage(double _damage, Object _object) {
		damage = _damage;
		object = _object;
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

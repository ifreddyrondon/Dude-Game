package com.spantons.object;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import com.spantons.entity.Entity;
import com.spantons.magicNumbers.SoundPath;
import com.spantons.singleton.SoundCache;

public class ObjectAttributeGetDrunk implements IObjectAttribute {

	private double damage;
	private Timer timer;
	private Object object;
	
	/****************************************************************************************/
	public ObjectAttributeGetDrunk(double _damage, Object _object) {
		damage = _damage;
		object = _object;
	}

	/****************************************************************************************/
	@Override
	public void loadAttribute(final Entity _entity) {
		SoundCache.getInstance().getSound(SoundPath.SFX_BURP).play();
		double newDamage = _entity.getDamage() + damage;
		if (newDamage <= 0) 
			newDamage = 0;	
		
		_entity.setDamage(newDamage);
		
		timer = new Timer(object.timeToConsumable, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				unloadAttribute(_entity);
				timer.stop();
			}
		});
		timer.start();
	}

	/****************************************************************************************/
	@Override
	public void unloadAttribute(Entity _entity) {
		_entity.setDamage(_entity.getDamage() - damage);
	} 

}

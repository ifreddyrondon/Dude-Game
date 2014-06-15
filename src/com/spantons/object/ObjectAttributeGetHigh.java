package com.spantons.object;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import com.spantons.entity.Entity;
import com.spantons.magicNumbers.SoundPath;
import com.spantons.singleton.SoundCache;

public class ObjectAttributeGetHigh implements IObjectAttribute {

	private double moveSpeed;
	private Timer timer;
	private Object object;

	/****************************************************************************************/
	public ObjectAttributeGetHigh(double _moveSpeed, Object _object) {
		moveSpeed = _moveSpeed;
		object = _object;
	}

	/****************************************************************************************/
	@Override
	public void loadAttribute(final Entity _entity) {
		SoundCache.getInstance().getSound(SoundPath.SFX_SMOKE).play();
		_entity.setMoveSpeed((int) (_entity.getMoveSpeed() - moveSpeed));

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
		_entity.setMoveSpeed((int) (_entity.getMoveSpeed() + moveSpeed));
	}

}

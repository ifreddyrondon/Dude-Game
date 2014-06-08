package com.spantons.object;

import com.spantons.entity.Entity;
import com.spantons.entity.EntityUtils;

public class HandleObjects implements IHandleObjects {
	
	@Override
	public void takeOrLeaveObject(Entity _entity) {
		if (_entity.getObject() != null) {
			Object newObject = EntityUtils.checkIsOverObject(_entity, _entity.getStage());
			if (newObject != null) {
				unloadObject(_entity);
				loadObject(_entity,newObject, true);
			
			} else 
				unloadObject(_entity);
		} else {
			Object newObject = EntityUtils.checkIsOverObject(_entity, _entity.getStage());
			if (newObject != null) 
				loadObject(_entity,newObject, false);
		}
	}
	
	/****************************************************************************************/
	private void loadObject(Entity _entity, Object _object, boolean _change) {
		_entity.setObject(_object);
		_entity.getObject().setCarrier(_entity);
		_entity.getObject().actionLoad();
		if(!_change)
			_entity.getTileMap().setObjectToDraw(_entity.getObject().getXMap(), _entity.getObject().getYMap(), null);
		
		if (_entity.getObject().getType() == Object.CONSUMABLE) {
			_entity.getStage().getObjects().remove(_entity.getObject());
			_entity.setObject(null);
		}
	}
	
	/****************************************************************************************/
	private void unloadObject(Entity _entity) {
		if (_entity.getObject().getType() == Object.CONSUMABLE) 
			return;
		
		_entity.getObject().actionUnload();
		_entity.getObject().setCarrier(null);
		_entity.getTileMap().setObjectToDraw(_entity.getObject().getXMap(), _entity.getObject().getYMap(), _entity.getObject());
		_entity.setObject(null);
	}
}

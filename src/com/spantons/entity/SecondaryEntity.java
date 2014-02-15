package com.spantons.entity;

public class SecondaryEntity extends Thread {

	private Entity entity;
	
	public SecondaryEntity(Entity _entity) {
		entity = _entity;
	}
	
	public void initOtherCharacters(int _xMap, int _yMap){
		entity.setXMap(_xMap);
		entity.setYMap(_yMap);
	}
	
}

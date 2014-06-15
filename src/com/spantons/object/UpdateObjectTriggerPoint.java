package com.spantons.object;

import com.spantons.Interfaces.IUpdateable;
import com.spantons.entity.Entity;
import com.spantons.magicNumbers.SoundPath;
import com.spantons.singleton.SoundCache;
import com.spantons.stagesLevel.StagesLevel;

public class UpdateObjectTriggerPoint implements IUpdateable {

	private StagesLevel stage;
	private Object object;
	private UpdateObjectImmobile updateImmobile;
	private boolean soundPlay;
	private Entity characterInTrigger;
	
	/****************************************************************************************/
	public UpdateObjectTriggerPoint(StagesLevel _stage, Object _object) {
		stage = _stage;
		object = _object;
		updateImmobile = new UpdateObjectImmobile(_stage, _object);
		soundPlay = false;
	}
	
	/****************************************************************************************/
	@Override
	public void update() {
		updateImmobile.update();
		
		if (	stage.getCurrentCharacter().getXMap() == object.xMap
			&& stage.getCurrentCharacter().getYMap() == object.yMap) {

			characterInTrigger = stage.getCurrentCharacter();
			characterInTrigger.setBusy(true);

			if (!soundPlay) {
				SoundCache.getInstance()
						.getSound(SoundPath.SFX_DRAG_DOOR).play();
				soundPlay = true;
				object.activated = true;
			}

		} else if (characterInTrigger != null
				&& !characterInTrigger.isDead()
				&& characterInTrigger.getMapPositionOfCharacter().x == object.xMap
				&& characterInTrigger.getMapPositionOfCharacter().y == object.yMap)

			object.activated = true;

		else {
			soundPlay = false;
			object.activated = false;
			if (characterInTrigger != null)
				characterInTrigger.setBusy(false);
			characterInTrigger = null;
		}
	}
	
}

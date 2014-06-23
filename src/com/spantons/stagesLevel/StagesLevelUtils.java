package com.spantons.stagesLevel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import com.spantons.dialogue.Dialogue;
import com.spantons.entity.Entity;
import com.spantons.magicNumbers.ImagePath;
import com.spantons.magicNumbers.SoundPath;
import com.spantons.singleton.SoundCache;

public class StagesLevelUtils {

	public static int END_LEVEL_WIN = 0;
	public static int END_LEVEL_WITH_ALL_CHARACTERS = 1;
	public static int END_LEVEL_CHARACTERS_NOT_BUSY = 2;

	/****************************************************************************************/	
	public static Timer setTimerAwakeningDialogues(int _time, final StagesLevel _stage) {
		
		Timer timerAwakeningDialogues =  new Timer(_time, new ActionListener() { 
			@Override 
			public void actionPerformed(ActionEvent ae) { 
				for (String txt : _stage.stringDialogues.get("AWAKENING")) {
					if (!txt.equals("")) {
						_stage.dialogues.addDialogue(
								new Dialogue(
									txt,StagesLevel.fontDialogues, StagesLevel.colorDialogues, 2500, 
									ImagePath.DIALOGUE_SPEECH_BALLON_NORMAL,
									Dialogue.RANDOM, Dialogue.NORMAL_PRIORITY
							));
					}
				}
			} 
		}); 
		timerAwakeningDialogues.setRepeats(false);
		return timerAwakeningDialogues;
	}
	
	/****************************************************************************************/
	public static Timer setTimerLightsOn(int _time, final StagesLevel _stage){
		Timer lightsOn = new Timer(_time, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_stage.tileMap.turnLights();
				_stage.timerLightsOn.stop();
				_stage.timerLightsOff.start();
				SoundCache.getInstance().getSound(SoundPath.SFX_ELECTRIC_CURRENT).play();
			}
		});
		return lightsOn;
	}
	
	/****************************************************************************************/
	public static Timer setTimerLightsOff(int _time, final StagesLevel _stage){
		
		Timer lightsOff = new Timer(_time, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_stage.tileMap.turnLights();
				_stage.timerLightsOn.start();
				_stage.timerLightsOff.stop();
			}
		});
		return lightsOff;
	}
	
	/****************************************************************************************/
	public static void finishLevel(StagesLevel _stage, int _typeOfFinish, int _nextLevel){
		
		if (END_LEVEL_CHARACTERS_NOT_BUSY == _typeOfFinish) {
			_stage.gsm.setCurrentCharacter(_stage.currentCharacter);
      		ArrayList<Entity> charactersNotBusy = new ArrayList<Entity>();
      		for (Entity entity : _stage.characters) {
				if (!entity.isBusy()) 
					charactersNotBusy.add(entity);
			}
      		_stage.gsm.setCharacters(charactersNotBusy);
		
		} else if (END_LEVEL_WITH_ALL_CHARACTERS == _typeOfFinish) {
			_stage.gsm.setCurrentCharacter(_stage.currentCharacter);
			_stage.gsm.setCharacters(_stage.characters);
		} 
		
		
		if (_stage.timerLightsOff != null || _stage.timerLightsOn != null) {
			_stage.timerLightsOn.stop();
			_stage.timerLightsOff.stop();
		}
		SoundCache.getInstance().stopAllSound();
		_stage.currentCharacter.setAllMov(false);
		_stage.gsm.setStage(_nextLevel);
	}
	
}

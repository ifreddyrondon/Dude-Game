package com.spantons.gameState;

import java.awt.Graphics2D;

import com.spantons.Interfaces.IDrawable;
import com.spantons.dialogue.DialogueManager;
import com.spantons.entity.Hud;
import com.spantons.tileMap.TileMap;
import com.spantons.utilities.ToHours;

public class DrawLevel implements IDrawable {

	private TileMap tileMap;
	private Hud hud;
	private DialogueManager dialogues;
	private int countdown;

	/****************************************************************************************/
	public DrawLevel(TileMap _tileMap, Hud _hud, DialogueManager _dialogues) {
		tileMap = _tileMap;
		hud = _hud;
		dialogues = _dialogues;
	}
	
	/****************************************************************************************/
	@Override
	public void draw(Graphics2D g) {
		tileMap.draw(g);
		hud.Draw(g, ToHours.SecondsToHours(countdown));
		dialogues.draw(g);
	}
	
	/****************************************************************************************/
	public void setCountdown(int _countdown){
		countdown = _countdown;
	}

}

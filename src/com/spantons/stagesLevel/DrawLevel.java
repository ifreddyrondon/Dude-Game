package com.spantons.stagesLevel;

import java.awt.Graphics2D;

import com.spantons.Interfaces.IDrawable;
import com.spantons.dialogue.DialogueManager;
import com.spantons.entity.Hud;
import com.spantons.tileMap.TileMap;

public class DrawLevel implements IDrawable {

	private TileMap tileMap;
	private Hud hud;
	private DialogueManager dialogues;

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
		hud.draw(g);
		dialogues.draw(g);
	}

}

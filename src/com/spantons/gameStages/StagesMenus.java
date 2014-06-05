package com.spantons.gameStages;

import java.awt.Graphics2D;

import com.spantons.Interfaces.IDrawable;
import com.spantons.Interfaces.IKeyable;
import com.spantons.Interfaces.IUpdateable;
import com.spantons.gameState.interfaces.Stage;

public abstract class StagesMenus implements Stage, IDrawable, IKeyable, IUpdateable {

	protected GameStagesManager gsm;

	public abstract void draw(Graphics2D g);
	public abstract void update();
	public abstract void keyPressed(int k);

}

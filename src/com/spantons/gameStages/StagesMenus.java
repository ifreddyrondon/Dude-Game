package com.spantons.gameStages;

import java.awt.Graphics2D;

import com.spantons.gameState.interfaces.Stage;

public abstract class StagesMenus implements Stage {

	protected GameStagesManager gsm;

	public abstract void draw(Graphics2D g);
	public abstract void update();
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);

}

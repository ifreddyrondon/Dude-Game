package com.spantons.stagesMenu;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import com.spantons.magicNumbers.SoundPath;
import com.spantons.singleton.SoundCache;
import com.spantons.stages.GameStagesManager;
import com.spantons.stages.IFontStage;
import com.spantons.stages.IStage;

public class StagesMenu implements IStage {

	protected ISelectAction select;
	protected IFontStage font;
	
	protected GameStagesManager gsm;
	protected Background bg;
	protected ImagesIntoScene images;
	
	protected String title;
	protected int currentChoice;
	protected ArrayList<String> choices;
	
	protected Font titleFont;
	protected Color titleColor;
	protected Font choicesFont;
	protected Color choicesColor;
	protected Font footerFont;
	protected Color footerColor;
	protected FontMetrics fm;
	protected Rectangle2D r;
	
	protected String footer = "Copyright © 2013 Wasting Time For Game C.A Todos los derechos reservados.";
	
	/****************************************************************************************/
	public StagesMenu(GameStagesManager _gsm) {
		gsm = _gsm;
		currentChoice = 0;
		SoundCache.getInstance().getSound(SoundPath.MUSIC_HORROR_MOVIE_AMBIANCE).loop();
	}

	/****************************************************************************************/
	@Override
	public void update() {
		bg.update();
	}
	
	/****************************************************************************************/
	@Override
	public void draw(Graphics2D g) {
		if (bg != null) 
			bg.draw(g);
		if (images != null) 
			images.draw(g);	
		font.draw(g);
	}
	
	/****************************************************************************************/
	@Override
	public void keyPressed(int k) {
		SoundCache.getInstance().getSound(SoundPath.SFX_SCRATCH).play();
		
		if (k == KeyEvent.VK_ENTER) {
			select.select(currentChoice);
		} else if (k == KeyEvent.VK_UP) {
			currentChoice--;
			if (currentChoice == -1) 
				currentChoice = choices.size() - 1;
		} else if (k == KeyEvent.VK_DOWN) {
			currentChoice++;
			if (currentChoice == choices.size()) 
				currentChoice = 0;
		}
	}
	
	/****************************************************************************************/
	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		
	}
	
}

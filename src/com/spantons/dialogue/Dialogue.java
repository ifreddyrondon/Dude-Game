package com.spantons.dialogue;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.Timer;

import com.spantons.gameState.Stage;

public abstract class Dialogue {

	protected Stage stage;
	protected ArrayList<BufferedImage> speechBallon;
	protected BufferedImage[] exclamationImg;
	
	protected Color fontColor;
	protected Font dialogueFont;
	protected Font secondaryMenuFont;
	protected Font aloneFont;
	
	protected FontMetrics fm;
	protected Rectangle2D r;
	
	protected Timer timerExclamation;
	protected int countdownExclamation = 500; 
	protected boolean exclamation;
	
	protected Timer timerAlone;
	protected int countdownAlone = 1500;
	protected boolean alone = false;
	
	protected abstract void loadImages();
	public abstract void update();
	public abstract void draw(Graphics2D g);
	
	/****************************************************************************************/
	public void alone(){
		alone = true;
		timerAlone.start();
	}
	/****************************************************************************************/
	protected void characterClose(){
		timerExclamation.start();
	}
	/****************************************************************************************/
	protected void characterFar(){
		timerExclamation.stop();
		exclamation = false;
	}
	/****************************************************************************************/
	
}

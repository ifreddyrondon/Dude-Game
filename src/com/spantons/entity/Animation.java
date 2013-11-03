package com.spantons.entity;

import java.awt.image.BufferedImage;

public class Animation {

	private BufferedImage[] frames;
	private int currentFrame;
	
	private long startTimeFrame;
	private long delayTimeFrame;
	
	private boolean playedOnce;
	
	public Animation() {
		playedOnce = false;
	}
	
	public void setFrames(BufferedImage[] frames){
		this.frames = frames;
		currentFrame = 0;
		startTimeFrame = System.nanoTime();
		playedOnce = false;
	}
	
	public void update(){
		if (delayTimeFrame == -1) return;
		
		long elapsedTime = (System.nanoTime() - startTimeFrame) / 1000000;
		if (elapsedTime > delayTimeFrame) {
			currentFrame++;
			startTimeFrame = System.nanoTime();
		}	
		if (currentFrame == frames.length) {
			currentFrame = 0;
			playedOnce = true;
		}
	}
	
	public void setDelayTime(long d ){
		delayTimeFrame = d;
	}
	
	public void setFrame(int i){
		currentFrame = i;
	}
	
	public int getCurrentFrame() { 
		return currentFrame; 
	}
	
	public BufferedImage getImageFrame() { 
		return frames[currentFrame]; 
	}
	
	public boolean getPlayedOnce() { 
		return playedOnce; 
	}
	
}

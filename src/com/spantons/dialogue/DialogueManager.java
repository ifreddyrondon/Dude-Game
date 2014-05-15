package com.spantons.dialogue;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Timer;

import com.spantons.entity.Entity;
import com.spantons.gameState.Stage;
import com.spantons.utilities.RandomItemArrayList;

public abstract class DialogueManager {

	protected Stage stage;	
	protected ArrayList<Dialogue> dialogues;
	protected Dialogue currentDialogue;
	protected HashMap<String, String[]> strings;
	protected Entity characterSpeaking;
	
	protected Font secondaryMenuFont;
	protected Color secondaryMenuColor;
	protected Font aloneFont;
	protected Color aloneColor;
	
	protected FontMetrics fm;
	protected Rectangle2D r;
	
	protected Timer timerExclamation;
	protected int countdownExclamation = 500; 
	protected boolean exclamation;
	
	protected Timer timerAlone;
	protected int countdownAlone = 1500;
	protected boolean alone = false;
	
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
	public void addDialogue(Dialogue _dialogue){
		dialogues.add(_dialogue);
	}
	
	/****************************************************************************************/
	protected void calculatePositionOfDialogue() {
		if (currentDialogue.getWhoSpeak() == Dialogue.RANDOM) {
			@SuppressWarnings("unchecked")
			ArrayList<Entity> aux = (ArrayList<Entity>) stage
					.getCharacters().clone();
			aux.add(stage.getCurrentCharacter());
			characterSpeaking = (Entity) RandomItemArrayList
					.getRandomItemFromArrayList(aux);
			aux = null;
			if (!characterSpeaking.isVisible())
				calculatePositionOfDialogue();
		
		} else
			characterSpeaking = stage.getCurrentCharacter();
	}
	
	/****************************************************************************************/
	public ArrayList<Dialogue> getDialogues() {
		return dialogues;
	}
	public void setDialogues(ArrayList<Dialogue> dialogues) {
		this.dialogues = dialogues;
	}
	public HashMap<String, String[]> getStrings() {
		return strings;
	}
	public void setStrings(HashMap<String, String[]> strings) {
		this.strings = strings;
	}
	
}

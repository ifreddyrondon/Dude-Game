package com.spantons.dialogue;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import javax.swing.Timer;

import com.spantons.entity.character.Jason;
import com.spantons.gameStages.StagesLevels;
import com.spantons.magicNumbers.ImagePath;
import com.spantons.magicNumbers.StringDialogue;
import com.spantons.main.GamePanel;
import com.spantons.singleton.ImageCache;

class DialogueComparator implements Comparator<Dialogue> {

	@Override
	public int compare(Dialogue o1, Dialogue o2) {
		
		if (o1.getPriority() > o2.getPriority()) 
			return -1;
		
		if (o1.getPriority() < o2.getPriority())
			return 1;
		  
		return 0;
	}	
}

public class DialogueStage1 extends DialogueManager {

	private int characterWidth;
	private int characterHeight;

	private boolean endedDialogue = true;
	private Timer timerDialogue;
	
	private Comparator<Dialogue> comparator;
	
	/****************************************************************************************/
	public DialogueStage1(StagesLevels _stage) {
		stage = _stage;
		comparator = new DialogueComparator();
		dialogues = new PriorityQueue<Dialogue>(10, comparator);
		strings = new HashMap<String, String[]>();
		loadStrings();
		
		secondaryMenuFont = new Font("Century Gothic", Font.PLAIN, 35);
		aloneFont = new Font("Century Gothic", Font.PLAIN, 50);

		characterWidth = stage.getCurrentCharacter().getSpriteWidth();
		characterHeight = stage.getCurrentCharacter().getSpriteHeight();

		defineTimers();
	}
	
	/****************************************************************************************/
	private void loadStrings() {
		
		getStrings().put("THOUGHTS_RAMDON", StringDialogue.THOUGHTS_RAMDON);
		getStrings().put("THOUGHTS_WANTOUT", StringDialogue.THOUGHTS_WANTOUT);
		getStrings().put("THOUGHTS_AWAKENING_1", StringDialogue.THOUGHTS_AWAKENING_1);
		getStrings().put("STORY_DOOR", StringDialogue.STORY_DOOR_MAIN_ROOM);
		getStrings().put("STORY_ROOM_1", StringDialogue.STORY_ROOM_1);
		getStrings().put("STORY_MAIN_ROOM", StringDialogue.STORY_MAIN_ROOM);
		getStrings().put("STORY_DOOR_ROOM_1", StringDialogue.STORY_DOOR_ROOM_1);
	}

	/****************************************************************************************/
	private void defineTimers() {
		timerExclamation = new Timer(countdownExclamation,
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ae) {
						if (exclamation)
							exclamation = false;
						else
							exclamation = true;
					}
				});
		
		timerAlone = new Timer(countdownAlone,
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ae) {
						alone = false;
					}
				});
	}

	/****************************************************************************************/
	public void update() {
		if (stage.getCurrentCharacter().getCharacterClose() != null)
			characterClose();
		else
			characterFar();
		
		if (endedDialogue && getDialogues().size() > 0) {
			endedDialogue = false;
			currentDialogue = getDialogues().poll();
			calculatePositionOfDialogue();
			
			timerDialogue = new Timer(currentDialogue.getCountdown(),
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							currentDialogue = null;
							endedDialogue = true;
							timerDialogue.stop();
						}
					});
			
			timerDialogue.start();
		}
	}

	/****************************************************************************************/
	public void draw(Graphics2D g) {
		
		if (currentDialogue != null) {
			g.drawImage(
				ImageCache.getInstance().getImage(
					currentDialogue.getTypeOfBallon()
				),
				characterSpeaking.getX() - characterWidth, 
				characterSpeaking.getY() - 150 - characterHeight,
				null
			);
			
			g.setColor(currentDialogue.getColor());
			g.setFont(currentDialogue.getFont());
			
			String[] split = currentDialogue.getTxt().split("\n");
			int y = characterSpeaking.getY() - 190;
			int h = g.getFontMetrics().getHeight();
			
			for (int i = 0; i < split.length; i++) {
				g.drawString(split[i],
						characterSpeaking.getX() - 30,
						y + (h*i)
				);
			}
		}
		/******/
		if (exclamation) {
			if (stage.getCurrentCharacter().getCharacterClose()
					.getClass().equals(Jason.class)) {
				
				timerExclamation.setDelay(200);
				g.drawImage(
					ImageCache.getInstance().getImage(
							ImagePath.DIALOGUE_EXCLAMATION_ALERT
					), 
					stage.getCurrentCharacter().getX() - 20, 
					stage.getCurrentCharacter().getY() - 140, 
					null
				);
				
			} else  {
				timerExclamation.setDelay(500);
				
				g.drawImage(
					ImageCache.getInstance().getImage(
						ImagePath.DIALOGUE_EXCLAMATION
					), 
					stage.getCurrentCharacter().getX() - 20, 
					stage.getCurrentCharacter().getY() - 140, 
					null
				);
			}
		}
		/******/
		if (stage.isSecondaryMenu()) {
			g.setColor(Color.WHITE);
			g.setFont(secondaryMenuFont);
			g.drawString("Resume (R)",
					GamePanel.RESOLUTION_WIDTH / 2 + 50, -50
							+ GamePanel.RESOLUTION_HEIGHT / 2);
			g.drawString("Main Menu (M)",
					GamePanel.RESOLUTION_WIDTH / 2 + 50,
					GamePanel.RESOLUTION_HEIGHT / 2);
			g.drawString("Quit Game (Q)",
					GamePanel.RESOLUTION_WIDTH / 2 + 50,
					50 + GamePanel.RESOLUTION_HEIGHT / 2);
		}
		/******/
		if (alone) {
			g.setColor(new Color(0f,0f,0f,.5f));
			g.fillRect(0, 0, GamePanel.RESOLUTION_WIDTH,
				GamePanel.RESOLUTION_HEIGHT);
			g.setColor(Color.WHITE);
			g.setFont(aloneFont);
			fm = g.getFontMetrics();
			String stringAlone = "Estás solo!!!";
			rectangle = fm.getStringBounds(stringAlone, g);
			int x = (GamePanel.RESOLUTION_WIDTH - (int) rectangle.getWidth()) / 2;
			int y = (GamePanel.RESOLUTION_HEIGHT - (int) rectangle.getHeight()) / 2 - 150;
			g.drawString(stringAlone, x, y);
		}
	}
}

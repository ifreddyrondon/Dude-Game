package com.spantons.dialogue;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import utilities.RandomItemArrayList;

import com.spantons.entity.Entity;
import com.spantons.gameState.Stage;
import com.spantons.main.GamePanel;

public class DialogueStage1 {
	
	private static final int STORY = 0;
	private static final int HELP = 1;
	private static final int THOUGHTS = 2;
	
	private static final int THOUGHTS_RAMDON = 0;
	private static final int THOUGHTS_WANTOUT = 1;
	private static final int THOUGHTS_AWAKENING = 2;
	
	private static final int HELP_0_BATHROOM = 0;
	
	private static final int STORY_ROOM_1 = 0;
	private static final int STORY_MAIN_ROOM = 1;
	
	public Map<Integer, String[]> thoughts;
	public Map<Integer, String[]> help;
	public Map<Integer, String[]> story;
	
	
	
	
	
	
	private Stage stage;
	
	private ArrayList<BufferedImage> speechBallon;
	private BufferedImage[] exclamationImg;
	
	private Color fontColor;
	private Font dialogueFont;
	
	private int characterWidth;
	private int characterHeight;
	
	private Timer timerExclamation;
	private int countdownExclamation = 500; 
	private boolean exclamation;
	
	private Timer timerCharactersSpeaking;
	private int countdownCharactersSpeaking = 2000;
	private Entity characterSpeaking;
	private String characterSpeakingDialog;
	private BufferedImage dialogueImage;
	private ArrayList<String> setOfDialogues;
	
	private Timer timerAwakeningDialogues;
	private int countdownAwakeningDialogues = 2000;
	
	/****************************************************************************************/
	public DialogueStage1(Stage _stage) {
		stage = _stage;
		fontColor = Color.BLACK;
		dialogueFont = new Font("Century Gothic", Font.PLAIN, 16);
		
		characterWidth = stage.getCurrentCharacter().getSpriteWidth();
		characterHeight = stage.getCurrentCharacter().getSpriteHeight();
		
		loadImages();
		startTimers(); 
		
		
		
		
		// THOUGHTS ----------------------------------------------------------------------------------------
		thoughts = new HashMap<Integer, String[]>();
		
		String[] aux = {	"Odio este sitio", 
				"seguramente alguno de ellos me trajo hasta aca",
				"debería deshacerme de ellos"};
		thoughts.put(THOUGHTS_RAMDON, aux);

		String[] aux2 = {"Debemos salir de aquí",
				"Hay que buscar una salida",
				"Debe haber una puerta en algún lado"};
		thoughts.put(THOUGHTS_WANTOUT, aux2);
		
		String[] aux3 = {	"Hey qué hago aquí",
				"Quienes son ustedes",
				"Qué sucede",
				"???"};
		thoughts.put(THOUGHTS_AWAKENING, aux3);
				
		
		// HELP ----------------------------------------------------------------------------------------
		help = new HashMap<Integer, String[]>();
		
		String[] aux4 = {	"Parece que hay algo detrás que no deja abrirla",
												"necesitamos una palanca"};
		help.put(HELP_0_BATHROOM, aux4);
		
		// STORY ----------------------------------------------------------------------------------------
		story = new HashMap<Integer, String[]>();
		String[] aux5 = {"Falta alguien",
										"Seguro se quedó para usar el baño",
										"Qué asqueroso",
										"Tal vez encontró una salida"};
		story.put(STORY_ROOM_1, aux5);
		
		String[] aux6 = {	"¿Quién hizo esto?",
												"Lo sabía",
												"No tuve nada que ver con esto",
												"Maldición ¿Quién fue?",
												"¿Por qué lo hicieron?",
												"Vamos a morir todos"};
		story.put(STORY_MAIN_ROOM, aux6);
		
	}
	/****************************************************************************************/
	private void loadImages(){
		try {			
			exclamationImg = new BufferedImage[2];
			
			exclamationImg[0] = ImageIO.read(getClass()
					.getResourceAsStream("/dialog/exclamation.png"));
			
			exclamationImg[1] = ImageIO.read(getClass()
					.getResourceAsStream("/dialog/exclamation_alert.png"));
			
			speechBallon = new ArrayList<BufferedImage>();
			
			speechBallon.add(ImageIO.read(getClass()
					.getResourceAsStream("/dialog/speech_balloon_normal.png")));
			speechBallon.add(ImageIO.read(getClass()
					.getResourceAsStream("/dialog/speech_balloon_medium.png")));
			speechBallon.add(ImageIO.read(getClass()
					.getResourceAsStream("/dialog/speech_balloon_high.png")));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/****************************************************************************************/
	private void startTimers(){
		timerExclamation = new Timer(countdownExclamation, new ActionListener() { 
			@Override 
			public void actionPerformed(ActionEvent ae) { 
				if (exclamation) 
					exclamation = false;
				else 
					exclamation = true;
			} 
		});
		
		timerCharactersSpeaking = new Timer(countdownCharactersSpeaking, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (setOfDialogues.size() == 0) {
					characterSpeaking = null;
					timerCharactersSpeaking.stop();
					return;
				}
				@SuppressWarnings("unchecked")
				ArrayList<Entity> aux = (ArrayList<Entity>) stage.getCharacters().clone();
				aux.add(stage.getCurrentCharacter());
				characterSpeaking = (Entity) RandomItemArrayList.getRandomItemFromArrayList(aux);
				aux = null;
				if (!characterSpeaking.isVisible()) 
					characterSpeaking = null;
				else {
					characterSpeakingDialog = setOfDialogues.get(0);
					setOfDialogues.remove(0);
				}
			}
		});
		
		timerAwakeningDialogues = new Timer(countdownAwakeningDialogues, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setOfDialogues = new ArrayList<String>(Arrays.asList(thoughts.get(THOUGHTS_AWAKENING)));
				dialogueImage = speechBallon.get(0);
				timerCharactersSpeaking.start();
				timerAwakeningDialogues.stop();
			}
		});
		timerAwakeningDialogues.start();
				
	}
	/****************************************************************************************/
	private void characterClose(){
		timerExclamation.start();
	}
	/****************************************************************************************/
	private void characterFar(){
		timerExclamation.stop();
		exclamation = false;
	}
	/****************************************************************************************/
	public void update(){
		if (stage.getCurrentCharacter().getCharacterClose() != null) 
			characterClose();
		else 
			characterFar();
	}
	/****************************************************************************************/
	public void draw(Graphics2D g) {
		
		if (exclamation) {
			if (stage.getCurrentCharacter().getCharacterClose().getDescription().equals("Jason")) {
				timerExclamation.setDelay(200);
				g.drawImage(exclamationImg[1],
					stage.getCurrentCharacter().getX() - characterWidth / 3, 
					stage.getCurrentCharacter().getY() - exclamationImg[0].getHeight() - characterHeight, 
				null);
			}
			else if (!stage.getCurrentCharacter().getCharacterClose().getDescription().equals("Jason")){
				timerExclamation.setDelay(500);
				g.drawImage(exclamationImg[0],
					stage.getCurrentCharacter().getX() - characterWidth / 3, 
					stage.getCurrentCharacter().getY() - exclamationImg[0].getHeight() - characterHeight, 
				null);
			}
		}
		
		if(stage.isSecondaryMenu()){
			g.setColor(Color.WHITE);
			g.drawString("Resume (R)", 
				GamePanel.RESOLUTION_WIDTH / 2 + 50, 
				-50 + GamePanel.RESOLUTION_HEIGHT / 2);
			g.drawString("Main Menu (M)", 
				GamePanel.RESOLUTION_WIDTH / 2 + 50, 
				GamePanel.RESOLUTION_HEIGHT / 2);
			g.drawString("Quit Game (Q)", 
				GamePanel.RESOLUTION_WIDTH / 2 + 50, 
				50 + GamePanel.RESOLUTION_HEIGHT / 2);
		}
		
		if (characterSpeaking != null) {
			g.drawImage(dialogueImage,
				characterSpeaking.getX() - characterWidth , 
				characterSpeaking.getY() - dialogueImage.getHeight() - characterHeight, 
				null);
						
			g.setColor(fontColor);
			g.setFont(dialogueFont);
						
			int x = characterSpeaking.getX() - 30;
			int y = characterSpeaking.getY() - 180;
			g.drawString(characterSpeakingDialog, x, y);
		}		
	}
	
}

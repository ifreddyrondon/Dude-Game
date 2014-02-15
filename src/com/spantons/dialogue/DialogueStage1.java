package com.spantons.dialogue;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import com.spantons.entity.Entity;
import com.spantons.gameState.Level1Stage;
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
	
	private ArrayList<BufferedImage[]> sprites;
	private BufferedImage[] exclamationImg;
	
	private Color fontColor;
	private Font dialogueFont;
	
	private Timer timerExclamation;
	private int countdownExclamation = 500; 
	private boolean exclamation;
	
	private int characterWidth;
	private int characterHeight;
	
	/****************************************************************************************/
	public DialogueStage1(Stage _stage) {
		stage = _stage;
		fontColor = Color.BLACK;
		dialogueFont = new Font("Century Gothic", Font.PLAIN, 16);
		
		characterWidth = stage.getCurrentCharacter().getSpriteWidth();
		characterHeight = stage.getCurrentCharacter().getSpriteHeight();
		
		loadImages();
		
		timerExclamation = new Timer(countdownExclamation, new ActionListener() { 
			@Override 
			public void actionPerformed(ActionEvent ae) { 
				if (exclamation) 
					exclamation = false;
				else 
					exclamation = true;
			} 
		}); 
		
		// THOUGHTS ----------------------------------------------------------------------------------------
		thoughts = new HashMap<Integer, String[]>();
		
		String[] aux = {	"Odio este sitio", 
				"seguramente alguno de ellos me trajo hasta aca",
				"deber�a deshacerme de ellos"};
		thoughts.put(THOUGHTS_RAMDON, aux);

		String[] aux2 = {"Debemos salir de aqu�",
				"Hay que buscar una salida",
				"Debe haber una puerta en alg�n lado"};
		thoughts.put(THOUGHTS_WANTOUT, aux2);
		
		String[] aux3 = {	"Hey qu� hago aqu�",
				"Quienes son ustedes",
				"Qu� sucede",
				"???"};
		thoughts.put(THOUGHTS_AWAKENING, aux3);
				
		
		// HELP ----------------------------------------------------------------------------------------
		help = new HashMap<Integer, String[]>();
		
		String[] aux4 = {	"Parece que hay algo detr�s que no deja abrirla",
												"necesitamos una palanca"};
		help.put(HELP_0_BATHROOM, aux4);
		
		// STORY ----------------------------------------------------------------------------------------
		story = new HashMap<Integer, String[]>();
		String[] aux5 = {"Falta alguien",
										"Seguro se qued� para usar el ba�o",
										"Qu� asqueroso",
										"Tal vez encontr� una salida"};
		story.put(STORY_ROOM_1, aux5);
		
		String[] aux6 = {	"�Qui�n hizo esto?",
												"Lo sab�a",
												"No tuve nada que ver con esto",
												"Maldici�n �Qui�n fue?",
												"�Por qu� lo hicieron?",
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
			
			BufferedImage[] speechBallon = new BufferedImage[3];
			
			speechBallon[0] = ImageIO.read(getClass()
					.getResourceAsStream("/dialog/speech_balloon_normal.png"));
			speechBallon[1] = ImageIO.read(getClass()
					.getResourceAsStream("/dialog/speech_balloon_medium.png"));
			speechBallon[2] = ImageIO.read(getClass()
					.getResourceAsStream("/dialog/speech_balloon_high.png"));

			sprites = new ArrayList<BufferedImage[]>();
			sprites.add(speechBallon);
						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/****************************************************************************************/
	public void characterClose(){
		timerExclamation.start();
	}
	/****************************************************************************************/
	public void characterFar(){
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
					stage.getCurrentCharacter().getX() - characterWidth / 2 /2 , 
					stage.getCurrentCharacter().getY() - exclamationImg[0].getHeight() - characterHeight / 2 - 10, 
				null);
			}
			else if (!stage.getCurrentCharacter().getCharacterClose().getDescription().equals("Jason")){
				timerExclamation.setDelay(500);
				g.drawImage(exclamationImg[0],
					stage.getCurrentCharacter().getX() - characterWidth / 2 /2 , 
					stage.getCurrentCharacter().getY() - exclamationImg[0].getHeight() - characterHeight / 2 - 10, 
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
		
				
		//BufferedImage[] aux = sprites.get(STORY);
				//String[] dialogs = thoughts.get(THOUGHTS_AWAKENING);

//		boolean flinching2 = true;
//		double flinchingTime2;
		/*
		for (int i = 0; i < dialogs.length; i++) {
			
			if (flinching) {
				long elapsedTime = (System.nanoTime() - flinchingTime) / 1000000;
				if (elapsedTime > 2000) 
					flinching = false;
			
			} else {
				
//				while (flinching2) {
					
					g.drawImage(aux[0],
							_characters.get(i).getX() - characterHalfWidth, 
							_characters.get(i).getY() - aux[0].getHeight() - characterHalfHeight, 
							null);
							
					g.setColor(fontColor);
					g.setFont(dialogueFont);
							
					int x = _characters.get(i).getX();
					int y = _characters.get(i).getY() - aux[0].getHeight() ;
					g.drawString(dialogs[i], x, y);
					
//				}
				
				
					
				flinching = true;
				flinchingTime = System.nanoTime();
			}
		}
			*/	
		
	}
	
}

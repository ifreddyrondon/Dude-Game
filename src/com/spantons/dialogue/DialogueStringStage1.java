package com.spantons.dialogue;

import java.util.HashMap;
import java.util.Map;

public class DialogueStringStage1 {
	
	private static final int THOUGHTS_RAMDON = 0;
	private static final int THOUGHTS_WANTOUT = 1;
	private static final int THOUGHTS_AWAKENING = 2;
	
	private static final int HELP_0_BATHROOM = 0;
	
	private static final int STORY_ROOM_1 = 0;
	private static final int STORY_MAIN_ROOM = 1;
	
	public Map<Integer, String[]> thoughts;
	public Map<Integer, String[]> help;
	public Map<Integer, String[]> story;
	
	public DialogueStringStage1() {
		
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
	
}

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
	
}

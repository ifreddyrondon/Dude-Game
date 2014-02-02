package com.spantons.dialogue;

import java.util.Vector;

public class DialogueStage1 {
	
	private static final int THOUGHTS_RAMDON = 0;
	private static final int THOUGHTS_WANTOUT = 1;
	private static final int THOUGHTS_AWAKENING = 2;
	
	private static final int HELP_0_BATHROOM = 0;
	
	private static final int STORY_ROOM_1 = 0;
	private static final int STORY_MAIN_ROOM = 1;
	
	
	public Vector<String[]> thoughts;
	public Vector<String[]> help;
	public Vector<String[]> story;
	
	public DialogueStage1() {
		// THOUGHTS ----------------------------------------------------------------------------------------
		thoughts = new Vector<String[]>();
		
		String[] THOUGHTS_RAMDON = {	"Odio este sitio", 
												"seguramente alguno de ellos me trajo hasta aca",
												"debería deshacerme de ellos"};
		thoughts.add(THOUGHTS_RAMDON);

		String[] THOUGHTS_WANTOUT = {"Debemos salir de aquí",
												"Hay que buscar una salida",
												"Debe haber una puerta en algún lado"};
		thoughts.add(THOUGHTS_WANTOUT);
		
		String[] THOUGHTS_AWAKENING = {	"Hey qué hago aquí",
													"Quienes son ustedes",
													"Qué sucede",
													"???"};
		thoughts.add(THOUGHTS_AWAKENING);
		
		
		// HELP ----------------------------------------------------------------------------------------
		help = new Vector<String[]>();
		
		String[] HELP_0_BATHROOM = {	"Parece que hay algo detrás que no deja abrirla",
												"necesitamos una palanca"};
		help.add(HELP_0_BATHROOM);
		
		// STORY ----------------------------------------------------------------------------------------
		story = new Vector<String[]>();
		String[] STORY_ROOM_1 = {"Falta alguien",
										"Seguro se quedó para usar el baño",
										"Qué asqueroso",
										"Tal vez encontró una salida"};
		help.add(STORY_ROOM_1);
		
		String[] STORY_MAIN_ROOM = {	"¿Quién hizo esto?",
												"Lo sabía",
												"No tuve nada que ver con esto",
												"Maldición ¿Quién fue?",
												"¿Por qué lo hicieron?",
												"Vamos a morir todos"};
		help.add(STORY_MAIN_ROOM);
		
	}
	
}

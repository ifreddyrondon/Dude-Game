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
												"deber�a deshacerme de ellos"};
		thoughts.add(THOUGHTS_RAMDON);

		String[] THOUGHTS_WANTOUT = {"Debemos salir de aqu�",
												"Hay que buscar una salida",
												"Debe haber una puerta en alg�n lado"};
		thoughts.add(THOUGHTS_WANTOUT);
		
		String[] THOUGHTS_AWAKENING = {	"Hey qu� hago aqu�",
													"Quienes son ustedes",
													"Qu� sucede",
													"???"};
		thoughts.add(THOUGHTS_AWAKENING);
		
		
		// HELP ----------------------------------------------------------------------------------------
		help = new Vector<String[]>();
		
		String[] HELP_0_BATHROOM = {	"Parece que hay algo detr�s que no deja abrirla",
												"necesitamos una palanca"};
		help.add(HELP_0_BATHROOM);
		
		// STORY ----------------------------------------------------------------------------------------
		story = new Vector<String[]>();
		String[] STORY_ROOM_1 = {"Falta alguien",
										"Seguro se qued� para usar el ba�o",
										"Qu� asqueroso",
										"Tal vez encontr� una salida"};
		help.add(STORY_ROOM_1);
		
		String[] STORY_MAIN_ROOM = {	"�Qui�n hizo esto?",
												"Lo sab�a",
												"No tuve nada que ver con esto",
												"Maldici�n �Qui�n fue?",
												"�Por qu� lo hicieron?",
												"Vamos a morir todos"};
		help.add(STORY_MAIN_ROOM);
		
	}
	
}

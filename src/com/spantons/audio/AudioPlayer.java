package com.spantons.audio;

import javax.sound.sampled.*;

public class AudioPlayer {
<<<<<<< HEAD

	private Clip clip;

	public AudioPlayer(String s) {

		try {
			AudioInputStream ais = AudioSystem
					.getAudioInputStream(getClass()
							.getResourceAsStream(s));

			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED,
					baseFormat.getSampleRate(), 16,
					baseFormat.getChannels(),
					baseFormat.getChannels() * 2,
					baseFormat.getSampleRate(), false);

			AudioInputStream dais = AudioSystem.getAudioInputStream(
					decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/****************************************************************************************/
	public void play() {
		if (clip == null)
			return;

		stop();
		clip.setFramePosition(0);
		clip.start();
	}
	/****************************************************************************************/
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	/****************************************************************************************/
	public void stop() {
		if (clip.isRunning())
			clip.stop();
	}
	/****************************************************************************************/
	public void close() {
		stop();
		clip.close();
	}

}
=======
	
	private Clip clip;
	
	public AudioPlayer (String s){
		
		try{
			AudioInputStream ais = AudioSystem.getAudioInputStream(
					getClass().getResourceAsStream(s));
			
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED,
					baseFormat.getSampleRate(),16,baseFormat.getChannels(),
					baseFormat.getChannels() * 2,
					baseFormat.getSampleRate(),false);	
			
			AudioInputStream dais = AudioSystem.getAudioInputStream
					(decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
		}
		catch(Exception e){
			
			e.printStackTrace();			
		}
		
		
	}
	
	public void play(){
		
		if(clip == null) return;
		stop();
		clip.setFramePosition(0);//para revovinar la pista de sonido
		clip.start();
		
	}
	
	public void loop(){
		
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		
		
	}
	
	public void stop(){
		
		if(clip.isRunning()) 
			clip.stop();
		
	}
	
	public void close(){
		
		stop();
		clip.close();
		
	}
		
	}


>>>>>>> 88e447555dd79227f26fc4022409c65f6c639550

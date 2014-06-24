package com.spantons.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.spantons.main.GamePanel;
import com.spantons.stagesLevel.StagesLevel;

public class Hud {

	private StagesLevel stage;
	private Color fontColor;
	private Font descriptionFont;
	private Font attributesFont;
	private Font countdownFont;
	private BufferedImage backgroundHud;
	private String countdown;

	/****************************************************************************************/
	public Hud(StagesLevel _stage) {
		try {
			stage = _stage;
			backgroundHud = ImageIO.read(getClass().getResourceAsStream("/hud/glass.png"));
			fontColor = Color.WHITE;
			descriptionFont = new Font("Century Gothic", Font.BOLD, 20);
			attributesFont = new Font("Century Gothic", Font.PLAIN, 15);
			countdownFont = new Font("Century Gothic", Font.BOLD, 35);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/****************************************************************************************/
	public void update(String _countdown) {
		countdown = _countdown;
	}
	
	/****************************************************************************************/
	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.setFont(countdownFont);
		g.drawString(countdown, GamePanel.RESOLUTION_WIDTH / 2 - 20, 55);
		
		/***********/
		g.drawImage(backgroundHud, 7, 30, null);
		
		g.setColor(fontColor);
		g.setFont(descriptionFont);
		g.drawString(stage.getCurrentCharacter().description, 15, 50);
		
		g.drawImage(stage.getCurrentCharacter().face,18, 63, null);
		
		g.setFont(attributesFont);
		
		if (stage.getCurrentCharacter().getHealth() > 3.5) 
			g.setColor(Color.GREEN);
		else if (stage.getCurrentCharacter().getHealth() <= 3.5 && stage.getCurrentCharacter().getHealth() > 2) 
			g.setColor(Color.WHITE);
		else if (stage.getCurrentCharacter().getHealth() <= 2) 
			g.setColor(Color.RED);
		
		g.drawString("Vida: "+ String.format("%.1f", stage.getCurrentCharacter().getHealth()) 
				+ "/" + String.format("%.1f", stage.getCurrentCharacter().getMaxHealth()), 80, 68);
		
		g.setColor(Color.WHITE);
		g.drawString("Maldad: "+ stage.getCurrentCharacter().perversity 
				+ "/" + stage.getCurrentCharacter().maxPerversity, 80, 87);
		g.drawString("Golpe: "+ String.format("%.1f", stage.getCurrentCharacter().getDamage()), 80, 106);
		
		/***********/
		if (stage.getCurrentCharacter().characterClose != null) {
			
			int rightSizeMirrorProportion =  GamePanel.RESOLUTION_WIDTH - backgroundHud.getWidth();
			
			g.drawImage(backgroundHud, rightSizeMirrorProportion - 7, 30, null);
			
			if (stage.getCurrentCharacter().characterClose.update instanceof UpdateEnemy) 
				g.setColor(Color.RED);
			else
				g.setColor(fontColor);
			
			g.setFont(descriptionFont);
			g.drawString(stage.getCurrentCharacter().characterClose.description, rightSizeMirrorProportion, 50);
			
			g.drawImage(stage.getCurrentCharacter().characterClose.face,rightSizeMirrorProportion, 63, null);
			
			g.setFont(attributesFont);
			if (stage.getCurrentCharacter().characterClose.getHealth() > 3.5) 
				g.setColor(Color.GREEN);
			else if (stage.getCurrentCharacter().characterClose.getHealth() <= 3.5 
					&& stage.getCurrentCharacter().characterClose.getHealth() > 2) 
				g.setColor(Color.WHITE);
			else if (stage.getCurrentCharacter().characterClose.getHealth() <= 2) 
				g.setColor(Color.RED);
			
			g.drawString("Vida: "+ String.format("%.1f", stage.getCurrentCharacter().characterClose.getHealth()) 
					+ "/" + String.format("%.1f", stage.getCurrentCharacter().characterClose.getMaxHealth()), rightSizeMirrorProportion + 60, 68);
			
			g.setColor(Color.WHITE);
			g.drawString("Maldad: "+ stage.getCurrentCharacter().characterClose.perversity 
					+ "/" + stage.getCurrentCharacter().characterClose.maxPerversity, rightSizeMirrorProportion + 60, 87);
			g.drawString("Golpe: "+ String.format("%.1f", stage.getCurrentCharacter().characterClose.getDamage()), rightSizeMirrorProportion + 60, 106);
		}
		
	}
	
	
}

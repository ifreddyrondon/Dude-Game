package com.spantons.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.spantons.gameStages.StagesLevels;
import com.spantons.main.GamePanel;

public class Hud {

	private StagesLevels stage;
	private Color fontColor;
	private Font descriptionFont;
	private Font attributesFont;
	private Font countdownFont;
	private BufferedImage backgroundHud;

	public Hud(StagesLevels _stage) {
		try {
			stage = _stage;
			backgroundHud = ImageIO.read(getClass().getResourceAsStream("/hud/glass.png"));
			fontColor = Color.WHITE;
			descriptionFont = new Font("Century Gothic", Font.PLAIN, 20);
			attributesFont = new Font("Century Gothic", Font.PLAIN, 15);
			countdownFont = new Font("Century Gothic", Font.PLAIN, 35);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void Draw(Graphics2D g, String countdown) {
		
		g.drawImage(backgroundHud, 7, 30, null);
		
		g.setColor(fontColor);
		g.setFont(descriptionFont);
		g.drawString(stage.getCurrentCharacter().getDescription(), 15, 50);
		
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
		g.drawString("Maldad: "+ stage.getCurrentCharacter().getPerversity() 
				+ "/" + stage.getCurrentCharacter().getMaxPerversity(), 80, 87);
		g.drawString("Golpe: "+ String.format("%.1f", stage.getCurrentCharacter().getDamage()), 80, 106);
		
		g.setFont(countdownFont);
		g.drawString(countdown, GamePanel.RESOLUTION_WIDTH / 2 - 20, 55);
	}
	
	
}

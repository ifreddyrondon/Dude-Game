package com.spantons.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.spantons.main.GamePanel;

public class Hud {

	private Color fontColor;
	private Font descriptionFont;
	private Font attributesFont;
	private Font countdownFont;
	private BufferedImage backgroundHud;
	
	public Hud() {
		try {
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
	
	public void Draw(Graphics2D g, Entity character, String countdown) {
		
		g.drawImage(backgroundHud, 7, 30, null);
		
		g.setColor(fontColor);
		g.setFont(descriptionFont);
		g.drawString(character.getDescription(), 15, 48);
		
		g.drawImage(character.face,18, 53, null);
		
		g.setFont(attributesFont);
		g.drawString("Vida: "+ character.getHealth() + "/" + character.getMaxHealth(), 80, 66);
		g.drawString("Maldad: "+ character.getPerversity() + "/" + character.getMaxPerversity(), 80, 85);
		
		g.setFont(countdownFont);
		g.drawString(countdown, GamePanel.RESOLUTION_WIDTH / 2 - 20, 55);
	}
	
	
}

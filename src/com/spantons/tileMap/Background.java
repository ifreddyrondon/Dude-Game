package com.spantons.tileMap;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.imageio.ImageIO;

import com.spantons.main.GamePanel;

public class Background {

	private Image image;

	private double x;
	private double y;
	private double dx;
	private double dy;

	private double movementScale;

	public Background(String imageSource, double movementScale) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream(
					imageSource));
			
			image = image.getScaledInstance(GamePanel.RESOLUTION_WIDTH, GamePanel.RESOLUTION_HEIGHT, Image.SCALE_FAST);
			
			this.movementScale = movementScale;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setPosition(double x, double y) {
		this.x = (x * movementScale) % GamePanel.RESOLUTION_WIDTH;
		this.y = (y * movementScale) % GamePanel.RESOLUTION_HEIGHT;
	}

	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public void update() {
		x += (dx * movementScale) % GamePanel.RESOLUTION_WIDTH;
		y += (dy * movementScale) % GamePanel.RESOLUTION_HEIGHT;
	}

	public void draw(Graphics2D g) {
		
		g.drawImage(image, (int) x, (int) y, null);
		
		if (x < 0)
			g.drawImage(image, (int) x + GamePanel.RESOLUTION_WIDTH, (int) y, null);
			//System.out.println("x < 0");
		else if (x > 0)
			System.out.println("x > 0");
			//g.drawImage(image, (int) x - GamePanel.RESOLUTION_WIDTH, (int) y, null);
					
	}
	
}

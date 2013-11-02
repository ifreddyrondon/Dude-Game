package com.spantons.tileMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.spantons.main.GamePanel;

public class Background {

	private BufferedImage image;
	
	private double x;
	private double y;
	private double dx;
	private double dy;
	
	private double movementScale;
	
	public Background(String imageSource, double movementScale) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imageSource));
			this.movementScale = movementScale;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setPosition(double x, double y){
		this.x = (x * movementScale) % GamePanel.WIDTH;
		this.y = (y * movementScale) % GamePanel.HEIGHT;
	}
	
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public void update(){
		x += (dx * movementScale) % GamePanel.WIDTH;
		y += (dy * movementScale) % GamePanel.HEIGHT;
	}
	
	public void draw(Graphics2D g){		
		g.drawImage(image, (int)x, (int)y, null);
		
		if (x < 0) 
			g.drawImage(image, (int)x + GamePanel.WIDTH, (int)y, null);
		else if (x > 0)
			g.drawImage(image, (int)x - GamePanel.WIDTH, (int)y, null);
	}
	
}

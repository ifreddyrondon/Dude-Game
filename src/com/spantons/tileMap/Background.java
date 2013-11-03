package com.spantons.tileMap;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.imageio.ImageIO;

import com.spantons.main.GamePanel;

public class Background {

	private Image image;
	private boolean repeat;
	private double movementScale;

	private double x;
	private double y;
	private double dx;
	private double dy;

	public Background(String imageSource, double movementScale, boolean repeat) {
		try {
			this.repeat = repeat;

			image = ImageIO.read(getClass().getResourceAsStream(
					imageSource));

			if (!repeat)
				image = image.getScaledInstance(
						GamePanel.RESOLUTION_WIDTH,
						GamePanel.RESOLUTION_HEIGHT,
						Image.SCALE_FAST);

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

		if (repeat) {
			int iw = image.getWidth(null);
			int ih = image.getHeight(null);
			if (iw > 0 && ih > 0) {
				for (int x = 0; x < GamePanel.RESOLUTION_WIDTH; x += iw) {
					for (int y = 0; y < GamePanel.RESOLUTION_HEIGHT; y += ih) 
						g.drawImage(image, x, y, iw, ih, null);
				}
			}
		} else {
			g.drawImage(image, (int) x, (int) y, null);

			if (x < 0)
				g.drawImage(image,
						(int) x + GamePanel.RESOLUTION_WIDTH,
						(int) y, null);
			else if (x > 0)
				g.drawImage(image,
						(int) x - GamePanel.RESOLUTION_WIDTH,
						(int) y, null);

		}

	}

}

package com.spantons.stagesMenu;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.imgscalr.Scalr;

import com.spantons.singleton.ImageCache;

public class ImageComposite {

	protected BufferedImage image;
	protected int x;
	protected int y;
	
	/****************************************************************************************/
	public ImageComposite(String _pathImage, int _resizeLength, int _x, int _y) {
		image = ImageCache.getInstance().getImage(_pathImage);
		x = _x;
		y = _y;
		if (_resizeLength != 0) 
			image = Scalr.resize(image, _resizeLength);
	}
	
	/****************************************************************************************/
	public void draw(Graphics2D g) {
		g.drawImage(image,x,y, null);	
	} 
	
}

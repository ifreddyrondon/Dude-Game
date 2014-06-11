package com.spantons.stagesMenu;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class ImagesIntoScene {
	
	private ArrayList<ImageComposite> images;
	
	/****************************************************************************************/
	public ImagesIntoScene(ArrayList<ImageComposite> _images) {
		if (_images.size() > 0) {
			images = _images;
		} else
			System.err.println("err ImagesIntoScene not images");
	}
	
	/****************************************************************************************/
	public void draw(Graphics2D g) {
		for (ImageComposite image : images) 
			image.draw(g);
	}
	
}

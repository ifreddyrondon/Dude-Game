package com.spantons.utilities;

import java.awt.Dimension;

public class ScaledDimension {

	public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

		    int originalWidth = imgSize.width;
		    int originalHeight = imgSize.height;
		    int boundWidth = boundary.width;
		    int boundHeight = boundary.height;
		    int newWidth = originalWidth;
		    int newHeight = originalHeight;

		    // first check if we need to scale width
		    if (originalWidth > boundWidth) {
		        //scale width to fit
		        newWidth = boundWidth;
		        //scale height to maintain aspect ratio
		        newHeight = (newWidth * originalHeight) / originalWidth;
		    }

		    // then check if we need to scale even with the new height
		    if (newHeight > boundHeight) {
		        //scale height to fit instead
		        newHeight = boundHeight;
		        //scale width to maintain aspect ratio
		        newWidth = (newHeight * originalWidth) / originalHeight;
		    }

		    return new Dimension(newWidth, newHeight);
		}
	
}

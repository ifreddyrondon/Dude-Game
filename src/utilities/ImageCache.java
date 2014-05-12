package utilities;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ImageCache {

	private static ImageCache instace;
	
	private Map<String, BufferedImage> map = new HashMap<String, BufferedImage>();

	/****************************************************************************************/
	private ImageCache() {

	}

	/****************************************************************************************/
	private synchronized static void createInstance() {
		if (instace == null) {
			instace = new ImageCache();
		}
	}

	/****************************************************************************************/
	public static ImageCache getInstance() {
		createInstance();
		return instace;
	}
	
	/****************************************************************************************/
	public BufferedImage getImage(String _key) {
	        BufferedImage image = map.get(_key);
	        if (image == null) {
	            image = createImage(_key);
	            map.put(_key, image);
	        }
	        return image;
	    }

	/****************************************************************************************/
	private BufferedImage createImage(String _key) {
		BufferedImage image = null;
		
		try {
			image = ImageIO
					.read(getClass()
							.getResourceAsStream(_key));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return image;
	}
}

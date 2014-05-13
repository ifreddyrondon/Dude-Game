package singleton;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FontCache {

private static FontCache instace;
	
	private Map<String, Font> map = new HashMap<String, Font>();

	/****************************************************************************************/
	private FontCache() {

	}

	/****************************************************************************************/
	private synchronized static void createInstance() {
		if (instace == null) {
			instace = new FontCache();
		}
	}

	/****************************************************************************************/
	public static FontCache getInstance() {
		createInstance();
		return instace;
	}
	
	/****************************************************************************************/
	public Font getFont(String _key) {
	        Font font = map.get(_key);
	        if (font == null) {
	      	  font = createFont(_key);
	            map.put(_key, font);
	        }
	        return font;
	    }

	/****************************************************************************************/
	private Font createFont(String _key) {
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream(_key));
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return font;
	}
	
}

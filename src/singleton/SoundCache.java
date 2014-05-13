package singleton;

import java.util.HashMap;
import java.util.Map;

import com.spantons.audio.AudioPlayer;

public class SoundCache {

private static SoundCache instace;
	
	private Map<String, AudioPlayer> map = new HashMap<String, AudioPlayer>();

	/****************************************************************************************/
	private SoundCache() {

	}

	/****************************************************************************************/
	private synchronized static void createInstance() {
		if (instace == null) {
			instace = new SoundCache();
		}
	}

	/****************************************************************************************/
	public static SoundCache getInstance() {
		createInstance();
		return instace;
	}
	
	/****************************************************************************************/
	public AudioPlayer getSound(String _key) {
		AudioPlayer player = map.get(_key);
	        if (player == null) {
	      	player = createSound(_key);
	            map.put(_key, player);
	        }
	        return player;
	    }

	/****************************************************************************************/
	private AudioPlayer createSound(String _key) {
		AudioPlayer player = null;
		player = new AudioPlayer(_key);
		return player;
	}
	
	/****************************************************************************************/
	public void stopAllSound() {
		for (String key : map.keySet()) {
			map.get(key).stop();
		}
	}
	
	/****************************************************************************************/
	public void closeAllSound() {
		for (String key : map.keySet()) {
			map.get(key).close();
		}
	}
}

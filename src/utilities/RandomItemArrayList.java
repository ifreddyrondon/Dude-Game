package utilities;

import java.util.ArrayList;
import java.util.Random;

public class RandomItemArrayList {

	private static Random randomGenerator;
	
	public static <E> Object getRandomItemFromArrayList(ArrayList<E> _list) {
		randomGenerator = new Random();
		return _list.get(randomGenerator.nextInt(_list.size()));
	}
	
}

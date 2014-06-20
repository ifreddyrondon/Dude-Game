package com.spantons.utilities;

import java.util.ArrayList;
import java.util.Arrays;

public class ArraysUtil {

	public static <T> T[] concat(T[] first, T[] second) {
		T[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

	/****************************************************************************************/
	public static ArrayList<String> getParts(String string, int partitionSize) {
		ArrayList<String> parts = new ArrayList<String>();
		int len = string.length();
		for (int i = 0; i < len; i += partitionSize) {
			parts.add(string.substring(i,
					Math.min(len, i + partitionSize)));
		}
		return parts;
	}

}

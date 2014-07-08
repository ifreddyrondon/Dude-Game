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
	public static ArrayList<String> getPartsOfStringByPartitionSize(String string, int partitionSize) {
		ArrayList<String> parts = new ArrayList<String>();
		int len = string.length();
		for (int i = 0; i < len; i += partitionSize) {
			parts.add(string.substring(i,
					Math.min(len, i + partitionSize)));
		}
		return parts;
	}
	
	/****************************************************************************************/
	public static ArrayList<String> getPartsOfStringByPartitionSizeWithoutCutPhrase(String string, int partitionSize) {
		ArrayList<String> parts = new ArrayList<String>();
		
		int len = string.length();
		int i = 0;
		int minCut = 0;
		int maxCut = 0;
		boolean spaceValidator = false;
		
		while (i <= len) {
			minCut = i;
			maxCut = Math.min(len, i + partitionSize);
			
			if (maxCut != len) 
				spaceValidator = string.substring(maxCut, maxCut+1).equals(" ");
			else {
				parts.add(string.substring(minCut,len));
				return parts;
			}
			
			if (spaceValidator) 
				parts.add(string.substring(minCut,maxCut));
			else {
				while (!spaceValidator) {
					maxCut--;
					spaceValidator = string.substring(maxCut, maxCut+1).equals(" ");
				}
				parts.add(string.substring(minCut,maxCut));
			}
			i = maxCut + 1;
		}
		
		return parts;
	}
}

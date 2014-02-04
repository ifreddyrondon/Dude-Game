package utilities;

public class ToHours {

	public static String SecondsToHours(int _seconds){
		
		int hours = _seconds/3600;
		int minutes = (_seconds-(3600*hours))/60;
		int seconds = _seconds-((hours*3600)+(minutes*60));
		 
		return Integer.toString(minutes) + ":" + Integer.toString(seconds);
	}
	
}

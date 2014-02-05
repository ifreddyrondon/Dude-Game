package utilities;

public class ToHours {

	public static String SecondsToHours(int _seconds){
		
		int hours = _seconds/3600;
		int minutes = (_seconds-(3600*hours))/60;
		int seconds = _seconds-((hours*3600)+(minutes*60));
		 
		String secondsString;
		if (seconds < 10) 
			secondsString = "0" + Integer.toString(seconds);
		else
			secondsString = Integer.toString(seconds);
		
		return Integer.toString(minutes) + ":" + secondsString;
	}
	
}

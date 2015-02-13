package traffic_analyze;
import traffic_input.Postcodeinput;

public class Postcode_Processing {

	public static String[] createarea(String postcode1 , String postcode2)
	{
		String[] Areacords = new String[2];
		
		//Will get the lang and long and then will split it off to ints
		String temp = Postcodeinput.getcords(postcode1);
		Areacords[0] = temp;
		//Will get the lang and long and then will split it off to ints
		temp = Postcodeinput.getcords(postcode2);
		Areacords[1] = temp;
		return Areacords;
		
	}
	public static Boolean checkifonline()
	{
		
		return false;
	}
}

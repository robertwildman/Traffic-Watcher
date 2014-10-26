package traffic_analyze;
import traffic_input.Postcodeinput;

public class Postcode_Processing {

	public static String[] createarea(String postcode1 , String postcode2)
	{
		//Will get the lang and long and then will split it off to ints
		String temp = Postcodeinput.getcords(postcode1);
		System.out.println(temp);
		String[] postcode1cords = temp.split(",");
		double postcode1long = Double.parseDouble(postcode1cords[0]);
		double postcode1lang = Double.parseDouble(postcode1cords[1]);
		
		//Will get the lang and long and then will split it off to ints
		temp = Postcodeinput.getcords(postcode2);
		String[] postcode2cords = temp.split(",");
		System.out.println(temp);
		double postcode2long = Double.parseDouble(postcode2cords[0]);
		double postcode2lang = Double.parseDouble(postcode2cords[1]);
		//This will put it into a an array that will then be used for the area.
		String[] Areacords = new String[2];
		Areacords[0] = String.valueOf(postcode1lang) +","+String.valueOf(postcode1long);
		Areacords[1] = String.valueOf(postcode2lang) +","+String.valueOf(postcode2long);
		System.out.println(Areacords.toString());
		return Areacords;
		
	}
	public static boolean checkinrange(){
		return false;
	}
}

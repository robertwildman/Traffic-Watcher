package traffic_input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

public class Postcodeinput {
	//This class will deal with the input of data from the postcode website 
	//This will allow other classes to get infomation from the api 
	public static String getcords(String Postcode)
	{
		//This will be the starting point for getting the cords it will then
		//go into getting the json from the website and sorting thought the infomation to get them
		//Makes the url for the postcode 
		Postcode.replace(" ", "%20");
		
		String url = "http://api.postcodes.io/postcodes/"+Postcode;
		JSONObject data = readdata(readwebsite(url));
		System.out.println(data);
		data = data.getJSONObject("result");
		System.out.println(data);
		String cords = String.valueOf(data.getDouble("latitude")) +"," + String.valueOf(data.getDouble("longitude"));  
		System.out.println(cords);
		return cords;
		
	}
	public static String readwebsite(String url)
	{
		//This will read the website and read turn in text form the json.
		try {
			InputStream websitein = new URL(url).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(websitein));
			StringBuilder sb = new StringBuilder();
			String holder = "";
			while ((holder = rd.readLine()) != null) {
			    sb.append(holder);
			}
			return sb.toString();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static JSONObject readdata(String data)
	{
		JSONObject json= new JSONObject(data);
		return json;
	}
}

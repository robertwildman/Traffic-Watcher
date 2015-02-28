package traffic_input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

public class Postcodeinput {
	// This class will deal with the input of data from the postcode website
	// This will allow other classes to get information from the api
	public static String getcords(String Postcode) {
		// This will be the starting point for getting the cords it will then
		// go into getting the json from the website and sorting thought the
		// information to get them
		// Makes the url for the postcode
		Postcode.replace(" ", "%20");
		String url = "http://api.postcodes.io/postcodes/" + Postcode;
		JSONObject data = readdata(readwebsite(url));
		data = data.getJSONObject("result");
		String cords = String.valueOf(data.getDouble("latitude")) + ","
				+ String.valueOf(data.getDouble("longitude"));
		return cords;

	}

	public static JSONObject readdata(String data) {
		// Makes the string input into a JSONObject
		JSONObject json = new JSONObject(data);
		return json;
	}

	public static String readwebsite(String url) {
		// This will read the website and read turn in text for the json.
		try {
			InputStream websitein = new URL(url).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					websitein));
			StringBuilder sb = new StringBuilder();
			String holder = "";
			while ((holder = rd.readLine()) != null) {
				sb.append(holder);
			}
			return sb.toString();
		} catch (MalformedURLException e) {
			// Catch Errors
			e.printStackTrace();
		} catch (IOException e) {
			// Catch Errors
			e.printStackTrace();
		}
		return null;
	}
}

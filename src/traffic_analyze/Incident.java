package traffic_analyze;

public class Incident {

	String title; 
	String desc;
	double lat;
	double longa;
	String type;
	
	public Incident(String intitle,String indesc,double inlat,double inlonga,String type)
	{
		title = intitle;
		desc = indesc;
		lat = inlat;
		longa = inlonga;
		this.type = type;
	}
	public String printdata()
	{
		String output = "Title: " + title +"\n";
		return output;
		
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLonga() {
		return longa;
	}

	public void setLonga(double longa) {
		this.longa = longa;
	}
	
}

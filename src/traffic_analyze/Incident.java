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
	public Boolean inrange(double inlong,double inlong2,double inlat,double inlat2)
	{
		// 
		if(lat <= inlat2 && lat >= inlat && longa <= inlong && longa >= inlong2)
		{
			//In range!
			return true;
		}else
		{
			//Out of range!
			return false;
		}
				
	}
	public String getdesc()
	{
		return desc;
	}
	public String gettitle()
	{
		return title;
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

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
		//This will work out what lat and long is bigger
		double highlat = 0,lowlat = 0,highlong = 0,lowlong = 0;
		//This deals with setting the higgest and lowest lat and long. 
		if(inlat > inlat2)
		{
			highlat = inlat;
			lowlat = inlat2;
		}else
		{
			highlat = inlat2;
			lowlat = inlat;
		}
		
		if(inlong > inlong2)
		{
			highlong = inlong;
			lowlong = inlong2;
		}else
		{
			highlong = inlong2;
			lowlong = inlong;
		}
		//Will now check to see if its inrange
		if(lat <= highlat && lat >= lowlat)
		{
			//In range on lat!
			return true;
		}else if(longa <= highlong && longa >= lowlong)
		{
			//In range on long!
			return true;
		}else
		{
			//Out of range on both
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

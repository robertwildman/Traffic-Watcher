package traffic_analyze;

public class Incident {

	String title; 
	String desc;
	double lat;
	double longa;
	int directioncode;
	String type;
	
	public Incident(String intitle,String indesc,double inlat,double inlonga,String type,int directioncode)
	{
		title = intitle;
		desc = indesc;
		lat = inlat;
		longa = inlonga;
		this.type = type;
		this.directioncode = directioncode;
	}
	public Boolean inrange(double inlong,double inlong2,double inlat,double inlat2)
	{
		//This will work out what lat and long is bigger
		double highlat = 0,lowlat = 0,highlong = 0,lowlong = 0;
		//This deals with setting the higgest and lowest lat and long. 
		int tempdirectioncode,rangedirectioncode;
		if(inlat > inlat2)
		{
			highlat = inlat;
			lowlat = inlat2;
			//Going North
			tempdirectioncode=0;
		}else
		{
			highlat = inlat2;
			lowlat = inlat;
			//Going South
			tempdirectioncode=1;
		}
		
		if(inlong > inlong2)
		{
			highlong = inlong;
			lowlong = inlong2;
			//Going East
			if(tempdirectioncode == 0)
			{
				//Going North East
				rangedirectioncode= 1;
			}else
			{
				//Going South East 
				rangedirectioncode= 2;
			}
		}else
		{
			highlong = inlong2;
			lowlong = inlong;
			//Going West 
			if(tempdirectioncode == 0)
			{
				//Going North West
				rangedirectioncode= 4;
			}else
			{
				//Going South West
				rangedirectioncode= 3;
			}
		}
		if(rangedirectioncode == 1 && (directioncode == 1 || directioncode == 2))
		{
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
		}else if(rangedirectioncode == 2 && (directioncode == 3 || directioncode == 2))
		{
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
		else if(rangedirectioncode == 3 && (directioncode == 3 || directioncode == 4))
		{
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
		}else if(rangedirectioncode == 4 && (directioncode == 1 || directioncode == 4))
		{
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
		}else
		{
			return false;
		}
		
				
	}
	public String getdesc()
	{
		return desc;
	}
	public String getsmalltitle()
	{
		if(title.contains("|"))
		{
			 return title.split("|")[0];
		}else if(title.contains("-"))
		{
			 return title.split("-")[0];
		}
		else
		{
			return title;
		}
		
	}
	public String getdirection()
	{
		return String.valueOf(directioncode);
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

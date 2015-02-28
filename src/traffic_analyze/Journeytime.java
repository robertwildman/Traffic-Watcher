package traffic_analyze;

public class Journeytime {
	double tolat;
	double fromlat;
	double tolong;
	double fromlong;
	double travelTime;
	double freeFlowTravelTime;
	double normallyExpectedTravelTime;
	boolean gottime;
	int directioncode = 1;
	String sectionid;
	String toroadmain;
	String tojunction;
	String fromroadmain;
	String fromjunction;
	String direction;

	public Journeytime(double tolat, double fromlat, double tolong,
			double fromlong, String toroadmain,
			String tojunction, String fromroadmain,
			String fromjunction, String direction,String sectionid) {
			//Creates an instance of the journeytime class
		this.sectionid = sectionid;
		this.tolong = tolong;
		this.tolat = tolat;
		this.fromlat = fromlat;
		this.fromlong = fromlong;
		this.toroadmain = toroadmain;
		this.tojunction = tojunction;
		this.fromjunction = fromjunction;
		this.fromroadmain =fromroadmain;
		this.direction = direction;
		this.gottime = false;
	}
	public double getlat()
	{
		//Returns the to junction lat
		return tolat;
	}
	public double getlong()
	{
		//Returns the to junction long
		return tolong;
	}
	public double gettolat()
	{
		//Returns the to junction lat
		return tolat;
	}
	public double getfromlat()
	{
		//Returns the from junction lat
		return fromlat;
	}
	public double gettolong()
	{
		//Returns the to junction long
		return tolong;
	}
	public double getfromlong()
	{
		//Returns the from junction long
		return fromlong;
	}
	public void setalltime(double travelTime, double freeFlowTravelTime,double normallyExpectedTravelTime )
	{
		//Used to set the time aspect to each 
		this.travelTime = travelTime;
		this.freeFlowTravelTime = freeFlowTravelTime;
		this.normallyExpectedTravelTime = normallyExpectedTravelTime;
		this.gottime = true;
		
	}
	public Boolean gottime()
	{
		//Returns the time
		return this.gottime;
	}
	public String getid()
	{
		//Returns the roadid
		return sectionid;
	}
	public double getnormaltime()
	{
		//Returns the normaltime
		return normallyExpectedTravelTime;
	}
	public double getcurrenttime()
	{
		//Returns the current time
		return travelTime;
	}
	public Boolean delayed()
	{
		if(travelTime > normallyExpectedTravelTime)
		{
			//This means that the journey is taking longer then it would be 
			return true;
			
		}else
		{
			//This means it will be ontime or below the normal time 
			return false;
		}
	}
	public String getroad()
	{
		//Returns the main to road
		return toroadmain;
	}
	public String gettoroad()
	{
		//Returns the main to road
		return toroadmain;
	}
	public String getfromroad()
	{
		//Return the main from road
		return fromroadmain;
	}
	public String gettojuction()
	{
		//Returns the main to junction
		return tojunction;
	}
	public String getfromjuction()
	{
		//Returns the from junction
		return fromjunction;
	}
	public Boolean inrange(double inlong,double inlong2,double inlat,double inlat2)
	{
		//This will work out what lat and long is bigger
		double highlat = 0,lowlat = 0;
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
			if(tolat <= highlat && tolat >= lowlat)
			{
				//In range on lat!
				return true;
			}else
			{
				//Out of range on both
				return false;
			}
		}else if(rangedirectioncode == 2 && (directioncode == 3 || directioncode == 2))
		{
			//Will now check to see if its inrange
			if(tolat <= highlat && tolat >= lowlat)
			{
				//In range on lat!
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
			if(tolat <= highlat && tolat >= lowlat)
			{
				//In range on lat!
				return true;
			}else
			{
				//Out of range on both
				return false;
			}
		}else if(rangedirectioncode == 4 && (directioncode == 1 || directioncode == 4))
		{
			//Will now check to see if its inrange
			if(tolat <= highlat && tolat >= lowlat)
			{
				//In range on lat!
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

}

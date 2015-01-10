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
		return this.gottime;
	}
	public String getid()
	{
		return sectionid;
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
		return toroadmain;
	}
	public Boolean inrange(double inlong,double inlong2,double inlat,double inlat2)
	{
		// 
		if(tolat <= inlat2 && tolat >= inlat && tolong <= inlong && tolong >= inlong2 && fromlat <= inlat2 && fromlat >= inlat && fromlong <= inlong && fromlong >= inlong2)
		{
			System.out.println("In Range");
			//In range!
			return true;
		}else
		{
			System.out.println("Out of Range");
			//Out of range!
			return false;
		}
				
	}

}

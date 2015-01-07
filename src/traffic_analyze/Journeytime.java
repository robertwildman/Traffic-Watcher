package traffic_analyze;

public class Journeytime {
	double tolat;
	double fromlat;
	double tolong;
	double fromlong;
	double travelTime;
	double freeFlowTravelTime;
	double normallyExpectedTravelTime;
	String toroadmain;
	String tojunction;
	String fromroadmain;
	String fromjunction;
	String direction;

	public Journeytime(double tolat, double fromlat, double tolong,
			double fromlong, String toroadmain,
			String tojunction, String fromroadmain,
			String fromjunction, String direction) {

		this.tolong = tolong;
		this.tolat = tolat;
		this.fromlat = fromlat;
		this.fromlong = fromlong;
		this.toroadmain = toroadmain;
		this.tojunction = tojunction;
		this.fromjunction = fromjunction;
		this.fromroadmain =fromroadmain;
		this.direction = direction;
	}
	public void setalltime(double travelTime, double freeFlowTravelTime,double normallyExpectedTravelTime )
	{
		
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

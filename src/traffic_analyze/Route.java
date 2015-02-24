package traffic_analyze;

import java.util.ArrayList;

public class Route {

	//This class is used to hold all the infomation that will be need after making up the route and during. 
	//This will be the backbone for the start screen. 
	
	ArrayList<Junction> junctions= new ArrayList<Junction>();
	ArrayList<Road> roads = new ArrayList<Road>();
	public Route(ArrayList<Junction> alljunctions , ArrayList<Road> allroads)
	{
		this.junctions = alljunctions;
		//this.roads = allroads;
	}
	public void addjunction(Junction temp)
	{
		junctions.add(temp);
	}
	
	public ArrayList<Junction> getJunctions() {
		return junctions;
	}
	public void setJunctions(ArrayList<Junction> junctions) {
		this.junctions = junctions;
	}
	public void addroad(Road road)
	{
		roads.add(road);	
	}
	public boolean checkifroadadded(String roadname)
	{
		for(Road temp:roads)
		{
			if(temp.getRoadname().equalsIgnoreCase(roadname))
			{
				return true;
			}
		}
		return false;
	}
	public double getcurrenttime()
	{
		double currenttime = 0;
		for(Junction temp : junctions)
		{
			currenttime = currenttime + temp.getTravelTime();
		}
		return currenttime;
	}
	public double getnormaltime()
	{
		double traveltime = 0;
		for(Junction temp : junctions)
		{
			traveltime = traveltime + temp.getNormallyExpectedTravelTime();
		}
		return traveltime;
	}

	public double getfreeflowtime()
	{
		double freeflow = 0;
		for(Junction temp : junctions)
		{
			freeflow = freeflow + temp.getFreeFlowTravelTime();
		}
		return freeflow;
	}
	public double getdelay()
	{
		return getcurrenttime() - getnormaltime();
	}
	public boolean isdelayed()
	{
		if(getdelay() > 0 )
		{
			//There is an delay 
			return true;
		}else
		{
			return false;
		}
	}
}

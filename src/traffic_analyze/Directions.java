package traffic_analyze;

import java.util.ArrayList;

public class Directions {

	//This class will deal with sorting the data and giving the user directions
	public static ArrayList<Road> orderlist(ArrayList<Road> roads,double tolat , double tolong,double fromlat , double fromlong)
	{
		//This will order a list based on what road the user will meet next so they can plan there journeys better
		double disbetweenlat = Math.abs(tolat - fromlat);
		double disbetweenlong = Math.abs(tolong - fromlong);
		if(disbetweenlat < disbetweenlong)
		{
			//Will favor the lat
			for(int i = 0; i < roads.size() -1 ; i++)
			{
				for(int i1 = 0;i1 < roads.size() - i - 1;i1++)
				{
					if(roads.get(i1).getlong() > roads.get(i1+1).getlong())
					{
						Road temp = roads.get(i1);
						roads.set(i1, roads.get(i1 + 1));
						roads.set(i1+1, temp);

					}
				}
			}
		}
		else
		{
			//Will favor the Long
			for(int i = 0; i < roads.size() - 1; i++)
			{
				for(int i1 = 0;i1 < roads.size() - i - 1;i1++)
				{
					if(roads.get(i1).getlat() > roads.get(i1+1).getlat())
					{
						Road temp = roads.get(i1);
						roads.set(i1, roads.get(i1 + 1));
						roads.set(i1+1, temp);
					}
				}
			}
		}
		return roads;
	}

}

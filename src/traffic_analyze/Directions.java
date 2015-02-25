package traffic_analyze;

import java.util.ArrayList;

import org.openstreetmap.gui.jmapviewer.Coordinate;

import traffic_input.JourneyTime_input;

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

	public static ArrayList<Route> getroute(ArrayList<Coordinate> cords)
	{
		//This methord will be the main methord dealing with the finding the best route 
		//It will be calling man different methords 
		//Will get the starting lat and long and the from lat and long
		double tolat = cords.get(0).getLat();
		double tolng = cords.get(0).getLon();
		double fromlat = cords.get(1).getLat();
		double fromlng = cords.get(1).getLon();
		//The aim of this methord is: 
		//First find the closest junction to the starting point (Finding 2 junctions different roads)  
		//Then finding the closest juction to the end point (Finding 2 junctions different roads)
		//They will be looped trying to find the next junction till they hit the junctions closest to the end point set above 
		//Will be keeping a list of each of the different junctions that gets used 
		//Then will allow the distance of each one 
		//Also the time 

		//First getting raw data of jounrey locations 
		ArrayList<Journeytime> allrawjourney = JourneyTime_input.getJourneys(false);
		//Making a list of Junctions 
		ArrayList<Junction> allrawjunctions = new ArrayList<Junction>();
		for(Journeytime temp : allrawjourney)
		{
			Junction tempjunction = new Junction(temp.gettolat(),temp.gettolong(),temp.getfromlat(),temp.getfromlong(),getint(temp.gettojuction()),temp.gettoroad());
			allrawjunctions.add(tempjunction);
		}
		//Getting the closest junction
		/*ArrayList<Junction>currentroute = new ArrayList<Junction>();
		Junction startjunction = findclosestjunction(allrawjunctions,cords,true);
		Junction endjunction = findclosestjunction(allrawjunctions,cords,false);
		Junction currentjunction = startjunction;
		System.out.println(allrawjunctions.size());
		System.out.println(startjunction.getfromlat() + " "+startjunction.getfromlng() + "   " + startjunction.getdirection());
		System.out.println(endjunction.getfromlat() + " "+endjunction.getfromlng() + "   " + endjunction.getdirection());
		currentroute.add(currentjunction);
		while(endjunction.getfromlat() != currentjunction.getfromlat())
		{
			currentjunction = findnextjunction(allrawjunctions,currentroute,currentjunction,currentjunction.getdirection());
			System.out.println(currentjunction.getfromlat() + " Compared to " + endjunction.getfromlat());
			currentroute.add(currentjunction);
			
		}
		currentroute.add(currentjunction);*/
		//Getting the next ones till the end junction has been meet
		//Setting up temp route for testing 
		Route temp = new Route(allrawjunctions, null);
		ArrayList<Route> temproutes = new ArrayList<Route>();
		temproutes.add(temp);
		return temproutes;
	}
	public static int getint(String input)
	{
		try
		{
			int temp = Integer.parseInt(input);
			return temp;
		}catch(Exception e)
		{

		}
		return 0;
	}
	public static Junction findclosestjunction(ArrayList<Junction> alljunctions, ArrayList<Coordinate> cords,Boolean start)
	{
		//Will get the starting lat and long and the from lat and long
		double tolat = cords.get(0).getLat();
		double tolng = cords.get(0).getLon();
		double fromlat = cords.get(1).getLat();
		double fromlng = cords.get(1).getLon();
		//Find out the direction
		int tempdirectioncode,directioncode;
		if (tolat < fromlat) {
			// Going South
			tempdirectioncode = 0;
		} else {
			// Going North
			tempdirectioncode = 1;
		}

		if (tolng < fromlng) {
			// Going West
			if (tempdirectioncode == 0) {
				// Going North West
				directioncode = 1;
			} else {
				// Going South West
				directioncode= 3;
			}
		} else {
			// Going West
			if (tempdirectioncode == 0) {
				// Going North East
				directioncode= 2;
			} else {
				// Going South East
				directioncode = 4;
			}
		}
		//Will know go thought the whole junctions finding the closet one 
		//Checking just the from 
		Junction currentclosest = alljunctions.get(0);
		double avgdistance = ( Math.abs(alljunctions.get(0).getfromlng() - tolng) + Math.abs(alljunctions.get(0).getfromlat() - tolat)) % 2;
		for(Junction temp : alljunctions )
		{
			if(directioncode == 1 || directioncode == 2)
			{
				//Going north
				if(temp.getdirection() == 1 ||temp.getdirection() ==  2)
				{
					if(start == true)
					{
						double tempdistancefromlat = Math.abs(temp.getfromlat() - tolat);
						double tempdistancefromlng = Math.abs(temp.getfromlng() - tolng);
						double tempavgdistance = (tempdistancefromlat + tempdistancefromlng) % 2;
						if(avgdistance > tempavgdistance)
						{
							//CLosest on avg 

							currentclosest = temp;

						}
					}else
					{
						double tempdistancefromlat = Math.abs(temp.getfromlat() - fromlat);
						double tempdistancefromlng = Math.abs(temp.getfromlng() - fromlng);
						double tempavgdistance = (tempdistancefromlat + tempdistancefromlng) % 2;
						if(avgdistance > tempavgdistance)
						{
							//CLosest on avg 
							avgdistance = tempavgdistance;
							currentclosest = temp;

						}
					}
				}else
				{
					//Out of ranged 
				}
			}
			else
			{
				//Going south
				if(temp.getdirection() == 3 ||temp.getdirection() ==  4)
				{
					if(start == true)
					{
						double tempdistancefromlat = Math.abs(temp.getfromlat() - tolat);
						double tempdistancefromlng = Math.abs(temp.getfromlng() - tolng);
						double tempavgdistance = (tempdistancefromlat + tempdistancefromlng) % 2;
						if(avgdistance > tempavgdistance)
						{
							//CLosest on avg 

							currentclosest = temp;

						}
					}else
					{
						double tempdistancefromlat = Math.abs(temp.getfromlat() - fromlat);
						double tempdistancefromlng = Math.abs(temp.getfromlng() - fromlng);
						double tempavgdistance = (tempdistancefromlat + tempdistancefromlng) % 2;
						if(avgdistance > tempavgdistance)
						{
							//CLosest on avg 
							avgdistance = tempavgdistance;
							currentclosest = temp;

						}
					}
				}else
				{
					//Out of ranged 
				}
			}


		}
		return currentclosest;

	}
	public static Junction findnextjunction(ArrayList<Junction> alljunctions,ArrayList<Junction> currentlyadded,Junction current, int directioncode)
	{
		//This methord will find out which junction will be based as a next junction 
		//first in checks to see if there is a junction going in the right direction on the same road 
		//Then will check if there is a near by junction that is going to the same way pridoty 
		//Then will check if there is a junction on the road going a different way
		if(directioncode == 1)
		{

			//Going North West
			Junction currentclosest = alljunctions.get(0);

			double avgdistance = ( Math.abs(alljunctions.get(0).getfromlng() - current.gettolat()) + Math.abs(alljunctions.get(0).getfromlat() - current.gettolng())) % 2;
			System.out.println(avgdistance);
			for(Junction temp : alljunctions)
			{
				if(temp.getdirection() == 1 || temp.getdirection() == 2)
				{
					if(temp.getfromlat() == current.getfromlat() || checkifinarray(currentlyadded,temp) == true)
					{
						System.out.println("Caught");
					}else
					{
						double tempdistancefromlat = Math.abs(temp.getfromlat() - current.gettolat());
						double tempdistancefromlng = Math.abs(temp.getfromlng() - current.gettolng());
						double tempavgdistance = (tempdistancefromlat + tempdistancefromlng) % 2;

						if(avgdistance > tempavgdistance)
						{
							//CLosest on avg 
							System.out.println(tempavgdistance);
							avgdistance = tempavgdistance;
							currentclosest = temp;

						}
					}
				}
			}		
			return currentclosest;
		}
		else if(directioncode == 2)
		{
			//Going North East	
			Junction currentclosest = alljunctions.get(0);

			double avgdistance = ( Math.abs(alljunctions.get(0).getfromlng() - current.gettolat()) + Math.abs(alljunctions.get(0).getfromlat() - current.gettolng())) % 2;
			for(Junction temp : alljunctions)
			{
				if(temp.getdirection() == 1 || temp.getdirection() == 2)
				{

					if(temp.getfromlat() == current.getfromlat() || checkifinarray(currentlyadded,temp) == true)
					{
						System.out.println("Caught");
					}else
					{
						double tempdistancefromlat = Math.abs(temp.getfromlat() - current.gettolat());
						double tempdistancefromlng = Math.abs(temp.getfromlng() - current.gettolng());
						double tempavgdistance = (tempdistancefromlat + tempdistancefromlng) % 2;

						if(avgdistance > tempavgdistance)
						{
							//CLosest on avg 
							System.out.println(tempavgdistance);
							avgdistance = tempavgdistance;
							currentclosest = temp;

						}
					}
				}
			}
			return currentclosest;
		}
		else if(directioncode == 3)
		{
			//Going South West
			Junction currentclosest = alljunctions.get(0);

			double avgdistance = ( Math.abs(alljunctions.get(0).getfromlng() - current.gettolat()) + Math.abs(alljunctions.get(0).getfromlat() - current.gettolng())) % 2;
			for(Junction temp : alljunctions)
			{
				if(temp.getdirection() == 3 || temp.getdirection() == 4)
				{
					if(temp.getfromlat() == current.getfromlat() || checkifinarray(currentlyadded,temp) == true)
					{
						System.out.println("Caught");
					}else
					{
						double tempdistancefromlat = Math.abs(temp.getfromlat() - current.gettolat());
						double tempdistancefromlng = Math.abs(temp.getfromlng() - current.gettolng());
						double tempavgdistance = (tempdistancefromlat + tempdistancefromlng) % 2;

						if(avgdistance > tempavgdistance)
						{
							//CLosest on avg 
							System.out.println(tempavgdistance);
							avgdistance = tempavgdistance;
							currentclosest = temp;

						}
					}
				}
			}
			return currentclosest;
		}
		else if(directioncode == 4)
		{
			//Going South East
			Junction currentclosest = alljunctions.get(0);

			double avgdistance = ( Math.abs(alljunctions.get(0).getfromlng() - current.gettolat()) + Math.abs(alljunctions.get(0).getfromlat() - current.gettolng())) % 2;
			System.out.println(avgdistance);
			for(Junction temp : alljunctions)
			{
				if(temp.getdirection() == 3 || temp.getdirection() == 4)
				{

					if(temp.getfromlat() == current.getfromlat() || checkifinarray(currentlyadded,temp) == true)
					{
						System.out.println("Caught");
					}else
					{
						double tempdistancefromlat = Math.abs(temp.getfromlat() - current.gettolat());
						double tempdistancefromlng = Math.abs(temp.getfromlng() - current.gettolng());
						double tempavgdistance = (tempdistancefromlat + tempdistancefromlng) % 2;

						if(avgdistance > tempavgdistance)
						{
							//CLosest on avg 
							System.out.println(tempavgdistance);
							avgdistance = tempavgdistance;
							currentclosest = temp;

						}
					}
				}
			}
			return currentclosest;
		}

		return null;
	}

	public static boolean checkifinarray(ArrayList<Junction> inarray , Junction tempjunction)
	{
		for(Junction temp: inarray)
		{
			
			if(tempjunction.getfromlat() == temp.getfromlat())
			{
				return true;
			}else if(tempjunction.getfromlng() == temp.getfromlng())
			{
				return true;
			}

		}
		return false;
	}


}

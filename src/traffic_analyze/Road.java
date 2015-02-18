package traffic_analyze;

import java.util.ArrayList;

public class Road {
	//This class will deal with the road network for england 
	String Roadname;
	double Roadlat;
	double Roadlong;
	double travelTime;
	double normallyExpectedTravelTime;
	ArrayList<Incident> roadincidents = new ArrayList<Incident>();
	ArrayList<Journeytime> roadJuctions = new ArrayList<Journeytime>();
	
	public ArrayList<Journeytime> getRoadJuctions() {
		return roadJuctions;
	}
	
	public void setRoadJuctions(ArrayList<Journeytime> roadJuctions) {
		this.roadJuctions = roadJuctions;
	}
	public void addjuctions(Journeytime juction)
	{
		roadJuctions.add(juction);	
	}
	public Road(String name)
	{
		this.Roadname = name;
	}
	public String incidentsize()
	{
		return String.valueOf(roadincidents.size());
	}
	public ArrayList<Incident> getRoadincidents() {
		return roadincidents;
	}
	public void setRoadincidents(ArrayList<Incident> roadincidents) {
		this.roadincidents = roadincidents;
	}
	
	public String getdelay()
	{
		int delay = (int)(travelTime - normallyExpectedTravelTime)/60;
		return String.valueOf(delay);
		
	}
	public int getintdelay()
	{
		return (int)(travelTime - normallyExpectedTravelTime)/60;
		
	}
	public double getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(double travelTime) {
		this.travelTime = travelTime;
	}

	public double getNormallyExpectedTravelTime() {
		return normallyExpectedTravelTime;
	}

	public void setNormallyExpectedTravelTime(double normallyExpectedTravelTime) {
		this.normallyExpectedTravelTime = normallyExpectedTravelTime;
	}

	

	public String getRoadname() {
		return Roadname;
	}

	public void setRoadname(String roadname) {
		Roadname = roadname;
	}

	public double getlat() {
		return Roadlat;
	}

	public void setRoadlat(double roadlat) {
		Roadlat = roadlat;
	}

	public double getlong() {
		return Roadlong;
	}

	public void setRoadlong(double roadlong) {
		Roadlong = roadlong;
	}
}

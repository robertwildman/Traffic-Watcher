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

	public Road(String name) {
		// Creates a new road with the name inputed
		Roadname = name;
	}

	public void addjuctions(Journeytime juction) {
		// Adding a junction to the array
		roadJuctions.add(juction);
	}

	public String getdelay() {
		// Finds the delays and returns it
		int delay = (int) (travelTime - normallyExpectedTravelTime) / 60;
		return String.valueOf(delay);

	}

	public int getintdelay() {
		// Returns the delay as a int
		return (int) (travelTime - normallyExpectedTravelTime) / 60;

	}

	public double getlat() {
		// Returns the lat
		return Roadlat;
	}

	public double getlong() {
		// Returns the long
		return Roadlong;
	}

	public double getNormallyExpectedTravelTime() {
		// Returns the normally time
		return normallyExpectedTravelTime;
	}

	public ArrayList<Incident> getRoadincidents() {
		// Returns the array of incidents
		return roadincidents;
	}

	public ArrayList<Journeytime> getRoadJuctions() {
		// Returns the roadjunctions
		return roadJuctions;
	}

	public String getRoadname() {
		// Returns the roadname
		return Roadname;
	}

	public double getTravelTime() {
		// Returns the traveltime
		return travelTime;
	}

	public String incidentsize() {
		// Returns the size of the incident array
		return String.valueOf(roadincidents.size());
	}

	public void setNormallyExpectedTravelTime(double normallyExpectedTravelTime) {
		// Sets the normal time
		this.normallyExpectedTravelTime = normallyExpectedTravelTime;
	}

	public void setRoadincidents(ArrayList<Incident> roadincidents) {
		// Sets the array of incidents
		this.roadincidents = roadincidents;
	}

	public void setRoadJuctions(ArrayList<Journeytime> roadJuctions) {
		// Setting the array
		this.roadJuctions = roadJuctions;
	}

	public void setRoadlat(double roadlat) {
		// Sets the lat
		Roadlat = roadlat;
	}

	public void setRoadlong(double roadlong) {
		// Sets the Long
		Roadlong = roadlong;
	}

	public void setRoadname(String roadname) {
		// Sets the roadname
		Roadname = roadname;
	}

	public void setTravelTime(double travelTime) {
		// Sets the travel time
		this.travelTime = travelTime;
	}
}

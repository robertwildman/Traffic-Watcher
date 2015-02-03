package traffic_analyze;

public class Road {
	//This class will deal with the road network for england 
	String Roadname;
	double Roadlat;
	double Roadlong;
	
	public Road(String name)
	{
		this.Roadname = name;
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

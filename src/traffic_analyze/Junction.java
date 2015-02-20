package traffic_analyze;

public class Junction  {

	Road road;
	int direction;
	double lat;
	double lng;
	double travelTime;
	double freeFlowTravelTime;
	double normallyExpectedTravelTime;
	
	public Junction(Road inroad) {
		this.road = inroad;
		
	}
	public double getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(double travelTime) {
		this.travelTime = travelTime;
	}

	public double getFreeFlowTravelTime() {
		return freeFlowTravelTime;
	}

	public void setFreeFlowTravelTime(double freeFlowTravelTime) {
		this.freeFlowTravelTime = freeFlowTravelTime;
	}

	public double getNormallyExpectedTravelTime() {
		return normallyExpectedTravelTime;
	}

	public void setNormallyExpectedTravelTime(double normallyExpectedTravelTime) {
		this.normallyExpectedTravelTime = normallyExpectedTravelTime;
	}
	

}

package traffic_analyze;

public class Junction {

	Road road;
	int direction;
	double tolat;
	double tolng;
	double fromlat;
	double fromlng;
	int junctioncode;
	String roadname;
	double travelTime;
	double freeFlowTravelTime;
	double normallyExpectedTravelTime;

	public Junction(double tolat, double tolng, double fromlat, double fromlng,
			int junctioncode, String roadname) {
		// Sets up a new instance with all the information inputed
		this.tolat = tolat;
		this.tolng = tolng;
		this.fromlat = fromlat;
		this.fromlng = fromlng;
		this.junctioncode = junctioncode;
		this.roadname = roadname;
	}

	public int getdirection() {
		int tempdirectioncode;
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
				return 1;
			} else {
				// Going South West
				return 3;
			}
		} else {
			// Going West
			if (tempdirectioncode == 0) {
				// Going North East
				return 2;
			} else {
				// Going South East
				return 4;
			}
		}
	}

	public double getFreeFlowTravelTime() {
		// Get the freeflowtime
		return freeFlowTravelTime;
	}

	public double getfromlat() {
		// Returns the lat of the from junction
		return fromlat;
	}

	public double getfromlng() {
		// Returns the long of the from junction
		return fromlng;
	}

	public int getjunctioncode() {
		// Returns the junctioncode
		return junctioncode;
	}

	public double getNormallyExpectedTravelTime() {
		// Returns the normally time
		return normallyExpectedTravelTime;
	}

	public String getroadname() {
		// Returns the roadname
		return roadname;
	}

	public double gettolat() {
		// Returns the lat of the to
		return tolat;
	}

	public double gettolng() {
		// Returns the long of the to
		return tolng;
	}

	public double getTravelTime() {
		// Returns the traveltime
		return travelTime;
	}

	public void setFreeFlowTravelTime(double freeFlowTravelTime) {
		// Set the freeflow time
		this.freeFlowTravelTime = freeFlowTravelTime;
	}

	public void setNormallyExpectedTravelTime(double normallyExpectedTravelTime) {
		// Sets the normally time
		this.normallyExpectedTravelTime = normallyExpectedTravelTime;
	}

	public void setTravelTime(double travelTime) {
		// Set the travel time
		this.travelTime = travelTime;
	}

}

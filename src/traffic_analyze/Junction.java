package traffic_analyze;

public class Junction  {

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
	
	public Junction(double tolat,double tolng,double fromlat,double fromlng,int junctioncode,String roadname) {
		this.tolat = tolat;
		this.tolng = tolng;
		this.fromlat = fromlat;
		this.fromlng = fromlng;
		this.junctioncode = junctioncode;
		this.roadname = roadname;
	}
	public int getdirection()
	{
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
	public String getroadname()
	{
		return roadname;
	}
	public int getjunctioncode()
	{
		return junctioncode;
	}
	public double gettolat()
	{
		return tolat;
	}
	public double gettolng()
	{
		return tolng;
	}
	public double getfromlat()
	{
		return fromlat;
	}
	public double getfromlng()
	{
		return fromlng;
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

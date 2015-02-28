package traffic_analyze;

public class Incident {

	String title;
	String desc;
	String road;
	double lat;
	double longa;
	int directioncode;
	String type;

	public Incident(String title, String desc, double lat,
			double longa, String type, int directioncode, String road) {
		//Makes an instance of the incident class
		this.title = title;
		this.desc = desc;
		this.lat = lat;
		this.road = road;
		this.longa = longa;
		this.type = type;
		this.directioncode = directioncode;
	}

	public String getroad() {
		//Returns the roadname
		return road;
	}

	public Boolean inrange(double inlong, double inlong2, double inlat,
			double inlat2) {
		// This will work out what lat and long is bigger
		double highlat = 0, lowlat = 0;
		// This deals with setting the highest and lowest lat and long.
		int tempdirectioncode, rangedirectioncode;
		if (inlat > inlat2) {
			highlat = inlat;
			lowlat = inlat2;
			// Going North
			tempdirectioncode = 0;
		} else {
			highlat = inlat2;
			lowlat = inlat;
			// Going South
			tempdirectioncode = 1;
		}

		if (inlong > inlong2) {
			// Going East
			if (tempdirectioncode == 0) {
				// Going North East
				rangedirectioncode = 1;
			} else {
				// Going South East
				rangedirectioncode = 2;
			}
		} else {
			// Going West
			if (tempdirectioncode == 0) {
				// Going North West
				rangedirectioncode = 4;
			} else {
				// Going South West
				rangedirectioncode = 3;
			}
		}
		if (rangedirectioncode == 1
				&& (directioncode == 1 || directioncode == 2)) {
			// Will now check to see if its inrange
			if (lat <= highlat && lat >= lowlat) {
				// In range on lat!
				return true;
			} else {
				// Out of range on both
				return false;
			}
		} else if (rangedirectioncode == 2
				&& (directioncode == 3 || directioncode == 2)) {
			// Will now check to see if its inrange
			if (lat <= highlat && lat >= lowlat) {
				// In range on lat!
				return true;
			} else {
				// Out of range on both
				return false;
			}
		} else if (rangedirectioncode == 3
				&& (directioncode == 3 || directioncode == 4)) {
			// Will now check to see if its inrange
			if (lat <= highlat && lat >= lowlat) {
				// In range on lat!
				return true;
			} else {
				// Out of range on both
				return false;
			}
		} else if (rangedirectioncode == 4
				&& (directioncode == 1 || directioncode == 4)) {
			// Will now check to see if its inrange
			if (lat <= highlat && lat >= lowlat) {
				// In range on lat!
				return true;

			} else {
				// Out of range on both
				return false;
			}
		} else {
			return false;
		}

	}

	public String getdesc() {
		//Returns the desc
		return desc;
	}

	public String getsmalltitle() {
		//Spilits the title and returns 
		if (title.contains("|")) {
			return title.split("|")[0];
		} else if (title.contains("-")) {
			return title.split("-")[0];
		} else {
			return title;
		}

	}

	public String getdirection() {
		//Returns a string version of the direction
		return String.valueOf(directioncode);
	}

	public String gettitle() {
		//Returns the title
		return title;
	}

	public String printdata() {
		//Returns the title as printed 
		String output = "Title: " + title + "\n";
		return output;

	}

	public double getLat() {
		//Returns the lat
		return lat;
	}

	public void setLat(double lat) {
		//Sets the lat
		this.lat = lat;
	}

	public double getLonga() {
		//Returns the long
		return longa;
	}

	public void setLonga(double longa) {
		//Sets the Long
		this.longa = longa;
	}

}

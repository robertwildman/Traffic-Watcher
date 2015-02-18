package traffic_gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;

import traffic_analyze.Directions;
import traffic_analyze.Incident;
import traffic_analyze.Journeytime;
import traffic_analyze.Postcode_Processing;
import traffic_analyze.Road;
import traffic_input.JourneyTime_input;
import traffic_input.Postcodeinput;
import traffic_input.traffic_input;

public class Startscreen implements ActionListener {

	public static boolean checkifroadadded(ArrayList<String> Road,
			String Roadname) {
		for (int i = 0; i < Road.size(); i++) {
			if (Road.get(i).equalsIgnoreCase(Roadname)) {
				return true;
			}
		}
		return false;

	}

	public static void main(String[] args) {
		new Startscreen();
	}

	public static ArrayList<String> roadbyname(ArrayList<Incident> Journeys,
			String name) {
		ArrayList<String> roadinfo = new ArrayList<String>();
		for (int i = 0; i < Journeys.size(); i++) {
			if (Journeys.get(i).getroad().equalsIgnoreCase(name)) {
				roadinfo.add(Journeys.get(i).gettitle() + " # "
						+ Journeys.get(i).getdesc());
			}
		}
		return roadinfo;
	}

	public JPanel postcodepanel, setcordpanel, main, sp, mainRoadpanel;
	public JTextField tfpostcode1, tfpostcode2, townname, townlat, townlong,
			townpostcode;
	public JTextArea output;
	public JTabbedPane tabs;
	public String totown, fromtown;
	public JFrame frame, offlineframe, postcodeframe, newrouteframe;
	public JMenuBar menu;
	public JMapViewer map;
	public JPanel combopanel,mainmappanel, roadpanel, Overviewpanel, Overviewinnerpanel1,
			Overviewinnerpanel3, Overviewinnerpanel4, Overviewinnerpanel5,
			Overviewinnerpanel6;
	public JScrollPane Scrollpane;
	public ArrayList<String> Towninfo, smalltitle, fulltitle, fulldesc;
	public ArrayList<Incident> affectedIncidents;

	public Boolean Toaddress;

	public Double tolat, fromlat, tolong, fromlong;

	public JLabel lpostcode1, lpostcode2, info, JLTitle, JLDesc;

	public Startscreen() {
		super();
		// Will open the start screen
		layout();
	}
/*
	@Override
	public void actionPerformed(ActionEvent e) {
		// This will deal with the handling the to and from button pushes
		if (Toaddress == true) {
			// This will add the cords to the to address
			String[] address = getcordsoftown(e.getActionCommand());
			tolat = Double.valueOf(address[0]);
			tolong = Double.valueOf(address[1]);
			// Then it will set the ToAddress to false
			Toaddress = false;
			// Then will set the to: to from:
			info.setText("     From:");
			// Sets the to town
			totown = e.getActionCommand();

		} else {
			// This means that it wants to get the from address and has the to
			// address
			String[] address = getcordsoftown(e.getActionCommand());
			fromlat = Double.valueOf(address[0]);
			fromlong = Double.valueOf(address[1]);
			// Then it will set the ToAddress to true
			Toaddress = true;
			// Then will set the From: to To:
			info.setText("     To:");
			fromtown = e.getActionCommand();
			traffic_input.gettraffic(true);
			final ArrayList<Incident> incidentlist = traffic_input.allincidents;
			affectedIncidents = new ArrayList<Incident>();
			smalltitle = new ArrayList<String>();
			fulltitle = new ArrayList<String>();
			fulldesc = new ArrayList<String>();
			for (int i = 0; i < incidentlist.size(); i++) {

				// This will use the function inrange and if true will add the
				// titles to a JList
				// Then set full titles and descrtion in an other array
				if (incidentlist.get(i).inrange(tolong, fromlong, tolat,
						fromlat) == true) {
					smalltitle.add(incidentlist.get(i).getsmalltitle());
					fulltitle.add(incidentlist.get(i).gettitle());
					fulldesc.add(incidentlist.get(i).getdesc());
					affectedIncidents.add(incidentlist.get(i));
				} else {

				}

			}
			String[] smalltitlelist = new String[smalltitle.size()];
			smalltitlelist = smalltitle.toArray(smalltitlelist);
			model = (DefaultComboBoxModel) trafficlist.getModel();
			model.removeAllElements();
			roadpanel.removeAll();
			JLabel fromtownlabel = new JLabel(fromtown);
			JLabel totownlabel = new JLabel(totown);
			roadpanel.add(fromtownlabel);
			for (String item : getroads(affectedIncidents)) {
				if (item.equalsIgnoreCase("Error")) {
					item = "Other";
				}
				model.addElement(item);
				JButton tempbutton = new JButton(item);
				roadpanel.add(tempbutton);
			}
			roadpanel.add(totownlabel);
			roadpanel.revalidate();
			if (model.getSize() == 0) {
				model.addElement("No roads affected!");
			}

			trafficlist.setModel(model);
			trafficlist.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent actionEvent) {
					// Happens when the user clicks on the combo box
					JComboBox traffic = (JComboBox) actionEvent.getSource();
					if (model.getElementAt(traffic.getSelectedIndex())
							.toString().equalsIgnoreCase("test")) {
						test(affectedIncidents);
					}
					// Gets any issues on the road that the user has picked
					ArrayList<String> allroadincidents = roadbyname(
							affectedIncidents,
							model.getElementAt(traffic.getSelectedIndex())
									.toString());
					output.setVisible(true);
					output.setText(" ");
					for (String item : allroadincidents) {
						String[] items = new String[2];
						items = item.split("#");
						output.append(items[0] + "\n");
						output.append("                 " + items[1] + "\n");
						output.append("    \n");
					}
					// Will get a list of affected jounreytimes
					ArrayList<Journeytime> alljourneytime = getinrangejourneytime(
							JourneyTime_input.getJourneys(false), tolong,
							fromlong, tolat, fromlat);
					// This will work out if there is a delay and then display
					// message to user.
					output.append(getjourneytimedata(alljourneytime, model
							.getElementAt(traffic.getSelectedIndex())
							.toString()));

				}

			});
		}

	}*/

	public void addtown() {
		// Custom view allowing to enter Town Name and lat and long;
		frame = new JFrame("Add New Town");
		main = new JPanel();
		main.add(new JLabel("Enter Town Name: "));
		townname = new JTextField(15);
		main.add(townname);
		main.add(new JLabel("Enter Town Lat:  "));
		townlat = new JTextField(15);
		main.add(townlat);
		main.add(new JLabel("Enter Town Long: "));
		townlong = new JTextField(15);
		main.add(townlong);
		JButton ok = new JButton("Create Town");
		JButton Cancel = new JButton("Cancel");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if ((townname.getText().length() > 0)
						&& (townlat.getText().length() > 0)
						&& (townlong.getText().length() > 0)) {
					savetown(townname.getText(), townlat.getText(),
							townlong.getText());
					frame.setVisible(false);
					layout();
				} else {
					JOptionPane.showMessageDialog(frame,
							"Please enter data in each field!", "Thank You",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		Cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				frame.setVisible(false);

			}
		});
		main.add(ok);
		main.add(Cancel);
		frame.add(main);
		frame.setSize(200, 260);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void addtownwithpostcode() {
		// This will look after the postcode
		postcodeframe = new JFrame("Add New Town");
		main = new JPanel();
		main.add(new JLabel("Enter Town Name "));
		townname = new JTextField(15);
		main.add(townname);
		main.add(new JLabel("Enter To Town Postcode:  "));
		townpostcode = new JTextField(15);
		main.add(townpostcode);
		JButton ok = new JButton("Create Town");
		JButton Cancel = new JButton("Cancel");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if ((townname.getText().length() > 0)
						&& (townpostcode.getText().length() > 0)) {
					String[] cords = Postcodeinput.getcords(
							townpostcode.getText().toString()).split(",");
					savetown(townname.getText(), cords[0], cords[1]);
					frame.setVisible(false);
					layout();
				} else {
					JOptionPane.showMessageDialog(frame,
							"Please enter each field!", "Thank You",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		Cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				frame.setVisible(false);

			}
		});
		main.add(ok);
		main.add(Cancel);
		postcodeframe.add(main);
		postcodeframe.setSize(200, 260);
		postcodeframe.setLocationRelativeTo(null);
		postcodeframe.setVisible(true);
	}

	public String correctjuction(Journeytime journey, Boolean direction) {
		if (direction == true) {
			// This means that it is a to junction
			if (journey.gettojuction().equalsIgnoreCase("Error")) {
				return "The " + journey.gettoroad();
			} else {
				return "Junction " + journey.gettojuction();
			}
		} else {
			// This means that the junction is a from junction
			if (journey.getfromjuction().equalsIgnoreCase("Error")) {
				return "The " + journey.getfromroad();
			} else {
				return "Junction " + journey.getfromjuction();
			}
		}

	}

	public String[] createarea(String postcode1, String postcode2) {
		// Will get the lang and long and then will split it off to ints
		String temp = Postcodeinput.getcords(postcode1);
		System.out.println(temp);
		String[] postcode1cords = temp.split(",");
		int postcode1long = Integer.parseInt(postcode1cords[0]);
		int postcode1lang = Integer.parseInt(postcode1cords[1]);

		// Will get the lang and long and then will split it off to ints
		temp = Postcodeinput.getcords(postcode2);
		String[] postcode2cords = temp.split(",");
		System.out.println(temp);
		int postcode2long = Integer.parseInt(postcode2cords[0]);
		int postcode2lang = Integer.parseInt(postcode2cords[1]);
		// This will put it into a an array that will then be used for the area.
		String[] Areacords = new String[2];
		Areacords[0] = String.valueOf(postcode1lang) + ","
				+ String.valueOf(postcode1long);
		Areacords[1] = String.valueOf(postcode2lang) + ","
				+ String.valueOf(postcode2long);
		System.out.println(Areacords);

		return Areacords;

	}

	public String currentdistance(String totown2, String fromtown2) {
		//First will get the lat and long of the town 
		double lat1,lat2,lng1,lng2;
		String[] cords = getcordsoftown(totown2);
		lat1 = Double.parseDouble(cords[0]);
		lng1  = Double.parseDouble(cords[1]);
		System.out.println(totown + " " + fromtown);
		String[] cords1 = getcordsoftown(fromtown2);
		lat2 = Double.parseDouble(cords1[0]);
		lng2  = Double.parseDouble(cords1[1]);
		System.out.println(lat1 + " " + lat2 + " " + lng1 + " " + lng2);
		if(lat2 > lat1)
		{
			double temp = lat1;
			lat1 = lat2;
			lat2 = temp;
		}
		if(lng2 > lng1)
		{
			double temp = lng1;
			lng1 = lng2;
			lng2 = temp;
		}
		System.out.println(lat1 + " " + lat2 + " " + lng1 + " " + lng2);
		//Now will work out the distances between lat and long  using website formula 
		double earthRadius = 3958.75;
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lng2-lng1);
	    double sindLat = Math.sin(dLat / 2);
	    double sindLng = Math.sin(dLng / 2);
	    double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
	            * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    int dist = (int) (earthRadius * c);

	    return String.valueOf(dist + " Miles.");

	}

	public String currenttime(String totown2, String fromtown2) {
		// TODO Auto-generated method stub
		return null;
	}

	public void displaytraffic(String totown, String fromtown) {
		// Removes old infomation
		Overviewinnerpanel1.removeAll();
		Overviewinnerpanel3.removeAll();
		Overviewinnerpanel4.removeAll();
		Overviewinnerpanel5.removeAll();
		Overviewinnerpanel5.removeAll();
		mainRoadpanel.removeAll();
		
		// Gets the lat and long of the town
		String[] address = getcordsoftown(totown);
		tolat = Double.valueOf(address[0]);
		tolong = Double.valueOf(address[1]);
		address = getcordsoftown(fromtown);
		fromlat = Double.valueOf(address[0]);
		fromlong = Double.valueOf(address[1]);

		// Getting data used to display
		ArrayList<Journeytime> inrangedjounreytime = getinrangejourneytime(
				JourneyTime_input.getJourneys(false), tolong, fromlong, tolat,
				fromlat);
		ArrayList<Incident> inrangedincidents = getinranged(
				traffic_input.gettraffic(true), tolong, fromlong, tolat,
				fromlat);
		ArrayList<String> inrangedroads = getroads(inrangedincidents);
		ArrayList<Road> inrangedsortedroads = Directions.orderlist(
				getroadsasclass(inrangedincidents), tolat, tolong, fromlat,
				fromlong);
		setroadswithdata(inrangedjounreytime, inrangedincidents,
				inrangedsortedroads);
		
		// Shows the tabs
		tabs.setVisible(true);

		// Dealing with first tab
		// Setting up the overview class
		Overviewinnerpanel1.add(new JLabel("Traveling to: " + totown));
		Overviewinnerpanel1.add(new JLabel("Traveling from: " + fromtown));
		Overviewinnerpanel1.add(new JLabel("Distance:  "
				+ currentdistance(totown, fromtown)));
		Overviewinnerpanel1.add(new JLabel("Time on English main roads:  "
				+ currenttime(totown, fromtown)));
		// Getting best road 
		
		// Getting worst road
		ArrayList<String> worstroad = worstroad(inrangedsortedroads);
		Overviewinnerpanel5.add(new JLabel("Road Name: " + worstroad.get(0)));
		Overviewinnerpanel5
				.add(new JLabel("Current Time: " + worstroad.get(1)));
		Overviewinnerpanel5.add(new JLabel("Delayed by:  " + worstroad.get(2)));
		Overviewinnerpanel5.add(new JLabel("Incidents:  " + worstroad.get(3)));
		
		//Getting worst junction 
		ArrayList<String> worstjuction = worstjuction(inrangedjounreytime,"fewef");
		Overviewinnerpanel6
				.add(new JLabel("Road Name: " + worstjuction.get(0)));
		Overviewinnerpanel6.add(new JLabel("Juction: " + worstjuction.get(1)));
		Overviewinnerpanel6.add(new JLabel("Current Time: "
				+ worstjuction.get(2)));
		Overviewinnerpanel6.add(new JLabel("Delayed by:  "
				+ worstjuction.get(3)));
		Overviewinnerpanel6
				.add(new JLabel("Incidents:  " + worstjuction.get(4)));
		
		// Getting worst road
				ArrayList<String> bestroad = bestroad(inrangedsortedroads);
				Overviewinnerpanel3.add(new JLabel("Road Name: " + bestroad.get(0)));
				Overviewinnerpanel3
						.add(new JLabel("Current Time: " + bestroad.get(1)));
				Overviewinnerpanel3.add(new JLabel("Delayed by:  " + bestroad.get(2)));
				Overviewinnerpanel3.add(new JLabel("Incidents:  " + bestroad.get(3)));

		Overviewinnerpanel1.revalidate();
		Overviewinnerpanel3.revalidate();
		Overviewinnerpanel4.revalidate();
		Overviewinnerpanel5.revalidate();
		Overviewinnerpanel6.revalidate();
		if (inrangedroads.size() < 30) {
			mainRoadpanel.setPreferredSize(new Dimension(1000, 800));
			mainRoadpanel.setMaximumSize(new Dimension(1000, 800));
		} else if (inrangedroads.size() < 50) {
			mainRoadpanel.setPreferredSize(new Dimension(1000, 1200));
			mainRoadpanel.setMaximumSize(new Dimension(1000, 1200));
		}
		// Setting up the roads infomation
		for (int i = 0; i < inrangedroads.size(); i++) {
			// This is where the grid layout will be made and enter in based on
			// how many roads there is
			JPanel temproadpanel = new JPanel();
			BoxLayout templayout = new BoxLayout(temproadpanel,
					BoxLayout.Y_AXIS);
			temproadpanel.setLayout(templayout);
			Border tempborder = BorderFactory
					.createTitledBorder(inrangedsortedroads.get(i)
							.getRoadname());
			temproadpanel.setBorder(tempborder);
			temproadpanel.setAlignmentX(Component.CENTER_ALIGNMENT);
			temproadpanel.setPreferredSize(new Dimension(100, 100));
			temproadpanel.setMaximumSize(new Dimension(100, 100));
			temproadpanel.add(Box.createRigidArea(new Dimension(0, 5)));
			if (inrangedsortedroads.get(i).getdelay() != null) {
				int delay = inrangedsortedroads.get(i).getintdelay();
				if (delay > 0) {
					temproadpanel.add(new JLabel("Delay: "
							+ inrangedsortedroads.get(i).getdelay()
							+ " minutes"));
					temproadpanel
							.add(Box.createRigidArea(new Dimension(0, 10)));
				}
			}
			temproadpanel.add(new JLabel("Number of Incidents: "
					+ inrangedsortedroads.get(i).incidentsize()));
			temproadpanel.add(Box.createRigidArea(new Dimension(0, 10)));
			JButton tempbutton = new JButton("View Incidents");
			temproadpanel.add(tempbutton);
			System.out.println(i);
			mainRoadpanel.add(temproadpanel);

		}
		mainRoadpanel.revalidate();

		//This will add markers to the map based on any incidents 
		for(int i = 0; i < inrangedincidents.size(); i++)
		{
		map.addMapMarker(new MapMarkerDot(inrangedincidents.get(i).getLat(),inrangedincidents.get(i).getLonga()));
		}
		
		
		
		
		
		// This will add a new panel to the grid for each road

		/*
		 * //Gets any issues on the road that the user has picked
		 * 
		 * ArrayList<String> allroadincidents = roadbyname( affectedIncidents,
		 * model.getElementAt(traffic.getSelectedIndex()) .toString());
		 * output.setVisible(true); output.setText(" "); for (String item :
		 * allroadincidents) { String[] items = new String[2]; items =
		 * item.split("#"); output.append(items[0] + "\n");
		 * output.append("                 " + items[1] + "\n");
		 * output.append("    \n"); } //Will get a list of affected jounreytimes
		 * ArrayList<Journeytime> alljourneytime =
		 * getinrangejourneytime(JourneyTime_input .getJourneys(false),tolong,
		 * fromlong, tolat, fromlat); //This will work out if there is a delay
		 * and then display message to user.
		 * output.append(getjourneytimedata(alljourneytime, model
		 * .getElementAt(traffic.getSelectedIndex()) .toString()));
		 * 
		 * //This will get any delays on the road and return a String array
		 * which then will be print onto the screen; ArrayList<String>
		 * affectedroads = getdelayedjuctions( alljourneytime,
		 * model.getElementAt(traffic.getSelectedIndex()) .toString());
		 * 
		 * if (affectedroads.size() > 0) {
		 * output.append("However there is minor issues at: \n"); } for (String
		 * roadinfo : affectedroads) { output.append(roadinfo); }
		 */
	}

	public String[] getcordsoftown(String intown) {
		// Returns the cords of the town

		for (int i = 0; i < Towninfo.size(); i++) {
			if (Towninfo.get(i).contains(intown)) {
				String[] arrayparts = Towninfo.get(i).split(",");
				String[] address = new String[2];
				address[0] = arrayparts[1];
				address[1] = arrayparts[2];
				return address;
			}
		}
		return null;
	}

	public String getcorrectdelay(Journeytime journeytime) {
		// This will return the correct amount of time the delay
		if (journeytime.delayed()) {
			long delayedtime = (long) (journeytime.getcurrenttime() - journeytime
					.getnormaltime());

			return String.valueOf(TimeUnit.SECONDS.toMinutes(delayedtime));
		}
		return null;
	}

	public ArrayList<String> getdelayedjuctions(
			ArrayList<Journeytime> alljourneytime, String road) {
		// This function will return the infomation on affect juctions
		ArrayList<String> roadinfo = new ArrayList<String>();
		for (int i = 0; i < alljourneytime.size(); i++) {

			if ((alljourneytime.get(i).delayed() == true)
					&& (alljourneytime.get(i).getroad().equalsIgnoreCase(road) == true)) {
				roadinfo.add("There is a delay between: "
						+ correctjuction(alljourneytime.get(i), false) + " - "
						+ correctjuction(alljourneytime.get(i), true) + " \n");
				roadinfo.add("This delay is about: "
						+ getcorrectdelay(alljourneytime.get(i))
						+ " Minutes  \n");
			}
		}
		return roadinfo;

	}

	public ArrayList<Incident> getinranged(ArrayList<Incident> allincidents,
			double tolong, double fromlong, double tolat, double fromlat) {
		ArrayList<Incident> inrangedincident = new ArrayList<Incident>();
		for (int i = 0; i < allincidents.size(); i++) {
			if (allincidents.get(i).inrange(tolong, fromlong, tolat, fromlat)) {
				inrangedincident.add(allincidents.get(i));
			}
		}
		return inrangedincident;

	}

	public ArrayList<Journeytime> getinrangejourneytime(
			ArrayList<Journeytime> all, double tolong, double fromlong,
			double tolat, double fromlat) {
		ArrayList<Journeytime> inrangejourneys = new ArrayList<Journeytime>();
		for (int i = 0; i < all.size(); i++) {
			if (all.get(i).inrange(tolong, fromlong, tolat, fromlat) == true) {
				inrangejourneys.add(all.get(i));
			}
		}

		return inrangejourneys;
	}

	public String getjourneytimedata(ArrayList<Journeytime> alljourneytime,
			String road) {
		// This will get a list of all the journey time data and return the data
		// based on the road

		String journeyinfo;
		long normaltime = 0;
		long currenttime = 0;
		long delayedtime = 0;
		for (int i = 0; i < alljourneytime.size(); i++) {

			if (alljourneytime.get(i).gettoroad().equalsIgnoreCase(road)) {
				normaltime = (long) (normaltime + alljourneytime.get(i)
						.getnormaltime());
				currenttime = (long) (currenttime + alljourneytime.get(i)
						.getcurrenttime());

			}
			delayedtime = currenttime - normaltime;
		}
		if (delayedtime > 0) {
			journeyinfo = "Current delay is: "
					+ String.valueOf(TimeUnit.SECONDS.toMinutes(delayedtime));
		} else {
			// Delay
			journeyinfo = "No Major delay! \n";
		}
		return journeyinfo;
	}

	public ArrayList<String> getroads(ArrayList<Incident> Journeys) {
		// This will collect the journey times on the spades.
		ArrayList<String> roads = new ArrayList<String>();
		ArrayList<Road> allroads = new ArrayList<Road>();
		for (int i = 0; i < Journeys.size(); i++) {
			for (int i2 = i + 1; i2 < Journeys.size(); i2++) {
				if (Journeys.get(i).getroad()
						.equals(Journeys.get(i2).getroad())
						& (i != i2)) {

					if (checkifroadadded(roads, Journeys.get(i).getroad()) == false) {
						// Adding Road
						Road Temp = new Road(Journeys.get(i).getroad());
						Temp.setRoadlat(Journeys.get(i).getLat());
						Temp.setRoadlong(Journeys.get(i).getLonga());
						allroads.add(Temp);
						roads.add(Journeys.get(i).getroad());
					}

				}
			}
		}
		return roads;

	}

	public ArrayList<Road> getroadsasclass(ArrayList<Incident> Journeys) {
		// This will collect the journey times on the spades.
		ArrayList<String> roads = new ArrayList<String>();
		ArrayList<Road> allroads = new ArrayList<Road>();
		for (int i = 0; i < Journeys.size(); i++) {
			for (int i2 = i + 1; i2 < Journeys.size(); i2++) {
				if (Journeys.get(i).getroad()
						.equals(Journeys.get(i2).getroad())
						& (i != i2)) {

					if (checkifroadadded(roads, Journeys.get(i).getroad()) == false) {
						// Adding Road
						Road Temp = new Road(Journeys.get(i).getroad());
						Temp.setRoadlat(Journeys.get(i).getLat());
						Temp.setRoadlong(Journeys.get(i).getLonga());
						allroads.add(Temp);
						roads.add(Journeys.get(i).getroad());
					}

				}
			}
		}
		return allroads;
	}

	public void newroute() {
		// This will display the route finding items
		newrouteframe = new JFrame("Starting new route");
		JPanel mainpanel = new JPanel();
		final ArrayList<String> temptownarray = readtowns();
		String[] alltowns = new String[temptownarray.size()];
		alltowns = temptownarray.toArray(alltowns);
		JLabel tocombolabel = new JLabel("Traveling to: ");
		final JComboBox Tocombo = new JComboBox(alltowns);
		JLabel fromcombolabel = new JLabel("Traveling from: ");
		final JComboBox Fromcombo = new JComboBox(alltowns);
		JButton Findroutes = new JButton("Find Traffic");
		Findroutes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				// This will input the 2 towns into the function
				totown = temptownarray.get(Tocombo.getSelectedIndex());
				fromtown = temptownarray.get(Fromcombo.getSelectedIndex());
				displaytraffic(totown, fromtown);
				newrouteframe.setVisible(false);
			}

		});

		tocombolabel.setLabelFor(Tocombo);
		fromcombolabel.setLabelFor(Fromcombo);
		mainpanel.add(tocombolabel);
		mainpanel.add(Tocombo);
		mainpanel.add(fromcombolabel);
		mainpanel.add(Fromcombo);
		mainpanel.add(Findroutes);
		newrouteframe.add(mainpanel);
		newrouteframe.setSize(220, 200);
		newrouteframe.setLocationRelativeTo(null);
		newrouteframe.setVisible(true);

	}

	public void layout() {
		// This will display the starting screen for the program with input of
		// postcodes.
		offlineframe = new JFrame("Traffic Watcher");
		offlineframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Setting up the panel of set cords for offline testing
		setcordpanel = new JPanel();
		// Setting up a menu bar
		menu = new JMenuBar();
		offlineframe.setJMenuBar(menu);
		JMenu add = new JMenu("Add");
		menu.add(add);
		JMenuItem newtownlat = new JMenuItem("New Town with Lat and Long");
		JMenuItem newtownpostcode = new JMenuItem("New Town with Postcode");
		add.add(newtownlat);
		add.add(newtownpostcode);
		newtownlat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				addtown();
			}

		});
		newtownpostcode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				// Will open the postcode input
				addtownwithpostcode();
			}

		});
		JMenu Route = new JMenu("Route");
		menu.add(Route);
		JMenuItem newroute = new JMenuItem("Start new route");
		JMenuItem saveroute = new JMenuItem("Save current route");
		JMenuItem loadroute = new JMenuItem("Load route");
		Route.add(newroute);
		Route.add(saveroute);
		Route.add(loadroute);
		newroute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				newroute();
			}

		});
		saveroute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				// TODO
			}

		});
		loadroute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				// TODO
			}

		});
		// Will setup the main panels
		Overviewpanel = new JPanel();
		Overviewpanel.setLayout(new BorderLayout());
		Box leftsidebox = Box.createVerticalBox();
		Box middlesidebox = Box.createVerticalBox();
		Box rightsidebox = Box.createVerticalBox();

		// Setting up left hand side
		Overviewinnerpanel1 = new JPanel();
		BoxLayout innerlayout1 = new BoxLayout(Overviewinnerpanel1,
				BoxLayout.Y_AXIS);
		Overviewinnerpanel1.setLayout(innerlayout1);
		Overviewinnerpanel1.setAlignmentX(Component.CENTER_ALIGNMENT);
		Overviewinnerpanel1.setPreferredSize(new Dimension(300, 300));
		Overviewinnerpanel1.setMaximumSize(new Dimension(300, 300));
		Overviewinnerpanel1
				.add(new JLabel(
						"                                                            "));
		Border innerborder1 = BorderFactory.createTitledBorder("Journey Info");
		Overviewinnerpanel1.setBorder(innerborder1);
		leftsidebox.add(Box.createVerticalStrut(30));
		leftsidebox.add(Overviewinnerpanel1);

		// Creating middle panel
		Overviewinnerpanel3 = new JPanel();
		Overviewinnerpanel4 = new JPanel();
		BoxLayout innerlayout3 = new BoxLayout(Overviewinnerpanel3,
				BoxLayout.Y_AXIS);
		BoxLayout innerlayout4 = new BoxLayout(Overviewinnerpanel4,
				BoxLayout.Y_AXIS);
		Overviewinnerpanel3.setLayout(innerlayout3);
		Overviewinnerpanel4.setLayout(innerlayout4);
		Overviewinnerpanel3.setAlignmentX(Component.CENTER_ALIGNMENT);
		Overviewinnerpanel3.setPreferredSize(new Dimension(300, 300));
		Overviewinnerpanel3.setMaximumSize(new Dimension(300, 300));
		Overviewinnerpanel4.setAlignmentX(Component.CENTER_ALIGNMENT);
		Overviewinnerpanel4.setPreferredSize(new Dimension(300, 300));
		Overviewinnerpanel4.setMaximumSize(new Dimension(300, 300));
		Overviewinnerpanel3
				.add(new JLabel(
						"                                                            "));
		Overviewinnerpanel4
				.add(new JLabel(
						"                                                            "));
		Border innerborder3 = BorderFactory.createTitledBorder("Best Road");
		Border innerborder4 = BorderFactory.createTitledBorder("Best Juction");
		Overviewinnerpanel3.setBorder(innerborder3);
		Overviewinnerpanel4.setBorder(innerborder4);
		middlesidebox.add(Box.createVerticalStrut(30));
		middlesidebox.add(Overviewinnerpanel3);
		middlesidebox.add(Box.createRigidArea(new Dimension(20, 40)));
		middlesidebox.add(Overviewinnerpanel4);

		// Adding Right panel
		Overviewinnerpanel5 = new JPanel();
		Overviewinnerpanel6 = new JPanel();
		BoxLayout innerlayout5 = new BoxLayout(Overviewinnerpanel5,
				BoxLayout.Y_AXIS);
		BoxLayout innerlayout6 = new BoxLayout(Overviewinnerpanel6,
				BoxLayout.Y_AXIS);
		Overviewinnerpanel5.setLayout(innerlayout5);
		Overviewinnerpanel6.setLayout(innerlayout6);
		Overviewinnerpanel5.setAlignmentX(Component.CENTER_ALIGNMENT);
		Overviewinnerpanel5.setPreferredSize(new Dimension(300, 300));
		Overviewinnerpanel5.setMaximumSize(new Dimension(300, 300));
		Overviewinnerpanel6.setAlignmentX(Component.CENTER_ALIGNMENT);
		Overviewinnerpanel6.setPreferredSize(new Dimension(300, 300));
		Overviewinnerpanel6.setMaximumSize(new Dimension(300, 300));
		Overviewinnerpanel5
				.add(new JLabel(
						"                                                            "));
		Overviewinnerpanel6
				.add(new JLabel(
						"                                                            "));
		Border innerborder5 = BorderFactory.createTitledBorder("Worst Road");
		Border innerborder6 = BorderFactory.createTitledBorder("Worst Juction");
		Overviewinnerpanel5.setBorder(innerborder5);
		Overviewinnerpanel6.setBorder(innerborder6);
		rightsidebox.add(Box.createVerticalStrut(30));
		rightsidebox.add(Overviewinnerpanel5);
		rightsidebox.add(Box.createRigidArea(new Dimension(20, 40)));
		rightsidebox.add(Overviewinnerpanel6);
		// Adding to main panel
		Overviewpanel.add(leftsidebox, BorderLayout.WEST);
		Overviewpanel.add(middlesidebox, BorderLayout.CENTER);
		Overviewpanel.add(rightsidebox, BorderLayout.EAST);

		// Setting up road layout
		mainRoadpanel = new JPanel();
		GridLayout experimentLayout = new GridLayout(0, 7);
		mainRoadpanel.setLayout(experimentLayout);
		mainRoadpanel.setPreferredSize(new Dimension(1000, 1500));
		mainRoadpanel.setMaximumSize(new Dimension(1000, 1500));
		JScrollPane roadscroll = new JScrollPane(mainRoadpanel,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		roadscroll.getVerticalScrollBar().setUnitIncrement(16);
		//Setting up the Maps Panel
		mainmappanel = new JPanel();
		map = new JMapViewer();
		mainmappanel.add(map);
		
		tabs = new JTabbedPane();
		tabs.addTab("Overview", Overviewpanel);
		tabs.addTab("Roads", roadscroll);
		tabs.addTab("Map", mainmappanel);
		offlineframe.add(tabs);
		tabs.setVisible(false);
		combopanel = new JPanel();
		output = new JTextArea();
		output.setEditable(false);
		output.setVisible(false);
		info = new JLabel("     To:");
		
		
		// offlineframe.add(Scrollpane, BorderLayout.CENTER);
		// Sets the size of the Frame
		// ONLINE AT
		// http://stackoverflow.com/questions/11570356/jframe-in-full-screen-java
		offlineframe.setExtendedState(Frame.MAXIMIZED_BOTH);
		// Shows the frame
		offlineframe.setVisible(true);
	}
	public ArrayList<String> readalltowninfo() {
		try {
			// This class will read the file then will return 2 arrays one with
			// towname|lat|long and one with just townname
			ArrayList<String> townnames = new ArrayList<String>();
			Towninfo = new ArrayList<String>();
			// Setting up the files and the readers.
			File townfile = new File("Offlinetowns.txt");
			BufferedReader in = new BufferedReader(new FileReader(townfile));
			String nextline = in.readLine();
			// Will keep reading the next line till there is no more lines to
			// read
			while (nextline != null) {
				// Each time it reads a new line it splits the line and then
				// adds to the array.
				Towninfo.add(nextline);
				String[] parts = nextline.split(",");
				System.out.println(parts[0] + "," + parts[1]);
				townnames.add(parts[0]);
				nextline = in.readLine();
			}
			in.close();
			return Towninfo;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<String> readtowns() {

		try {
			// This class will read the file then will return 2 arrays one with
			// towname|lat|long and one with just townname
			ArrayList<String> townnames = new ArrayList<String>();
			Towninfo = new ArrayList<String>();
			// Setting up the files and the readers.
			File townfile = new File("Offlinetowns.txt");
			BufferedReader in = new BufferedReader(new FileReader(townfile));
			String nextline = in.readLine();
			// Will keep reading the next line till there is no more lines to
			// read
			while (nextline != null) {
				// Each time it reads a new line it splits the line and then
				// adds to the array.
				try {
					Towninfo.add(nextline);
					String[] parts = nextline.split(",");
					System.out.println(parts[0] + "," + parts[1]);
					townnames.add(parts[0]);
					nextline = in.readLine();

				} catch (Exception e) {
					nextline = null;
				}

			}
			in.close();
			return townnames;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void savetown(String Townname, String TownLat, String TownLong) {
		// This will add to the file which holds all of the saved town data
		try {
			File townfile = new File("Offlinetowns.txt");
			ArrayList<String> Towns = readalltowninfo();
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter(townfile)));
			for (String towninfo : Towns) {
				out.append(towninfo + "\n");
			}
			out.append(Townname + "," + TownLat + "," + TownLong);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setroadswithdata(ArrayList<Journeytime> inrangedjounreytime,
			ArrayList<Incident> inrangedincidents,
			ArrayList<Road> inrangedsortedroads) {
		for (Road road : inrangedsortedroads) {
			for (Journeytime item : inrangedjounreytime) {
				if (item.gettoroad().equalsIgnoreCase(road.getRoadname())
						|| item.getfromroad().equalsIgnoreCase(
								road.getRoadname())) {
					road.setNormallyExpectedTravelTime(road
							.getNormallyExpectedTravelTime()
							+ item.getnormaltime());
					road.setTravelTime(road.getTravelTime()
							+ item.getcurrenttime());
				}
			}
			for (Incident currentincident : inrangedincidents) {
				if (currentincident.getroad().equalsIgnoreCase(
						road.getRoadname())) {
					ArrayList<Incident> temp = road.getRoadincidents();
					temp.add(currentincident);
					road.setRoadincidents(temp);
				}
			}
		}
	}

	public void test(ArrayList<Incident> incidents) {
		// TODO Auto-generated method stub
		for (Road item : getroadsasclass(incidents)) {
			System.out.println(item.getRoadname() + " lat: " + item.getlat()
					+ " long: " + item.getlong());
		}
		System.out.println("STARTING SORT");
		ArrayList<Road> allroadssorted = Directions.orderlist(
				getroadsasclass(incidents), tolat, tolong, fromlat, fromlong);
		for (Road item : allroadssorted) {
			System.out.println(item.getRoadname() + " lat: " + item.getlat()
					+ " long: " + item.getlong());
		}
	}

	public ArrayList<String> worstjuction(ArrayList<Journeytime> all,String road) {
		// TODO Auto-generated method stub
		
		ArrayList<String> worstjuctioninfo = new ArrayList<String>();
		worstjuctioninfo.add("f");
		worstjuctioninfo.add("f");
		worstjuctioninfo.add("f");
		worstjuctioninfo.add("f");
		worstjuctioninfo.add("f");
		worstjuctioninfo.add("f");
		return worstjuctioninfo;
	}

	public ArrayList<String> worstroad(ArrayList<Road> all) {
		// TODO Auto-generated method stub
		Road currentworstroad = all.get(0); 
		for(int i = 0; i < all.size(); i++)
		{
			if(all.get(i).getintdelay() > currentworstroad.getintdelay())
			{
				currentworstroad = all.get(i);
			}
		}
		ArrayList<String> worstroad = new ArrayList<String>();
		worstroad.add(currentworstroad.getRoadname());
		worstroad.add(String.valueOf(currentworstroad.getTravelTime() + " Miniutes"));
		worstroad.add(String.valueOf(currentworstroad.getdelay() + " Miniutes"));
		worstroad.add("f");
		return worstroad;
	}
	public ArrayList<String> bestjuction(ArrayList<Journeytime> all,String road) {
		// TODO Auto-generated method stub
		ArrayList<String> bestjuction = new ArrayList<String>();
		bestjuction.add("f");
		bestjuction.add("f");
		bestjuction.add("f");
		bestjuction.add("f");
		bestjuction.add("f");
		bestjuction.add("f");
		return bestjuction;
	}

	public ArrayList<String> bestroad(ArrayList<Road> all) {
		//Will find the best road best on the amount of delays or lack of delays 
		Road currentbestroad = all.get(0);
		for(int i = 0; i < all.size(); i++)
		{
			if(all.get(i).getintdelay() < currentbestroad.getintdelay())
			{
				currentbestroad = all.get(i);
			}
		}
		ArrayList<String> bestroad = new ArrayList<String>();
		bestroad.add(currentbestroad.getRoadname());
		bestroad.add(String.valueOf(currentbestroad.getTravelTime() + " Miniutes"));
		bestroad.add(String.valueOf(currentbestroad.getdelay() + " Miniutes"));
		bestroad.add("f");
		return bestroad;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	 
		
	}

	public void saveroute(String totown, String fromtown)
	{
		// This will add to the file which holds all of the user 
				try {
					File routefile = new File("Routes.txt");
					ArrayList<String> Routes = readroutes();
					PrintWriter out = new PrintWriter(new BufferedWriter(
							new FileWriter(routefile)));
					for (String routeinfo : Routes) {
						out.append(routeinfo + "\n");
					}
					out.append(totown + "," + fromtown);
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

	public ArrayList<String> readroutes() {
		try {
			// This class will read the file then will return the routes info 
			//In the format of to town name, from town name 
			ArrayList<String> Routes = new ArrayList<String>();
			// Setting up the files and the readers.
			File routefile = new File("Routes.txt");
			BufferedReader in = new BufferedReader(new FileReader(routefile));
			String nextline = in.readLine();
			// Will keep reading the next line till there is no more lines to
			// read
			while (nextline != null) {
				// Each time it reads a new line it splits the line and then
				// adds to the array.
				Routes.add(nextline);
				nextline = in.readLine();
			}
			in.close();
			return Routes;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}

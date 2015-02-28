package traffic_gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import traffic_analyze.Directions;
import traffic_analyze.Incident;
import traffic_analyze.Journeytime;
import traffic_analyze.Road;
import traffic_input.JourneyTime_input;
import traffic_input.Postcodeinput;
import traffic_input.traffic_input;

public class Startscreen {

	public JPanel main, mainRoadpanel;
	public JTextField townname, townlat, townlong, townpostcode;
	public JTabbedPane tabs;
	public String totown, fromtown;
	public JFrame frame, routesframe, incidentframe, offlineframe,
			postcodeframe, newrouteframe;
	public JMenuBar menu;
	public JCheckBox onlinecheckbox;
	public JMapViewer map;
	public JPanel mainmappanel, Overviewpanel, Overviewinnerpanel1,
			Overviewinnerpanel3, Overviewinnerpanel5;
	public ArrayList<String> Towninfo;
	public Double tolat, fromlat, tolong, fromlong;

	
	public static boolean checkifroadadded(ArrayList<String> Road,
			String Roadname) {
		// Will check to see if the road inputed has been in the array that has
		// been inputed.
		for (int i = 0; i < Road.size(); i++) {
			if (Road.get(i).equalsIgnoreCase(Roadname)) {
				return true;
			}
		}
		return false;

	}

	public static void main(String[] args) {
		// Creates a new instances of the startscreen.
		new Startscreen();
	}

	public Startscreen() {
		super();
		// Will open the start screen
		layout();
	}

	// Deals with the screen for adding in the town
	public void addtown() {
		// Custom view allowing to enter Town Name and lat and long;
		// Setting up the frame
		frame = new JFrame("Add New Town");
		// Setting up the main panel
		main = new JPanel();
		// Adding Labels and Textsfields to main panel
		main.add(new JLabel("Enter Town Name: "));
		townname = new JTextField(15);
		main.add(townname);
		main.add(new JLabel("Enter Town Lat:  "));
		townlat = new JTextField(15);
		main.add(townlat);
		main.add(new JLabel("Enter Town Long: "));
		townlong = new JTextField(15);
		main.add(townlong);
		// Checking buttons and there onclicklisteners
		JButton ok = new JButton("Create Town");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				// Make sure that there is something in all textfield
				if ((townname.getText().length() > 0)
						&& (townlat.getText().length() > 0)
						&& (townlong.getText().length() > 0)) {
					if ((isdouble(townlat.getText()) == true)
							&& isdouble(townlong.getText())) {
						// Lat and long are doubles
						if (((Double.parseDouble(townlat.getText()) > 50) && (Double
								.parseDouble(townlat.getText()) < 60))
								&& ((Double.parseDouble(townlong.getText()) > -2) && (Double
										.parseDouble(townlong.getText()) < 7))) {
							// LAt and Long in the uk
							savetown(townname.getText(), townlat.getText(),
									townlong.getText());
							frame.setVisible(false);
							layout();
						} else {
							// Error message
							JOptionPane.showMessageDialog(frame,
									"Lat or Long are not in the uk!", "Error",
									JOptionPane.ERROR_MESSAGE);
						}

					} else {
						// Error message
						JOptionPane.showMessageDialog(frame,
								"Incorrect Lat or Long", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					// Error message
					JOptionPane.showMessageDialog(frame,
							"Please enter data in each field!", "Thank You",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		// Adding buttons to the panel
		main.add(ok);
		// Finishing up the frame
		frame.add(main);
		frame.setSize(200, 260);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	// Deals with the screen for adding in the town with postcode
	public void addtownwithpostcode() {
		// This will deal with the input of postcodes for adding towns
		postcodeframe = new JFrame("Add New Town");
		// Setting up the panel
		main = new JPanel();
		// Setting up textfields and labels and adding them to the panel
		main.add(new JLabel("Enter Town Name "));
		townname = new JTextField(15);
		main.add(townname);
		main.add(new JLabel("Enter To Town Postcode:  "));
		townpostcode = new JTextField(15);
		main.add(townpostcode);
		// Setting up buttons and there listeners
		JButton ok = new JButton("Create Town");
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
		main.add(ok);
		// Adding panel to the frame then setting it up to show the user
		postcodeframe.add(main);
		postcodeframe.setSize(200, 260);
		postcodeframe.setLocationRelativeTo(null);
		postcodeframe.setVisible(true);
	}

	// Find the best road
	public ArrayList<String> bestroad(ArrayList<Road> all) {
		// Will find the best road best on the amount of delays or lack of
		// delays
		Road currentbestroad = all.get(0);
		for (int i = 0; i < all.size(); i++) {
			if (all.get(i).getintdelay() < currentbestroad.getintdelay()) {
				currentbestroad = all.get(i);
			}
		}
		// Its found the best road now to get infomation on the road and return
		// them in a string array
		ArrayList<String> bestroad = new ArrayList<String>();
		bestroad.add(currentbestroad.getRoadname());
		bestroad.add(String.valueOf(currentbestroad.getTravelTime()));
		bestroad.add(String.valueOf(currentbestroad.getdelay()));
		if (currentbestroad.getRoadincidents().isEmpty()) {
			bestroad.add("No Incidents!");
		} else {
			String incidentlist = "Incidents: ";
			for (Incident incidents : currentbestroad.getRoadincidents()) {
				incidentlist = incidentlist + " ~ " + incidents.getsmalltitle();
			}
			bestroad.add(incidentlist);
		}
		return bestroad;
	}

	// Returns the correct junction
	public String correctjuction(Journeytime journey) {
		// if there is an error as the junction that means its a road so we
		// change it here
		if (journey.gettojuction().equalsIgnoreCase("Error")) {
			return "Road  " + journey.gettoroad();
		} else {
			return "Junction " + journey.gettojuction();
		}

	}

	// Finds the distance between town points
	public String currentdistance(String totown2, String fromtown2) {
		// First will get the lat and long of the town
		double lat1, lat2, lng1, lng2;
		String[] cords = getcordsoftown(totown2);
		lat1 = Double.parseDouble(cords[0]);
		lng1 = Double.parseDouble(cords[1]);
		String[] cords1 = getcordsoftown(fromtown2);
		lat2 = Double.parseDouble(cords1[0]);
		lng2 = Double.parseDouble(cords1[1]);
		if (lat2 > lat1) {
			double temp = lat1;
			lat1 = lat2;
			lat2 = temp;
		}
		if (lng2 > lng1) {
			double temp = lng1;
			lng1 = lng2;
			lng2 = temp;
		}
		// Now will work out the distances between lat and long using website
		// formula
		double earthRadius = 3958.75;
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double sindLat = Math.sin(dLat / 2);
		double sindLng = Math.sin(dLng / 2);
		double a = Math.pow(sindLat, 2)
				+ (Math.pow(sindLng, 2) * Math.cos(Math.toRadians(lat1)) * Math
						.cos(Math.toRadians(lat2)));
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		int dist = (int) (earthRadius * c);

		return String.valueOf(dist + " Miles.");

	}

	// Used to display traffic information
	public void displaytraffic(String totown, String fromtown, Boolean online) {
		// Removes old information
		Overviewinnerpanel1.removeAll();
		Overviewinnerpanel3.removeAll();
		Overviewinnerpanel5.removeAll();
		map.removeAllMapMarkers();
		mainRoadpanel.removeAll();

		// Gets the lat and long of the town
		String[] address = getcordsoftown(totown);
		tolat = Double.valueOf(address[0]);
		tolong = Double.valueOf(address[1]);
		address = getcordsoftown(fromtown);
		fromlat = Double.valueOf(address[0]);
		fromlong = Double.valueOf(address[1]);

		// Getting data used to display
		final ArrayList<Journeytime> inrangedjounreytime = getinrangejourneytime(
				JourneyTime_input.getJourneys(online), tolong, fromlong, tolat,
				fromlat);
		new Coordinate(tolat, tolong);
		new Coordinate(fromlat, fromlong);
		final ArrayList<Incident> inrangedincidents = getinranged(
				traffic_input.gettraffic(online), tolong, fromlong, tolat,
				fromlat);
		ArrayList<String> inrangedroads = getroads(inrangedincidents);
		final ArrayList<Road> inrangedsortedroads = Directions.orderlist(
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
		// Getting best road
		ArrayList<String> bestroad = bestroad(inrangedsortedroads);
		Overviewinnerpanel3.add(new JLabel("Road Name: " + bestroad.get(0)));
		Overviewinnerpanel3.add(new JLabel("Current Time: "
				+ gettime(bestroad.get(1))));
		Overviewinnerpanel3
				.add(new JLabel("Above time by:  "
						+ String.valueOf(Math.abs(Double.parseDouble(bestroad
								.get(2))))));
		JTextArea output = new JTextArea();
		JScrollPane scroll = new JScrollPane(output);
		String[] allincidents = bestroad.get(3).split("~");
		for (String temp : allincidents) {
			output.append("    " + temp + "\n");
		}
		Overviewinnerpanel3.add(scroll);

		// Getting worst road
		ArrayList<String> worstroad = worstroad(inrangedsortedroads);
		Overviewinnerpanel5.add(new JLabel("Road Name: " + worstroad.get(0)));
		Overviewinnerpanel5
				.add(new JLabel("Current Time: " + worstroad.get(1)));
		Overviewinnerpanel5.add(new JLabel("Delayed by:  " + worstroad.get(2)));
		JTextArea output1 = new JTextArea();
		JScrollPane scroll1 = new JScrollPane(output1);
		String[] allincidents1 = worstroad.get(3).split("~");
		for (String temp : allincidents1) {
			output1.append("    " + temp + "\n");
		}
		Overviewinnerpanel5.add(scroll1);

		// Refresh the panels.
		Overviewinnerpanel1.revalidate();
		Overviewinnerpanel3.revalidate();
		Overviewinnerpanel5.revalidate();

		// Changes the panel size based on the amount of roads that will be
		// shown in the panel
		if (inrangedroads.size() < 5) {
			mainRoadpanel.setPreferredSize(new Dimension(1000, 200));
			mainRoadpanel.setMaximumSize(new Dimension(1000, 200));
		} else if (inrangedroads.size() < 10) {
			mainRoadpanel.setPreferredSize(new Dimension(1000, 400));
			mainRoadpanel.setMaximumSize(new Dimension(1000, 400));
		} else if (inrangedroads.size() < 20) {
			mainRoadpanel.setPreferredSize(new Dimension(1000, 600));
			mainRoadpanel.setMaximumSize(new Dimension(1000, 600));
		} else if (inrangedroads.size() < 30) {
			mainRoadpanel.setPreferredSize(new Dimension(1000, 800));
			mainRoadpanel.setMaximumSize(new Dimension(1000, 800));
		} else if (inrangedroads.size() < 50) {
			mainRoadpanel.setPreferredSize(new Dimension(1000, 1200));
			mainRoadpanel.setMaximumSize(new Dimension(1000, 1200));
		}

		// Setting up the roads information
		for (int i = 0; i < inrangedroads.size(); i++) {
			// This is where the grid layout will be made and enter in based on
			// how many roads there is
			JPanel temproadpanel = new JPanel();
			BoxLayout templayout = new BoxLayout(temproadpanel,
					BoxLayout.Y_AXIS);
			// Creates a border and then sets the border ot the panel

			temproadpanel.setLayout(templayout);
			Border tempborder = BorderFactory
					.createTitledBorder(inrangedsortedroads.get(i)
							.getRoadname());
			temproadpanel.setBorder(tempborder);
			// Sets the preferred size
			temproadpanel.setAlignmentX(Component.CENTER_ALIGNMENT);
			temproadpanel.setPreferredSize(new Dimension(100, 100));
			temproadpanel.setMaximumSize(new Dimension(100, 100));
			// Adds padding to the left hand side
			temproadpanel.add(Box.createRigidArea(new Dimension(0, 5)));
			// Then will see if there is a delay and if there is then it will
			// display it however if not then it will create black space
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
			// Display the amount of incident and then allows the user to find
			// out more
			temproadpanel.add(new JLabel("Number of Incidents: "
					+ inrangedsortedroads.get(i).incidentsize()));
			temproadpanel.add(Box.createRigidArea(new Dimension(0, 10)));
			JButton tempbutton = new JButton("View Incidents");
			final ArrayList<Incident> incidents = inrangedsortedroads.get(i)
					.getRoadincidents();
			tempbutton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent actionEvent) {
					// Creates and incident frame and display them in a textarea
					incidentframe = new JFrame("View Incidents");
					main = new JPanel();
					JTextArea taoutput = new JTextArea();
					for (Incident item : incidents) {
						taoutput.append(item.getsmalltitle() + "\n");
					}
					JScrollPane scrollpane = new JScrollPane(taoutput);
					incidentframe.add(scrollpane);
					incidentframe.setSize(600, 500);
					incidentframe.setLocationRelativeTo(null);
					incidentframe.setVisible(true);
				}
			});
			// Adding them to the panel
			temproadpanel.add(tempbutton);
			mainRoadpanel.add(temproadpanel);

		}
		// Refresh the main road panel
		mainRoadpanel.revalidate();

		// This will add markers to the map based on any incidents
		for (int i = 0; i < inrangedincidents.size(); i++) {
			map.addMapMarker(new MapMarkerDot(
					inrangedincidents.get(i).getLat(), inrangedincidents.get(i)
							.getLonga()));
		}
		// This will show the jounrey time data
		for (int i = 0; i < inrangedjounreytime.size(); i++) {
			MapMarkerDot temp = new MapMarkerDot(
					gettrafficcolor(inrangedjounreytime.get(i)),
					inrangedjounreytime.get(i).getfromlat(),
					inrangedjounreytime.get(i).getfromlong());
			temp.setBackColor(gettrafficcolor(inrangedjounreytime.get(i)));
			map.addMapMarker(temp);
		}

		// Adding a Mouse Listener so you can tell when the user has clicked
		map.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {

					Point p = e.getPoint();
					int X = p.x + 3;
					int Y = p.y + 3;
					List<MapMarker> ar = map.getMapMarkerList();
					Iterator<MapMarker> i = ar.iterator();
					while (i.hasNext()) {

						MapMarker mapMarker = i.next();

						Point MarkerPosition = map.getMapPosition(
								mapMarker.getLat(), mapMarker.getLon());
						if (MarkerPosition != null) {

							int centerX = MarkerPosition.x;
							int centerY = MarkerPosition.y;

							// calculate the radius from the touch to the center
							// of the dot
							double radCircle = Math
									.sqrt((((centerX - X) * (centerX - X)) + ((centerY - Y) * (centerY - Y))));

							// if the radius is smaller then 23 (radius of a
							// ball is 5), then it must be on the dot
							if (radCircle < 8) {
								String message = getincidentsat(
										inrangedincidents, inrangedjounreytime,
										mapMarker.getLat(), mapMarker.getLon());
								if (message.length() > 1) {
									JOptionPane.showMessageDialog(frame,
											message, String.valueOf(mapMarker
													.hashCode()),
											JOptionPane.PLAIN_MESSAGE);
								}
							}

						}
					}
				}
			}
		});

	}

	// Returns a string array of the town
	public String[] getcordsoftown(String intown) {
		// Returns the cords of the town
		Towninfo = readalltowninfo();
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

	// Returns a string message based on where the user click
	public String getincidentsat(ArrayList<Incident> allincidents,
			ArrayList<Journeytime> alljounreytime, double inlat, double inlong) {
		// Finds the incident or journeytime on where the user has clicked as it
		// will have to be one of them.
		for (Incident temp : allincidents) {
			if ((temp.getLat() == inlat) || (temp.getLonga() == inlong)) {
				return "Issue at " + temp.gettitle() + "\n " + temp.getdesc();

			}
		}
		for (Journeytime temp : alljounreytime) {
			if ((temp.getfromlat() == inlat) || (temp.getfromlong() == inlong)) {
				return "Currently going between point: " + correctjuction(temp)
						+ " to " + correctjuction(temp) + " Will take you "
						+ temp.getcurrenttime() + " !";
			}
		}
		return "";
	}

	// Find all the incidents in ranged
	public ArrayList<Incident> getinranged(ArrayList<Incident> allincidents,
			double tolong, double fromlong, double tolat, double fromlat) {
		// Returns an array of all the in ranged incidents by checking each
		// instance
		ArrayList<Incident> inrangedincident = new ArrayList<Incident>();
		for (int i = 0; i < allincidents.size(); i++) {
			if (allincidents.get(i).inrange(tolong, fromlong, tolat, fromlat)) {
				inrangedincident.add(allincidents.get(i));
			}
		}
		return inrangedincident;

	}

	// Finds all the Journeytimes in range
	public ArrayList<Journeytime> getinrangejourneytime(
			ArrayList<Journeytime> all, double tolong, double fromlong,
			double tolat, double fromlat) {
		// Returns an array of all the in ranged Journeytime by checking each
		// instance
		ArrayList<Journeytime> inrangejourneys = new ArrayList<Journeytime>();
		for (int i = 0; i < all.size(); i++) {
			if (all.get(i).inrange(tolong, fromlong, tolat, fromlat) == true) {
				inrangejourneys.add(all.get(i));
			}
		}

		return inrangejourneys;
	}

	// Gets a String array of roads based on the incidents that have been
	// inputted
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

	// Creates a road classes based on incident
	public ArrayList<Road> getroadsasclass(ArrayList<Incident> Journeys) {
		// This will collect the journey times on the spades.
		ArrayList<String> roads = new ArrayList<String>();
		ArrayList<Road> allroads = new ArrayList<Road>();
		// Will first check to see if the road from the first array is the same
		// of the second array
		// Then will see if its been added before if its hasn't then it will
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
						// Adding the new instace to the array
						allroads.add(Temp);
						roads.add(Journeys.get(i).getroad());
					}

				}
			}
		}
		return allroads;
	}

	// Formats time
	public String gettime(String time) {
		int inttime = (int) Math.round(Double.parseDouble(time));
		if (inttime < 60) {
			return time + " Minutes.";
		} else {
			return String.valueOf(inttime / 60) + "Hour(s).";
		}
	}

	// Gives color based on delay
	public Color gettrafficcolor(Journeytime tempinput) {
		Double delay = tempinput.getcurrenttime()
				- (tempinput.getnormaltime() % 60);
		if (delay < 1) {
			// No delay so green
			return Color.GREEN;
		} else if (delay < 60) {
			// Mild delay so amber
			return Color.ORANGE;
		} else if (delay > 60) {
			// Heavy delay so red
			return Color.RED;
		} else {
			return Color.GREEN;
		}

	}

	// Checks to see if the input is double
	public Boolean isdouble(String input) {
		// Will try to parse if it can't then its not a double
		try {
			Double.parseDouble(input);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	// Creates the main layout
	public void layout() {
		// This will display the starting screen for the program with input of
		// postcodes.
		offlineframe = new JFrame("Traffic Watcher");
		offlineframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Setting up a menu bar
		menu = new JMenuBar();
		offlineframe.setJMenuBar(menu);
		JMenu add = new JMenu("Add");
		menu.add(add);
		// Making Menu Items to add to the main menu drop down
		JMenuItem newtownlat = new JMenuItem("New Town with Lat and Long");
		JMenuItem newtownpostcode = new JMenuItem("New Town with Postcode");
		// Adding Action Listener so that we have events when clicked
		add.add(newtownlat);
		add.add(newtownpostcode);

		newtownlat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				// Allows user to add a new town
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
		// Creating a new route menu bar
		JMenu Route = new JMenu("Route");
		menu.add(Route);
		// Adds Menu Items to the menu
		JMenuItem newroute = new JMenuItem("Start new route");
		JMenuItem saveroute = new JMenuItem("Save current route");
		JMenuItem loadroute = new JMenuItem("Load route");
		Route.add(newroute);
		Route.add(saveroute);
		Route.add(loadroute);
		// Adds onactionlisteners
		newroute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				// Open up the new route layout
				newroute();
			}

		});
		saveroute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				// Will try and save the route however can't save if the user is
				// not in a route
				try {
					if (totown.isEmpty() || fromtown.isEmpty()) {
						// Display error message
						JOptionPane.showMessageDialog(offlineframe,
								"Please start a route to save it.", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						// Saving route
						saveroute(totown, fromtown);
					}
				} catch (Exception a) {
					// Display error message
					JOptionPane.showMessageDialog(offlineframe,
							"Please start a route to save it.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		loadroute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				// This loads in a all saved routes
				ArrayList<String> routes = readroutes();

				String[][] realroutes = new String[routes.size()][2];
				for (int i = 0; i < routes.size(); i++) {
					String[] temparray = routes.get(i).split(",");
					realroutes[i][0] = temparray[0];
					realroutes[i][1] = temparray[1];
				}
				// Starting to create the table
				String[] columns = { "To", "From" };

				// Creates the frame and panel for the routes
				routesframe = new JFrame();
				JPanel routespanel = new JPanel();
				// Adds the 2D array and headers into the JTable
				JTable routestable = new JTable(realroutes, columns);
				// Makes the table scrollable
				JScrollPane routespane = new JScrollPane(routestable);
				routestable.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						// Finds the JTable then finds what row had been click
						// and pull the to town and from town
						JTable clickedtable = (JTable) e.getSource();
						String totown = clickedtable.getValueAt(
								clickedtable.getSelectedRow(), 0).toString();
						String fromtown = clickedtable.getValueAt(
								clickedtable.getSelectedRow(), 1).toString();
						Boolean online;
						// Asks the user if they are online or offline
						if (JOptionPane.showConfirmDialog(null, "Use online?",
								"Question", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
							online = true;
						} else {
							online = false;
						}
						// Displays the traffic based on the information
						displaytraffic(totown, fromtown, online);
						// Removes the frame
						routesframe.setVisible(false);
					}
				});
				// Adds the routes panel and add the header to the pnale
				routespanel.add(routestable.getTableHeader());
				routesframe.add(routespanel);
				routesframe.add(routespane);
				// Display the panel
				routesframe.setVisible(true);
				routesframe.setSize(600, 300);
			}

		});
		// Setting the main panels
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
		BoxLayout innerlayout3 = new BoxLayout(Overviewinnerpanel3,
				BoxLayout.Y_AXIS);
		Overviewinnerpanel3.setLayout(innerlayout3);
		Overviewinnerpanel3.setAlignmentX(Component.CENTER_ALIGNMENT);
		Overviewinnerpanel3.setPreferredSize(new Dimension(300, 300));
		Overviewinnerpanel3.setMaximumSize(new Dimension(300, 300));
		Overviewinnerpanel3
				.add(new JLabel(
						"                                                            "));
		Border innerborder3 = BorderFactory.createTitledBorder("Best Road");
		Overviewinnerpanel3.setBorder(innerborder3);
		middlesidebox.add(Box.createVerticalStrut(30));
		middlesidebox.add(Overviewinnerpanel3);
		middlesidebox.add(Box.createRigidArea(new Dimension(20, 40)));
		// Adding Right panel
		Overviewinnerpanel5 = new JPanel();
		BoxLayout innerlayout5 = new BoxLayout(Overviewinnerpanel5,
				BoxLayout.Y_AXIS);
		Overviewinnerpanel5.setLayout(innerlayout5);
		Overviewinnerpanel5.setAlignmentX(Component.CENTER_ALIGNMENT);
		Overviewinnerpanel5.setPreferredSize(new Dimension(300, 300));
		Overviewinnerpanel5.setMaximumSize(new Dimension(300, 300));
		Overviewinnerpanel5
				.add(new JLabel(
						"                                                            "));
		Border innerborder5 = BorderFactory.createTitledBorder("Worst Road");
		Overviewinnerpanel5.setBorder(innerborder5);
		rightsidebox.add(Box.createVerticalStrut(30));
		rightsidebox.add(Overviewinnerpanel5);
		rightsidebox.add(Box.createRigidArea(new Dimension(20, 40)));
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
		// Setting up the Maps Panel
		mainmappanel = new JPanel();
		map = new JMapViewer();
		map.setSize(1000, 1000);
		map.setDisplayPositionByLatLon(53.508804, -1.702287, 6);
		map.setPreferredSize(new Dimension(1000, 800));
		map.setMaximumSize(new Dimension(1000, 800));
		mainmappanel.add(map);
		// Setting up the tabs
		tabs = new JTabbedPane();
		tabs.addTab("Overview", Overviewpanel);
		tabs.addTab("Roads", roadscroll);
		tabs.addTab("Map", mainmappanel);
		// Addings tabs to the frame
		offlineframe.add(tabs);
		tabs.setVisible(false);
		// ONLINE AT
		// http://stackoverflow.com/questions/11570356/jframe-in-full-screen-java
		offlineframe.setExtendedState(Frame.MAXIMIZED_BOTH);
		// Shows the frame
		offlineframe.setVisible(true);
	}

	// Create the layout for creating a new route
	public void newroute() {
		// This will display the route finding items
		newrouteframe = new JFrame("Starting new route");
		JPanel mainpanel = new JPanel();
		final ArrayList<String> temptownarray = readtowns();
		String[] alltowns = new String[temptownarray.size()];
		alltowns = temptownarray.toArray(alltowns);
		// Creates dropdown boxs for the user ot pick from filled with
		// information from the towns stored
		JLabel tocombolabel = new JLabel("Traveling to: ");
		final JComboBox Tocombo = new JComboBox(alltowns);
		JLabel fromcombolabel = new JLabel("Traveling from: ");
		final JComboBox Fromcombo = new JComboBox(alltowns);
		onlinecheckbox = new JCheckBox("Online", false);
		// Creates a button and its listener.
		JButton Findroutes = new JButton("Find Traffic");
		Findroutes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				// This will input the 2 towns into the function
				totown = temptownarray.get(Tocombo.getSelectedIndex());
				fromtown = temptownarray.get(Fromcombo.getSelectedIndex());
				displaytraffic(totown, fromtown, onlinecheckbox.isSelected());
				newrouteframe.setVisible(false);
			}

		});
		// Giving the dropdown box its labels
		tocombolabel.setLabelFor(Tocombo);
		fromcombolabel.setLabelFor(Fromcombo);
		// Adding to the main panel
		mainpanel.add(tocombolabel);
		mainpanel.add(Tocombo);
		mainpanel.add(fromcombolabel);
		mainpanel.add(Fromcombo);
		mainpanel.add(Findroutes);
		// Setting up the frame
		newrouteframe.add(mainpanel);
		newrouteframe.setSize(220, 200);
		newrouteframe.setLocationRelativeTo(null);
		newrouteframe.setVisible(true);

	}

	// Reads the town file and returns all data held
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

	// Reads the route file and returns all possible routes
	public ArrayList<String> readroutes() {
		try {
			// This class will read the file then will return the routes info
			// In the format of to town name, from town name
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

	// Reads the town file and returns town names not lat and long
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

	// Saves the Routes
	public void saveroute(String totown, String fromtown) {
		// This will add to the file which holds all of the user
		try {
			File routefile = new File("Routes.txt");
			// Creates an array of data currently in the file
			ArrayList<String> Routes = readroutes();
			// Creates an output writer
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter(routefile)));
			for (String routeinfo : Routes) {
				// Adds all the old infomation back into the file
				out.append(routeinfo + "\n");
			}
			// Adds the new information to the file
			out.append(totown + "," + fromtown);
			// Closes the file
			out.close();
			// Lets the user know that the route has been saved
			JOptionPane.showMessageDialog(offlineframe, "Route has been Saved",
					"Thank You", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			// Catch Errors
			e.printStackTrace();
		}

	}

	// Saves the Towns
	public void savetown(String Townname, String TownLat, String TownLong) {
		// This will add to the file which holds all of the saved town data
		try {
			File townfile = new File("Offlinetowns.txt");
			// Creates an array of the old data
			ArrayList<String> Towns = readalltowninfo();
			// Creates an output stream
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter(townfile)));
			for (String towninfo : Towns) {
				// Saving old data
				out.append(towninfo + "\n");
			}
			// Adding the new data
			out.append(Townname + "," + TownLat + "," + TownLong);
			out.close();
			// Letting the user know its been saved
			JOptionPane.showMessageDialog(offlineframe, "Town has been Saved",
					"Thank You", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			// Catch Errors
			e.printStackTrace();
		}

	}

	// This deals with adding to the road classes
	public void setroadswithdata(ArrayList<Journeytime> inrangedjounreytime,
			ArrayList<Incident> inrangedincidents,
			ArrayList<Road> inrangedsortedroads) {
		for (Road road : inrangedsortedroads) {
			// This will add the information on journeytime to the correct road
			for (Journeytime item : inrangedjounreytime) {
				if (item.gettoroad().equalsIgnoreCase(road.getRoadname())
						|| item.getfromroad().equalsIgnoreCase(
								road.getRoadname())) {

					road.setNormallyExpectedTravelTime(road
							.getNormallyExpectedTravelTime()
							+ item.getnormaltime());

					road.setTravelTime(road.getTravelTime()
							+ item.getcurrenttime());
					road.addjuctions(item);
				}
			}
			// This will add the information on Incident to the correct road
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

	// Collects information based on the worstroad.
	public ArrayList<String> worstroad(ArrayList<Road> all) {
		// This will find the worst road and return infomation based on it.
		if (all.size() < 1) {
			return null;
		}
		Road currentworstroad = all.get(0);
		for (int i = 0; i < all.size(); i++) {
			if (all.get(i).getintdelay() > currentworstroad.getintdelay()) {
				currentworstroad = all.get(i);
			}
		}
		// Found worst road now save the information to a string array
		ArrayList<String> worstroad = new ArrayList<String>();
		worstroad.add(currentworstroad.getRoadname());
		worstroad.add(String.valueOf(currentworstroad.getTravelTime()));
		worstroad.add(String.valueOf(currentworstroad.getdelay()));
		if (currentworstroad.getRoadincidents().isEmpty()) {
			worstroad.add("No Incidents!");
		} else {
			String incidentlist = "Incidents: ";
			for (Incident incidents : currentworstroad.getRoadincidents()) {
				incidentlist = incidentlist + " ~ " + incidents.getsmalltitle();
			}
			worstroad.add(incidentlist);
		}

		return worstroad;
	}
}

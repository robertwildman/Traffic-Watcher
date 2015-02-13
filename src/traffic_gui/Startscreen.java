package traffic_gui;

import java.awt.*;
import java.awt.event.*;
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

import javax.swing.*;
import javax.swing.border.Border;

import traffic_analyze.Directions;
import traffic_analyze.Incident;
import traffic_analyze.Journeytime;
import traffic_analyze.Postcode_Processing;
import traffic_analyze.Road;
import traffic_input.JourneyTime_input;
import traffic_input.Postcodeinput;
import traffic_input.traffic_input;

public class Startscreen implements ActionListener {

	public JPanel postcodepanel, setcordpanel, main, sp,mainRoadpanel;
	public JTextField tfpostcode1, tfpostcode2, townname, townlat, townlong,townpostcode;
	public JTextArea output;
	public JTabbedPane tabs;
	public String totown,fromtown;
	public JFrame frame,offlineframe,postcodeframe,newrouteframe;
	public DefaultComboBoxModel model;
	public JMenuBar menu;
	public JPanel combopanel,roadpanel,Overviewpanel,Overviewinnerpanel1,Overviewinnerpanel2;
	public JScrollPane Scrollpane;
	public JComboBox trafficlist;
	public ArrayList<String> Towninfo, smalltitle, fulltitle, fulldesc;
	public ArrayList<Incident> affectedIncidents;
	public Boolean Toaddress;
	public Double tolat, fromlat, tolong, fromlong;
	public JLabel lpostcode1, lpostcode2, info, JLTitle, JLDesc;

	public static void main(String[] args) {
		// This will display the starting screen for the program with input of
		// postcodes.
		Startscreen screen = new Startscreen();
	}

	public Startscreen() {
		super();
		// Will open the start screen
		Toaddress = true;
		offlinelayout();
	}

	public void onlinelayout() {
		// This will display the online part of the program.
		JFrame frame = new JFrame("Online Mode");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel mainpanel = new JPanel();

		
		// Setting up the postcode panel
		postcodepanel = new JPanel();
		BoxLayout layout = new BoxLayout(postcodepanel, BoxLayout.X_AXIS);
		postcodepanel.setLayout(layout);
		// Adding the Postcode Labels and TextFields
		lpostcode1 = new JLabel("Postcode of starting point: ");
		tfpostcode1 = new JTextField();
		lpostcode2 = new JLabel("Postcode of end point: ");
		tfpostcode2 = new JTextField();
		lpostcode1.setLabelFor(tfpostcode1);
		lpostcode2.setLabelFor(tfpostcode2);
		// Adding them to the panel
		postcodepanel.add(lpostcode1);
		postcodepanel.add(tfpostcode1);
		postcodepanel.add(lpostcode2);
		postcodepanel.add(tfpostcode2);
		// Setting it to the top
		frame.add(postcodepanel, BorderLayout.PAGE_START);
		frame.add(mainpanel, BorderLayout.CENTER);
		// Sets the size of the Frame
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		// Shows the frame
		frame.setVisible(true);
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

	public void offlinelayout() {
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
				//Will open the postcode input
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
				//TODO
			}

			
		});
		loadroute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				//TODO
			}

			
		});
		
		JMenu View = new JMenu("View");
		menu.add(View);
		JMenuItem showmap = new JMenuItem("Show Map");
		JMenuItem clearscreen = new JMenuItem("Reset");
		View.add(showmap);
		View.add(clearscreen);
		showmap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if(Postcode_Processing.checkifonline() == true)
				{
					
				}else
				{
					JOptionPane.showMessageDialog(frame,
							"Not Conntected to the internet!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}

			
		});
		clearscreen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				//TODO
			}

			
		});

		//Will setup the pnaels for the tabs 
		Overviewpanel = new JPanel();
		Overviewpanel.setLayout(new BorderLayout());
		Box sidebox = Box.createVerticalBox();
	
		Overviewinnerpanel1 = new JPanel();
		Overviewinnerpanel2 = new JPanel();
		BoxLayout innerlayout1 = new BoxLayout(Overviewinnerpanel1, BoxLayout.Y_AXIS);
		BoxLayout innerlayout2 = new BoxLayout(Overviewinnerpanel2, BoxLayout.Y_AXIS);
		Overviewinnerpanel1.setLayout(innerlayout1);
		Overviewinnerpanel2.setLayout(innerlayout2); 
		Overviewinnerpanel1.setAlignmentX(Component.CENTER_ALIGNMENT);
		Overviewinnerpanel1.setPreferredSize(new Dimension(300, 300));
		Overviewinnerpanel1.setMaximumSize(new Dimension(300, 300)); 
		Overviewinnerpanel2.setAlignmentX(Component.CENTER_ALIGNMENT);
		Overviewinnerpanel2.setPreferredSize(new Dimension(300, 300));
		Overviewinnerpanel2.setMaximumSize(new Dimension(300, 300)); 
		Overviewinnerpanel1.add(new JLabel("                                                            "));
		Overviewinnerpanel2.add(new JLabel("                                                            "));
		Border innerborder1 = BorderFactory.createTitledBorder("Journey Info");
		Border innerborder2 = BorderFactory.createTitledBorder("Worst Road");
		Overviewinnerpanel1.setBorder(innerborder1);
		Overviewinnerpanel2.setBorder(innerborder2);
		sidebox.add(Box.createVerticalStrut(30));
		sidebox.add(Overviewinnerpanel1);
		sidebox.add(Box.createRigidArea(new Dimension(20,40)));
		sidebox.add(Overviewinnerpanel2);
		Overviewpanel.add(sidebox,BorderLayout.WEST);

		
		//Setting up road layout
		mainRoadpanel = new JPanel();
		GridLayout experimentLayout = new GridLayout(0,8,5,5);
		mainRoadpanel.setLayout(experimentLayout);
		mainRoadpanel.setPreferredSize(new Dimension(1000,1500));
		mainRoadpanel.setMaximumSize(new Dimension(1000, 1500)); 
		JScrollPane roadscroll = new JScrollPane(mainRoadpanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		roadscroll.getVerticalScrollBar().setUnitIncrement(16);
		tabs = new JTabbedPane();
		tabs.addTab("Overview", Overviewpanel);
		tabs.addTab("Roads", roadscroll);
		tabs.addTab("Map", new JLabel("Map"));
		offlineframe.add(tabs);
		tabs.setVisible(false);
		combopanel = new JPanel();
		output = new JTextArea();
		output.setEditable(false);
		output.setVisible(false);
		info = new JLabel("     To:");
		//offlineframe.add(Scrollpane, BorderLayout.CENTER);
		// Sets the size of the Frame
		// ONLINE AT
		// http://stackoverflow.com/questions/11570356/jframe-in-full-screen-java
		offlineframe.setExtendedState(Frame.MAXIMIZED_BOTH);
		// Shows the frame
		offlineframe.setVisible(true);
	}

	public String currentdistance(String totown2, String fromtown2) {
		// TODO Auto-generated method stub
		return "1000";
	}

	public void addtownwithpostcode() {
		//This will look after the postcode
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
				if (townname.getText().length() > 0 && townpostcode.getText().length() > 0) {
					String[] cords = Postcodeinput.getcords(townpostcode.getText().toString()).split(",");
					savetown(townname.getText(),cords[0],cords[1]);
					frame.setVisible(false);
					offlinelayout();
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
				if (townname.getText().length() > 0
						&& townlat.getText().length() > 0
						&& townlong.getText().length() > 0) {
					savetown(townname.getText(), townlat.getText(),
							townlong.getText());
					frame.setVisible(false);
					offlinelayout();
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
		frame.add(main);
		frame.setSize(200, 260);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
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
			//Sets the to town 
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
			model = (DefaultComboBoxModel) trafficlist
					.getModel();
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
			if(model.getSize() == 0)
			{
				model.addElement("No roads affected!");
			}
			
			trafficlist.setModel(model);
			trafficlist.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent actionEvent) {
					//Happens when the user clicks on the combo box
					JComboBox traffic = (JComboBox) actionEvent.getSource();
					if(model.getElementAt(traffic.getSelectedIndex())
									.toString().equalsIgnoreCase("test"))
					{
						test(affectedIncidents);
					}
					//Gets any issues on the road that the user has picked
					ArrayList<String> allroadincidents = roadbyname(
							affectedIncidents,
							model.getElementAt(traffic.getSelectedIndex()).toString());
					output.setVisible(true);
					output.setText(" ");
					for (String item : allroadincidents) {
						String[] items = new String[2];
						items = item.split("#");
						output.append(items[0] + "\n");
						output.append("                 " + items[1] + "\n");
						output.append("    \n");
					}
					//Will get a list of affected jounreytimes
					ArrayList<Journeytime> alljourneytime = getinrangejourneytime(JourneyTime_input
							.getJourneys(false),tolong, fromlong, tolat,
							fromlat);
					//This will work out if there is a delay and then display message to user. 
					output.append(getjourneytimedata(alljourneytime, model
							.getElementAt(traffic.getSelectedIndex())
							.toString()));
					
				}

				

			});
		}

	}

	public void newroute() {
		//This will display the route finding items 
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
				//This will input the 2 towns into the function 
				totown = temptownarray.get(Tocombo.getSelectedIndex()); 
				fromtown =  temptownarray.get(Fromcombo.getSelectedIndex()); 
				displaytraffic(totown,fromtown);
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
	
	public void displaytraffic(String totown, String fromtown)
	{
		//Removes old infomation 
		Overviewinnerpanel1.removeAll();
		Overviewinnerpanel2.removeAll();
		mainRoadpanel.removeAll();
		//Gets the lat and long of the town 
		String[] address = getcordsoftown(totown);
		tolat = Double.valueOf(address[0]);
		tolong = Double.valueOf(address[1]);
		address = getcordsoftown(fromtown);
		fromlat = Double.valueOf(address[0]);
		fromlong = Double.valueOf(address[1]);
		
		//Getting data used to display 
		ArrayList<Journeytime> inrangedjounreytime = getinrangejourneytime(JourneyTime_input.getJourneys(false),tolong,fromlong,tolat,fromlat);
		ArrayList<Incident> inrangedincidents = getinranged(traffic_input.gettraffic(true),tolong,fromlong,tolat,fromlat);
		ArrayList<String>inrangedroads = getroads(inrangedincidents);
		ArrayList<Road> inrangedsortedroads = Directions.orderlist(getroadsasclass(inrangedincidents), tolat ,  tolong, fromlat ,  fromlong);
		setroadswithdata(inrangedjounreytime,inrangedincidents,inrangedsortedroads);
		//Shows the tabs
		tabs.setVisible(true);
		
		//Deaing with first tab
		//Setting up the overview class 
		Overviewinnerpanel1.add(new JLabel("Traveling to: " + totown));
		Overviewinnerpanel1.add(new JLabel("Traveling from: "+ fromtown));
		Overviewinnerpanel1.add(new JLabel("Distance:  "+ currentdistance(totown,fromtown)));
		Overviewinnerpanel1.add(new JLabel("Time on English main roads:  "+ currenttime(totown,fromtown)));
		Overviewinnerpanel1.revalidate();
		//Gets worst roads and all the infomation based on it 
		ArrayList<String> worstroad = worstroad();
		Overviewinnerpanel2.add(new JLabel("Road Name: " + worstroad.get(0)));
		Overviewinnerpanel2.add(new JLabel("Current Time: "+ worstroad.get(1)));
		Overviewinnerpanel2.add(new JLabel("Delayed by:  "+ worstroad.get(2)));
		Overviewinnerpanel2.add(new JLabel("Incidents:  "+ worstroad.get(3)));
		
		if(inrangedroads.size() < 30)
		{
			mainRoadpanel.setPreferredSize(new Dimension(1000,800));
			mainRoadpanel.setMaximumSize(new Dimension(1000, 800)); 
		}else if(inrangedroads.size() < 50)
		{
			mainRoadpanel.setPreferredSize(new Dimension(1000,1200));
			mainRoadpanel.setMaximumSize(new Dimension(1000, 1200)); 
		}
		//Setting up the roads infomation 
		for(int i = 0; i < inrangedroads.size(); i++)
		{
			//This is where the grid layout will be made and enter in based on how many roads there is 
			JPanel temproadpanel = new JPanel();
			BoxLayout templayout = new BoxLayout(temproadpanel, BoxLayout.Y_AXIS);
			temproadpanel.setLayout(templayout);
			Border tempborder = BorderFactory.createTitledBorder(inrangedsortedroads.get(i).getRoadname());
			temproadpanel.setBorder(tempborder);
			temproadpanel.setAlignmentX(Component.CENTER_ALIGNMENT);
			temproadpanel.setPreferredSize(new Dimension(100, 100));
			temproadpanel.setMaximumSize(new Dimension(100, 100)); 
			temproadpanel.add(Box.createRigidArea(new Dimension(0, 5)));
			if(inrangedsortedroads.get(i).getdelay() != null)
			{
				int delay =inrangedsortedroads.get(i).getintdelay();
			if( delay > 0)
			{
			temproadpanel.add(new JLabel("Delay: " + inrangedsortedroads.get(i).getdelay()+" minutes"));
			}
			} 	
			temproadpanel.add(Box.createRigidArea(new Dimension(0, 10)));
			temproadpanel.add(new JLabel("Number of Incidents: " + inrangedsortedroads.get(i).incidentsize()));
			temproadpanel.add(Box.createRigidArea(new Dimension(0, 10)));
			JButton tempbutton = new JButton("View Incidents");
			temproadpanel.add(tempbutton);
			System.out.println(i);
			mainRoadpanel.add(temproadpanel);
			
		}
		mainRoadpanel.revalidate();
		
		
		
		//This will add a new panel to the grid for each road 
		
	/*	//Gets any issues on the road that the user has picked
		
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
		//Will get a list of affected jounreytimes
		ArrayList<Journeytime> alljourneytime = getinrangejourneytime(JourneyTime_input
				.getJourneys(false),tolong, fromlong, tolat,
				fromlat);
		//This will work out if there is a delay and then display message to user. 
		output.append(getjourneytimedata(alljourneytime, model
				.getElementAt(traffic.getSelectedIndex())
				.toString()));
		
		//This will get any delays on the road and return a String array which then will be print onto the screen;
		ArrayList<String> affectedroads = getdelayedjuctions(
				alljourneytime,
				model.getElementAt(traffic.getSelectedIndex())
						.toString());
		
		if (affectedroads.size() > 0) {
			output.append("However there is minor issues at: \n");
		}
		for (String roadinfo : affectedroads) {
			output.append(roadinfo);
		}*/
	}
	public void setroadswithdata(ArrayList<Journeytime> inrangedjounreytime,ArrayList<Incident> inrangedincidents ,ArrayList<Road> inrangedsortedroads) {
		for(Road road : inrangedsortedroads){
			for(Journeytime item : inrangedjounreytime)
			{
				if(item.gettoroad().equalsIgnoreCase(road.getRoadname()) || item.getfromroad().equalsIgnoreCase(road.getRoadname()))
				{
					road.setNormallyExpectedTravelTime(road.getNormallyExpectedTravelTime() + item.getnormaltime());
					road.setTravelTime(road.getTravelTime() + item.getcurrenttime());
				}
			}
			for(Incident currentincident : inrangedincidents)
			{
				if(currentincident.getroad().equalsIgnoreCase(road.getRoadname()))
				{
					ArrayList<Incident> temp = road.getRoadincidents();
					temp.add(currentincident);
					road.setRoadincidents(temp);
				}
			}
		}		
	}

	public ArrayList<Incident> getinranged(ArrayList<Incident> allincidents, double tolong, double fromlong, double tolat, double fromlat)
	{
		ArrayList<Incident> inrangedincident = new ArrayList<Incident>();
		for(int i = 0; i < allincidents.size(); i++)
		{
			if(allincidents.get(i).inrange(tolong, fromlong, tolat, fromlat))
			{
				inrangedincident.add(allincidents.get(i));
			}
		}
			return inrangedincident;
		
	}
	public ArrayList<String> worstroad() {
		// TODO Auto-generated method stub
		ArrayList<String> worstroad = new ArrayList<String>();
		worstroad.add("f");
		worstroad.add("f");
		worstroad.add("f");
		worstroad.add("f");
		worstroad.add("f");
		worstroad.add("f");
		worstroad.add("f");
		worstroad.add("f");
		return worstroad;
	}

	public String currenttime(String totown2, String fromtown2) {
		// TODO Auto-generated method stub
		return null;
	}

	public void test(ArrayList<Incident> incidents) {
		// TODO Auto-generated method stub
		for(Road item: getroadsasclass(incidents))
		{
			System.out.println(item.getRoadname() + " lat: " + item.getlat()+ " long: " + item.getlong());
		}
		System.out.println("STARTING SORT");
		ArrayList<Road> allroadssorted = Directions.orderlist(getroadsasclass(incidents), tolat ,  tolong, fromlat ,  fromlong);
		for(Road item: allroadssorted)
		{
			System.out.println(item.getRoadname()+ " lat: " + item.getlat()+ " long: " + item.getlong());
		}
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
				normaltime = (long) (normaltime + alljourneytime.get(i).getnormaltime());
				currenttime = (long) (currenttime
						+ alljourneytime.get(i).getcurrenttime());

			}
			delayedtime = currenttime - normaltime;
		}
		if (delayedtime > 0) {
			journeyinfo = "Current delay is: " + String.valueOf(TimeUnit.SECONDS.toMinutes(delayedtime));
		} else {
			// Delay
			journeyinfo = "No Major delay! \n";
		}
		return journeyinfo;
	}

	public ArrayList<String> getdelayedjuctions(
			ArrayList<Journeytime> alljourneytime, String road) {
		// This function will return the infomation on affect juctions
		ArrayList<String> roadinfo = new ArrayList<String>();
		for (int i = 0; i < alljourneytime.size(); i++) {

			if (alljourneytime.get(i).delayed() == true
					&& alljourneytime.get(i).getroad().equalsIgnoreCase(road) == true) {
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

	public String getcorrectdelay(Journeytime journeytime) {
		// This will return the correct amount of time the delay
		if (journeytime.delayed()) {
			long delayedtime = (long) (journeytime.getcurrenttime()
					- journeytime.getnormaltime());

			return String.valueOf(TimeUnit.SECONDS.toMinutes(delayedtime));
		}
		return null;
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

	public ArrayList<String> getroads(ArrayList<Incident> Journeys) {
		// This will collect the journey times on the spades.
		ArrayList<String> roads = new ArrayList<String>();
		ArrayList<Road> allroads = new ArrayList<Road>();
		for (int i = 0; i < Journeys.size(); i++) {
			for (int i2 = i + 1; i2 < Journeys.size(); i2++) {
				if (Journeys.get(i).getroad()
						.equals(Journeys.get(i2).getroad())
						& i != i2) {

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
	
	public ArrayList<Road>getroadsasclass(ArrayList<Incident> Journeys)
	{
		// This will collect the journey times on the spades.
		ArrayList<String> roads = new ArrayList<String>();
		ArrayList<Road> allroads = new ArrayList<Road>();
		for (int i = 0; i < Journeys.size(); i++) {
			for (int i2 = i + 1; i2 < Journeys.size(); i2++) {
				if (Journeys.get(i).getroad()
						.equals(Journeys.get(i2).getroad())
						& i != i2) {

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

	public ArrayList<Journeytime> getinrangejourneytime(ArrayList<Journeytime> all,double tolong, double fromlong, double tolat, double fromlat)
	{
		ArrayList<Journeytime> inrangejourneys = new ArrayList<Journeytime>();
		for(int i = 0; i < all.size(); i++)
		{
			if(all.get(i).inrange(tolong, fromlong, tolat,fromlat) == true)
			{
				inrangejourneys.add(all.get(i));
			}
		}
		
		
		return inrangejourneys;
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

	public static boolean checkifroadadded(ArrayList<String> Road,
			String Roadname) {
		for (int i = 0; i < Road.size(); i++) {
			if (Road.get(i).equalsIgnoreCase(Roadname)) {
				return true;
			}
		}
		return false;

	}
}

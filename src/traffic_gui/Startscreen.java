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

import javax.swing.*;

import traffic_analyze.Incident;
import traffic_analyze.Journeytime;
import traffic_input.JourneyTime_input;
import traffic_input.Postcodeinput;
import traffic_input.traffic_input;


public class Startscreen implements ActionListener {

	public JPanel postcodepanel,setcordpanel,main,sp;
	public JTextField tfpostcode1 , tfpostcode2, townname,townlat,townlong;
	public JTextArea output;
	public  JFrame frame;
	public JPanel combopanel;
	public  JScrollPane Scrollpane;
	public JComboBox trafficlist;
	public  ArrayList<String> Towninfo,smalltitle,fulltitle,fulldesc;
	public  Boolean Toaddress;
	public  Double tolat,fromlat,tolong,fromlong;
	public JLabel lpostcode1,lpostcode2,info,JLTitle,JLDesc;
	

	public static void main(String[] args) {
		//This will display the starting screen for the program with input of postcodes.
		Startscreen screen = new Startscreen();
	}
	public Startscreen()
	{
		super();
		//Will open the start screen 
		Toaddress = true;
		startlayout();
	}
	public void startlayout()
	{
		//This will display the starting screen and ask the user to pick what mode they would like.
		JFrame frame = new JFrame("Pick Mode");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainpanel = new JPanel();
        //Setting up buttons and there listner 
        JButton bOnline = new JButton("Online Mode");
        JButton bOffline = new JButton("Offline Mode");
        JButton testfeature = new JButton("Test Feature");
        bOnline.addActionListener(new ActionListener() {
      	  @Override
		public void actionPerformed(ActionEvent actionEvent) {
      		  	onlinelayout();
    		  }
    		});
        bOffline.addActionListener(new ActionListener() {
        	  @Override
			public void actionPerformed(ActionEvent actionEvent) {
        		  offlinelayout();
      		  }
      		});
        testfeature.addActionListener(new ActionListener() {
        	  @Override
			public void actionPerformed(ActionEvent actionEvent) {
        		 //Input test function and call methords 
        		  JourneyTime_input.getJourneys(false);
      		  }
      		});
        //Adding Buttons to panel then adding panel to frame
        mainpanel.add(bOnline);
        mainpanel.add(bOffline);
        mainpanel.add(testfeature);
        frame.add(mainpanel, BorderLayout.CENTER);
        //Sets the size of the Frame
        frame.setSize(200, 150);
        frame.setLocationRelativeTo(null);
        //Shows the frame
        frame.setVisible(true);
	}
	public void onlinelayout()
	{
		//This will display the online part of the program. 
		JFrame frame = new JFrame("Online Mode");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainpanel = new JPanel();
        //Setting up the postcode panel
        postcodepanel = new JPanel();
        BoxLayout layout = new BoxLayout(postcodepanel, BoxLayout.X_AXIS);
        postcodepanel.setLayout(layout);
        //Adding the Postcode Labels and TextFields
        lpostcode1 = new JLabel("Postcode of starting point: ");
        tfpostcode1 = new JTextField();
        lpostcode2 = new JLabel("Postcode of end point: ");
        tfpostcode2 = new JTextField();
        lpostcode1.setLabelFor(tfpostcode1);
        lpostcode2.setLabelFor(tfpostcode2);
        //Adding them to the panel
        postcodepanel.add(lpostcode1);
        postcodepanel.add(tfpostcode1);
        postcodepanel.add(lpostcode2);
        postcodepanel.add(tfpostcode2);
        //Setting it to the top
        frame.add(postcodepanel, BorderLayout.PAGE_START);
        frame.add(mainpanel, BorderLayout.CENTER);
        //Sets the size of the Frame
        frame.setExtendedState(Frame.MAXIMIZED_BOTH); 
        //Shows the frame
        frame.setVisible(true);
	}
	public  String[] createarea(String postcode1 , String postcode2)
	{
		//Will get the lang and long and then will split it off to ints
		String temp = Postcodeinput.getcords(postcode1);
		System.out.println(temp);
		String[] postcode1cords = temp.split(",");
		int postcode1long = Integer.parseInt(postcode1cords[0]);
		int postcode1lang = Integer.parseInt(postcode1cords[1]);
		
		//Will get the lang and long and then will split it off to ints
		temp = Postcodeinput.getcords(postcode2);
		String[] postcode2cords = temp.split(",");
		System.out.println(temp);
		int postcode2long = Integer.parseInt(postcode2cords[0]);
		int postcode2lang = Integer.parseInt(postcode2cords[1]);
		//This will put it into a an array that will then be used for the area.
		String[] Areacords = new String[2];
		Areacords[0] = String.valueOf(postcode1lang) +","+String.valueOf(postcode1long);
		Areacords[1] = String.valueOf(postcode2lang) +","+String.valueOf(postcode2long);
		System.out.println(Areacords);
		
		return Areacords;
		
	}
	public ArrayList<String> readtowns()
	{
	
		try {
			//This class will read the file then will return 2 arrays one with towname|lat|long and one with just townname
			ArrayList<String> townnames = new ArrayList<String>();
			Towninfo = new ArrayList<String>();
			//Setting up the files and the readers.
			File townfile = new File("Offlinetowns.txt");
			BufferedReader in = new BufferedReader(new FileReader(townfile));
			String nextline = in.readLine();
			//Will keep reading the next line till there is no more lines to read
			while(nextline != null)
			{
				//Each time it reads a new line it splits the line and then adds to the array. 
				try
				{
				Towninfo.add(nextline);
				String[] parts = nextline.split(",");
				System.out.println(parts[0] + ","+parts[1]);
				townnames.add(parts[0]);
				nextline = in.readLine();
			
				}catch (Exception e) {
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
	public ArrayList<String> readalltowninfo()
	{
		try {
			//This class will read the file then will return 2 arrays one with towname|lat|long and one with just townname
			ArrayList<String> townnames = new ArrayList<String>();
			Towninfo = new ArrayList<String>();
			//Setting up the files and the readers.
			File townfile = new File("Offlinetowns.txt");
			BufferedReader in = new BufferedReader(new FileReader(townfile));
			String nextline = in.readLine();
			//Will keep reading the next line till there is no more lines to read
			while(nextline != null)
			{
				//Each time it reads a new line it splits the line and then adds to the array. 
				Towninfo.add(nextline);
				String[] parts = nextline.split(",");
				System.out.println(parts[0] + ","+parts[1]);
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
	public void offlinelayout()
	{
				//This will display the starting screen for the program with input of postcodes.
				JFrame frame = new JFrame("Offline Mode");
		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        //Setting up the panel of set cords for offline testing
		        setcordpanel = new JPanel();
		        
		        //This is a Scrollpane for the output of the data 
		        String[] liststartdata = {"Please pick a town"};
 		        trafficlist = new JComboBox(liststartdata);
 		        
		        combopanel = new JPanel();
		        output = new JTextArea();
		        output.setEditable(false);
		        output.setVisible(false);
		        Scrollpane = new JScrollPane(output);
		        combopanel.add(trafficlist);
		        BoxLayout setcordlayout = new BoxLayout(setcordpanel, BoxLayout.Y_AXIS);
		        setcordpanel.setLayout(setcordlayout);
		        info = new JLabel("     To:");
		        ArrayList<String> Towns = readtowns();
		        if(Towns == null)
		        {
		        	//Allows user to add towns due to no towns.  
		        	JButton addtown = new JButton("Add New Town");
		        	addtown.addActionListener(new ActionListener() {
		            	  @Override
						public void actionPerformed(ActionEvent actionEvent) {
		            		  	addtown();
		          		  }
		          		});
		        	setcordpanel.add(addtown);
		        }else
		        {
		        	 JButton addtown = new JButton("Add New Town");
			        	addtown.addActionListener(new ActionListener() {
			            	  @Override
							public void actionPerformed(ActionEvent actionEvent) {
			            		  	addtown();
			          		  }
			          		});
			        setcordpanel.add(info);
			        for(int i = 0; i < Towns.size(); i++)
			        {
			        	JButton temp = new JButton(Towns.get(i).toString());
			        	temp.addActionListener(this);
			        	setcordpanel.add(temp);
			        }
			      
		        	setcordpanel.add(addtown);
			        
		        }
		        frame.add(combopanel, BorderLayout.PAGE_START);
		        frame.add(Scrollpane, BorderLayout.CENTER);
		        frame.add(setcordpanel, BorderLayout.EAST);
		        //Sets the size of the Frame
		        //ONLINE AT http://stackoverflow.com/questions/11570356/jframe-in-full-screen-java
		        frame.setExtendedState(Frame.MAXIMIZED_BOTH); 
		        //Shows the frame
		        frame.setVisible(true);
	}
	public void addtown() {
		//Custom view allowing to enter Town Name and lat and long;
		frame = new JFrame("Add New Town");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		            		  if(townname.getText().length() > 0 && townlat.getText().length() > 0 && townlong.getText().length() > 0 )
		            		  {
		            		  	savetown(townname.getText(),townlat.getText(),townlong.getText());
		            		  	frame.setVisible(false);
		            		  	offlinelayout();
		            		  }else
		            		  {
		            			  JOptionPane.showMessageDialog(frame, "Please enter each field!","Thank You", JOptionPane.ERROR_MESSAGE);
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
		//This will add to the file which holds all of the saved town data 
		try {
			File townfile = new File("Offlinetowns.txt");
			ArrayList<String> Towns = readalltowninfo();
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(townfile)));
			for(String towninfo : Towns)
			{
				out.append(towninfo+"\n");
			}
			out.append(Townname +"," + TownLat +","+TownLong);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
			 //This will deal with the handling the to and from button pushes
			if(Toaddress == true)
			{
				//This will add the cords to the to address
				String[] address = getcordsoftown(e.getActionCommand());
				tolat = Double.valueOf(address[0]);
				tolong = Double.valueOf(address[1]);
		  		//Then it will set the ToAddress to false
		  		Toaddress = false;
		  		//Then will set the to: to from:
		  		info.setText("     From:");
				
			}else
			{
				//This means that it wants to get the from address and has the to address
				String[] address = getcordsoftown(e.getActionCommand());
				fromlat = Double.valueOf(address[0]);
				fromlong = Double.valueOf(address[1]);
				//Then it will set the ToAddress to true
		  		Toaddress = true;
		  		//Then will set the From: to To:
		  		info.setText("     To:");
				traffic_input.gettraffic(true);
       		 	final ArrayList<Incident> incidentlist = traffic_input.allincidents;
       		 	smalltitle = new ArrayList<String>();
       		 	fulltitle = new ArrayList<String>();
       		 	fulldesc = new ArrayList<String>();
       		    for(int i = 0; i < incidentlist.size(); i++)
       		    {
       		    	
       		    	//This will use the function inrange and if true will add the titles to a JList 
       		    	//Then set full titles and descrtion in an other array
       		    	if(incidentlist.get(i).inrange(tolong,fromlong,tolat, fromlat) == true)
       		    	{
       		    		smalltitle.add(incidentlist.get(i).getsmalltitle());
       		    		fulltitle.add(incidentlist.get(i).gettitle());
       		    		fulldesc.add(incidentlist.get(i).getdesc());
       		    	}else
       		    	{

       	
       		    	}
       		    	
       		    	
       		    }
       		    String[] smalltitlelist = new String[smalltitle.size()];
       		    smalltitlelist = smalltitle.toArray(smalltitlelist);
       		    final DefaultComboBoxModel model = (DefaultComboBoxModel) trafficlist.getModel();
       		    model.removeAllElements();
       		    for(String item: getroads(incidentlist))
       		    {
       		    	if(item.equalsIgnoreCase("Error"))
       		    	{
       		    		item = "Other";
       		    	}
       		    	model.addElement(item);
       		    }
       		    trafficlist.setModel(model);
       		    trafficlist.addActionListener(new ActionListener() {
	            	  @Override
					public void actionPerformed(ActionEvent actionEvent) {
	            		JComboBox traffic = (JComboBox) actionEvent.getSource();
	            		ArrayList<String> allroadincidents = roadbyname(incidentlist,model.getElementAt(traffic.getSelectedIndex()).toString());
	            		output.setVisible(true);
	            		output.setText(" ");
	            		for(String item: allroadincidents)	
	            		{
	            			String[] items = new String[2];
	           		    	items = item.split("#");
	            			output.append(items[0] +"\n");
	            			output.append("                 "+items[1] +"\n");
	            			output.append("    \n");
	            		}
	            			output.append(getjourneytimedata(model.getElementAt(traffic.getSelectedIndex()).toString()));
	            		
	            		
	            	  }

					
	          		});
       		  }
       	
		 }
	public String[] getcordsoftown(String intown) {
			//Returns the cords of the town 
			
			for(int i = 0; i < Towninfo.size(); i++)
			{
				if(Towninfo.get(i).contains(intown))
				{
					String[] arrayparts = Towninfo.get(i).split(",");
					String[] address = new String[2];
					address[0]= arrayparts[1];
					address[1]= arrayparts[2];
					return address;
				}
			}
			return null;
		}
	public String getjourneytimedata(String road) {
		//This will get a list of all the journey time data and return the data based on the road 
		ArrayList<Journeytime> alljourneytime = JourneyTime_input.getJourneys(false);
		String journeyinfo;
		double normaltime = 0;
		double currenttime = 0;
		double deleyedtime = 0;
		for(int i = 0; i < alljourneytime.size(); i++)
		{
			
			if(alljourneytime.get(i).gettoroad().equalsIgnoreCase(road))
			{
				normaltime = normaltime + alljourneytime.get(i).getnormaltime();
				currenttime = currenttime + alljourneytime.get(i).getcurrenttime();
				
				
			}
			deleyedtime =  currenttime - normaltime; 
		}
		if(deleyedtime > 0)
		{
		journeyinfo = "Current deley is: " + String.valueOf(deleyedtime);
		}
		else
		{
		journeyinfo = "No deley!!";
		}
		return journeyinfo;
	}
	 
public static ArrayList<String> getroads(ArrayList<Incident> Journeys)
{
	//This will collect the journey times on the spades.
	ArrayList<String> roads = new ArrayList<String>();
	for(int i = 0; i < Journeys.size(); i++)
	{
		for(int i2 = i+1;  i2 < Journeys.size(); i2++)
		{
			if(Journeys.get(i).getroad().equals(Journeys.get(i2).getroad()) & i != i2)
			{
				
				
				if(checkifroadadded(roads,Journeys.get(i).getroad()) == false)
				{
					//Adding Road 
					roads.add(Journeys.get(i).getroad());
					System.out.println("Road Added:" + Journeys.get(i).getroad());
				}
				
			}
		}
	}
	return roads;
	
}
public static ArrayList<String> roadbyname(ArrayList<Incident> Journeys,String name)
{
	ArrayList<String> roadinfo = new ArrayList<String>();
	for(int i = 0; i < Journeys.size(); i++)
	{
		if(Journeys.get(i).getroad().equalsIgnoreCase(name))
		{
			roadinfo.add(Journeys.get(i).gettitle()+" # " +Journeys.get(i).getdesc());
		}
	}
	return roadinfo;
}
public static boolean checkifroadadded(ArrayList<String>Road, String Roadname)
{
	for(int i = 0; i < Road.size(); i++)
	{
		if(Road.get(i).equalsIgnoreCase(Roadname))
		{
			return true;
		}
	}
	return false;
	
}
}



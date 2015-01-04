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
import traffic_input.Journey_input;
import traffic_input.Postcodeinput;
import traffic_input.traffic_input;


public class Startscreen implements ActionListener {

	public static JPanel postcodepanel,setcordpanel,main;
	public static JTextField tfpostcode1 , tfpostcode2, townname,townlat,townlong;
	public static JTextArea output;
	public static JFrame frame;
	public static JScrollPane sp;
	public static ArrayList<String> Towninfo;
	public static Boolean Toaddress;
	public static Double tolat,fromlat,tolong,fromlong;
	public static JLabel lpostcode1,lpostcode2,info;
	public static void main(String[] args) {
		//This will display the starting screen for the program with input of postcodes.
		Toaddress = true;
		Startscreen screen = new Startscreen();
		
      
	}
	public Startscreen()
	{
		super();
		startlayout();
		//layout();
	}
	public void startlayout()
	{
		//This will display the starting screen for the program with input of postcodes.
		JFrame frame = new JFrame("Pick Mode");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainpanel = new JPanel();
        JButton bOnline = new JButton("Online Mode");
        JButton bOffline = new JButton("Offline Mode");
        bOnline.addActionListener(new ActionListener() {
      	  public void actionPerformed(ActionEvent actionEvent) {
      		  	onlinelayout();
    		  }
    		});
        bOffline.addActionListener(new ActionListener() {
        	  public void actionPerformed(ActionEvent actionEvent) {
        		  offlinelayout();
      		  }
      		});
        JButton testfeature = new JButton("Test Feature");
        testfeature.addActionListener(new ActionListener() {
        	  public void actionPerformed(ActionEvent actionEvent) {
        		 //Input test function and call methords 
        		  Journey_input.getJourneys(true);
      		  }
      		});
        mainpanel.add(bOnline);
        mainpanel.add(bOffline);
        mainpanel.add(testfeature);
        frame.add(mainpanel, BorderLayout.CENTER);
        //Sets the size of the Frame
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        //Shows the frame
        frame.setVisible(true);
	}
	public void onlinelayout()
	{
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
        JLabel info = new JLabel("This would be the online section");
        frame.add(mainpanel, BorderLayout.CENTER);
        //Sets the size of the Frame
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
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
			File townfile = new File("Offlinetowns.txt");
			BufferedReader in = new BufferedReader(new FileReader(townfile));
			String nextline = in.readLine();
			while(nextline != null)
			{
				Towninfo.add(nextline);
				String[] parts = nextline.split(",");
				System.out.println(parts[0] + ","+parts[1]);
				townnames.add(parts[0]);
				nextline = in.readLine();
			}
			return townnames;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
		        output = new JTextArea();
		        sp = new JScrollPane(output);
		        output.setEditable(false);
		        BoxLayout layout = new BoxLayout(postcodepanel, BoxLayout.X_AXIS);
		        BoxLayout setcordlayout = new BoxLayout(setcordpanel, BoxLayout.Y_AXIS);
		        setcordpanel.setLayout(setcordlayout);
		        info = new JLabel("     To:");
		        ArrayList<String> Towns = readtowns();
		        if(Towns == null)
		        {
		        	//Allows user to add towns due to no towns.  
		        	JButton addtown = new JButton("Add New Town");
		        	addtown.addActionListener(new ActionListener() {
		            	  public void actionPerformed(ActionEvent actionEvent) {
		            		  	addtown();
		          		  }
		          		});
		        	setcordpanel.add(addtown);
		        }else
		        {
		        	 JButton addtown = new JButton("Add New Town");
			        	addtown.addActionListener(new ActionListener() {
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
		        frame.add(sp, BorderLayout.CENTER);
		        frame.add(setcordpanel, BorderLayout.EAST);
		        //Sets the size of the Frame
		        //ONLINE AT http://stackoverflow.com/questions/11570356/jframe-in-full-screen-java
		        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
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
		            	  public void actionPerformed(ActionEvent actionEvent) {
		            		  if(townname.getText().length() > 0 && townlat.getText().length() > 0 && townlong.getText().length() > 0 )
		            		  {
		            		  	savetown(townname.getText(),townlat.getText(),townlong.getText());
		            		  	frame.setVisible(false);
		            		  }else
		            		  {
		            			  JOptionPane.showMessageDialog(frame, "Please enter each field!","Thank You", JOptionPane.ERROR_MESSAGE);
		            		  }
		          		  }
		          		});
        Cancel.addActionListener(new ActionListener() {
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
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(townfile)),true);
			out.append(Townname +"," + TownLat +","+TownLong+",\n");
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void actionPerformed(ActionEvent e) {
			 //This will deal with the handling the to and from button pushes
			if(Toaddress == true)
			{
				output.setText(" ");
				output.append("Journey to " + e.getActionCommand());
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
				output.append(" from "+ e.getActionCommand() + "\n");
				//This means that it wants to get the from address and has the to address
				String[] address = getcordsoftown(e.getActionCommand());
				fromlat = Double.valueOf(address[0]);
				fromlong = Double.valueOf(address[1]);
				//Then it will set the ToAddress to true
		  		Toaddress = true;
		  		//Then will set the From: to To:
		  		info.setText("     To:");
				traffic_input.gettraffic(true);
       		 	ArrayList<Incident> incidentlist = traffic_input.allincidents;
       		    for(int i = 0; i < incidentlist.size(); i++)
       		    {
       		    	
       		    	//REMEMBER TO CHANGE THIS TO VARS
       		    	if(incidentlist.get(i).inrange(tolong,fromlong,tolat, fromlat) == true)
       		    	{
       		    		output.append("Title: " + incidentlist.get(i).gettitle());
       		    		output.append("\n");
       		    		output.append("Desc: " + incidentlist.get(i).getdesc());
       		    		output.append("\n");
       		    		output.append(" ");
       		    		output.append("\n");
       		    	}else
       		    	{

       	
       		    	}
       		    	
       		    	
       		    }
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

	 }


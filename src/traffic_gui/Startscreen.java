package traffic_gui;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;

import traffic_analyze.Incident;
import traffic_analyze.Postcode_Processing;
import traffic_input.Journey_input;
import traffic_input.Postcodeinput;
import traffic_input.traffic_input;


public class Startscreen implements ActionListener {

	public static JPanel postcodepanel,setcordpanel;
	public static JTextField tfpostcode1 , tfpostcode2;
	public static JTextArea output;
	public static JScrollPane sp;
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
        
        mainpanel.add(bOnline);
        mainpanel.add(bOffline);
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
		        JButton bElgin = new JButton("Elgin");
		        JButton bDunblane = new JButton("Dunblane");
		        JButton bStirling = new JButton("Stirling");
		        JButton bGlasgow = new JButton("Glasgow");
		        JButton bEdinburgh = new JButton("Edinburgh");
		        JButton bLiverpool = new JButton("Liverpool");
		        JButton bManchester = new JButton("Manchester");
		        JButton bBirmingham = new JButton("Birmingham");
		        JButton bBristol = new JButton("Bristol");
		        JButton bLondon = new JButton("London");
		        JButton bPlymouth = new JButton("Plymouth");
		        bDunblane.addActionListener(this);
		        bElgin.addActionListener(this);
		        bStirling.addActionListener(this);
		        bGlasgow.addActionListener(this);
		        bEdinburgh.addActionListener(this);
		        bLiverpool.addActionListener(this);
		        bManchester.addActionListener(this);
		        bBirmingham.addActionListener(this);
		        bBristol.addActionListener(this);
		        bLondon.addActionListener(this);
		        bPlymouth.addActionListener(this);
		        setcordpanel.add(info);
		        setcordpanel.add(bElgin);
		        setcordpanel.add(bDunblane);
		        setcordpanel.add(bStirling);
		        setcordpanel.add(bGlasgow);
		        setcordpanel.add(bEdinburgh);
		        setcordpanel.add(bLiverpool);
		        setcordpanel.add(bManchester);
		        setcordpanel.add(bBirmingham); 
		        setcordpanel.add(bBristol);
		        setcordpanel.add(bLondon); 
		        setcordpanel.add(bPlymouth);
		        frame.add(sp, BorderLayout.CENTER);
		        frame.add(setcordpanel, BorderLayout.EAST);
		        //Sets the size of the Frame
		        //ONLINE AT http://stackoverflow.com/questions/11570356/jframe-in-full-screen-java
		        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		        //Shows the frame
		        frame.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
			 //This will deal with the handling the to and from button pushes
			if(Toaddress == true)
			{
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
			System.out.println(intown);
			if(intown.equalsIgnoreCase("Elgin"))
			{
				//Lat Long
				String[] address = new String[2];
				address[0]="57.646452";
				address[1]="-3.312664";
				return address;
			}else if(intown.equalsIgnoreCase("Dunblane"))
			{
				//Lat Long
				String[] address = new String[2];
				address[0]="56.183059";
				address[1]="-3.969917";
				return address;
			}
			else if(intown.equalsIgnoreCase("Stirling"))
			{
				//Lat Long
				String[] address = new String[2];
				address[0]="56.1172";
				address[1]="-3.9397";
				return address;
			}
			else if(intown.equalsIgnoreCase("Glasgow"))
			{
				//Lat Long
				String[] address = new String[2];
				address[0]="55.8580";
				address[1]="-4.2590";
				return address;
			}
			else if(intown.equalsIgnoreCase("Edinburgh"))
			{
				//Lat Long
				String[] address = new String[2];
				address[0]="55.9531";
				address[1]="-3.1889";
				return address;
			}
			else if(intown.equalsIgnoreCase("Liverpool"))
			{
				//Lat Long
				String[] address = new String[2];
				address[0]="53.4000";
				address[1]="-3";
				return address;
			}
			else if(intown.equalsIgnoreCase("Manchester"))
			{
				//Lat Long
				String[] address = new String[2];
				address[0]="53.4667";
				address[1]="-2.2333";
				return address;
			}
			else if(intown.equalsIgnoreCase("Birmingham"))
			{
				//Lat Long
				String[] address = new String[2];
				address[0]="52.4831";
				address[1]="-1.8936";
				return address;	
			}
			else if(intown.equalsIgnoreCase("Bristol"))
			{
				//Lat Long
				String[] address = new String[2];
				address[0]="51.4500";
				address[1]="-2.5833";
				return address;
			}
			else if(intown.equalsIgnoreCase("London"))
			{
				//Lat Long
				String[] address = new String[2];
				address[0]="52.5072";
				address[1]="-0.1275";
				return address;	
			}
			else if(intown.equalsIgnoreCase("Plymouth"))
			{
				//Lat Long
				String[] address = new String[2];
				address[0]="50.3714";
				address[1]="-4.1422";
				return address;
			}
			
			return null;
		}

	 }


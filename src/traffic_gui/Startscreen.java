package traffic_gui;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import traffic_analyze.Postcode_Processing;
import traffic_input.Postcodeinput;
import traffic_input.traffic_input;


public class Startscreen {

	public static JPanel postcodepanel;
	public static JTextField tfpostcode1 , tfpostcode2; 
	public static JLabel lpostcode1,lpostcode2;
	public static void main(String[] args) {
		//This will display the starting screen for the program with input of postcodes.
		layout();
      
	}
	public static String[] createarea(String postcode1 , String postcode2)
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
	public static void layout()
	{
		//This will display the starting screen for the program with input of postcodes.
				JFrame frame = new JFrame("Traffic Planer");
		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		        //Button and its listener 
		        JButton bchecktraffic = new JButton("Check Traffic");
		        bchecktraffic.addActionListener(new ActionListener() {
		        	  public void actionPerformed(ActionEvent actionEvent) {
		        		  traffic_input.gettraffic(true);
		        		 // System.out.println("Postcode1 :"+tfpostcode1.getText());
		        		   // System.out.println("Postcode2 :"+tfpostcode2.getText());
		        		  // String[] cords = Postcode_Processing.createarea(tfpostcode1.getText(),tfpostcode2.getText());
		        		  	
		        		    
		        		  }
		        		});

		        frame.add(bchecktraffic, BorderLayout.SOUTH);
		        //Sets the size of the Frame
		        frame.setSize(500, 300);
		        //Shows the frame
		        frame.setVisible(true);
	}
}

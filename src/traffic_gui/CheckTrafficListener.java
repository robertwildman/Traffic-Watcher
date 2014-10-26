package traffic_gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import traffic_analyze.Postcode_Processing;

public class CheckTrafficListener implements ActionListener {
	//This class will be the starting point of the checking traffic 
	//It will deal with the button click from the mainlayout
	public void actionPerformed(ActionEvent actionEvent) {
		String[] cords = Postcode_Processing.createarea(Startscreen.tfpostcode1.getText(),Startscreen.tfpostcode2.getText());
	  	System.out.println("Postcode1 :"+Startscreen.tfpostcode1.getText());
	    System.out.println("Postcode2 :"+Startscreen.tfpostcode2.getText());
		    System.out.println("I was selected.");
		  }
		}

